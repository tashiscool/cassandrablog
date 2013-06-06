package org.lacassandra.smooshyfaces.persistence.cassandra;

import com.google.common.base.Function;
import com.netflix.astyanax.ColumnListMutation;
import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.*;
import com.netflix.astyanax.query.RowQuery;
import com.netflix.astyanax.retry.ExponentialBackoff;
import com.netflix.astyanax.serializers.ByteBufferSerializer;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.serializers.TimeUUIDSerializer;
import com.netflix.astyanax.util.RangeBuilder;
import com.netflix.astyanax.util.TimeUUIDUtils;
import org.apache.commons.lang.StringUtils;
import org.lacassandra.smooshyfaces.entity.User;
import org.lacassandra.smooshyfaces.persistence.EntityNotFoundException;
import org.lacassandra.smooshyfaces.persistence.PersistenceException;
import org.lacassandra.smooshyfaces.persistence.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class CassandraUserDAO extends BaseCassandraDAO<User, UUID> implements UserDAO {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public final static String COLUMN_NAME_ID = "id";
    public final static String COLUMN_NAME_CREATED_ON = "created_on";
    public final static String COLUMN_NAME_MODIFIED_ON = "modified_on";
    public final static String COLUMN_NAME_USER_NAME = "user_name";
    public final static String COLUMN_NAME_EMAIL_ADDRESS = "email_address";
    public final static String COLUMN_NAME_PASSWORD_HASH = "password_hash";
    public final static String COLUMN_NAME_PASSWORD_SALT = "password_salt";
    public final static String COLUMN_NAME_DELETED = "deleted";
    public final static String COLUMN_NAME_DELETED_ON = "deleted_on";
    public final static String COLUMN_NAME_REGISTERED_ON = "registered_on";
    public final static String COLUMN_NAME_USER_ID = "user_id";
    public final static String ROW_KEY_ALL_USERS = "all-users";

    public final static ColumnFamily<UUID, String> COLUMN_FAMILY_USERS = new ColumnFamily<UUID, String>("users",
            TimeUUIDSerializer.get(),
            StringSerializer.get(),
            ByteBufferSerializer.get());

    public final static ColumnFamily<String, UUID> COLUMN_FAMILY_USER_LISTS = new ColumnFamily<String, UUID>("user_lists",
            StringSerializer.get(),
            TimeUUIDSerializer.get(),
            ByteBufferSerializer.get());

    public final static ColumnFamily<String, String> COLUMN_FAMILY_USER_NAMES = new ColumnFamily<String, String>("user_names",
            StringSerializer.get(),
            StringSerializer.get(),
            TimeUUIDSerializer.get());

    public final static ColumnFamily<String, String> COLUMN_FAMILY_USER_EMAILS = new ColumnFamily<String, String>("user_emails",
            StringSerializer.get(),
            StringSerializer.get(),
            TimeUUIDSerializer.get());


    public CassandraUserDAO() {
        super();
    }

    protected User userFromRowColumns(final ColumnList<String> columns) {
        if (columns == null || columns.isEmpty()) {
            return null;
        }

        User user = new User();

        user.setCreatedOn(columns.getDateValue(COLUMN_NAME_CREATED_ON, null));
        user.setModifiedOn(columns.getDateValue(COLUMN_NAME_MODIFIED_ON, null));
        user.setId(columns.getUUIDValue(COLUMN_NAME_ID, null));
        user.setUserName(columns.getStringValue(COLUMN_NAME_USER_NAME, StringUtils.EMPTY));
        user.setPasswordHash(columns.getStringValue(COLUMN_NAME_PASSWORD_HASH, StringUtils.EMPTY));
        user.setPasswordSalt(columns.getStringValue(COLUMN_NAME_PASSWORD_SALT, StringUtils.EMPTY));
        user.setRegisteredOn(columns.getDateValue(COLUMN_NAME_REGISTERED_ON, null));
        user.setDeleted(columns.getBooleanValue(COLUMN_NAME_DELETED, false));
        user.setDeletedOn(columns.getDateValue(COLUMN_NAME_DELETED_ON, null));
        user.setEmail(columns.getStringValue(COLUMN_NAME_EMAIL_ADDRESS, StringUtils.EMPTY));

        return user;
    }

    @Override
    public User findById(final UUID id) {
        if (id == null) {
            return null;
        }
        try {
            OperationResult<ColumnList<String>> result = getKeyspace()
                    .prepareQuery(COLUMN_FAMILY_USERS)
                    .withRetryPolicy(new ExponentialBackoff(250, 10))
                    .getKey(id)
                    .execute();

            User user = userFromRowColumns(result.getResult());
            if (user == null) {
                throw new EntityNotFoundException("User with id " + id + " does not exist");
            }
            return user;
        }
        catch (ConnectionException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<User> findByIds(List<UUID> ids) {
        if (ids == null) {
            throw new PersistenceException("ids cannot be null");
        }

        List<User> users = new ArrayList<User>();

        try {
            OperationResult<Rows<UUID, String>> result =
                    getKeyspace()
                            .prepareQuery(COLUMN_FAMILY_USERS)
                            .getKeySlice(ids)
                            .execute();

            for (Row<UUID, String> row : result.getResult()) {
                User user = userFromRowColumns(row.getColumns());
                if (user != null) {
                    users.add(user);
                }
            }

            return users;
        }
        catch (ConnectionException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public User save(final User user) {
        if (user == null) {
            throw new PersistenceException("User cannot be null");
        }
        if (user.getId() == null) {
            user.setId(TimeUUIDUtils.getUniqueTimeUUIDinMillis());
        }
        if (user.getCreatedOn() == null) {
            user.setCreatedOn(new Date());
        }
        user.setModifiedOn(new Date());

        MutationBatch m = getKeyspace().prepareMutationBatch();

        ColumnListMutation<String> mutation = m.withRow(COLUMN_FAMILY_USERS, user.getId())
                .putColumn(COLUMN_NAME_ID, user.getId())
                .putColumn(COLUMN_NAME_CREATED_ON, user.getCreatedOn())
                .putColumn(COLUMN_NAME_MODIFIED_ON, user.getModifiedOn())
                .putColumn(COLUMN_NAME_DELETED, user.getDeleted());

        if (!StringUtils.isEmpty(user.getUserName())) {
            mutation.putColumn(COLUMN_NAME_USER_NAME, user.getUserName());
            m.withRow(COLUMN_FAMILY_USER_NAMES, user.getUserName().toLowerCase()).putColumn(COLUMN_NAME_USER_ID, user.getId());
        }
        if (!StringUtils.isEmpty(user.getEmail())) {
            mutation.putColumn(COLUMN_NAME_EMAIL_ADDRESS, user.getEmail() != null ? user.getEmail() : StringUtils.EMPTY);
            m.withRow(COLUMN_FAMILY_USER_EMAILS, user.getEmail().toLowerCase()).putColumn(COLUMN_NAME_USER_ID, user.getId());
        }
        if (user.getRegisteredOn() != null) {
            mutation.putColumn(COLUMN_NAME_REGISTERED_ON, user.getRegisteredOn());
        }
        if (!StringUtils.isEmpty(user.getPasswordHash())) {
            mutation.putColumn(COLUMN_NAME_PASSWORD_HASH, user.getPasswordHash());
        }
        if (!StringUtils.isEmpty(user.getPasswordSalt())) {
            mutation.putColumn(COLUMN_NAME_PASSWORD_SALT, user.getPasswordSalt());
        }
        if (user.getDeletedOn() != null) {
            mutation.putColumn(COLUMN_NAME_DELETED_ON, user.getDeletedOn());
        }

        try {
            OperationResult<Void> result = m.execute();
            return user;
        }
        catch (ConnectionException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public User findByEmail(final String email) {
        if (StringUtils.isEmpty(email)) {
            throw new PersistenceException("email cannot empty");
        }

        try {
            OperationResult<ColumnList<String>> result = getKeyspace()
                    .prepareQuery(COLUMN_FAMILY_USER_EMAILS)
                    .getKey(email.toLowerCase())
                    .execute();
            ColumnList<String> columns = result.getResult();
            Column<String> userIdColumn = columns.getColumnByName(COLUMN_NAME_USER_ID);
            if (userIdColumn != null) {
                User user = findById(userIdColumn.getUUIDValue());
                if (user == null) {
                    return null;
                }

                // Check to see that this current user pulled from Cassandra has
                // an email that matches what was passed in. If it does,
                // then the user is linked to this email and we return them. If
                // they do not match, it means that the user updated their
                // email at some point and we return null to designate that
                // there is no user with this email so that we can write to
                // user_emails[email_address]['user_id']=xx and
                // overwrite the current xx with a new xx
                if (!email.equals(user.getEmail())) {
                    return null;
                }

                return user;
            }
            else {
                return null;
            }
        }
        catch (ConnectionException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public User findByUserName(final String userName) {
        if (StringUtils.isEmpty(userName)) {
            throw new PersistenceException("User name cannot be null or empty");
        }

        try {
            OperationResult<ColumnList<String>> result = getKeyspace()
                    .prepareQuery(COLUMN_FAMILY_USER_NAMES)
                    .getKey(userName.toLowerCase())
                    .execute();
            ColumnList<String> columns = result.getResult();
            Column<String> userIdColumn = columns.getColumnByName(COLUMN_NAME_USER_ID);
            if (userIdColumn != null) {
                User user = findById(userIdColumn.getUUIDValue());
                if (user == null) {
                    return null;
                }

                // Check to see that this current user pulled from Cassandra has
                // a user name that matches what was passed in. If it does,
                // then the user is linked to this user name and we return them. If
                // they do not match, it means that the user updated their
                // user name at some point and we return null to designate that
                // there is no user with this user name so that we can write to
                // user_names[user_name]['user_id']=xx and
                // overwrite the current xx with a new xx
                if (!userName.equalsIgnoreCase(user.getUserName())) {
                    return null;
                }

                return user;
            }
            else {
                return null;
            }
        }
        catch (ConnectionException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void forAllUserIds(Function<UUID, Void> callback, int pageSize) {
        try {
            ColumnList<UUID> columns;
            RowQuery<String, UUID> query = getKeyspace()
                    .prepareQuery(CassandraUserDAO.COLUMN_FAMILY_USER_LISTS)
                    .getKey(CassandraUserDAO.ROW_KEY_ALL_USERS)
                    .autoPaginate(true)
                    .withColumnRange(new RangeBuilder().setLimit(pageSize).build());

            while (!(columns = query.execute().getResult()).isEmpty()) {
                for (Column<UUID> c : columns) {
                    callback.apply(c.getName());
                }
            }
        }
        catch (ConnectionException e) {
            throw new PersistenceException(e);
        }
    }
}
