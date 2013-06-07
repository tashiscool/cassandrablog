package com.welflex.service;

import io.cassandra.sdk.exception.CassandraIoException;

import org.lacassandra.smooshyfaces.entity.Book;

public interface BookService {

	void create(Book blog) throws CassandraIoException;

}
