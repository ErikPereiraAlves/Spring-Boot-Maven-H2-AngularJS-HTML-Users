package com.erikalves.application.controllers;


import com.erikalves.application.model.User;
import com.erikalves.application.service.UserService;
import com.erikalves.application.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
class UserRestApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestApiController.class);

    @Autowired
    private UserService userService;

    // GET ALL
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findAll() {

        LOGGER.debug("[REST API CONTROLLER] Return all users ");
        List <User> users =Util.iterableToCollection(userService.findAll());
        if(null!=users && users.size() >0) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //GET ONE
    @GetMapping(value = "/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User>  findOne(@PathVariable("user_id") String userId) {

        LOGGER.debug("[REST API CONTROLLER] Return a user "+userId);
        User user =userService.get(Util.LongfyId(userId));
        LOGGER.debug("*** user found in H2 {}",user);
        if(null!=user) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //DELETE
    @DeleteMapping(value = "/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable("user_id") String userId) {

        LOGGER.debug("[REST API CONTROLLER] Delete a user "+userId);
        userService.delete(Util.LongfyId(userId));
        return ResponseEntity.ok((userId));
    }

    // CREATE
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestBody User user) {

        LOGGER.debug("[REST API CONTROLLER] Create a user "+user);
        User createdUser = userService.save(user);
        return ResponseEntity.created(URI.create("/" + createdUser.getUserId())).body((createdUser));
    }


    // UPDATE
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody User user) {

        LOGGER.debug("[REST API CONTROLLER] Update a user "+user);
        userService.update(user);
        return ResponseEntity.noContent().build();
    }

}