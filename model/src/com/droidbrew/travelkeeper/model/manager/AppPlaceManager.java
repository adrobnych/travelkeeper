package com.droidbrew.travelkeeper.model.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.droidbrew.travelkeeper.model.entity.Place;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AppPlaceManager {

	private final String URL = "http://192.168.0.14:8080/backend/rest/place";
	private HttpClient httpClient = null;
	private final Gson gson = new Gson();

	public int createPlace(Place place) throws ClientProtocolException,
			IOException {
		httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(URL);

		StringEntity se = new StringEntity(gson.toJson(place), "UTF-8");
		se.setContentType("text/html");
		post.setEntity(se);

		HttpResponse response = httpClient.execute(post);
		return response.getStatusLine().getStatusCode();
	}

	public boolean hasComment(String imei, String id)
			throws ClientProtocolException, IOException {
		httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(URL + "/has_place/" + imei + "/" + id);
		HttpResponse response = httpClient.execute(get);
		return Boolean.parseBoolean(readHTMLResponse(response));
	}

	public List<Place> getPlace(String id)
			throws ClientProtocolException, IOException {
		httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(URL + "/places/" + id);
		HttpResponse response = httpClient.execute(get);
		List<Place> list = null;
		list = gson.fromJson(new InputStreamReader(response.getEntity()
				.getContent(), "UTF-8"), new TypeToken<List<Place>>() {
		}.getType());
		return list;
	}

	public Map<String, List<Place>> getPlaces()
			throws ClientProtocolException, IOException {
		httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(URL + "/places/all");
		HttpResponse response = httpClient.execute(get);
		List<Place> list = gson.fromJson(new InputStreamReader(response.getEntity()
				.getContent(), "UTF-8"), new TypeToken<List<Place>>() {
		}.getType());
		Map<String, List<Place>> places = new HashMap<String, List<Place>>();
		for(Place place : list) {
			if(places.containsKey(place.getPlaceId()))
				places.get(place.getPlaceId()).add(place);
			else {
				List<Place> l = new ArrayList<Place>();
				l.add(place);
				places.put(place.getPlaceId(), l);
			}
		}
		return places;
	}

	public String readHTMLResponse(HttpResponse response)
			throws IllegalStateException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent(), "UTF-8"));
		StringBuffer sb = new StringBuffer();
		String line = "";
		while ((line = br.readLine()) != null)
			sb.append(line);
		br.close();
		return sb.toString();
	}

	public int getRating(List<Place> list) {
		int rating = getLikes(list) - getDislikes(list);
		return rating;
	}

	public int getDislikes(List<Place> list) {
		int rating = 0;
		for (Place place : list)
			if (!place.getLike())
				rating++;
		return rating;
	}

	public int getLikes(List<Place> list) {
		int rating = 0;
		for (Place place : list)
			if (place.getLike())
				rating++;
		return rating;
	}

}
