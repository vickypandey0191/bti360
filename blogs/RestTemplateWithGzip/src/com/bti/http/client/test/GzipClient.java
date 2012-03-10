package com.bti.http.client.test;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.bti.http.client.GzipClientHttpRequestFactory;

public class GzipClient {

	private static String url = "http://search.twitter.com/search.json?q=#REST&rpp=100&until=2011-04-02";
	
	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate(new GzipClientHttpRequestFactory());
		try {
			ClientHttpRequest request = restTemplate.getRequestFactory().createRequest(URI.create(url), HttpMethod.GET);
			System.out.println("Request Headers");
			printHeaders(request.getHeaders());
			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
			System.out.println("Response Headers");
			printHeaders(response.getHeaders());
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void printHeaders(HttpHeaders headers) {
		for(String key : headers.keySet()) {
			System.out.println(key + ": " + StringUtils.collectionToCommaDelimitedString(headers.get(key)));
		}
	}
}
