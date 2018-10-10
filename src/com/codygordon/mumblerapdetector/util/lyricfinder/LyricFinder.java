package com.codygordon.mumblerapdetector.util.lyricfinder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import com.codygordon.mumblerapdetector.util.lyricfinder.models.Song;
import com.google.gson.Gson;

public class LyricFinder {

	private HttpClient client;
	
	public LyricFinder() {
		initClient();
	}
	
	private void initClient() {
		client = HttpClientBuilder.create().build();
	}
	
	public String GetLyrics(String artist, String track) {
		String lyricsJson = retrieveLyrics(artist, track);
		Song song = getSong(lyricsJson);
		String lyrics = song.result.track.text;
		return lyrics;
	}
	
	private String retrieveLyrics(String artist, String track) {
		try {			
			URI uri = new URIBuilder()
			        .setScheme("https")
			        .setHost("orion.apiseeds.com")
			        .setPath("/api/music/lyric/" + artist + "/" + track)
			        .setParameter("apikey", "bJSkcYKVjiOspmcu5NrcJeU9swmqLGAH4bgw9yUSDQCBR8dMU4tTZFe8gTnznRGl")
			        .build();
			HttpGet request = new HttpGet(uri);

			HttpResponse response = client.execute(request);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while((line = reader.readLine()) != null) {
				result.append(line);
			}	
			return result.toString();
		}
		catch(Exception e) {
			return "Error!";
		}
	}

	private Song getSong(String json) {
		return new Gson().fromJson(json, Song.class);
	}
}