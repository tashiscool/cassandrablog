package org.lacassandra.smooshyfaces.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.lacassandra.smooshyfaces.entity.skinny.SkinnyUser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@XmlRootElement(name = "user")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User extends IdBaseEntity<UUID> implements Serializable {
    protected String userName;
    protected String email;
    protected String passwordHash;
    protected String passwordSalt;
    protected UserSession userSession;
    protected Date deletedOn = null;
    protected Date registeredOn;
    protected boolean deleted = false;

    public User() {
        super();
    }

    @XmlElement
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        User rhs = (User) obj;
        return new EqualsBuilder().append(getId(), rhs.getId()).isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(907, 193).append(getId()).hashCode();
    }

    @XmlTransient
    @JsonIgnore
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @XmlTransient
    @JsonIgnore
    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    @XmlTransient
    @JsonIgnore
    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }

    @XmlTransient
    @JsonIgnore
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    @XmlTransient
    public boolean isValid() {
        return !getDeleted();
    }

    @XmlTransient
    @JsonIgnore
    public Date getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }

    @XmlTransient
    @JsonIgnore
    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @XmlTransient
    @JsonIgnore
    public Date getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Date deletedOn) {
        this.deletedOn = deletedOn;
    }

    @Override
    public SkinnyUser loseWeight() {
        SkinnyUser user = new SkinnyUser();
        user.setUserName(getUserName());
        user.setId(getId());
        user.setCreatedOn(getCreatedOn());
        return user;
    }
}
