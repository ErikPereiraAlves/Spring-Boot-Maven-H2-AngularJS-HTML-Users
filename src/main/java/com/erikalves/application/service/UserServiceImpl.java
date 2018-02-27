package com.erikalves.application.service;

import com.erikalves.application.model.User;
import com.erikalves.application.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Long getId(User entity) {
        return entity.getUserId();
    }

    @Override
    public CrudRepository<User, Long> getRepository() {
        return this.userRepository;
    }

    @Override
    public User findUserByName(String name) {
        return userRepository.findUserByName(name);
    }

}
