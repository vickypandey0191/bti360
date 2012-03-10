package com.bti.http.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AbstractClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;

public class GzipClientHttpRequest extends AbstractClientHttpRequest {

	public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
	public static final String ENCODING_GZIP = "gzip";
	
	private final HttpURLConnection connection;

	public GzipClientHttpRequest(HttpURLConnection connection) {
		this.connection = connection;
		this.getHeaders().add(HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
	}

	@Override
	public HttpMethod getMethod() {
		return HttpMethod.valueOf(this.connection.getRequestMethod());
	}

	@Override
	public URI getURI() {
		try {
			return this.connection.getURL().toURI();
		}
		catch (URISyntaxException ex) {
			throw new IllegalStateException("Could not get HttpURLConnection URI: " + ex.getMessage(), ex);
		}
	}

	@Override
	protected ClientHttpResponse executeInternal(HttpHeaders headers,
			byte[] bufferedOutput) throws IOException {
		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			String headerName = entry.getKey();
			for (String headerValue : entry.getValue()) {
				this.connection.addRequestProperty(headerName, headerValue);
			}
		}
		this.connection.connect();
		if (bufferedOutput.length > 0) {
			FileCopyUtils.copy(bufferedOutput, this.connection.getOutputStream());
		}
		return new GzipClientHttpResponse(this.connection);
	}
}
