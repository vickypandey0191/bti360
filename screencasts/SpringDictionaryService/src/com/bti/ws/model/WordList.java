package com.bti.ws.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="words")
public class WordList {

	private static final long serialVersionUID = 1L;

	@XmlElement(name="word")
    List<Word> list = new ArrayList<Word>();

	public void addAll(List<Word> words) {
		list.addAll(words);
	}
	
	public List<Word> getWords() {
		return list;
	}
}
