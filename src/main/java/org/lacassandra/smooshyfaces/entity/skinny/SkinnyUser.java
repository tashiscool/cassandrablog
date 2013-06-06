package org.lacassandra.smooshyfaces.entity.skinny;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement(name = "skinnyUser")
public class SkinnyUser extends SkinnyBaseEntity {
    protected String userName;

    @XmlElement
    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }
}
