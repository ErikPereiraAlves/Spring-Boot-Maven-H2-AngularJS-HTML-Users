package com.erikalves.application.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebAppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebAppController.class);

    @RequestMapping("/")
    String home(ModelMap modal) {
        modal.addAttribute("title","Erik Alves - Users list");
        return "users";
    }


}

