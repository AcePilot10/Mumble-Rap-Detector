package com.codygordon.mumblerapdetector.util.duration.models;

import java.util.List;

public class ContentDetails {
	public String kind;
	public String etag;
	public List<ContentDetailItem> items;
	
	public class ContentDetailItem {
		public String id;
		public String kind;
		public String etag;
		public ContentDetailsData contentDetails;
		
		public class ContentDetailsData {
			public String duration;
		}
	}
}