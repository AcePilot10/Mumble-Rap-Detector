package com.codygordon.mumblerapdetector.util.duration.models;

public class Video {

	public String kind;
	public String etag;
	public VideoId id;
	public Snippet snippet;
	
	public class Snippet {
		public String publishedAt;
		public String channelId;
		public String title;
		public String description;
	}
	
	public class VideoId {
		public String kind;
		public String videoId;
	}
}