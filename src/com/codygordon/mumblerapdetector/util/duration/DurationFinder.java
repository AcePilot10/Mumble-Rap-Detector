package com.codygordon.mumblerapdetector.util.duration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import com.codygordon.mumblerapdetector.util.duration.models.ContentDetails;
import com.codygordon.mumblerapdetector.util.duration.models.SearchResult;
import com.codygordon.mumblerapdetector.util.duration.models.Video;
import com.google.gson.Gson;

public class DurationFinder {

	private static final String API_KEY = "AIzaSyDNk2ImQBBpcvFnw_hDxIOOQ03K5sNKB9U";
	
	private HttpClient client;
	
	public DurationFinder() {
		initClient();
	}
	
	private void initClient() {
		client = HttpClientBuilder.create().build();
	}
	
	public int getDuration(String query) {
		try {
			Video video = getVideo(query);
			
			String id = video.id.videoId;			
	
			URI uri = new URIBuilder()
					.setScheme("https")
					.setHost("www.googleapis.com/youtube/v3/videos")
					.addParameter("id", id)
					.addParameter("part", "contentDetails")
					.addParameter("key", API_KEY)
					.build();
			
			HttpGet request = new HttpGet(uri);
			
			String result = getResponse(request);
			
			ContentDetails details = new Gson().fromJson(result, ContentDetails.class);
			
			String durationRaw = details.items.get(0).contentDetails.duration;
			String durationFormatted = durationRaw.replace("PT", "");
			
			int mins = Integer.parseInt(durationFormatted.split("M")[0]);
			int secs = Integer.parseInt(durationFormatted.split("M")[1].replace("S", ""));
			
			int totalSeconds = mins * 60 + secs; 
			return totalSeconds;
			
		} catch (Exception e) {
			return -1;
		}		
	}
	
	private Video getVideo(String query) {
		try {
			URI uri = new URIBuilder()
					.setScheme("https")
					.setHost("www.googleapis.com/youtube/v3/search/")
					.addParameter("part", "snippet")
					.addParameter("maxResults", "1")
					.addParameter("q", query)
					.addParameter("key", API_KEY)
					.build();
			
			HttpGet request = new HttpGet(uri);
			
			String resultContent = getResponse(request);
			
			SearchResult searchResult = new Gson().fromJson(resultContent, SearchResult.class);
			
			return searchResult.items.get(0);
		}
		catch(Exception e) {
			return null;
		}
	}
	
	private String getResponse(HttpGet request) {
		try {
			HttpResponse response = client.execute(request);
			
			InputStreamReader inputStreamReader = new InputStreamReader(response.getEntity().getContent());
			BufferedReader reader = new BufferedReader(inputStreamReader);
			
			StringBuffer result = new StringBuffer();
			String line = "";
			
			while((line = reader.readLine()) != null) {
				result.append(line);
			}
			
			String resultContent = result.toString();
			return resultContent;
		}
		catch(Exception e) {
			return "An error occured!";
		}
	}
}