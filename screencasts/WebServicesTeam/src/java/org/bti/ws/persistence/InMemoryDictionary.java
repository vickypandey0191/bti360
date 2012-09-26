package org.bti.ws.persistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.bti.ws.model.Word;

/**
 * This is an implementation of a very simple dictionary that remains in
 * memory.
 *
 * @author david
 */
public class InMemoryDictionary {
    private static InMemoryDictionary instance = null;
    private HashMap<String, Word> words = new HashMap<String, Word>();

    /*
     * Private constructor that creates the Singleton instance.
     */
    private InMemoryDictionary() {
        initialize();
    }

    /**
     * Gets the single instance of this.
     *
     * @return the single <code>InMemoryDictionary</code> instance.
     */
    public static InMemoryDictionary instance() {
        if(instance == null) {
            instance = new InMemoryDictionary();
        }

        return instance;
    }

    private void initialize() {
        Word set = new Word("set");
        set.setDefinition("A collection that contains no duplicate elements.");

        Word list = new Word("list");
        list.setDefinition("An ordered collection (also known as a sequence).");

        putWord(set);
        putWord(list);
    }

    /**
     * Gets all the words in the dictionary.
     *
     * @return  a <code>Map</code> with all the words in the dictionary.
     */
    public Map getWords() {
        return Collections.unmodifiableMap(words);
    }

    /**
     * Get the definition of a word.
     *
     * @param wordName  the word for which the definition is requested.
     * @return      if the word is in the dictionary, a <code>List</code> of
     *              definitions will be returned. Else, <code>null</code> will
     *              be returned.
     */
    public Word getWord(String wordName) {
        return words.get(wordName);
    }
    
    public void putWord(Word word) {
        words.put(word.getName(), word);
    }

    public void removeWord(String wordName)
    {
        words.remove(wordName);
    }
}
