package io.datacleansing.common.rest.representations;

import java.util.ArrayList;
import java.util.List;

import io.datacleansing.common.query.QueryOptions;

public class CollectionLinksBuilder {

	private CollectionLinksBuilder(String uri, QueryOptions options) {
	}

	public static CollectionLinksBuilder newInstance(String uri, QueryOptions options) {
		return new CollectionLinksBuilder(uri, options);
	}

	public CollectionLinksBuilder paging(int count) {
		return this;
	}

	public List<Link> buildLinks() {
		ArrayList<Link> ls = new ArrayList<Link>();
		
		return ls;
	}

}
