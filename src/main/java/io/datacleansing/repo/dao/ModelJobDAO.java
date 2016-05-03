package io.datacleansing.repo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.datacleansing.common.dao.AbstractDAO;
import io.datacleansing.common.rest.Utils;
import io.datacleansing.repo.representations.ModelJob;
import io.datacleansing.repo.representations.ModelMetadata;

@Component
public class ModelJobDAO extends AbstractDAO<ModelJob>{

	@Autowired
	TagDAO tagDAO;

	@Autowired
	ModelDAO modelDAO;
	
	public ModelJob get(ModelJob model) {
		return get(model.getRepository(), model.getId());
	}
	
	public ModelJob get(String hashKey, String rangeKey){
		ModelJob job = super.get(hashKey, rangeKey);
		String[] hashKeys = hashKey.split("/"); 
		ModelMetadata model = modelDAO.get(hashKeys[0], hashKeys[1]);
		job.setDescription(model.getDescription());
		job.setDomain(model.getDomain());
		job.setDatatype(model.getDatatype());
		job.setLocale(model.getLocale());
		return job;
	}

	@Override
	protected Class<ModelJob> getDomainClass() {
		return ModelJob.class;
	}

	public ModelJob update(String repoId, String modelId, String tagId,
			String algorithm) {
		StringBuffer buf = new StringBuffer(algorithm);
		buf.append(":");
		buf.append(tagDAO.get(repoId, modelId, tagId).getDataURI());
		
		ModelJob job = new ModelJob();
		job.setData(buf.toString());
		job.setRepository(Utils.generateURI(repoId, modelId));
		job.setId(tagId);
		
		return super.update(job);
	}

}
