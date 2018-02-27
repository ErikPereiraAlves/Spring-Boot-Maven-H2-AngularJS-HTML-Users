package com.erikalves.application.service;


import com.erikalves.application.model.User;


public interface UserService extends GenericService<User,Long> {

   public User findUserByName(String name);
}
