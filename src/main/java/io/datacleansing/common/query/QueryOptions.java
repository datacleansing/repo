package io.datacleansing.common.query;


public class QueryOptions {

	PagingParameters paging;

	public PagingParameters getPaging() {
		return paging;
	}

	public void setPaging(PagingParameters paging) {
		this.paging = paging;
	}
	
	public static QueryOptionsBuilder builder() {
		return new QueryOptionsBuilder();
	}

}
