package org.lacassandra.smooshyfaces.persistence;


import io.cassandra.sdk.exception.CassandraIoException;

import java.util.List;
import java.util.UUID;

import org.lacassandra.smooshyfaces.entity.Book;

public interface TagsDAO {
	
	void saveTag(final String tag, String bookIsbn) throws CassandraIoException;
	// return a list of isbns
	List<String> findByTag(String tag) throws CassandraIoException;
}
