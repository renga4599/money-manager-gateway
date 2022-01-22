package com.renga.money.manager.gateway.controller;

import com.renga.money.manager.common.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private SecurityService securityService;

    @GetMapping("/")
    public String showLoginPage(){
        return "login";
    }

    @PostMapping("/submitForm")
    public String login(String email, String password){
        boolean loginResponse = securityService.login(email, password);
        if(loginResponse){
            return "index";
        }
        return "login";
    }
}
