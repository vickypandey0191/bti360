package com.bti.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import com.bti.model.Word;

public class DictionaryClientImpl implements DictionaryClient {

	private static String dictionaryServiceUrl = "http://btiwst.appspot.com/bti/dictionary";
	
	private HttpClient httpClient;
	
	public DictionaryClientImpl() {
		HttpParams params = new BasicHttpParams(); // create a HttpParams object to customize the client
		HttpConnectionParams.setConnectionTimeout(params, 20000); // Add a 20 second connection timeout, default value is infinite.
		HttpConnectionParams.setSoTimeout(params, 20000); // Add a 20 second timeout when waiting for data, default value is infinite.
		httpClient = new DefaultHttpClient(params); // create a default client using the param
	}
	
	@Override
	public void post(String name, String definition) {
		Word word = new Word();
		word.setName(name);
		word.setDefinition(definition);
		HttpPost post = new HttpPost(dictionaryServiceUrl);
		post.addHeader("Content-Type", 	"application/json; charset=UTF-8");
		try {
			HttpEntity entity = encodeWord(word);
			post.setEntity(entity);
			HttpResponse resp = httpClient.execute(post);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {
				throw new DictionaryClientException(resp.getStatusLine().toString());
			}
			resp.getEntity().consumeContent();
		} catch (Exception e) {
			throw new DictionaryClientException(e);
		}
	}

	@Override
	public void put(Word word) {
		HttpPut put = new HttpPut(dictionaryServiceUrl + "/" + word.getName());
		put.addHeader("Content-Type", "application/json; charset=UTF-8");
		try {
			HttpEntity entity = encodeWord(word);
			put.setEntity(entity);
			HttpResponse resp = httpClient.execute(put);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new DictionaryClientException(resp.getStatusLine().toString());
			}
			resp.getEntity().consumeContent();
		} catch (Exception e) {
			throw new DictionaryClientException(e);
		}
	}

	@Override
	public Word get(String name) {
		Word word = null;
		HttpGet get = new HttpGet(dictionaryServiceUrl + "/" + name);
		get.addHeader("Accept", "application/json");
		try {
			HttpResponse resp = httpClient.execute(get);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				word = parseWord(entity);
			}
		} catch (Exception e) {
			throw new DictionaryClientException(e);
		} 
		return word;
	}

	@Override
	public List<Word> getAll() {
		List<Word> words = null;
		HttpGet getAll = new HttpGet(dictionaryServiceUrl);
		getAll.addHeader("Accept", "application/json");
		try {
			HttpResponse resp = httpClient.execute(getAll);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				words = parseWords(entity);
			}
		} catch (Exception e) {
			throw new DictionaryClientException(e);
		}
		return words;
	}

	@Override
	public void delete(String name) {
		HttpDelete delete = new HttpDelete(dictionaryServiceUrl + "/" + name);
		try {
			HttpResponse resp = httpClient.execute(delete);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new DictionaryClientException(resp.getStatusLine().toString());
			}
			resp.getEntity().consumeContent();
		} catch (Exception e) {
			throw new DictionaryClientException(e);
		}
	}

	private Word parseWord(HttpEntity entity) throws Exception {
		InputStream inputStream = entity.getContent();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(inputStream, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		JSONObject jsonObject = new JSONObject(sb.toString());
		Word word = new Word();
		word.setDefinition(jsonObject.getString("definition"));
		word.setName(jsonObject.getString("name"));
		inputStream.close();
		entity.consumeContent();
		return word;
	}
	
	private List<Word> parseWords(HttpEntity entity) throws Exception {
		List<Word> words = new ArrayList<Word>();
		InputStream inputStream = entity.getContent();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(inputStream, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		JSONObject jsonObject = new JSONObject(sb.toString());
		JSONArray wordsArray = jsonObject.getJSONArray("words");
		for (int i = 0; i < wordsArray.length(); i++) {
			JSONObject jsonWord = wordsArray.getJSONObject(i);
			Word word = new Word();
			word.setDefinition(jsonWord.getString("definition"));
			word.setName(jsonWord.getString("name"));
			words.add(word);
		}
		inputStream.close();
		entity.consumeContent();
		return words;
	}
	
	private HttpEntity encodeWord(Word word) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", word.getName());
		jsonObject.put("definition", word.getDefinition());
		return new StringEntity(jsonObject.toString());
	}
}
