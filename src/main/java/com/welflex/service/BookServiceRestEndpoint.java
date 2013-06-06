package com.welflex.service;

import org.lacassandra.smooshyfaces.entity.Book;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/book")
public class BookServiceRestEndpoint
{
	
	private BookService bookService;
	
	@RequestMapping("/create")
	@ResponseBody
	public Book create(@RequestBody Book blog)
	{
		bookService.create(blog);
		return blog;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService blogService) {
		this.bookService = blogService;
	}
	

}
