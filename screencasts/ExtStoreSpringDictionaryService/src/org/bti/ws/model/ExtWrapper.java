package org.bti.ws.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="root")
public class ExtWrapper {
	
	private static final long serialVersionUID = 1L;

	@XmlElement(name="data")
	Word[] data;
	
	@XmlElement(name="success")
	boolean success = true;
	
	public ExtWrapper() {}
	
	public ExtWrapper(Word[] data){
		this.data = data;
	}

	public Word[] getData() {
		return data;
	}

	public boolean isSuccess() {
		return success;
	}
}
