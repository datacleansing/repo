package io.datacleansing.service.rest;

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
import io.datacleansing.common.rest.representations.ResourceCollection;
import io.datacleansing.service.dao.ServiceDAO;
import io.datacleansing.service.representations.Service;

@RestController
@RequestMapping(value= RESTConstants.SERVICE_REPO_URI)
public class ServiceController {
	public static final String SERVICE_URI = "/{svcId}";

	public static final String SERVICE_JSON_VALUE = "application/vnd.datacleansing.service+json";

	@Autowired
	ServiceDAO svcDAO;

	@RequestMapping( method = RequestMethod.POST )
	public ResponseEntity<Service> createService(
		HttpServletRequest request,
		@PathVariable String repoId,
		@RequestBody Service svc) {
		svc.setRepository(repoId);
		Service updatedSvc = svcDAO.update(svc);
		return new ResponseEntity<Service>(updatedSvc, HttpStatus.CREATED);
	}

	@RequestMapping( method = { RequestMethod.GET, RequestMethod.OPTIONS },
			produces = {
					ResourceCollection.MEDIA_TYPE_JSON_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResourceCollection<Service>> getModels(
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
		QueryResult<Service> result = svcDAO.query(options);
		ResourceCollection<Service> collection = Utils.buildCollection(
				options,
				result.getResultList(),
				result.getCount(),
				"models",
				SERVICE_JSON_VALUE, RESTConstants.MODELS_URI);
		return new ResponseEntity<ResourceCollection<Service>>(collection, HttpStatus.OK);
	}

	@RequestMapping( value = SERVICE_URI, method = RequestMethod.GET,
			produces = {
				SERVICE_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Service> getService(
			@PathVariable String repoId,
			@PathVariable String svcId,
			HttpServletRequest request) {

		Service model = svcDAO.get(repoId, svcId);
		return new ResponseEntity<Service>(model, HttpStatus.OK);
	}

	@RequestMapping( value = SERVICE_URI, method = RequestMethod.DELETE )
	public ResponseEntity<String> deleteModel(
			HttpServletRequest request,
			@PathVariable String repoId,
			@PathVariable String svcId) {
		Service svc = svcDAO.get(repoId, svcId);
		if(svc == null){
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}else{
			svcDAO.delete(svc);
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
	}
}
