package com.si.xe.trader.webui.restapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderDescriptior {
	private String username;
	private String symbol;
	private String side;
	private double quantity;
	private double price;

	@JsonCreator
	public OrderDescriptior(@JsonProperty("username") String username,
			@JsonProperty("symbol") String symbol,
			@JsonProperty("side") String side,
			@JsonProperty("quantity") double quantity,
			@JsonProperty("price") double price) {
		this.username = username;
		this.symbol = symbol;
		this.side = side;
		this.quantity = quantity;
		this.price = price;
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

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
