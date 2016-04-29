package io.datacleansing.repo.representations;

import io.datacleansing.common.Constants;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="dch_tag")
public class Tag {	
	private String resourceURI;
	private String id;

	private String dataURI;
	
  @DynamoDBAttribute(attributeName=Constants.DATA_URI)
	public String getDataURI() {
		return dataURI;
	}
	public void setDataURI(String dataUri) {
		this.dataURI = dataUri;
	}

  @DynamoDBHashKey(attributeName=Constants.RESOURCE_URI)
	public String getResourceURI() {
		return resourceURI;
	}
	public void setResourceURI(String resourceURI) {
		this.resourceURI = resourceURI;
	}
	
	@DynamoDBRangeKey(attributeName=Constants.ID)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
