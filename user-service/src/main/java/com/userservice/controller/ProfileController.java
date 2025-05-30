package com.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/profile")
public class ProfileController {

    // This controller handles requests related to user profiles.
    @GetMapping
    public String test() {
        return "Profile Controller is working!";
    }

}
