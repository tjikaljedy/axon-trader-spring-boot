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

package com.si.xe.trader.api.orders.transaction;

import com.si.xe.trader.api.orders.trades.OrderBookId;
import com.si.xe.trader.api.orders.trades.PortfolioId;
import com.si.xe.trader.api.orders.trades.TransactionId;

/**
 * @author Jettro Coenradie
 */
public abstract class AbstractTransactionStartedEvent {
	private TransactionId transactionIdentifier;
	private OrderBookId orderbookIdentifier;
	private PortfolioId portfolioIdentifier;
	private double totalItems;
	private double pricePerItem;

	public AbstractTransactionStartedEvent(TransactionId transactionIdentifier,
			OrderBookId orderbookIdentifier, PortfolioId portfolioIdentifier,
			double totalItems, double pricePerItem) {
		this.transactionIdentifier = transactionIdentifier;
		this.orderbookIdentifier = orderbookIdentifier;
		this.portfolioIdentifier = portfolioIdentifier;
		this.totalItems = totalItems;
		this.pricePerItem = pricePerItem;
	}

	public OrderBookId getOrderbookIdentifier() {
		return orderbookIdentifier;
	}

	public PortfolioId getPortfolioIdentifier() {
		return portfolioIdentifier;
	}

	public TransactionId getTransactionIdentifier() {
		return transactionIdentifier;
	}

	public double getPricePerItem() {
		return pricePerItem;
	}

	public double getTotalItems() {
		return totalItems;
	}
}
