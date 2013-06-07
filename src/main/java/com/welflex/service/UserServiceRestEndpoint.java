/**
 * 
 */
package com.welflex.service;

import org.lacassandra.smooshyfaces.entity.Book;
import org.lacassandra.smooshyfaces.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author UPAPIFR
 *
 */
@Controller
@RequestMapping("/user")
public class UserServiceRestEndpoint {

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	private UserService userService;
	@RequestMapping( method = RequestMethod.POST, value = "/create", produces="application/json")
	@ResponseBody
	public User create(@RequestBody User newUser) {
		
		User u = userService.create(newUser);
		return u;
	}

	@RequestMapping(value = "/{userid}/like/{isbn}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public User likeBook(@PathVariable String userid, @PathVariable String isbn)
	{
		User user = userService.like(userid,isbn); 
		return user;
	}


	@RequestMapping(value = "/{userid}/dislike/{isbn}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public User disLikeBook(@PathVariable String userid, @PathVariable String isbn)
	{
		User user = userService.dislike(userid,isbn); 
		return user;
	}
	@RequestMapping(value = "/{userid}/next", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Book next(@PathVariable String userid)
	{
		return userService.next(userid);
	}

}
