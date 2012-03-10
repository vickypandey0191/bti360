package com.bti.ws.persistence;

import java.util.List;

import com.bti.ws.model.Word;

public interface DictionaryDao {

	void putWord(Word word);
	
	Word getWord(String word);

	void removeWord(String word);

	List<Word> getWords(); 
}
