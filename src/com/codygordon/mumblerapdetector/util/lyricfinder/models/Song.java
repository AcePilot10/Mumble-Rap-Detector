package com.codygordon.mumblerapdetector.util.lyricfinder.models;

public class Song {
	
	public SongResult result;
	
	public class SongResult {

		public Artist artist;
		public Track track;
		public Copyright copyright;
		public int probability;
		public int similarity;
		
		public class Artist {
			public String name;
		}
		public class Track {
			public String name;
			public String text;
			public Lang lang;
			
			public class Lang {
				public String code;
				public String name;
			}
		}
		public class Copyright {
			public String notice;
			public String artist;
			public String text;
		}
	}
}
