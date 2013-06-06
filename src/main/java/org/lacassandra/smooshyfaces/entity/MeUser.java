package org.lacassandra.smooshyfaces.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement(name = "user")
public class MeUser extends User {
    @Override
    @XmlElement
    @JsonIgnore(false)
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    @XmlElement
    @JsonIgnore(false)
    public Date getRegisteredOn() {
        return super.getRegisteredOn();
    }

    public static MeUser fromUser(User user) {
        MeUser me = new MeUser();
        me.setId(user.getId());
        me.setUserName(user.getUserName());
        me.setEmail(user.getEmail());
        me.setPasswordHash(user.getPasswordHash());
        me.setPasswordSalt(user.getPasswordSalt());
        me.setUserSession(user.getUserSession());
        me.setDeleted(user.getDeleted());
        me.setDeletedOn(user.getDeletedOn());
        me.setRegisteredOn(user.getRegisteredOn());
        me.setCreatedOn(user.getCreatedOn());
        me.setModifiedOn(user.getModifiedOn());
        return me;
    }
}
