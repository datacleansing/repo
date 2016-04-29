package io.datacleansing.common.query;

import java.util.List;

public class QueryResult<T> {
	private List<T> result;
	private QueryOptions options;

	public QueryResult(QueryOptions options) {
		this.options = options;
	}

	public int getCount() {
		return result.size();
	}

	public QueryOptions getQueryOptions() {
		return this.options;
	}

	public void setResultList(List<T> result) {
		this.result = result;
	}
	public List<T> getResultList() {
		return result;
	}
}
