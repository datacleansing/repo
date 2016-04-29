package io.datacleansing.common.query;

public class PagingParameters {

	public static final int DEFAULT_PAGING_START = 0;
	public static final int DEFAULT_PAGING_LIMIT = 10;
	public static final int PAGING_LIMIT_MIN = 0;
	public static final int PAGING_LIMIT_MAX = 100000;
	
	private long start;
	private int limit;
	
	public PagingParameters(long start, int limit) {
		super();
		this.start = start;
		this.limit = limit;
	}
	
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	

}
