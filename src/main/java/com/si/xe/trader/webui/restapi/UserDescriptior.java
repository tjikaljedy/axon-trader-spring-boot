package com.si.xe.trader.webui.restapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDescriptior {

	private String longname;
	private String username;
	private String userpass;

	@JsonCreator
	public UserDescriptior(@JsonProperty("longname") String longname,
			@JsonProperty("username") String username,
			@JsonProperty("userpass") String userpass) {
		this.longname = longname;
		this.username = username;
		this.userpass = userpass;
	}

	public String getLongname() {
		return longname;
	}

	public void setLongname(String longname) {
		this.longname = longname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpass() {
		return userpass;
	}

	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}

}
