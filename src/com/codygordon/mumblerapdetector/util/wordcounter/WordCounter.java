package com.codygordon.mumblerapdetector.util.wordcounter;

import java.util.ArrayList;
import java.util.List;

public class WordCounter {

	public int countWords(String lyrics) {
		String[] words = lyrics.split(" ");
		return words.length;
	}
	
	public int countUniqueWords(String lyrics) {
		String[] words = lyrics.split(" ");
		List<String> uniqueWordList = new ArrayList<String>();
		for(String word : words) {
			if(!uniqueWordList.contains(word)) {
				uniqueWordList.add(word);
			}
		}
		return uniqueWordList.size();
	}
}