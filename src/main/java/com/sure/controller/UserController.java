package com.sure.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by SURE on ${DATA}.
 */
@RestController
public class UserController {

    @RequestMapping("/login")
    public String login() {
        System.out.println("hello");
        return "hello";
    }

}
