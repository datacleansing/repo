package io.datacleansing.repo.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.datacleansing.common.query.PagingParameters;
import io.datacleansing.common.query.QueryOptions;
import io.datacleansing.common.query.QueryResult;

import io.datacleansing.common.Constants;
import io.datacleansing.common.rest.RESTConstants;
import io.datacleansing.common.rest.representations.CollectionLinksBuilder;
import io.datacleansing.common.rest.representations.ResourceCollection;
import io.datacleansing.common.rest.representations.ResourceCollectionBuilder;
import io.datacleansing.repo.dao.ModelDAO;
import io.datacleansing.repo.dao.TagDAO;

import io.datacleansing.repo.representations.ModelMetadata;
import io.datacleansing.repo.representations.Tag;

@RestController
public class ModelController {
	private static final String BASE_URI = "";
	public static final String MODELS_URI = BASE_URI + "/models";
	public static final String MODEL_URI = MODELS_URI + "/{modelUri}";
	public static final String MODEL_TAGS_URI = MODEL_URI + RESTConstants.TAGS_URI;
	public static final String MODEL_DATA_URI = MODEL_TAGS_URI + RESTConstants.DATA_URI;
	public static final String MODEL_TAG_URI = MODEL_TAGS_URI + RESTConstants.TAG_URI;

	public static final String MODEL_JSON_VALUE = "application/vnd.datacleansing.model+json";
	public static final String MODEL_TAG_JSON_VALUE = "application/vnd.datacleansing.model.tag+json";

	private static final int DEFAULT_PAGING_START = 0;
	private static final int DEFAULT_PAGING_LIMIT = 10;
	private static final int PAGING_LIMIT_MIN = 0;
	private static final int PAGING_LIMIT_MAX = 100000;

	@Autowired
	ModelDAO modelDAO;

	@Autowired
	TagDAO tagDAO;

	public final <T> ResourceCollection<T> buildCollection(
			QueryOptions options,
			List<T> items,
			int count,
			String collectionName,
			String acceptType,
			String uri) {
		PagingParameters paging = options.getPaging();
		long start = (paging == null ? DEFAULT_PAGING_START : paging.getStart());
		int limit = (paging == null ? DEFAULT_PAGING_LIMIT : paging.getLimit());

		if (limit > count) {
			limit = count;
		}
		ResourceCollection<T> collection = ResourceCollectionBuilder.<T> builder().accepts(acceptType)
				.name(collectionName).start(start).limit(limit).count(count).items(items)
				.links(CollectionLinksBuilder.newInstance(uri, options).paging(count).buildLinks()).create();

		return collection;
	}

	@RequestMapping(value = MODELS_URI, method = RequestMethod.POST)
	public ResponseEntity<ModelMetadata> createModel(
		HttpServletRequest request,
		HttpServletResponse response,
		@RequestBody ModelMetadata model) {

		return new ResponseEntity<ModelMetadata>(model, HttpStatus.CREATED);
	}

	@RequestMapping( value = MODELS_URI, method = RequestMethod.GET,
			produces = {
					ResourceCollection.MEDIA_TYPE_JSON_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResourceCollection<ModelMetadata>> getModels(
			HttpServletRequest request,
			@RequestParam(
				value = RESTConstants.REQUEST_PARAM_START,
				defaultValue = DEFAULT_PAGING_START + "",
				required = false) int start,
			@RequestParam(
				value = RESTConstants.REQUEST_PARAM_LIMIT,
				defaultValue = DEFAULT_PAGING_LIMIT + "",
				required = false) int limit,
			@RequestParam(
				value = RESTConstants.REQUEST_PARAM_FILTER,
				required = false) String filter,
			@RequestParam(
				value = RESTConstants.REQUEST_PARAM_SORTBY,
				defaultValue = "name",
				required = false) String sortBy) {
		if (limit < PAGING_LIMIT_MIN || limit > PAGING_LIMIT_MAX){
			//handle bad request
		}

		QueryOptions options = QueryOptions.builder().
				paging(start, limit).
				filter(RESTConstants.REQUEST_PARAM_FILTER, filter).
				create();
		QueryResult<ModelMetadata> result = modelDAO.query(options);
		ResourceCollection<ModelMetadata> collection = buildCollection(
				options,
				result.getResultList(),
				result.getCount(),
				"models",
				MODEL_JSON_VALUE, MODELS_URI);
		return new ResponseEntity<ResourceCollection<ModelMetadata>>(collection, HttpStatus.OK);
	}

	@RequestMapping( value = MODEL_URI, method = RequestMethod.GET,
			produces = {
					MODEL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ModelMetadata> getModelMetadata(
			@PathVariable String modelUri,
			HttpServletRequest request) {

		ModelMetadata model = modelDAO.getModel(modelUri);
		return new ResponseEntity<ModelMetadata>(model, HttpStatus.OK);
	}

	@RequestMapping( value = MODEL_URI, method = RequestMethod.DELETE )
	public ResponseEntity<String> deleteModel(
			HttpServletRequest request,
			@PathVariable String modelUri) {
		ModelMetadata model = modelDAO.getModel(modelUri);
		if(model == null){
			//TODO
		}
		modelDAO.deleteModel(modelUri);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping( value = MODEL_TAGS_URI, method = RequestMethod.GET,
			produces = {
					ResourceCollection.MEDIA_TYPE_JSON_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResourceCollection<Tag>> getModelTags(
			@PathVariable String modelUri,
			@RequestParam(
				value = RESTConstants.REQUEST_PARAM_START,
				defaultValue = DEFAULT_PAGING_START + "",
				required = false) int start,
			@RequestParam(
				value = RESTConstants.REQUEST_PARAM_LIMIT,
				defaultValue = DEFAULT_PAGING_LIMIT + "",
				required = false) int limit
			) {
		if (limit < PAGING_LIMIT_MIN || limit > PAGING_LIMIT_MAX){
			//handle bad request
		}

		QueryOptions options = QueryOptions.builder().
				paging(start, limit).
				create();
		QueryResult<Tag> result = tagDAO.query(modelUri, options);
		ResourceCollection<Tag> collection = buildCollection(
				options,
				result.getResultList(),
				result.getCount(),
				"modelTags",
				MODEL_TAG_JSON_VALUE, MODEL_TAGS_URI);
		return new ResponseEntity<ResourceCollection<Tag>>(collection, HttpStatus.OK);
	}

	@RequestMapping( value = MODEL_TAGS_URI, method = RequestMethod.POST )
	public ResponseEntity<Tag> createModelTag(
		@PathVariable String modelUri,
		@PathVariable String tag,
		@RequestBody String data) {
		Tag modelTag = tagDAO.update(modelUri, tag, data);
		return new ResponseEntity<Tag>(modelTag, HttpStatus.CREATED);
	}

	@RequestMapping( value = MODEL_TAG_URI, method = RequestMethod.GET )
	public ResponseEntity<Tag> getModelTag(
		@PathVariable String modelUri,
		@PathVariable String tag ) {
		Tag modelTag = tagDAO.get(modelUri, tag);
		return new ResponseEntity<Tag>(modelTag, HttpStatus.CREATED);
	}
}
