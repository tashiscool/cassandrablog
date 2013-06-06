package com.welflex.service;

import org.lacassandra.smooshyfaces.entity.Book;

public class BookServiceImpl implements BookService {

	@Override
	public void create(Book blog) {
		daoFactory.createBookDAO().save(blog);
	}


}
