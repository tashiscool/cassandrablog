package com.welflex.service;

import java.util.List;
import java.util.Random;

import io.cassandra.sdk.exception.CassandraIoException;

import org.lacassandra.smooshyfaces.entity.Book;
import org.lacassandra.smooshyfaces.entity.User;
import org.lacassandra.smooshyfaces.persistence.cassandra.CassandraBookDAO;
import org.lacassandra.smooshyfaces.persistence.cassandra.CassandraTagsDAO;

public class UserServiceImpl implements UserService {

	CassandraTagsDAO tagsDAO;
	CassandraBookDAO booksDAO;
	UserDao userDAO;
	Random randomGenerator = new Random();
	
	
	public CassandraTagsDAO getTagsDAO() {
		return tagsDAO;
	}

	public void setTagsDAO(CassandraTagsDAO tagsDAO) {
		this.tagsDAO = tagsDAO;
	}

	public CassandraBookDAO getBooksDAO() {
		return booksDAO;
	}

	public void setBooksDAO(CassandraBookDAO booksDAO) {
		this.booksDAO = booksDAO;
	}

	public UserDao getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDao userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public User create(User newUser) {
		// TODO Auto-generated method stub
		try {
			userDAO.saveUser(newUser);
		} catch (CassandraIoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newUser;
	}

	@Override
	public User like(String userid, String isbn) {

		User user = null;
		try {
			user = userDAO.getUser(userid);
			Book books = booksDAO.findById(isbn);
			user.getBooks().add(books.getIsbn());
			user.getPreferences().addAll(books.getTags());
		} catch (CassandraIoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User dislike(String userid, String isbn) {
		User user = null;
		try {
			user = userDAO.getUser(userid);
			Book books = booksDAO.findById(isbn);
			user.getBooks().add(books.getIsbn());
			user.getPreferences().removeAll(books.getTags());
		} catch (CassandraIoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Book next(String userid) {

		User user = null;
		List<String> bookIsbns = null;
		try {
			user = userDAO.getUser(userid);
		} catch (CassandraIoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int number = randomGenerator.nextInt(user.getPreferences().size());
		
		try {
			bookIsbns = tagsDAO.findByTag(user.getPreferences().get(number));
		} catch (CassandraIoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		number = randomGenerator.nextInt(bookIsbns.size());
		
		return booksDAO.findById(bookIsbns.get(number));
	}

}
