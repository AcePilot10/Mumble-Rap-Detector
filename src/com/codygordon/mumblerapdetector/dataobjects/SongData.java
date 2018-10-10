package com.codygordon.mumblerapdetector.dataobjects;

public class SongData {

	public int length;
	public String lyrics;
	public int rawWordCount;
	public int uniqueWordCount;
	public String title;
	
	public float getWordsPerSecond() {
		float wps = (float)rawWordCount / (float)length;
		return wps;
	}
}