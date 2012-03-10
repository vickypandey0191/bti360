package com.bti.client.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bti.client.DictionaryClient;
import com.bti.client.DictionaryClientImpl;
import com.bti.model.Word;

public class DictionaryClientTest {

	DictionaryClient dictionaryClient;
	
	@Before
	public void setUp() throws Exception {
		dictionaryClient = new DictionaryClientImpl();
	}

	@Test
	public void testPost() {
		dictionaryClient.post("object", "a representation of some real word thing");
		Word word = dictionaryClient.get("object");
		Assert.assertNotNull(word);
		Assert.assertEquals(word.getName(), "object");
		Assert.assertNotNull(word.getDefinition());
	}

	@Test
	public void testPut() {
		Word w = dictionaryClient.get("object");
		w.setDefinition("a representation of some real world thing.");
		dictionaryClient.put(w);
		Word word = dictionaryClient.get("object");
		Assert.assertNotNull(word);
		Assert.assertEquals(word.getDefinition(), "a representation of some real world thing.");
	}
	
	@Test
	public void testGet() {
		Word word = dictionaryClient.get("object");
		Assert.assertNotNull(word);
		Assert.assertEquals(word.getName(), "object");
		Assert.assertNotNull(word.getDefinition());
	}

	@Test
	public void testGetAll() {
		List<Word> words = dictionaryClient.getAll();
		Assert.assertNotNull(words);
		Assert.assertTrue(!words.isEmpty());
	}

	@Test
	public void testDelete() {
		dictionaryClient.delete("object");
		Word word = dictionaryClient.get("object");
		Assert.assertNull(word);
	}

}
