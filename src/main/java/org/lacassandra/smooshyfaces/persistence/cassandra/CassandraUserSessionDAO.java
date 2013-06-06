package org.lacassandra.smooshyfaces.persistence.cassandra;


import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.retry.ExponentialBackoff;
import com.netflix.astyanax.serializers.ByteBufferSerializer;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.serializers.TimeUUIDSerializer;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.lacassandra.smooshyfaces.entity.User;
import org.lacassandra.smooshyfaces.entity.UserSession;
import org.lacassandra.smooshyfaces.persistence.DAOFactory;
import org.lacassandra.smooshyfaces.persistence.PersistenceException;
import org.lacassandra.smooshyfaces.persistence.UserSessionDAO;

import java.util.Date;
import java.util.UUID;

public class CassandraUserSessionDAO extends BaseCassandraDAO<UserSession, Long> implements UserSessionDAO {
    private final static String COLUMN_NAME_EXPIRATION_DATE = "expiration_date";
    private final static String COLUMN_NAME_TOKEN = "token";
    private final static String COLUMN_NAME_USER_ID = "user_id";

    protected final static ColumnFamily<UUID, String> COLUMN_FAMILY_USER_SESSIONS = new ColumnFamily<UUID, String>("user_sessions",
            TimeUUIDSerializer.get(),
            StringSerializer.get(),
            ByteBufferSerializer.get());

    protected final static ColumnFamily<String, String> COLUMN_FAMILY_USER_SESSIONS_INVERTED = new ColumnFamily<String, String>("user_sessions_inverted",
            StringSerializer.get(),
            StringSerializer.get(),
            ByteBufferSerializer.get());

    protected DAOFactory daoFactory;

    public CassandraUserSessionDAO() {
        super();
    }

    @Override
    public UserSession save(UserSession userSession) {
        MutationBatch m = getKeyspace().prepareMutationBatch();
        m.withRow(COLUMN_FAMILY_USER_SESSIONS, userSession.getUser().getId())
                .putColumn(COLUMN_NAME_EXPIRATION_DATE, userSession.getExpirationDate())
                .putColumn(COLUMN_NAME_TOKEN, userSession.getToken());
        m.withRow(COLUMN_FAMILY_USER_SESSIONS_INVERTED, userSession.getToken())
                .putColumn(COLUMN_NAME_USER_ID, userSession.getUser().getId())
                .putColumn(COLUMN_NAME_EXPIRATION_DATE, userSession.getExpirationDate());
        try {
            OperationResult<Void> result = m.execute();
            return userSession;
        }
        catch (ConnectionException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void delete(UserSession userSession) {

    }

    @Override
    public UserSession findByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new PersistenceException("token cannot be null");
        }

        try {
            OperationResult<ColumnList<String>> result = getKeyspace()
                    .prepareQuery(COLUMN_FAMILY_USER_SESSIONS_INVERTED)
                    .getKey(token)
                    .execute();
            ColumnList<String> columns = result.getResult();
            if (columns.isEmpty()) {
                return null;
            }

            UserSession session = new UserSession();
            session.setToken(token);
            session.setExpirationDate(columns.getDateValue(COLUMN_NAME_EXPIRATION_DATE, null));
            session.setUser(daoFactory.createUserDAO().findById(columns.getUUIDValue(COLUMN_NAME_USER_ID, null)));
            return session;
        }
        catch (ConnectionException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public UserSession findByUser(User user) {
        if (user == null) {
            throw new PersistenceException("user cannot be null");
        }

        try {
            OperationResult<ColumnList<String>> result = getKeyspace()
                .prepareQuery(COLUMN_FAMILY_USER_SESSIONS)
                .getKey(user.getId())
                .execute();
            ColumnList<String> columns = result.getResult();
            if (columns.isEmpty()) {
                return null;
            }

            UserSession session = new UserSession();
            session.setToken(columns.getStringValue(COLUMN_NAME_TOKEN, StringUtils.EMPTY));
            session.setExpirationDate(columns.getDateValue(COLUMN_NAME_EXPIRATION_DATE, null));
            session.setUser(daoFactory.createUserDAO().findById(user.getId()));
            return session;
        }
        catch (ConnectionException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int deleteByUser(User user) {
        throw new NotImplementedException();
    }
}
