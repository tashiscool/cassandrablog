package com.welflex.service;

import io.cassandra.sdk.exception.CassandraIoException;

import org.lacassandra.smooshyfaces.entity.Book;
import org.lacassandra.smooshyfaces.persistence.TagsDAO;
import org.lacassandra.smooshyfaces.persistence.cassandra.CassandraBookDAO;

public class BookServiceImpl implements BookService {

	private CassandraBookDAO bookDao;
	private TagsDAO tagDao;
	@Override
	public void create(Book blog) throws CassandraIoException {
		bookDao.save(blog);
		String bookIsbn = blog.getIsbn();
		for (String tag : blog.getTags())
			tagDao.saveTag(tag, bookIsbn );
	}
	public CassandraBookDAO getBookDao() {
		return bookDao;
	}
	public void setBookDao(CassandraBookDAO bookDao) {
		this.bookDao = bookDao;
	}
	public TagsDAO getTagDao() {
		return tagDao;
	}
	public void setTagDao(TagsDAO tagDao) {
		this.tagDao = tagDao;
	}


}
