package io.datacleansing.common.rest.representations;

import java.io.Serializable;
import java.util.List;

public class ResourceCollection<T> implements Serializable{
	private static final long serialVersionUID = -1255961657016269938L;
	
	public static final String MEDIA_TYPE_BASE_VALUE = "application/vnd.datacleansing.collection";
    public static final String MEDIA_TYPE_JSON_VALUE = MEDIA_TYPE_BASE_VALUE + "+json";
    
    public static final String COLLECTION_NAME = "items";
    

    private List<Link> links;

    private String name = COLLECTION_NAME, accept;

    private Long start, count;

    private List<T> items;

    private Integer limit;
    
    private int version;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
    

}
