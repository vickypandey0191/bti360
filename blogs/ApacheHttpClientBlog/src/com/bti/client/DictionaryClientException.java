package com.bti.client;

public class DictionaryClientException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DictionaryClientException(Exception e) {
		super(e);
	}

	public DictionaryClientException(String message) {
		super(message);
	}

}
