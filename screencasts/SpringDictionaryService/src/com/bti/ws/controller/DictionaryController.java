package com.bti.ws.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.bti.ws.model.Word;
import com.bti.ws.model.WordList;
import com.bti.ws.persistence.DictionaryDao;

@Controller
@RequestMapping("/dictionary")
public class DictionaryController {

	@Autowired 
	DictionaryDao dictionaryDao;
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	WordList get() {
		WordList wordList = new WordList();
		wordList.addAll(dictionaryDao.getWords());
		return wordList;
	}
	
	@RequestMapping(value="/{word}", method=RequestMethod.GET)
	@ResponseBody
	Word get(@PathVariable("word") String word) {
		Word w = dictionaryDao.getWord(word);
		if(w == null) {
			throw new NotFoundException();
		}
		return w;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
	void post(@RequestBody Word word, WebRequest req,
			HttpServletResponse resp) {
		dictionaryDao.putWord(word);
		resp.addHeader("Location", req.getContextPath() + "/bti/dictionary/"
				+ word.getName());
	}
	
	@RequestMapping(value="/{word}", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	void put(@PathVariable("word") String id, @RequestBody Word word) {
		dictionaryDao.putWord(word);
	}
	
	@RequestMapping(value="/{word}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	void remove(@PathVariable("word") String word) {
		dictionaryDao.removeWord(word);
	}
	
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Word not found")
	void handleNotFound(NotFoundException exc) {	
	}
	
	class NotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;		
	}
}
