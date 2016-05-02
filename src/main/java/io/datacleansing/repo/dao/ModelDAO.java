package io.datacleansing.repo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.datacleansing.common.Constants;
import io.datacleansing.common.query.QueryOptions;
import io.datacleansing.common.query.QueryResult;
import io.datacleansing.DataStore;
import io.datacleansing.repo.representations.ModelMetadata;

@Component
public class ModelDAO {
	@Autowired
	DataStore hubRepo;

	public ModelMetadata get(String repoId, String modelId) {
		QueryOptions options = QueryOptions.builder().
				hashKey(Constants.REPOSITORY, repoId).
				rangeKey(Constants.ID, modelId).
				paging(0, 1).
				create();
		QueryResult<ModelMetadata> result = query( options);
		if(result.getCount() == 1)
			return result.getResultList().get(0);
		else
			return null;
	}

	public void delete(ModelMetadata target) {
        hubRepo.getMapper().delete(target);
	}

	public ModelMetadata update(ModelMetadata model) {
		hubRepo.getMapper().save(model);
		return get(model.getRepository(), model.getId());
	}

	public QueryResult<ModelMetadata> query(QueryOptions options) {
		QueryResult<ModelMetadata> result = new QueryResult<ModelMetadata>(options);
		result.setResultList(hubRepo.getMapper().query(
				ModelMetadata.class, options.<ModelMetadata> outputQueryExpression()));
		return result;
	}

}
