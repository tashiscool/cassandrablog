package org.lacassandra.smooshyfaces.entity.skinny;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;
import java.util.UUID;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public abstract class SkinnyBaseEntity {
    protected Date createdOn;
    protected UUID id;

    @XmlElement
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @XmlElement
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
