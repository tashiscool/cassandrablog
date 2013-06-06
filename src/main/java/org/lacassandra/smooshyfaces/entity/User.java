package org.lacassandra.smooshyfaces.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.common.base.Joiner;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement(name = "user")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User implements Serializable {
	public List<String> getPreferences() {
		return preferences;
	}

	public void setPreferences(List<String> preferences) {
		this.preferences = preferences;
	}

	public List<String> getNonpreferences() {
		return nonpreferences;
	}

	public void setNonpreferences(List<String> nonpreferences) {
		this.nonpreferences = nonpreferences;
	}

	public void setBooks(List<String> books) {
		this.books = books;
	}

	private String piId;
	private List<String> books = new ArrayList<String>();
	private List<String> preferences = new ArrayList<String>();
	private List<String> nonpreferences;

    public User() {
        super();
    }
    
	public User(String id, String string, String string2, String string3) {
		piId = id;
		books = Arrays.asList(string.split(","));
		preferences = Arrays.asList(string2.split(","));
		nonpreferences = Arrays.asList(string3.split(","));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((books == null) ? 0 : books.hashCode());
		result = prime * result
				+ ((nonpreferences == null) ? 0 : nonpreferences.hashCode());
		result = prime * result + ((piId == null) ? 0 : piId.hashCode());
		result = prime * result
				+ ((preferences == null) ? 0 : preferences.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (books == null) {
			if (other.books != null)
				return false;
		} else if (!books.equals(other.books))
			return false;
		if (nonpreferences == null) {
			if (other.nonpreferences != null)
				return false;
		} else if (!nonpreferences.equals(other.nonpreferences))
			return false;
		if (piId == null) {
			if (other.piId != null)
				return false;
		} else if (!piId.equals(other.piId))
			return false;
		if (preferences == null) {
			if (other.preferences != null)
				return false;
		} else if (!preferences.equals(other.preferences))
			return false;
		return true;
	}


	public String getPiId() {
		return piId;
	}

	public void setPiId(String piId) {
		this.piId = piId;
	}

	public String getBookLists() {
		// TODO Auto-generated method stub
		return Joiner.on(",").join(books);
	}

	public List<String> getBooks() {
		return books;
	}

	public String getPrefs() {
		return Joiner.on(",").join(preferences);
	}

	public String getNonPrefs() {
		// TODO Auto-generated method stub
		return Joiner.on(",").join(nonpreferences);
	}
}
