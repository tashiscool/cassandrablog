package org.lacassandra.smooshyfaces.persistence.cassandra;

import com.netflix.astyanax.annotations.Component;

import java.util.UUID;

public class BookCommentEntry {
    @Component(ordinal = 0)
    protected UUID timeUuid;

    @Component(ordinal = 1)
    protected UUID userId;

    protected String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public BookCommentEntry() {

    }

    public BookCommentEntry(final UUID timeUuid, final UUID userId) {
        this.timeUuid = timeUuid;
        this.userId = userId;
    }

    public UUID getTimeUuid() {
        return timeUuid;
    }

    public void setTimeUuid(final UUID timeUuid) {
        this.timeUuid = timeUuid;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(final UUID userId) {
        this.userId = userId;
    }
}
