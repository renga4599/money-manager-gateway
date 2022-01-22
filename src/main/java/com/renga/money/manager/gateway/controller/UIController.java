package com.renga.money.manager.gateway.controller;

import com.renga.money.manager.common.models.UserDetails;
import com.renga.money.manager.common.models.UserDetailsRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UIController {

    @Autowired
    private GatewayController gatewayController;
/*
    @GetMapping("/")
    public String index(){
        return "index";
    }*/

    @GetMapping("/showRegistrationPage")
    public String showRegistrationPage(Model model){
        model.addAttribute("userDetailsRequestWrapper", new UserDetailsRequestWrapper());
        return "registrationForm";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDetailsRequestWrapper") UserDetailsRequestWrapper userDetailsRequestWrapper){
        gatewayController.addUser(userDetailsRequestWrapper);
        return "registrationResponsePage";
    }

    @GetMapping("/showUserDetailsPage")
    public String showUserDetailsPage(Model model){
        model.addAttribute("userDetailsRequestWrapper", new UserDetailsRequestWrapper());
        return "userSearchPage";
    }

    @PostMapping("/searchUserDetails")
    public ModelAndView searchUserDetails(@ModelAttribute("userDetailsRequestWrapper") UserDetailsRequestWrapper userDetailsRequestWrapper){
        ModelAndView modelAndView = new ModelAndView("userDetails");
        ResponseEntity<UserDetails> userDetailsByEmail = gatewayController.getUserDetailsByEmail(userDetailsRequestWrapper.getEmail());
        modelAndView.addObject("userDetails", userDetailsByEmail.getBody());
//        modelAndView.addObject(userDetailsByEmail.getBody());
        return modelAndView;
    }
}
