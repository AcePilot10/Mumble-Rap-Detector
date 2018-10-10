package com.codygordon.mumblerapdetector.util.duration.models;

import java.util.List;

public class SearchResult {
	public String kind;
	public String etag;
	public String nextPageToken;
	public String regionCode;
	public PageInfo pageInfo;
	public List<Video> items;
	
	public class PageInfo {
		public int totalResults;
		public int resultsPerPage;
	}
}