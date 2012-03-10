package org.bti.ws.model;

import org.springframework.ui.ModelMap;

public class ExtModelMap extends ModelMap {
	private static final long serialVersionUID = 1L;
	
	private static final String SUCCESS = "success";
	
	private static final String ROOT = "data";
	
	public ExtModelMap(){
		this.addAttribute(SUCCESS, true);
	}
	
	public ExtModelMap(Object data){
		this();
		this.addAttribute(ROOT, data);
	}
}
