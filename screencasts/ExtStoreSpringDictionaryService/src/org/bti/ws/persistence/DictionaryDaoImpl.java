package org.bti.ws.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bti.ws.model.Word;
import org.springframework.stereotype.Repository;

@Repository
public class DictionaryDaoImpl implements DictionaryDao {

	private Map<Integer, Word> map = new HashMap<Integer, Word>();
	
	private int ctr = 0;
	
	public DictionaryDaoImpl() {
		putWord(new Word("set", "A collection that can only contain unique elements"));
		putWord(new Word("list", "A collection that can only contain unique elements"));
	}
	
	@Override
	public Word getWord(int id) {
		return map.get(id);
	}

	@Override
	public List<Word> getWords() {
		List<Word> wordList = new ArrayList<Word>();
		for(Map.Entry<Integer, Word> entry : map.entrySet()) {
			wordList.add(entry.getValue());
		}
		return wordList;
	}

	@Override
	public void putWord(Word word) {
		Integer id = word.getId();
		if(id == null){
			id = new Integer(ctr++);
			word.setId(id);
		}
		map.put(id, word);
	}

	@Override
	public void removeWord(int id) {
		map.remove(id);
	}
}
