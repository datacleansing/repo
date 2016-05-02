package io.datacleansing.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.datacleansing.common.Constants;
import io.datacleansing.common.query.QueryOptions;
import io.datacleansing.common.query.QueryResult;
import io.datacleansing.service.representations.Service;
import io.datacleansing.DataStore;

@Component
public class ServiceDAO {
	@Autowired
	DataStore hubRepo;

	public Service get(String repoId, String svcId) {
		QueryOptions options = QueryOptions.builder().
				hashKey(Constants.REPOSITORY, repoId).
				rangeKey(Constants.ID, svcId).
				paging(0, 1).
				create();
		QueryResult<Service> result = query( options);
		if(result.getCount() == 1)
			return result.getResultList().get(0);
		else
			return null;
	}

	public void delete(Service target) {
        hubRepo.getMapper().delete(target);
	}

	public Service update(Service svc) {
		hubRepo.getMapper().save(svc);
		return get(svc.getRepository(), svc.getId());
	}

	public QueryResult<Service> query(QueryOptions options) {
		QueryResult<Service> result = new QueryResult<Service>(options);
		result.setResultList(hubRepo.getMapper().query(
				Service.class, options.<Service> outputQueryExpression()));
		return result;
	}

}
