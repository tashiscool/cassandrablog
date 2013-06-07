package com.welflex.service;

import org.lacassandra.smooshyfaces.entity.Book;
import org.lacassandra.smooshyfaces.entity.User;

public interface UserService {

	User create(User newUser);

	User like(String userid, String isbn);

	User dislike(String userid, String isbn);

	Book next(String userid);

}
