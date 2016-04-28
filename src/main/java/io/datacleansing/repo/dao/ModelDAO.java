package io.datacleansing.repo.dao;

import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import io.datacleansing.common.query.QueryOptions;
import io.datacleansing.common.query.QueryResult;
import io.datacleansing.repo.representations.ModelMetadata;

@Component
public class ModelDAO {

	AmazonDynamoDBClient client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());

	DynamoDBMapper mapper = new DynamoDBMapper(client);
	
	public ModelMetadata getModel(String modelId) {
//		ModelMetadata item = new ModelMetadata(0, "MockName", "CI", "ADDRESS", "ZHCN", "2015");
//		mapper.save(item);
		
		return null;
	}

	public void deleteModel(String modelId) {
		// TODO Auto-generated method stub
		
	}

	public QueryResult<ModelMetadata> query(QueryOptions options) {
		QueryResult<ModelMetadata> result =new QueryResult<ModelMetadata>();
		HashSet<String> tags = new HashSet<String>();
		tags.add("tag1");
		tags.add("tag2");
		result.addResult(new ModelMetadata(0, "MockName", "CI", "ADDRESS", "ZHCN", "2015", tags));
		return result;
	}

}
