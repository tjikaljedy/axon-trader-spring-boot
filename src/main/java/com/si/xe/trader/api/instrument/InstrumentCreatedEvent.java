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

package com.si.xe.trader.api.instrument;

/**
 * <p>
 * A new company is created with a certain value and an amount of shares. Those
 * two values can be used to calculate the starting point for the value of a
 * share.
 * </p>
 *
 * @author Jettro Coenradie
 */
public class InstrumentCreatedEvent {
	private InstumentId instrumentId;
	private String instrumentName;
	private double contractMonth;
	private double contractRate;

	private String exchangeName;
	private String marketMaker;

	public InstrumentCreatedEvent(InstumentId instrumentId,
			String instrumentName, double contractMonth, double contractRate,
			String exchangeName, String marketMaker) {
		this.contractRate = contractMonth;
		this.instrumentName = instrumentName;
		this.contractMonth = contractRate;
		this.instrumentId = instrumentId;

		this.exchangeName = exchangeName;
		this.marketMaker = marketMaker;
	}

	public InstumentId getInstrumentIdentifier() {
		return this.instrumentId;
	}

	public double getContractRate() {
		return contractRate;
	}

	public String getInstrumentName() {
		return instrumentName;
	}

	public double getContractMonth() {
		return contractMonth;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public String getMarketMaker() {
		return marketMaker;
	}

}
