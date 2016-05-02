package io.datacleansing;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Component
public class DataStore {

	private AmazonDynamoDBClient client;
	private DynamoDBMapper mapper;

	@PostConstruct
	public void Init(){
		client = new AmazonDynamoDBClient(new EnvironmentVariableCredentialsProvider());
		client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
		mapper = new DynamoDBMapper(client);
	}

	public AmazonDynamoDBClient getClient() {
		return client;
	}

	public DynamoDBMapper getMapper() {
		return mapper;
	}
	
	
}
