package io.datacleansing.common.query;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;


public class QueryOptions {
	private PagingParameters paging;
	private HashMap<String, String> filters;
	private KeyValuePair<?> key_hash;
	private KeyValuePair<?> key_range;
	
	public QueryOptions(
			PagingParameters paging,
			HashMap<String, String> filters,
			KeyValuePair<?> key_hash, 
			KeyValuePair<?> key_range) {
		this.paging = paging;
		this.filters = filters;
		this.key_hash = key_hash;
		this.key_range = key_range;
	}

	public PagingParameters getPaging() {
		return paging;
	}

	public void setPaging(PagingParameters paging) {
		this.paging = paging;
	}
	
	public HashMap<String, String> getFilters() {
		return filters;
	}

	public void setFilters(HashMap<String, String> filters) {
		this.filters = filters;
	}

	public static QueryOptionsBuilder builder() {
		return new QueryOptionsBuilder();
	}

	public <T> DynamoDBQueryExpression<T> outputQueryExpression() {
		assert this.key_hash != null;
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		StringBuffer keyExpression = new StringBuffer();
		keyExpression.append(this.key_hash.getKey());
		keyExpression.append(" = :hashKeyValue");
		eav.put(":hashKeyValue", new AttributeValue().withS(this.key_hash.getValue().toString()));
		if(this.key_range != null){
			keyExpression.append(" and ");
			keyExpression.append(this.key_range.getKey());
			keyExpression.append(" = :rangeKeyValue");
			eav.put(":rangeKeyValue", new AttributeValue().withS(this.key_range.getValue().toString()));
		}

		DynamoDBQueryExpression<T> exp = new DynamoDBQueryExpression<T>()
	            .withKeyConditionExpression(keyExpression.toString())
	            .withExpressionAttributeValues(eav);
		return exp;
	}

}
