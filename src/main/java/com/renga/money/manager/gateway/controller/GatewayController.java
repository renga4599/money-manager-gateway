package com.renga.money.manager.gateway.controller;

import com.renga.money.manager.common.models.UserDetails;
import com.renga.money.manager.common.models.UserDetailsWrapper;
import com.renga.money.manager.common.models.UserDetailsRequestWrapper;
import com.renga.money.manager.gateway.service.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@Slf4j
@RestController
public class GatewayController {

    private final UserDetailsService userDetailsService;
    private final String message;

    @Autowired
    public GatewayController(UserDetailsService userDetailsService,
                             @Value("${renga.name:Test}") String message
//                             @Value("${spring.renga.name}") String message
    ) {
        this.userDetailsService = userDetailsService;
        this.message = message;
    }

    @GetMapping("/api/v1/user")
    public ResponseEntity<UserDetails> getUserDetailsByEmail(@RequestParam(name = "email") String email){
        try{
            UserDetails userDetailsByEmail = userDetailsService.findUserDetailsByEmail(email);
            if(Objects.isNull(userDetailsByEmail)){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(userDetailsByEmail, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<UserDetailsWrapper> getAllUserDetails() {
        try {
            log.debug("GatewayController - getAllUserDetails - starts message: {}", message);
            List<UserDetails> userDetailsList = userDetailsService.fetchAllUserDetails();
            return new ResponseEntity<>(new UserDetailsWrapper(userDetailsList), HttpStatus.OK);
        } catch (Exception e) {
            log.error("getAllUserDetails - exception in fetching all user details. ", e);
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PostMapping(value = "/api/v1/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetails> addUser(@RequestBody UserDetailsRequestWrapper userDetailsRequestWrapper) {
        try {
            log.debug("GatewayController - addUser - starts {}", userDetailsRequestWrapper);
            UserDetails userDetails = userDetailsService.addUser(userDetailsRequestWrapper);
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getAllUserDetails - exception in adding user details. ", e);
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }



    @GetMapping("/api/v1/hello")
    public ResponseEntity<String> sayHello() {
        return new ResponseEntity<>("Welcome to Spring security", HttpStatus.OK);
    }

    @GetMapping("/api/v1/shutdown")
    public ResponseEntity<String> shutdown() {
        return new ResponseEntity<>("Hey! You can not shutdown!!!", HttpStatus.OK);
    }
}
