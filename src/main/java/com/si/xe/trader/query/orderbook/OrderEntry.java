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

package com.si.xe.trader.query.orderbook;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

/**
 * @author Jettro Coenradie
 */
@Entity
public class OrderEntry {

    @javax.persistence.Id
    @GeneratedValue
    private Long jpaId;

    private String identifier;

    private double tradeCount;
    private double itemPrice;
    private String userId;
    private double itemsRemaining;
    private String type;

    public String getIdentifier() {
        return identifier;
    }

    void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getItemsRemaining() {
        return itemsRemaining;
    }

    void setItemsRemaining(double itemsRemaining) {
        this.itemsRemaining = itemsRemaining;
    }

    public double getTradeCount() {
        return tradeCount;
    }

    void setTradeCount(double tradeCount) {
        this.tradeCount = tradeCount;
    }

    public String getUserId() {
        return userId;
    }

    void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    public Long getJpaId() {
        return jpaId;
    }

    public void setJpaId(Long jpaId) {
        this.jpaId = jpaId;
    }
}
