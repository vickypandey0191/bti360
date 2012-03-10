package controllers;

import play.*;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;
import play.mvc.*;

import java.io.IOException;
import java.util.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.*;

public class Dictionary extends Controller {

	private static final String url = "http://btiwst.appspot.com/bti/dictionary/";
	
    public static void getWords() {
    	String format = request.format;
    	WSRequest req = WS.url(url).setHeader("Accept", "application/" + format);
    	String response = req.get().getString();
    	if(format.equalsIgnoreCase("json")) {
    		renderJSON(response);
    	} else if(format.equalsIgnoreCase("xml")){
    		renderXml(response);
    	}
    }
    
    public static void addWord() throws IOException {
    	StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = request.body.read(b)) != -1;) {
            out.append(new String(b, 0, n));
        }
        String format = request.format;
        WSRequest req = WS.url(url).setHeader("Content-Type", "application/" + format);
        req.body(out.toString());
        HttpResponse resp = req.post(); 
    	response.status = resp.getStatus();
    }
    
    public static void updateWord(String word) throws IOException {
    	StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = request.body.read(b)) != -1;) {
            out.append(new String(b, 0, n));
        }
        String format = request.format;
        WSRequest req = WS.url(url + "/" + word).setHeader("Content-Type", "application/" + format);
        req.body(out.toString());
        HttpResponse resp = req.put(); 
    	response.status = resp.getStatus();
    }
    
    public static void deleteWord(String word) {
    	WSRequest req = WS.url(url + word);
    	HttpResponse resp = req.delete();
    	response.status = resp.getStatus();
    }
}