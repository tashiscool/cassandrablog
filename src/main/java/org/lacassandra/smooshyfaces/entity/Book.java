package org.lacassandra.smooshyfaces.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.lacassandra.smooshyfaces.entity.skinny.SkinnyBaseEntity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@XmlRootElement
public class Book {
	
    protected String isbn = StringUtils.EMPTY;
    protected String sampler  = StringUtils.EMPTY;
    protected List<String> tags = new ArrayList<String>();
    protected String title  = StringUtils.EMPTY;
    
    private String isbnString= StringUtils.EMPTY;
    private String url = StringUtils.EMPTY;
    protected List<String> authors = new ArrayList<String>();
    protected String publisher= StringUtils.EMPTY;
    protected int article_count =0;
    
    @XmlElement
    public String getIsbn() {
		return isbn;
	}
	public String getIsbnString() {
		return isbnString;
	}
	public void setIsbnString(String isbnString) {
		this.isbnString = isbnString;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<String> getAuthors() {
		return authors;
	}
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public int getArticle_count() {
		return article_count;
	}
	public void setArticle_count(int article_count) {
		this.article_count = article_count;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
    @XmlElement
	public String getSampler() {
		return sampler;
	}
	public void setSampler(String sampler) {
		this.sampler = sampler;
	}
    @XmlElement
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
    @XmlElement
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
 
}
