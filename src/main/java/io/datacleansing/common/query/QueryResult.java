package io.datacleansing.common.query;

import java.util.ArrayList;
import java.util.List;

public class QueryResult<T> {
	List<T> result = new ArrayList<T>();

	public int getCount() {
		return result.size();
	}

	public QueryOptions getQueryOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<T> getResultList() {
		return result;
	}

	public void addResult(T item) {
		result.add(item);
	}
}
