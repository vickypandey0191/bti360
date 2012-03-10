package com.bti.client;

import java.util.List;

import com.bti.model.Word;

public interface DictionaryClient {

	void post(String name, String definition);
	
	void put(Word word);
	
	Word get(String word);
	
	List<Word> getAll();
	
	void delete(String word);
}
