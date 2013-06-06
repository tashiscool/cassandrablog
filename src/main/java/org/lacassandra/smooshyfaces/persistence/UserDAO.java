package org.lacassandra.smooshyfaces.persistence;

import com.google.common.base.Function;
import org.lacassandra.smooshyfaces.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserDAO extends BaseDAO<User, UUID> {
    public User findByEmail(String email);
    public User findByUserName(String userName);
    public List<User> findByIds(List<UUID> ids);
    public void forAllUserIds(Function<UUID, Void> callback, int pageSize);
}
