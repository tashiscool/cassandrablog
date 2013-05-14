package com.welflex.service;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welflex.model.BlogEntry;

@Controller
public class BlogServiceRestEndpoint 
{
	
	private BlogService blogService;
	
	@RequestMapping("")
	@ResponseBody
	public BlogEntry create(@RequestBody BlogEntry blog)
	{
		blogService.create(blog);
		return blog;
		
	}
	

}
