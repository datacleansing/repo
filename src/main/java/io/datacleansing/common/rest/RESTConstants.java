package io.datacleansing.common.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import io.datacleansing.common.rest.representations.ResourceCollection;

public class RESTConstants {
	public static final String REQUEST_PARAM_START = "start";
	public static final String REQUEST_PARAM_LIMIT = "limit";
	public static final String REQUEST_PARAM_SORTBY = "sortBy";
	public static final String REQUEST_PARAM_FILTER = "filter";
	public static final String APPLICATION_ZIP_VALUE = "application/zip";

	public static final HttpHeaders APPLICATION_JSON_HEADER = immutableHeaders(MediaType.APPLICATION_JSON);
	public static final MediaType COLLECTION_JSON = MediaType
			.valueOf(ResourceCollection.MEDIA_TYPE_JSON_VALUE);
	public static final HttpHeaders COLLECTION_JSON_HEADER = immutableHeaders(COLLECTION_JSON);
	public static final HttpHeaders TEXT_HTML_HEADER = immutableHeaders(MediaType.TEXT_HTML);
	public static final HttpHeaders TEXT_PLAIN_HEADER = immutableHeaders(MediaType.TEXT_PLAIN);
	public static final HttpHeaders ZIP_HEADER = immutableHeaders(MediaType
			.valueOf(APPLICATION_ZIP_VALUE));

	public static HttpHeaders mutable(HttpHeaders headers) {
		HttpHeaders m = new HttpHeaders();
		m.setContentType(headers.getContentType());
		return m;
	}
	public static HttpHeaders immutableHeaders(MediaType contentType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(contentType);
		return HttpHeaders.readOnlyHttpHeaders(headers);
	}

}
