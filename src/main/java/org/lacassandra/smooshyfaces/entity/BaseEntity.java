package org.lacassandra.smooshyfaces.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.lacassandra.smooshyfaces.entity.skinny.HealthConsciousEntity;
import org.lacassandra.smooshyfaces.temporal.JaxbDateAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public abstract class BaseEntity implements HealthConsciousEntity, Serializable {
    protected Date createdOn;
    protected Date modifiedOn;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbDateAdapter.class)
    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(final Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    @XmlElement
    @XmlJavaTypeAdapter(JaxbDateAdapter.class)
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(final Date createdOn) {
        this.createdOn = createdOn;
    }
}
