package com.bti.ws.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bti.ws.model.Word;

@Repository
public class DictionaryDaoImpl implements DictionaryDao {

	private Map<String, Word> map = new HashMap<String, Word>();
	
	public DictionaryDaoImpl() {
		Word w = new Word();
		w.setName("set");
		w.setDefinition("A collection that can only contain unique elements");
		map.put("set", w);
		w = new Word();
		w.setName("list");
		w.setDefinition("A collection that contains elements in a specific order");
		map.put("list", w);
	}
	
	@Override
	public Word getWord(String word) {
		return map.get(word);
	}

	@Override
	public List<Word> getWords() {
		List<Word> wordList = new ArrayList<Word>();
		for(Map.Entry<String, Word> entry : map.entrySet()) {
			wordList.add(entry.getValue());
		}
		return wordList;
	}

	@Override
	public void putWord(Word word) {
		map.put(word.getName(), word);
	}

	@Override
	public void removeWord(String word) {
		map.remove(word);
	}
}
