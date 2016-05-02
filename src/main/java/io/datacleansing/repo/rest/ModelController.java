package io.datacleansing.repo.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.datacleansing.common.query.PagingParameters;
import io.datacleansing.common.query.QueryOptions;
import io.datacleansing.common.query.QueryResult;
import io.datacleansing.common.Constants;
import io.datacleansing.common.rest.RESTConstants;
import io.datacleansing.common.rest.Utils;
import io.datacleansing.common.rest.representations.CollectionLinksBuilder;
import io.datacleansing.common.rest.representations.ResourceCollection;
import io.datacleansing.common.rest.representations.ResourceCollectionBuilder;
import io.datacleansing.repo.dao.ModelDAO;
import io.datacleansing.repo.dao.TagDAO;
import io.datacleansing.repo.representations.ModelMetadata;
import io.datacleansing.repo.representations.Tag;

@RestController
@RequestMapping(value= RESTConstants.MODELS_URI)
public class ModelController {
	public static final String MODEL_URI =  "/{modelId}";
	public static final String MODEL_TAGS_URI = MODEL_URI + RESTConstants.TAGS_URI;
	public static final String MODEL_TAG_URI = MODEL_TAGS_URI + RESTConstants.TAG_URI;

	public static final String MODEL_JSON_VALUE = "application/vnd.datacleansing.model+json";
	public static final String MODEL_TAG_JSON_VALUE = "application/vnd.datacleansing.model.tag+json";

	@Autowired
	ModelDAO modelDAO;

	@Autowired
	TagDAO tagDAO;

	@RequestMapping( method = RequestMethod.POST )
	public ResponseEntity<ModelMetadata> createModel(
		HttpServletRequest request,
		@PathVariable String repoId,
		@RequestBody ModelMetadata model) {
		model.setRepository(repoId);
		ModelMetadata updatedModel = modelDAO.update(model);
		return new ResponseEntity<ModelMetadata>(updatedModel, HttpStatus.CREATED);
	}

	@RequestMapping( method = { RequestMethod.GET, RequestMethod.OPTIONS },
			produces = {
					ResourceCollection.MEDIA_TYPE_JSON_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResourceCollection<ModelMetadata>> getModels(
			HttpServletRequest request,
			@PathVariable String repoId,
			@RequestParam(
				value = RESTConstants.REQUEST_PARAM_START,
				defaultValue = PagingParameters.DEFAULT_PAGING_START + "",
				required = false) int start,
			@RequestParam(
				value = RESTConstants.REQUEST_PARAM_LIMIT,
				defaultValue = PagingParameters.DEFAULT_PAGING_LIMIT + "",
				required = false) int limit,
			@RequestParam(
				value = RESTConstants.REQUEST_PARAM_FILTER,
				required = false) String filter,
			@RequestParam(
				value = RESTConstants.REQUEST_PARAM_SORTBY,
				defaultValue = "name",
				required = false) String sortBy) {
		if (limit < PagingParameters.PAGING_LIMIT_MIN || limit > PagingParameters.PAGING_LIMIT_MAX){
			//handle bad request
		}

		QueryOptions options = QueryOptions.builder().
				paging(start, limit).
				hashKey(Constants.REPOSITORY, repoId).
				filter(RESTConstants.REQUEST_PARAM_FILTER, filter).
				create();
		QueryResult<ModelMetadata> result = modelDAO.query(options);
		ResourceCollection<ModelMetadata> collection = Utils.buildCollection(
				options,
				result.getResultList(),
				result.getCount(),
				"models",
				MODEL_JSON_VALUE, RESTConstants.MODELS_URI);
		return new ResponseEntity<ResourceCollection<ModelMetadata>>(collection, HttpStatus.OK);
	}

	@RequestMapping( value = MODEL_URI, method = RequestMethod.GET,
			produces = {
					MODEL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ModelMetadata> getModelMetadata(
			@PathVariable String repoId,
			@PathVariable String modelId,
			HttpServletRequest request) {

		ModelMetadata model = modelDAO.get(repoId, modelId);
		return new ResponseEntity<ModelMetadata>(model, HttpStatus.OK);
	}

	@RequestMapping( value = MODEL_URI, method = RequestMethod.DELETE )
	public ResponseEntity<String> deleteModel(
			HttpServletRequest request,
			@PathVariable String repoId,
			@PathVariable String modelId) {
		ModelMetadata model = modelDAO.get(repoId, modelId);
		if(model == null){
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}else{
			modelDAO.delete(model);
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping( value = MODEL_TAGS_URI, method = RequestMethod.GET,
			produces = {
					ResourceCollection.MEDIA_TYPE_JSON_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResourceCollection<Tag>> getModelTags(
			@PathVariable String repoId,
			@PathVariable String modelId,
			@RequestParam(
				value = RESTConstants.REQUEST_PARAM_START,
				defaultValue = PagingParameters.DEFAULT_PAGING_START + "",
				required = false) int start,
			@RequestParam(
				value = RESTConstants.REQUEST_PARAM_LIMIT,
				defaultValue = PagingParameters.DEFAULT_PAGING_LIMIT + "",
				required = false) int limit
			) {
		if (limit < PagingParameters.PAGING_LIMIT_MIN || limit > PagingParameters.PAGING_LIMIT_MAX){
			//handle bad request
		}

		QueryOptions options = QueryOptions.builder().
				hashKey(Constants.RESOURCE_URI, Utils.generateURI(repoId, modelId)).
				paging(start, limit).
				create();
		QueryResult<Tag> result = tagDAO.query( options);
		ResourceCollection<Tag> collection = Utils.buildCollection(
				options,
				result.getResultList(),
				result.getCount(),
				"modelTags",
				MODEL_TAG_JSON_VALUE, MODEL_TAGS_URI);
		return new ResponseEntity<ResourceCollection<Tag>>(collection, HttpStatus.OK);
	}
	
	@RequestMapping( value = MODEL_TAGS_URI, method = RequestMethod.PUT )
	public ResponseEntity<Tag> createModelLatestTag(
		@PathVariable String repoId,
		@PathVariable String modelId,
		@RequestBody String dataUri) {
		Tag modelTag = tagDAO.update(Utils.generateURI(repoId, modelId), Constants.DEFALUT_TAG, dataUri);
		return new ResponseEntity<Tag>(modelTag, HttpStatus.CREATED);
	}

	@RequestMapping( value = MODEL_TAG_URI, method = RequestMethod.PUT )
	public ResponseEntity<Tag> createModelTag(
		@PathVariable String repoId,
		@PathVariable String modelId,
		@PathVariable String tagId,
		@RequestBody String dataUri) {
		Tag modelTag = tagDAO.update(Utils.generateURI(repoId, modelId), tagId, dataUri);
		return new ResponseEntity<Tag>(modelTag, HttpStatus.CREATED);
	}

	@RequestMapping( value = MODEL_TAG_URI, method = { RequestMethod.GET, RequestMethod.OPTIONS} )
	public ResponseEntity<Tag> getModelTag(
			@PathVariable String repoId,
			@PathVariable String modelId,
			@PathVariable String tagId ) {
		Tag modelTag = tagDAO.get(Utils.generateURI(repoId, modelId), tagId);		
		return new ResponseEntity<Tag>(modelTag, HttpStatus.OK);
	}
}
