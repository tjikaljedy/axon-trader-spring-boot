package com.si.xe.config;

import java.net.UnknownHostException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

@Configuration
@ImportResource({"classpath:/conf/axonframework-context.xml"})
public class AxonConfig {
	@Value("${mongodb.url}")
	private String databaseUrl;
	@Value("${mongodb.databaseName}")
	private String databaseName;
	@Value("${mongodb.username}")
	private String username;
	@Value("${mongodb.password}")
	private String password;
	
	@Bean
	@Profile("mongodb")
	public Mongo mongo() {
		try {
			ServerAddress serverAdress = new ServerAddress(databaseUrl, 27017);
			MongoCredential credential = MongoCredential
					.createMongoCRCredential(username, databaseName,
							password.toCharArray());
			Mongo mongo = new MongoClient(serverAdress,
					Arrays.asList(credential));
			mongo.setWriteConcern(WriteConcern.SAFE);
			return mongo;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Bean
	@Profile("mongodb")
	DB mongoDb() {
		return mongo().getDB(databaseName);
	}
}
