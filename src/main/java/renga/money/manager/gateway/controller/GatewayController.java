package renga.money.manager.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import renga.money.manager.common.models.UserDetails;

@RestController
public class GatewayController {

    @GetMapping("/api/v1/sayHello")
    public ResponseEntity<UserDetails> sayHello(){
        UserDetails userDetails = new UserDetails();
        userDetails.setAge(35);
        userDetails.setName("Rengarajan");
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }
}
