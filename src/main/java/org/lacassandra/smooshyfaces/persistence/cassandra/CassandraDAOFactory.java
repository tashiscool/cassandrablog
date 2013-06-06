package org.lacassandra.smooshyfaces.persistence.cassandra;

//import com.google.inject.Injector;
import org.lacassandra.smooshyfaces.persistence.*;

//import javax.inject.Inject;

public class CassandraDAOFactory extends DAOFactory {

	private UserDAO userDao;
	private UserSessionDAO userSessionDao;
	private BookDAO bookDao;

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public UserSessionDAO getUserSessionDao() {
		return userSessionDao;
	}

	public void setUserSessionDao(UserSessionDAO userSessionDao) {
		this.userSessionDao = userSessionDao;
	}

	public BookDAO getBookDao() {
		return bookDao;
	}

	public void setBookDao(BookDAO bookDao) {
		this.bookDao = bookDao;
	}

	@Override
	public UserDAO createUserDAO() {
		// TODO Auto-generated method stub
		return userDao;
	}

	@Override
	public UserSessionDAO createUserSessionDAO() {
		// TODO Auto-generated method stub
		return userSessionDao;
	}

	@Override
	public BookDAO createBookDAO() {
		// TODO Auto-generated method stub
		return bookDao;
	}

}
