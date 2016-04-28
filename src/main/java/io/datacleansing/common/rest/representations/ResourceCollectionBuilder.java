package io.datacleansing.common.rest.representations;

import io.datacleansing.common.query.PagingParameters;
import io.datacleansing.common.query.QueryOptions;
import io.datacleansing.common.query.QueryResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Builder class used for creating {@link ResourceCollection} instances.
 *
 * @author <A HREF="mailto:Eric.Bourn@sas.com">Eric Bourn</A>
 * @see ResourceCollection
 */
public class ResourceCollectionBuilder<T> {

    private ResourceCollection<T> collection;

    // allows callers to append to the links collection
    private List<Link> linksList;

    // private constructor
    private ResourceCollectionBuilder()
    {
        collection = new ResourceCollection<>();
    }

    /**
     * Creates a new instance of the builder
     * @param <T> the collection item type
     * @return the ResourceCollectionBuilder
     */
    public static <T> ResourceCollectionBuilder<T> builder()
    {
        return new ResourceCollectionBuilder<T>();
    }

    public ResourceCollectionBuilder<T> version(int version)
    {
        collection.setVersion(version);
        return this;
    }

    public ResourceCollectionBuilder<T> name(String name)
    {
        collection.setName(name);
        return this;
    }

    public ResourceCollectionBuilder<T> accepts(String... accept)
    {
        if (accept != null)
        {
            int len = accept.length - 1; // initialize for (n-1) spaces
            for (String type : accept)
            {
                len += type.length();
            }
            StringBuilder sb = new StringBuilder(len);
            String delim = "";
            for (String type : accept)
            {
                sb.append(delim);
                sb.append(type);
                delim = " ";
            }
            collection.setAccept(sb.toString()); // I18NOK:LSM
        }
        return this;
    }

    public ResourceCollectionBuilder<T> limit(int limit)
    {
        collection.setLimit(limit);
        return this;
    }

    public ResourceCollectionBuilder<T> start(long start)
    {
        collection.setStart(start);
        return this;
    }

    public ResourceCollectionBuilder<T> count(long count)
    {
        collection.setCount(count);
        return this;
    }

    private ResourceCollectionBuilder<T> queryOptions(QueryOptions options)
    {
        //marked as private as this probably won't be called directly by a controller
        PagingParameters paging = options.getPaging();
        if (paging != null)
        {
            limit(paging.getLimit());
            start(paging.getStart());
        }
        return this;
    }

    public ResourceCollectionBuilder<T> items(List<T> items)
    {
        collection.setItems(items);
        return this;
    }

    public <D> ResourceCollectionBuilder<T> items(List<D> items, Function<D,T> representationMapper)
    {
        if (items == null)
            return this;

        // add the individual items into the collection
        List<T> mappedItems = new ArrayList<>(items.size());
        if (representationMapper != null)
        {
            for (D data : items)
            {
                mappedItems.add(representationMapper.apply(data));
            }
        }
        return items(mappedItems);
    }

    public <D> ResourceCollectionBuilder<T> items(QueryResult<D> result, Function<D,T> representationMapper)
    {
        if (result == null)
            return this;

        count(result.getCount());

        queryOptions(result.getQueryOptions());
        return items(result.getResultList(), representationMapper);
    }

    public ResourceCollectionBuilder<T> links(List<Link> links)
    {
        if (links != null)
        {
            if (linksList == null)
            {
                linksList = new ArrayList<>();
            }
            linksList.addAll(links);
        }
        return this;
    }

    public ResourceCollectionBuilder<T> links(Link... links)
    {
        return links(links == null ? null : Arrays.asList(links));
    }

    public ResourceCollection<T> create()
    {
        collection.setLinks(linksList);
        return collection;
    }

}
