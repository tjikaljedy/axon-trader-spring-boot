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

package com.si.xe.trader.instrument.command;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import com.si.xe.trader.api.instrument.InstrumentCreatedEvent;
import com.si.xe.trader.api.instrument.InstumentId;
import com.si.xe.trader.api.instrument.OrderBookAddedToInstrumentEvent;
import com.si.xe.trader.api.orders.trades.OrderBookId;

/**
 * @author Jettro Coenradie
 */
class Instrument extends AbstractAnnotatedAggregateRoot {
	private static final long serialVersionUID = 8723320580782813954L;

	@AggregateIdentifier
	private InstumentId instrumentId;

	@SuppressWarnings("UnusedDeclaration")
	protected Instrument() {
	}

	public Instrument(InstumentId instrumentId, String name,
			double contractMonth, double contractRate, String exchangeName,
			String marketMaker) {
		apply(new InstrumentCreatedEvent(instrumentId, name, contractMonth,
				contractRate, exchangeName, marketMaker));
	}

	public void addOrderBook(OrderBookId orderBookId) {
		apply(new OrderBookAddedToInstrumentEvent(instrumentId, orderBookId));
	}

	@Override
	public InstumentId getIdentifier() {
		return this.instrumentId;
	}

	@EventHandler
	public void handle(InstrumentCreatedEvent event) {
		this.instrumentId = event.getInstrumentIdentifier();
	}
}
