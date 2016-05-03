package io.datacleansing.common.dao;

import org.springframework.beans.factory.annotation.Autowired;

import io.datacleansing.common.Constants;
import io.datacleansing.common.query.QueryOptions;
import io.datacleansing.common.query.QueryResult;
import io.datacleansing.DataStore;

public abstract class AbstractDAO<T> {
	@Autowired
	DataStore hubRepo;
	
	protected String getHashKey(){
		return Constants.REPOSITORY;
	}
	protected String getRangeKey(){
		return Constants.ID;
	}

	protected abstract Class<T> getDomainClass();
	public abstract T get(T queryObj);

	public QueryResult<T> query(QueryOptions options) {
		QueryResult<T> result = new QueryResult<T>(options);
		result.setResultList(hubRepo.getMapper().query(
				getDomainClass(), options.<T> outputQueryExpression()));
		return result;
	}
	
	public T get(String hashKeyValue, String rangeKeyValue) {
		QueryOptions options = QueryOptions.builder().
				hashKey(Constants.REPOSITORY, hashKeyValue).
				rangeKey(Constants.ID, rangeKeyValue).
				paging(0, 1).
				create();
		QueryResult<T> result = query(options);
		if(result.getCount() == 1)
			return result.getResultList().get(0);
		else
			return null;
	}

	public void delete(T target) {
        hubRepo.getMapper().delete(target);
	}

	public T update(T obj) {
		hubRepo.getMapper().save(obj);
		return get(obj);
	}

}
