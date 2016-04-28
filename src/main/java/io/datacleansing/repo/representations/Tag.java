package io.datacleansing.repo.representations;

import java.util.Set;

import io.datacleansing.common.*;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="dch_tag")
public class Tag {
	private String id;

	private String parentUri;
	private String name;

	private String dataUri;

	public Tag(
		String parentUri,
		String name,
		String dataUri){
		this.parentUri = parentUri;
		this.name = name;
		this.dataUri = dataUri;
	}

	@DynamoDBHashKey(attributeName="Id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

  @DynamoDBAttribute(attributeName="DataUri")
	public String getDataUri() {
		return dataUri;
	}
	public void setDataUri(String dataUri) {
		this.dataUri = dataUri;
	}

  @DynamoDBAttribute(attributeName="ParentUri")
	public String getParentUri() {
		return parentUri;
	}
	public void setParentUri(String parentUri) {
		this.parentUri = parentUri;
	}

  @DynamoDBAttribute(attributeName="Name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
