package com.welflex.service;

import java.util.UUID;

import org.lacassandra.smooshyfaces.entity.Book;
import org.lacassandra.smooshyfaces.persistence.DAOFactory;

public class BookServiceImpl implements BookService {

	DAOFactory daoFactory;
	
	public DAOFactory getDaoFactory() {
		return daoFactory;
	}

	public void setDaoFactory(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void create(Book blog) {
		blog.setId(UUID.randomUUID());
		daoFactory.createBookDAO().save(blog);
	}

}
