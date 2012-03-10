package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Word extends Model {
	
	@Required
    public String name;  
	
	@Required
	public String definition;
	
	public Word(String name, String definition) {
		this.name = name;
		this.definition = definition;
	}
}
