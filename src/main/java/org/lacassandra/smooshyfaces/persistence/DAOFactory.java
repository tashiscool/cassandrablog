package org.lacassandra.smooshyfaces.persistence;

public abstract class DAOFactory {
    public abstract UserDAO createUserDAO();
    public abstract UserSessionDAO createUserSessionDAO();
    public abstract BookDAO createBookDAO();
}
