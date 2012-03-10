package controllers;

import java.util.Date;
import java.util.List;

import models.Word;
import play.data.validation.Valid;
import play.mvc.Controller;

public class Dictionary extends Controller {
    
    public static void all() {
        List<Word> words = Word.find("order by name").fetch();
        renderJSON(words);
    }
    
    public static void create(@Valid Word word) {
        word.save();
    }
    
    public static void read(Long id) {
        Word word = Word.findById(id);
        renderJSON(word);
    }
    
    public static void update(@Valid Word word) {
        word.save();
    }
    
    public static void delete(Long id) {
    	Word word = Word.findById(id);
        word.delete();
    }

}