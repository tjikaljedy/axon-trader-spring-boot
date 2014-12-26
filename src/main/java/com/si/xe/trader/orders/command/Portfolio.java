/*
 * Copyright (c) 2010-2012. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.si.xe.trader.orders.command;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import com.si.xe.trader.api.orders.trades.OrderBookId;
import com.si.xe.trader.api.orders.trades.PortfolioId;
import com.si.xe.trader.api.orders.trades.TransactionId;
import com.si.xe.trader.api.portfolio.PortfolioCreatedEvent;
import com.si.xe.trader.api.portfolio.cash.*;
import com.si.xe.trader.api.portfolio.stock.*;
import com.si.xe.trader.api.users.UserId;

import java.util.HashMap;
import java.util.Map;

/**
 * Not a lot of checks are available. We will check if you still have item before you reserve them. Other than that
 * we will not do checks. It is possible to give more items than you reserve.
 * <p/>
 * When buying items you need to reserve cash. Reservations need to be confirmed or cancelled. It is up to the user
 * to confirm and cancel the right amounts. The Portfolio does not keep track of it.
 *
 * @author Jettro Coenradie
 */
public class Portfolio extends AbstractAnnotatedAggregateRoot {
    private static final long serialVersionUID = 996371335141649977L;

    @AggregateIdentifier
    private PortfolioId portfolioId;
    private Map<OrderBookId, Double> availableItems = new HashMap<OrderBookId, Double>();
    private Map<OrderBookId, Double> reservedItems = new HashMap<OrderBookId, Double>();

    private double amountOfMoney;
    private double reservedAmountOfMoney;

    protected Portfolio() {
    }

    public Portfolio(PortfolioId portfolioId, UserId userIdentifier) {
        apply(new PortfolioCreatedEvent(portfolioId, userIdentifier));
    }

    public void addItems(OrderBookId orderBookIdentifier, double amountOfItemsToAdd) {
        apply(new ItemsAddedToPortfolioEvent(portfolioId, orderBookIdentifier, amountOfItemsToAdd));
    }

    public void reserveItems(OrderBookId orderBookIdentifier, TransactionId transactionIdentifier, double amountOfItemsToReserve) {
        if (!availableItems.containsKey(orderBookIdentifier)) {
            apply(new ItemToReserveNotAvailableInPortfolioEvent(portfolioId, orderBookIdentifier, transactionIdentifier));
        } else {
            Double availableAmountOfItems = availableItems.get(orderBookIdentifier);
            if (availableAmountOfItems < amountOfItemsToReserve) {
                apply(new NotEnoughItemsAvailableToReserveInPortfolio(
                        portfolioId, orderBookIdentifier, transactionIdentifier, availableAmountOfItems, amountOfItemsToReserve));
            } else {
                apply(new ItemsReservedEvent(portfolioId, orderBookIdentifier, transactionIdentifier, amountOfItemsToReserve));
            }
        }
    }

    public void confirmReservation(OrderBookId orderBookIdentifier, TransactionId transactionIdentifier,
    		double amountOfItemsToConfirm) {
        apply(new ItemReservationConfirmedForPortfolioEvent(
                portfolioId,
                orderBookIdentifier,
                transactionIdentifier,
                amountOfItemsToConfirm));
    }

    public void cancelReservation(OrderBookId orderBookIdentifier, TransactionId transactionIdentifier, double amountOfItemsToCancel) {
        apply(new ItemReservationCancelledForPortfolioEvent(
                portfolioId,
                orderBookIdentifier,
                transactionIdentifier,
                amountOfItemsToCancel));
    }

    public void addMoney(double moneyToAddInCents) {
        apply(new CashDepositedEvent(portfolioId, moneyToAddInCents));
    }

    public void makePayment(double amountToPayInCents) {
        apply(new CashWithdrawnEvent(portfolioId, amountToPayInCents));
    }

    public void reserveMoney(TransactionId transactionIdentifier, double amountToReserve) {
        if (amountOfMoney >= amountToReserve) {
            apply(new CashReservedEvent(portfolioId, transactionIdentifier, amountToReserve));
        } else {
            apply(new CashReservationRejectedEvent(portfolioId, transactionIdentifier, amountToReserve));
        }
    }

