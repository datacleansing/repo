package io.datacleansing.repo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.datacleansing.common.Constants;
import io.datacleansing.common.query.QueryOptions;
import io.datacleansing.common.query.QueryResult;
import io.datacleansing.common.rest.Utils;
import io.datacleansing.DataStore;
import io.datacleansing.repo.representations.Tag;

@Component
public class TagDAO {
	@Autowired
	DataStore hubRepo;

	public Tag update(String resUri, String tag, String dataUri) {
		Tag newTag = new Tag();
		newTag.setResourceURI(resUri);
		newTag.setId(tag);
		newTag.setDataURI(dataUri);
		return updateTag(newTag);
	}

	public Tag updateTag(Tag tag) {
		hubRepo.getMapper().save(tag);
		return get(tag.getResourceURI(), tag.getId());
	}

	public QueryResult<Tag> query(QueryOptions options) {
		QueryResult<Tag> result = new QueryResult<Tag>(options);
		result.setResultList(hubRepo.getMapper().query(
				Tag.class, options.<Tag> outputQueryExpression()));
		return result;
	}

	public Tag get(String resURI, String tagId) {
		QueryOptions options = QueryOptions.builder().
				hashKey(Constants.RESOURCE_URI, resURI).
				rangeKey(Constants.ID, tagId).
				paging(0, 1).
				create();
		QueryResult<Tag> result = query(options);
		if(result.getCount() == 1)
			return result.getResultList().get(0);
		else
			return null;
	}

	public Tag get(String repoId, String resId, String tagId) {
		return get(Utils.generateURI(repoId, resId), tagId);
	}

}
