package com.si.xe.trader.webui.restapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MoneyDescriptior {
	private String username;
	private long money;
	
	@JsonCreator
	public MoneyDescriptior(@JsonProperty("username")String username, @JsonProperty("money")long money) {
		this.username = username;
		this.money = money;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}

	
	
}
