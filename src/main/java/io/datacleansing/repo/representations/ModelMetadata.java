package io.datacleansing.repo.representations;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="DataCleansing_model")
public class ModelMetadata {
	
	private Integer id;
	private String name;
	private String domain;
	private String datatype;
	private String locale;
	private String timestamp;
	private Set<String> tags;

    public ModelMetadata(Integer id, String name, String domain, String datatype, String locale, String timestamp, Set<String> tags) {
		super();
		this.id = id;
		this.name = name;
		this.domain = domain;
		this.datatype = datatype;
		this.locale = locale;
		this.timestamp = timestamp;
		this.tags = tags;
	}
    
	@DynamoDBHashKey(attributeName="Id")  
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

    @DynamoDBAttribute(attributeName="Name") 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	

    @DynamoDBAttribute(attributeName="Tags") 
	public Set<String> getTags() {
		return tags;
	}
	public void setTimestamp(Set<String> timestamp) {
		this.tags = tags;
	}
	
}
