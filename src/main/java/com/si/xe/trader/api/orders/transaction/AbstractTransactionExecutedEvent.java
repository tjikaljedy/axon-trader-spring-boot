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

import com.si.xe.trader.api.orders.trades.TransactionId;

/**
 * @author Jettro Coenradie
 */
public abstract class AbstractTransactionExecutedEvent {
	private TransactionId transactionIdentifier;
	private double amountOfItems;
	private double itemPrice;

	public AbstractTransactionExecutedEvent(
			TransactionId transactionIdentifier, double amountOfItems,
			double itemPrice) {
		this.transactionIdentifier = transactionIdentifier;
		this.amountOfItems = amountOfItems;
		this.itemPrice = itemPrice;
	}

	public TransactionId getTransactionIdentifier() {
		return transactionIdentifier;
	}

	public double getAmountOfItems() {
		return amountOfItems;
	}

	public double getItemPrice() {
		return itemPrice;
	}
}
