package com.si.xe.trader.webui.restapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemDescriptior {

	private String username;
	private String symbol;
	private double amount;

	@JsonCreator
	public ItemDescriptior(@JsonProperty("username") String username,
			@JsonProperty("symbol") String symbol,
			@JsonProperty("amount") double amount) {
		this.username = username;
		this.symbol = symbol;
		this.amount = amount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
