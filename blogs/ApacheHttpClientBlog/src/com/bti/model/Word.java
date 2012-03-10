package com.bti.model;

public class Word {

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
