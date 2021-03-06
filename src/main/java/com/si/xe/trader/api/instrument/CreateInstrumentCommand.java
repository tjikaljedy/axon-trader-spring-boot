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

import com.si.xe.trader.api.users.UserId;

/**
 * <p>
 * Create a new company by proving the name, the estimated value of the company
 * and the amount of shares that are available for the company. You also must
 * provide the id of the user that wants to create the company.
 * </p>
 *
 * @author Jettro Coenradie
 */
public class CreateInstrumentCommand {
	private InstumentId instrumentId;
	private UserId userId;
	private String instrumentName;
	private double contractMonth;
	private double contractRate;

	private String exchangeName;
	private String marketMaker;

	public CreateInstrumentCommand(InstumentId instrumentId, UserId userId,
			String instrumentName, double contractMonth, double contractRate,
			String exchangeName, String marketMaker) {
		this.instrumentId = instrumentId;
		this.contractRate = contractRate;
		this.instrumentName = instrumentName;
		this.contractMonth = contractMonth;
		this.userId = userId;

		this.exchangeName = exchangeName;
		this.marketMaker = marketMaker;
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

	public UserId getUserId() {
		return userId;
	}

	public InstumentId getInstrumentId() {
		return instrumentId;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public String getMarketMaker() {
		return marketMaker;
	}

}
