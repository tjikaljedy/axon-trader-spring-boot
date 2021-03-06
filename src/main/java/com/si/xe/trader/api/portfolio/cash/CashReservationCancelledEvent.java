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

package com.si.xe.trader.api.portfolio.cash;

import com.si.xe.trader.api.orders.trades.PortfolioId;
import com.si.xe.trader.api.orders.trades.TransactionId;

/**
 * @author Jettro Coenradie
 */
public class CashReservationCancelledEvent {
    private PortfolioId portfolioIdentifier;
    private TransactionId transactionIdentifier;
    private double amountOfMoneyToCancel;

    public CashReservationCancelledEvent(PortfolioId portfolioIdentifier,
                                         TransactionId transactionIdentifier,
                                         double amountOfMoneyToCancel) {
        this.portfolioIdentifier = portfolioIdentifier;
        this.transactionIdentifier = transactionIdentifier;
        this.amountOfMoneyToCancel = amountOfMoneyToCancel;
    }

    public PortfolioId getPortfolioIdentifier() {
        return portfolioIdentifier;
    }

    public TransactionId getTransactionIdentifier() {
        return transactionIdentifier;
    }

    public double getAmountOfMoneyToCancel() {
        return amountOfMoneyToCancel;
    }
}
