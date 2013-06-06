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
public class Book extends IdBaseEntity<UUID> {
    protected String description = StringUtils.EMPTY;
    protected String contentLocation;
    protected List<String> tags = new ArrayList<String>();
    protected User owner;

    // just a placeholder to support paging with cassandra
    protected UUID timelineUuid;

    // Since the property is for internal use, don't expose it to the API responses
    @XmlTransient
    @JsonIgnore
    public UUID getTimelineUuid() {
        return timelineUuid;
    }

    public void setTimelineUuid(final UUID timelineUuid) {
        this.timelineUuid = timelineUuid;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @XmlElement
    public String getContentLocation() {
        return contentLocation;
    }

    public void setContentLocation(final String contentLocation) {
        this.contentLocation = contentLocation;
    }

    @XmlElement
    public List<String> getTags() {
        return tags;
    }

    public void addTag(final String tag) {
        tags.add(tag);
    }

    public void setTags(final List<String> tags) {
        this.tags = tags;
    }

    @XmlElement
    public User getOwner() {
        return owner;
    }

    public void setOwner(final User owner) {
        this.owner = owner;
    }

    @Override
    public SkinnyBaseEntity loseWeight() {
        throw new NotImplementedException();
    }
}
