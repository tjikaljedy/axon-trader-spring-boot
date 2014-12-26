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

import org.springframework.data.annotation.Id;

import com.si.xe.trader.api.orders.transaction.TransactionType;

import javax.persistence.Entity;

/**
 * @author Jettro Coenradie
 */
@Entity
public class TransactionEntry {

    @Id
    @javax.persistence.Id
    private String identifier;
    private String orderbookIdentifier;
    private String portfolioIdentifier;

    private String companyName;
    private double amountOfItems;
    private double amountOfExecutedItems;
    private double pricePerItem;
    private TransactionState state;
    private TransactionType type;

    public double getAmountOfExecutedItems() {
        return amountOfExecutedItems;
    }

    public void setAmountOfExecutedItems(double amountOfExecutedItems) {
        this.amountOfExecutedItems = amountOfExecutedItems;
    }

    public double getAmountOfItems() {
        return amountOfItems;
    }

    public void setAmountOfItems(double amountOfItems) {
        this.amountOfItems = amountOfItems;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getOrderbookIdentifier() {
        return orderbookIdentifier;
    }

    public void setOrderbookIdentifier(String orderbookIdentifier) {
        this.orderbookIdentifier = orderbookIdentifier;
    }

    public String getPortfolioIdentifier() {
        return portfolioIdentifier;
    }

    public void setPortfolioIdentifier(String portfolioIdentifier) {
        this.portfolioIdentifier = portfolioIdentifier;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(double pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
