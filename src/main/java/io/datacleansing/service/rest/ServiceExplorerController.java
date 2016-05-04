package io.datacleansing.service.rest;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.datacleansing.common.query.PagingParameters;
import io.datacleansing.common.query.QueryOptions;
import io.datacleansing.common.query.QueryResult;
import io.datacleansing.common.Constants;
import io.datacleansing.common.Flow;
import io.datacleansing.common.rest.RESTConstants;
import io.datacleansing.common.rest.Utils;
import io.datacleansing.common.rest.representations.ResourceCollection;
import io.datacleansing.service.dao.ServiceDAO;
import io.datacleansing.service.representations.Service;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value= RESTConstants.SERVICES_URI)
public class ServiceExplorerController {

	@Autowired
	ServiceDAO svcDAO;
	
	RestTemplate restTemplate = new RestTemplate();

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
					value = RESTConstants.REQUEST_PARAM_TYPE,
					defaultValue = RESTConstants.REQUEST_PARAM_TYPE_COUNT,
					required = false) String type,
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
				filter(RESTConstants.REQUEST_PARAM_FILTER, filter).
				create();
		//QueryResult<Service> result = svcDAO.query(options);
		ResourceCollection<Service> collection = Utils.buildCollection(
				options,
				new ArrayList<Service>(),
				22,
				"services",
				RESTConstants.SERVICE_JSON_VALUE, RESTConstants.MODELS_URI);
		return new ResponseEntity<ResourceCollection<Service>>(collection, HttpStatus.OK);
	}
}
