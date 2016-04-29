package io.datacleansing.common.query;

import java.util.HashMap;

public class QueryOptionsBuilder {
	private PagingParameters paging;
	private HashMap<String, String> filters = new HashMap<String, String>();
	
	private KeyValuePair<?> key_hash;
	private KeyValuePair<?> key_range;

	public QueryOptionsBuilder paging(long start, int limit) {
		this.paging = new PagingParameters(start, limit);
		return this;
	}

	public QueryOptionsBuilder filter(String param, String filter) {
		this.filters.put(param, filter);
		return this;
	}

	public QueryOptions create() {
		return new QueryOptions(paging, this.filters, key_hash, key_range);
	}

	public <T> QueryOptionsBuilder hashKey(String param, T value) {
		this.key_hash = new KeyValuePair<T>(param, value);
		return this;
	}

	public <T> QueryOptionsBuilder rangeKey(String param, T value) {
		this.key_range = new KeyValuePair<T>(param, value);
		return this;
	}
}
