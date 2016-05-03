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
import org.springframework.web.bind.annotation.RestController;

import io.datacleansing.common.rest.RESTConstants;
import io.datacleansing.common.rest.Utils;
import io.datacleansing.repo.dao.ModelJobDAO;
import io.datacleansing.repo.representations.ModelJob;

@RestController
@RequestMapping(value= RESTConstants.MODELJOBS_URI)
public class ModelJobController {
	public static final String MODEL_URI =  "/{modelId}";
	public static final String MODEL_TAGS_URI = MODEL_URI + RESTConstants.TAGS_URI;
	public static final String MODEL_TAG_URI = MODEL_TAGS_URI + RESTConstants.TAG_URI;

	public static final String JOB_JSON_VALUE = "application/vnd.datacleansing.job+json";

	@Autowired
	ModelJobDAO modelJobDAO;

	@RequestMapping( value = MODEL_TAG_URI, method = RequestMethod.PUT )
	public ResponseEntity<ModelJob> createModelTag(
		@PathVariable String repoId,
		@PathVariable String modelId,
		@PathVariable String tagId,
		@RequestBody String algorithm) {
		ModelJob newJob = modelJobDAO.update(repoId, modelId, tagId, algorithm);
		return new ResponseEntity<ModelJob>(newJob, HttpStatus.CREATED);
	}

	@RequestMapping( value = MODEL_TAG_URI, method = { RequestMethod.GET, RequestMethod.OPTIONS} )
	public ResponseEntity<ModelJob> getModelTag(
			@PathVariable String repoId,
			@PathVariable String modelId,
			@PathVariable String tagId ) {
		ModelJob mj = modelJobDAO.get(Utils.generateURI(repoId, modelId), tagId);		
		return new ResponseEntity<ModelJob>(mj, HttpStatus.OK);
	}
}
