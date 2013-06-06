package org.lacassandra.smooshyfaces.entity.util;

import org.lacassandra.smooshyfaces.entity.User;
import org.lacassandra.smooshyfaces.entity.UserSession;
import org.lacassandra.smooshyfaces.persistence.UserSessionDAO;

public abstract class UserSessionUtil {
    public static User getUserFromSession(final UserSessionDAO userSessionDao, final String sessionId) {
        UserSession userSession = userSessionDao.findByToken(sessionId);
        if (userSession == null) {
            return null;
        }
        return userSession.getUser();
    }
}
