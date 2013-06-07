package com.welflex.service;

import org.lacassandra.smooshyfaces.entity.Book;
import org.lacassandra.smooshyfaces.persistence.cassandra.CassandraBookDAO;

public class BookServiceImpl implements BookService {

	private CassandraBookDAO bookDao;
	@Override
	public void create(Book blog) {
		bookDao.save(blog);
	}


}
