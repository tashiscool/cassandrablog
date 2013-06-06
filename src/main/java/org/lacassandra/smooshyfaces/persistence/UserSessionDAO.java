package org.lacassandra.smooshyfaces.persistence;

import org.lacassandra.smooshyfaces.entity.User;
import org.lacassandra.smooshyfaces.entity.UserSession;

public interface UserSessionDAO extends BaseDAO<UserSession, Long> {
    UserSession findByToken(String token);
    UserSession findByUser(User user);
    int deleteByUser(User user);
}
