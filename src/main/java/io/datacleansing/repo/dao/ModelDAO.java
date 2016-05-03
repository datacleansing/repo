package io.datacleansing.repo.dao;

import org.springframework.stereotype.Component;

import io.datacleansing.common.dao.AbstractDAO;
import io.datacleansing.repo.representations.ModelMetadata;

@Component
public class ModelDAO extends AbstractDAO<ModelMetadata>{

	public ModelMetadata get(ModelMetadata model) {
		return get(model.getRepository(), model.getId());
	}

	@Override
	protected Class<ModelMetadata> getDomainClass() {
		return ModelMetadata.class;
	}

}
