package io.datacleansing.repo.representations;

import io.datacleansing.common.Constants;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

public class RepoMetadata {
	private String repository;
	private String id;
	private Boolean isPrivate;
	private String description;
	private String domain;
	private String datatype;
	private String locale;
	private String timestamp;
	private Set<String> keywords;

	@DynamoDBHashKey(attributeName=Constants.REPOSITORY)
	public String getRepository() {
		return repository;
	}
	public void setRepository(String repository) {
		this.repository = repository;
	}
	
	@DynamoDBRangeKey(attributeName=Constants.ID)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@DynamoDBAttribute(attributeName="IsPrivate")
	public Boolean getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
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
	public void setDatatype(String datatype) {
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
	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}

}
