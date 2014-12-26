package com.si.xe.trader.webui.restapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InstrumentDescriptior {

	private String username;
	private String symbol;
	private double contractMonth;
	private double contractRate;

	private String exchangeName;
	private String marketMaker;

	@JsonCreator
	public InstrumentDescriptior(@JsonProperty("username") String username,
			@JsonProperty("symbol") String symbol,
			@JsonProperty("contractmonth") double contractMonth,
			@JsonProperty("contractrate") double contractRate,
			@JsonProperty("exchangename") String exchangeName,
			@JsonProperty("marketmaker") String marketMaker) {
		this.username = username;
		this.symbol = symbol;
		this.contractMonth = contractMonth;
		this.contractRate = contractRate;
		this.exchangeName = exchangeName;
		this.marketMaker = marketMaker;
	}

	public String getUsername() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getContractMonth() {
		return contractMonth;
	}

	public void setContractMonth(double contractMonth) {
		this.contractMonth = contractMonth;
	}

	public double getContractRate() {
		return contractRate;
	}

	public void setContractRate(double contractRate) {
		this.contractRate = contractRate;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public String getMarketMaker() {
		return marketMaker;
	}

	public void setMarketMaker(String marketMaker) {
		this.marketMaker = marketMaker;
	}

}
