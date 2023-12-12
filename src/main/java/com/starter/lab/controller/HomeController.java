package com.starter.lab.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/hi")
    public String hello(){
        return "hello";
    }
}
