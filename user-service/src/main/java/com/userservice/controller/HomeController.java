package com.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/home")
public class HomeController {

    // This is a test endpoint to check if the application is running
    @GetMapping
    public String test() {
        return "Home Controller is working!";
    }

}
