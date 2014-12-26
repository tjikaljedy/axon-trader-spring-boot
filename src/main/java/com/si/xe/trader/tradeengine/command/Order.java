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

package com.si.xe.trader.tradeengine.command;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;

import com.si.xe.trader.api.orders.trades.OrderId;
import com.si.xe.trader.api.orders.trades.PortfolioId;
import com.si.xe.trader.api.orders.trades.TradeExecutedEvent;
import com.si.xe.trader.api.orders.trades.TransactionId;

/**
 * @author Allard Buijze
 */
class Order extends AbstractAnnotatedEntity {

    private OrderId orderId;
    private TransactionId transactionId;
    private final double itemPrice;
    private final double tradeCount;
    private final PortfolioId portfolioId;
    private double itemsRemaining;

    public Order(OrderId orderId, TransactionId transactionId, double itemPrice, double tradeCount, PortfolioId portfolioId) {
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.itemPrice = itemPrice;
        this.tradeCount = tradeCount;
        this.itemsRemaining = tradeCount;
        this.portfolioId = portfolioId;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public double getTradeCount() {
        return tradeCount;
    }

    public PortfolioId getPortfolioId() {
        return portfolioId;
    }

    public double getItemsRemaining() {
        return itemsRemaining;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    private void recordTraded(double tradeCount) {
        this.itemsRemaining -= tradeCount;
    }

    public TransactionId getTransactionId() {
        return transactionId;
    }

    @EventHandler
    protected void onTradeExecuted(TradeExecutedEvent event) {
        if (orderId.equals(event.getBuyOrderId())
                || orderId.equals(event.getSellOrderId())) {
            recordTraded(event.getTradeCount());
        }
    }
}
