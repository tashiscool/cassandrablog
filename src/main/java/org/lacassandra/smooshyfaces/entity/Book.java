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
    
    @XmlElement
    public String getIsbn() {
		return isbn;
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
