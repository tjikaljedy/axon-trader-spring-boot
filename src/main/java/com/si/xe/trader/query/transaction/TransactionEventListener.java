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

package com.si.xe.trader.query.transaction;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.si.xe.trader.api.orders.transaction.*;
import com.si.xe.trader.query.orderbook.OrderBookEntry;
import com.si.xe.trader.query.orderbook.repositories.OrderBookQueryRepository;
import com.si.xe.trader.query.transaction.repositories.TransactionQueryRepository;

/**
 * @author Jettro Coenradie
 */
@Component
public class TransactionEventListener {

    private OrderBookQueryRepository orderBookQueryRepository;
    private TransactionQueryRepository transactionQueryRepository;

    @EventHandler
    public void handleEvent(BuyTransactionStartedEvent event) {
        startTransaction(event, TransactionType.BUY);
    }

    @EventHandler
    public void handleEvent(SellTransactionStartedEvent event) {
        startTransaction(event, TransactionType.SELL);
    }

    @EventHandler
    public void handleEvent(BuyTransactionCancelledEvent event) {
        changeStateOfTransaction(event.getTransactionIdentifier().toString(), TransactionState.CANCELLED);
    }

    @EventHandler
    public void handleEvent(SellTransactionCancelledEvent event) {
        changeStateOfTransaction(event.getTransactionIdentifier().toString(), TransactionState.CANCELLED);
    }

    @EventHandler
    public void handleEvent(BuyTransactionConfirmedEvent event) {
        changeStateOfTransaction(event.getTransactionIdentifier().toString(), TransactionState.CONFIRMED);
    }

    @EventHandler
    public void handleEvent(SellTransactionConfirmedEvent event) {
        changeStateOfTransaction(event.getTransactionIdentifier().toString(), TransactionState.CONFIRMED);
    }

    @EventHandler
    public void handleEvent(BuyTransactionExecutedEvent event) {
        executeTransaction(event);
    }

    @EventHandler
    public void handleEvent(SellTransactionExecutedEvent event) {
        executeTransaction(event);
    }

    @EventHandler
    public void handleEvent(BuyTransactionPartiallyExecutedEvent event) {
        partiallyExecuteTransaction(event);
    }

    @EventHandler
    public void handleEvent(SellTransactionPartiallyExecutedEvent event) {
        partiallyExecuteTransaction(event);
    }

    private void partiallyExecuteTransaction(AbstractTransactionPartiallyExecutedEvent event) {
        TransactionEntry transactionEntry = transactionQueryRepository.findOne(event.getTransactionIdentifier()
                .toString());

        double value = transactionEntry.getAmountOfExecutedItems() * transactionEntry.getPricePerItem();
        double additionalValue = event.getAmountOfExecutedItems() * event.getItemPrice();
        double newPrice = (value + additionalValue) / event.getTotalOfExecutedItems();

        transactionEntry.setState(TransactionState.PARTIALLYEXECUTED);
        transactionEntry.setAmountOfExecutedItems(event.getTotalOfExecutedItems());
        transactionEntry.setPricePerItem(newPrice);
        transactionQueryRepository.save(transactionEntry);
    }

    private void executeTransaction(AbstractTransactionExecutedEvent event) {
        TransactionEntry transactionEntry = transactionQueryRepository.findOne(event.getTransactionIdentifier()
                .toString());

        double value = transactionEntry.getAmountOfExecutedItems() * transactionEntry.getPricePerItem();
        double additionalValue = event.getAmountOfItems() * event.getItemPrice();
        double newPrice = (value + additionalValue) / transactionEntry.getAmountOfItems();

        transactionEntry.setState(TransactionState.EXECUTED);
        transactionEntry.setAmountOfExecutedItems(transactionEntry.getAmountOfItems());
        transactionEntry.setPricePerItem(newPrice);
        transactionQueryRepository.save(transactionEntry);
    }

    private void changeStateOfTransaction(String identifier, TransactionState newState) {
        TransactionEntry transactionEntry = transactionQueryRepository.findOne(identifier);
        transactionEntry.setState(newState);
        transactionQueryRepository.save(transactionEntry);
    }

    private void startTransaction(AbstractTransactionStartedEvent event, TransactionType type) {
        OrderBookEntry orderBookEntry = orderBookQueryRepository.findOne(event.getOrderbookIdentifier().toString());

        TransactionEntry entry = new TransactionEntry();
        entry.setAmountOfExecutedItems(0);
        entry.setAmountOfItems((int) event.getTotalItems());
        entry.setPricePerItem(event.getPricePerItem());
        entry.setIdentifier(event.getTransactionIdentifier().toString());
        entry.setOrderbookIdentifier(event.getOrderbookIdentifier().toString());
        entry.setPortfolioIdentifier(event.getPortfolioIdentifier().toString());
        entry.setState(TransactionState.STARTED);
        entry.setType(type);
        entry.setCompanyName(orderBookEntry.getCompanyName());

        transactionQueryRepository.save(entry);
    }

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setOrderBookQueryRepository(OrderBookQueryRepository orderBookQueryRepository) {
        this.orderBookQueryRepository = orderBookQueryRepository;
    }

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setTransactionQueryRepository(TransactionQueryRepository transactionQueryRepository) {
        this.transactionQueryRepository = transactionQueryRepository;
    }
}
