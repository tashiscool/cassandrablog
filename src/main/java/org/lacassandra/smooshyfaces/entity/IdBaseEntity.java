package org.lacassandra.smooshyfaces.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.xml.bind.annotation.XmlElement;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public abstract class IdBaseEntity<T> extends BaseEntity {
    protected T id;

    protected IdBaseEntity() {

    }

    protected IdBaseEntity(final T id) {
        this.id = id;
    }

    @XmlElement
    public T getId() {
        return id;
    }

    public void setId(final T id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        IdBaseEntity rhs = (IdBaseEntity)obj;
        return new EqualsBuilder()
            .append(id, rhs.id)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 191)
            .append(id)
            .hashCode();
    }
}
