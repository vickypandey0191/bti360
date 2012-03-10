package org.bti.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.bti.ws.model.ExtModelMap;
import org.bti.ws.model.ExtWrapper;
import org.bti.ws.model.Word;
import org.bti.ws.persistence.DictionaryDao;
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

@Controller
@RequestMapping("/dictionary")
public class DictionaryController {

	@Autowired
	DictionaryDao dictionaryDao;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	ExtWrapper get() {
		List<Word> l = dictionaryDao.getWords();
		Word[] words = new Word[l.size()];
		l.toArray(words);
		return new ExtWrapper(words);
	}

	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	ExtModelMap get(@PathVariable("id") int id) {
		Word w = dictionaryDao.getWord(id);
		if (w == null) {
			throw new NotFoundException();
		}
		return new ExtModelMap(w);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	ExtModelMap post(@RequestBody Word word, WebRequest req,
			HttpServletResponse resp) {
		dictionaryDao.putWord(word);
		return new ExtModelMap(word);
	}

	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	ExtModelMap put(@PathVariable("id") int id, @RequestBody Word word) {
		dictionaryDao.putWord(word);
		return new ExtModelMap(word);
	}

	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	ExtModelMap remove(@PathVariable("id") int id) {
		dictionaryDao.removeWord(id);
		return new ExtModelMap();
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Word not found")
	void handleNotFound(NotFoundException exc) {
	}

	class NotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
}
