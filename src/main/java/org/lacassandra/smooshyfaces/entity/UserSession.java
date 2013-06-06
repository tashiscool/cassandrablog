package org.lacassandra.smooshyfaces.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang.NotImplementedException;
import org.lacassandra.smooshyfaces.entity.skinny.SkinnyBaseEntity;

import java.util.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UserSession extends BaseEntity {
    private String token;
    private Date expirationDate;
    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public SkinnyBaseEntity loseWeight() {
        throw new NotImplementedException();
    }
}
