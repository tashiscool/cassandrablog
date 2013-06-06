package org.lacassandra.smooshyfaces.persistence;

public class EntityNotFoundException extends PersistenceException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
