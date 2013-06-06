package org.lacassandra.smooshyfaces.persistence;


import java.util.List;
import java.util.UUID;

import org.lacassandra.smooshyfaces.entity.Book;

public interface BookDAO extends BaseDAO<Book, UUID> {
    List<Book> findByTag(final String tag);
    List<Book> findByTag(final String tag, final UUID start, int pageSize);
}
