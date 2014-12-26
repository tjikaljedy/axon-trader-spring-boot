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

package com.si.xe.trader.api.orders.trades;

/**
 * <p>Abstract parent class for all buy and sell order placed events.</p>
 *
 * @author Allard Buijze
 */
public abstract class AbstractOrderPlacedEvent {

    private final OrderBookId orderBookId;
    private final OrderId orderId;
    private TransactionId transactionId;
    private final double tradeCount;
    private final double itemPrice;
    private final PortfolioId portfolioId;

    protected AbstractOrderPlacedEvent(OrderBookId orderBookId, OrderId orderId, TransactionId transactionId,
                                       double tradeCount, double itemPrice, PortfolioId portfolioId) {
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.tradeCount = tradeCount;
        this.itemPrice = itemPrice;
        this.portfolioId = portfolioId;
        this.orderBookId = orderBookId;
    }

    public OrderBookId orderBookIdentifier() {
        return this.orderBookId;
    }

    public TransactionId getTransactionIdentifier() {
        return transactionId;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public double getTradeCount() {
        return tradeCount;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public PortfolioId getPortfolioId() {
        return portfolioId;
    }
}
