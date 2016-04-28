package io.datacleansing.repo.representations;

import java.util.Set;

import io.datacleansing.common.*;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="dch_model")
public class ModelMetadata {

	private String id;
	private String uri;
	private String name;
	private String description;
	private String domain;
	private String datatype;
	private String locale;
	private String timestamp;
	private Set<String> keywords;

  public ModelMetadata(
		String uri,
		String name,
		String description,
		String domain,
		String datatype,
		String locale,
		String timestamp,
		Set<String> keywords) {
		super();
		this.uri = uri;
		this.name = name;
		this.description = description;
		this.domain = domain;
		this.datatype = datatype;
		this.locale = locale;
		this.timestamp = timestamp;
		this.keywords = keywords;
	}

	@DynamoDBHashKey(attributeName="Id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

  @DynamoDBAttribute(attributeName="Uri")
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

  @DynamoDBAttribute(attributeName="Name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

  @DynamoDBAttribute(attributeName="Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

  @DynamoDBAttribute(attributeName="Domain")
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}

  @DynamoDBAttribute(attributeName="Datatype")
	public String getDatatype() {
		return datatype;
	}
	public void GetDatatype(String datatype) {
		this.datatype = datatype;
	}

  @DynamoDBAttribute(attributeName="Locale")
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}

  @DynamoDBAttribute(attributeName="Timestamp")
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

  @DynamoDBAttribute(attributeName="Keywords")
	public Set<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(Set<String> timestamp) {
		this.keywords = keywords;
	}

}
