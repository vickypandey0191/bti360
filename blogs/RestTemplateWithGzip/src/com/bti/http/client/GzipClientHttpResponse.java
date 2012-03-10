package com.bti.http.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.zip.GZIPInputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

public class GzipClientHttpResponse implements ClientHttpResponse {

	private final HttpURLConnection connection;
	private HttpHeaders headers;
	
	public GzipClientHttpResponse(HttpURLConnection connection) {
		this.connection = connection;
	}

	@Override
	public InputStream getBody() throws IOException {
		InputStream errorStream = this.connection.getErrorStream();
		return (errorStream != null ? errorStream : StringUtils.hasLength(
				this.connection.getContentEncoding()) && 
				this.connection.getContentEncoding().equalsIgnoreCase(
				GzipClientHttpRequest.ENCODING_GZIP) ? new GZIPInputStream(
				this.connection.getInputStream()) : this.connection.getInputStream());
	}

	@Override
	public HttpHeaders getHeaders() {
		if (this.headers == null) {
			this.headers = new HttpHeaders();
			// Header field 0 is the status line for most HttpURLConnections, but not on GAE
			String name = this.connection.getHeaderFieldKey(0);
			if (StringUtils.hasLength(name)) {
				this.headers.add(name, this.connection.getHeaderField(0));
			}
			int i = 1;
			while (true) {
				name = this.connection.getHeaderFieldKey(i);
				if (!StringUtils.hasLength(name)) {
					break;
				}
				this.headers.add(name, this.connection.getHeaderField(i));
				i++;
			}
		}
		return this.headers;
	}

	@Override
	public HttpStatus getStatusCode() throws IOException {
		return HttpStatus.valueOf(this.connection.getResponseCode());
	}

	@Override
	public String getStatusText() throws IOException {
		return this.connection.getResponseMessage();
	}

	@Override
	public void close() {
		this.connection.disconnect();
	}
}