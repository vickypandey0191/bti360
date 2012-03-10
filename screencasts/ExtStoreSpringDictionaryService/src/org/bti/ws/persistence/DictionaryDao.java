package org.bti.ws.persistence;

import java.util.List;

import org.bti.ws.model.Word;

public interface DictionaryDao {

	void putWord(Word word);
	
	Word getWord(int word);

	void removeWord(int word);

	List<Word> getWords(); 
}
