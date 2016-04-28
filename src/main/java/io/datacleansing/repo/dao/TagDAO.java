package io.datacleansing.repo.dao;

import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import io.datacleansing.common.query.QueryOptions;
import io.datacleansing.common.query.QueryResult;
import io.datacleansing.repo.representations.Tag;

@Component
public class TagDAO {

	AmazonDynamoDBClient client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());

	DynamoDBMapper mapper = new DynamoDBMapper(client);

	public Tag get(String parentUri, String tag) {
		return null;
	}

	public void delete(String parentUri, String tag) {

	}

	public Tag update(String parentUri, String tag, String dataUri) {
		return null;
	}

	public QueryResult<Tag> query(String parentUri, QueryOptions options) {
		QueryResult<Tag> result =new QueryResult<Tag>();
		result.addResult(new Tag("MockParentUri", "MockName", "DMockataUri"));
		return result;
	}

}
