package org.bti.ws.model;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * A simple word object that contains a name and some definitions.
 */
@XmlRootElement
public class Word {
	
	private Integer id;

    private String name;
    
    private String definition;

    public Word() {

    }

    public Word(String name) {
        this.name = name;
    }

    public Word(String name, String definition) {
        this.name = name;
        this.definition = definition;
    }
    
    public Integer getId() {
		return id;
	}

	public Word setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
        return name;
    }

    public Word setName(String name) {
        this.name = name;
        return this;
    }

    public String getDefinition() {
        return definition;
    }

    public Word setDefinition(String definition) {
        this.definition = definition;
        return this;
    }
    
    @Override
	public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("Name: ");
    	sb.append(name);
    	sb.append(" Definition: ");
    	sb.append(definition);
    	return sb.toString();
	}
}
