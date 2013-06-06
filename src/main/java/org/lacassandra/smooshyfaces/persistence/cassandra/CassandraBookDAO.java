package org.lacassandra.smooshyfaces.persistence.cassandra;

import com.google.common.base.Joiner;
import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.retry.ExponentialBackoff;
import com.netflix.astyanax.serializers.AnnotatedCompositeSerializer;
import com.netflix.astyanax.serializers.ByteBufferSerializer;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.serializers.TimeUUIDSerializer;
import com.netflix.astyanax.util.TimeUUIDUtils;
import org.apache.commons.lang.NotImplementedException;
import org.lacassandra.smooshyfaces.entity.Book;
import org.lacassandra.smooshyfaces.persistence.PersistenceException;
import org.lacassandra.smooshyfaces.persistence.BookDAO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CassandraBookDAO extends BaseCassandraDAO<Book, UUID> implements BookDAO {
    public final static String COLUMN_NAME_ID = "id";
    public final static String COLUMN_NAME_CREATED_ON = "created_on";
    public final static String COLUMN_NAME_MODIFIED_ON = "modified_on";
    public final static String COLUMN_NAME_DESCRIPTION = "description";
    public final static String COLUMN_NAME_TAGS = "tags";
    public final static String COLUMN_NAME_CONTENT_LOCATION = "content_location";
    public final static String COLUMN_NAME_OWNER_ID = "owner_id";
    public final static String ROW_KEY_ALL_BOOKS = "all";

    protected final static AnnotatedCompositeSerializer<BookCommentEntry> BOOK_COMMENT_SERIALIZER =
            new AnnotatedCompositeSerializer<BookCommentEntry>(BookCommentEntry.class);

    public final static ColumnFamily<UUID, String> COLUMN_FAMILY_BOOKS = new ColumnFamily<UUID, String>("BOOKs",
            TimeUUIDSerializer.get(),
            StringSerializer.get(),
            ByteBufferSerializer.get());

    public final static ColumnFamily<String, UUID> COLUMN_FAMILY_TIMELINES = new ColumnFamily<String, UUID>("timelines",
            StringSerializer.get(),
            TimeUUIDSerializer.get(),
            TimeUUIDSerializer.get());

    public final static ColumnFamily<UUID, BookCommentEntry> COLUMN_FAMILY_BOOK_COMMENTS = new ColumnFamily<UUID, BookCommentEntry>("BOOK_comments",
            TimeUUIDSerializer.get(),
            BOOK_COMMENT_SERIALIZER,
            StringSerializer.get());

    public final static ColumnFamily<String, UUID> COLUMN_FAMILY_TAGGED_BOOKS = new ColumnFamily<String, UUID>("tagged_BOOKs",
            StringSerializer.get(),
            TimeUUIDSerializer.get(),
            TimeUUIDSerializer.get());

    @Override
    public Book findById(final UUID id) {
        // You figure this one out :)
        throw new NotImplementedException();
    }

    @Override
    public Book save(final Book entity) {
//        Preconditions.checkArgument(entity != null, "BOOK cannot be null", PersistenceException.class);
//        Preconditions.checkArgument(entity.getOwner() != null, "BOOK must have an owner", PersistenceException.class);
//        Preconditions.checkArgument(entity.getOwner().getId() != null, "a user without an ID...that's just silly", PersistenceException.class);
//        Preconditions.checkArgument(entity.getContentLocation() != null, "how will i ever find the bloody BOOK if you don't tell me where it is?!", PersistenceException.class);

        if (entity.getId() == null) {
            entity.setId(TimeUUIDUtils.getUniqueTimeUUIDinMillis());
        }
        if (entity.getCreatedOn() == null) {
            entity.setCreatedOn(new Date());
        }
        entity.setModifiedOn(new Date());

        MutationBatch m = getKeyspace().prepareMutationBatch().withRetryPolicy(new ExponentialBackoff(250, 10));

        m.withRow(COLUMN_FAMILY_BOOKS, entity.getId())
                .putColumn(COLUMN_NAME_ID, entity.getId())
                .putColumn(COLUMN_NAME_CREATED_ON, entity.getCreatedOn())
                .putColumn(COLUMN_NAME_MODIFIED_ON, entity.getModifiedOn())
                .putColumn(COLUMN_NAME_DESCRIPTION, entity.getDescription())
                .putColumn(COLUMN_NAME_CONTENT_LOCATION, entity.getContentLocation())
                .putColumn(COLUMN_NAME_TAGS, Joiner.on(',').join(entity.getTags()));

        // Do you see a problem here? Remember that writing a column is destructive
        // if the names are the same. However, if the names are different, a new column will
        // be inserted. Since we have UUID as names (b/c of time series), we will end up having
        // one BOOK come up multiple times every time the object is saved and its tags
        // are processed. Sooooo.... how would you solve this?
        // Ideas:
        // 1) You could have an inverted column family as an index
        // 2) You could change the comparator for tagged_BOOKs to be CompositeType(DateType, TimeUUIDType)
        //    so that you save the first component as the BOOKs createdOn date and the BOOK id as the
        //    2nd component.
        // 3) There are more... it's up to you
        for (String tag : entity.getTags()) {
            m.withRow(COLUMN_FAMILY_TAGGED_BOOKS, tag).putColumn(TimeUUIDUtils.getUniqueTimeUUIDinMillis(), entity.getId());
        }

        m.withRow(COLUMN_FAMILY_TIMELINES, ROW_KEY_ALL_BOOKS).putColumn(TimeUUIDUtils.getUniqueTimeUUIDinMillis(), entity.getId());

        try {
            OperationResult<Void> result = m.execute();
            return entity;
        }
        catch (ConnectionException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public List<Book> findByTag(final String tag) {
        // You figure this one out :)
        throw new NotImplementedException();
    }

    @Override
    public List<Book> findByTag(final String tag, final UUID start, final int pageSize) {
        // You figure this one out :)
        throw new NotImplementedException();
    }
}
