package io.datacleansing.repo.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

public class Utils {
	public static DynamoDBMapperConfig CONSISTENT_CONFIG = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
	
	public static String generateURI(String repo, String id){
		return repo + "/" + id;
	}
}
