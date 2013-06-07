package com.welflex.service;

import io.cassandra.sdk.exception.CassandraIoException;

import org.lacassandra.smooshyfaces.entity.Book;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/book")
public class BookServiceRestEndpoint
{
	
	private BookService bookService;
	
	@RequestMapping("/create")
	@ResponseBody
	public Book create(@RequestBody Book blog)
	{
		try {
			bookService.create(blog);
		} catch (CassandraIoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blog;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService blogService) {
		this.bookService = blogService;
	}
	

}
