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

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.springframework.beans.factory.annotation.Autowired;

import com.si.xe.trader.api.orders.trades.OrderBookId;
import com.si.xe.trader.api.orders.trades.PortfolioId;
import com.si.xe.trader.api.orders.trades.TransactionId;

/**
 * @author Jettro Coenradie
 */
public abstract class TradeManagerSaga extends AbstractAnnotatedSaga {

    private transient CommandBus commandBus;
    private double totalItems;
    private double pricePerItem;
    private TransactionId transactionIdentifier;
    private OrderBookId orderbookIdentifier;
    private PortfolioId portfolioIdentifier;

    /*-------------------------------------------------------------------------------------------*/
    /* Getters and setters                                                                       */
    /*-------------------------------------------------------------------------------------------*/
    @Autowired
    public void setCommandBus(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    protected CommandBus getCommandBus() {
        return commandBus;
    }

    protected OrderBookId getOrderbookIdentifier() {
        return orderbookIdentifier;
    }

    protected void setOrderbookIdentifier(OrderBookId orderbookIdentifier) {
        this.orderbookIdentifier = orderbookIdentifier;
    }

    protected PortfolioId getPortfolioIdentifier() {
        return portfolioIdentifier;
    }

    protected void setPortfolioIdentifier(PortfolioId portfolioIdentifier) {
        this.portfolioIdentifier = portfolioIdentifier;
    }

    protected double getPricePerItem() {
        return pricePerItem;
    }

    protected void setPricePerItem(double pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    protected double getTotalItems() {
        return totalItems;
    }

    protected void setTotalItems(double totalItems) {
        this.totalItems = totalItems;
    }

    protected TransactionId getTransactionIdentifier() {
        return transactionIdentifier;
    }

    protected void setTransactionIdentifier(TransactionId transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
    }
}
