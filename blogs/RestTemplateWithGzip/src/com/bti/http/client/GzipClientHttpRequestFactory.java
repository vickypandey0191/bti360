package com.bti.http.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.util.Assert;

public class GzipClientHttpRequestFactory implements ClientHttpRequestFactory {

	@Override
	public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod)
			throws IOException {
		HttpURLConnection connection = openConnection(uri.toURL());
		prepareConnection(connection, httpMethod.name());
		return new GzipClientHttpRequest(connection);
	}

	protected HttpURLConnection openConnection(URL url) throws IOException {
		URLConnection urlConnection = url.openConnection();
		Assert.isInstanceOf(HttpURLConnection.class, urlConnection);
		return (HttpURLConnection) urlConnection;
	}
	
	protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
		connection.setDoInput(true);
		if ("GET".equals(httpMethod)) {
			connection.setInstanceFollowRedirects(true);
		}
		else {
			connection.setInstanceFollowRedirects(false);
		}
		if ("PUT".equals(httpMethod) || "POST".equals(httpMethod)) {
			connection.setDoOutput(true);
		}
		else {
			connection.setDoOutput(false);
		}
		connection.setRequestMethod(httpMethod);
	}
}