    public void cancelMoneyReservation(TransactionId transactionIdentifier, double amountOfMoneyToCancel) {
        apply(new CashReservationCancelledEvent(portfolioId, transactionIdentifier, amountOfMoneyToCancel));
    }

    public void confirmMoneyReservation(TransactionId transactionIdentifier, double amountOfMoneyToConfirm) {
        apply(new CashReservationConfirmedEvent(portfolioId, transactionIdentifier, amountOfMoneyToConfirm));
    }

    /* EVENT HANDLING */
    @EventHandler
    public void onPortfolioCreated(PortfolioCreatedEvent event) {
        this.portfolioId = event.getPortfolioId();
    }

    @EventHandler
    public void onItemsAddedToPortfolio(ItemsAddedToPortfolioEvent event) {
    	double available = obtainCurrentAvailableItems(event.getOrderBookIdentifier());
        availableItems.put(event.getOrderBookIdentifier(), available + event.getAmountOfItemsAdded());
    }

    @EventHandler
    public void onItemsReserved(ItemsReservedEvent event) {
    	double available = obtainCurrentAvailableItems(event.getOrderBookIdentifier());
        availableItems.put(event.getOrderBookIdentifier(), available - event.getAmountOfItemsReserved());

        double reserved = obtainCurrentReservedItems(event.getOrderBookIdentifier());
        reservedItems.put(event.getOrderBookIdentifier(), reserved + event.getAmountOfItemsReserved());
    }

    @EventHandler
    public void onReservationConfirmed(ItemReservationConfirmedForPortfolioEvent event) {
    	double reserved = obtainCurrentReservedItems(event.getOrderBookIdentifier());
        reservedItems.put(event.getOrderBookIdentifier(), reserved - event.getAmountOfConfirmedItems());
    }

    @EventHandler
    public void onReservationCancelled(ItemReservationCancelledForPortfolioEvent event) {
    	double reserved = obtainCurrentReservedItems(event.getOrderBookIdentifier());
        reservedItems.put(event.getOrderBookIdentifier(), reserved - event.getAmountOfCancelledItems());

        double available = obtainCurrentAvailableItems(event.getOrderBookIdentifier());
        availableItems.put(event.getOrderBookIdentifier(), available + event.getAmountOfCancelledItems());
    }

    @EventHandler
    public void onMoneyAddedToPortfolio(CashDepositedEvent event) {
        amountOfMoney += event.getMoneyAddedInCents();
    }

    @EventHandler
    public void onPaymentMadeFromPortfolio(CashWithdrawnEvent event) {
        amountOfMoney -= event.getAmountPaidInCents();
    }

    @EventHandler
    public void onMoneyReservedFromPortfolio(CashReservedEvent event) {
        amountOfMoney -= event.getAmountToReserve();
        reservedAmountOfMoney += event.getAmountToReserve();
    }

    @EventHandler
    public void onMoneyReservationCancelled(CashReservationCancelledEvent event) {
        amountOfMoney += event.getAmountOfMoneyToCancel();
        reservedAmountOfMoney -= event.getAmountOfMoneyToCancel();
    }

    @EventHandler
    public void onMoneyReservationConfirmed(CashReservationConfirmedEvent event) {
        reservedAmountOfMoney -= event.getAmountOfMoneyConfirmedInCents();
    }

    /* UTILITY METHODS */
    private double obtainCurrentAvailableItems(OrderBookId orderBookIdentifier) {
    	double available = 0;
        if (availableItems.containsKey(orderBookIdentifier)) {
            available = availableItems.get(orderBookIdentifier);
        }
        return available;
    }

    private double obtainCurrentReservedItems(OrderBookId orderBookIdentifier) {
    	double reserved = 0;
        if (reservedItems.containsKey(orderBookIdentifier)) {
            reserved = reservedItems.get(orderBookIdentifier);
        }
        return reserved;
    }

    @Override
    public PortfolioId getIdentifier() {
        return portfolioId;
    }
}
