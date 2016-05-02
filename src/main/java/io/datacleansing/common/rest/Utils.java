package io.datacleansing.common.rest;

import io.datacleansing.common.query.PagingParameters;
import io.datacleansing.common.query.QueryOptions;
import io.datacleansing.common.rest.representations.CollectionLinksBuilder;
import io.datacleansing.common.rest.representations.ResourceCollection;
import io.datacleansing.common.rest.representations.ResourceCollectionBuilder;

import java.util.List;

public class Utils {
	public static String generateURI(String repo, String resId){
		return repo + "/" + resId;
	}

	public static <T> ResourceCollection<T> buildCollection(
			QueryOptions options,
			List<T> items,
			int count,
			String collectionName,
			String acceptType,
			String uri) {
		PagingParameters paging = options.getPaging();
		long start = (paging == null ? PagingParameters.DEFAULT_PAGING_START : paging.getStart());
		int limit = (paging == null ? PagingParameters.DEFAULT_PAGING_LIMIT : paging.getLimit());

		if (limit > count) {
			limit = count;
		}
		ResourceCollection<T> collection = ResourceCollectionBuilder.<T> builder().accepts(acceptType)
				.name(collectionName).start(start).limit(limit).count(count).items(items)
				.links(CollectionLinksBuilder.newInstance(uri, options).paging(count).buildLinks()).create();

		return collection;
	}
}
