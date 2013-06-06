package com.welflex.service;

import org.lacassandra.smooshyfaces.entity.Book;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welflex.model.BlogEntry;

@Controller("/")
public class BookServiceRestEndpoint 
{
	
	private BookService bookService;
	
	@RequestMapping("Book")
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
