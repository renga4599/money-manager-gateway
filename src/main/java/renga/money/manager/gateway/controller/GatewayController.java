package renga.money.manager.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import renga.money.manager.common.models.UserDetails;
import renga.money.manager.common.models.UserDetailsWrapper;
import renga.money.manager.gateway.service.UserDetailsService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RestController
public class GatewayController {

    private final UserDetailsService userDetailsService;

    @Autowired
    public GatewayController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<UserDetailsWrapper> getAllUserDetails(){
        try {
            log.debug("GatewayController - getAllUserDetails - starts");
            List<Integer> userIds = IntStream.range(1, 25).boxed().collect(Collectors.toList());
            List<UserDetails> userDetailsList = userDetailsService.fetchAllUserDetails(userIds);
            userDetailsService.fetchAllUserDetailsWithSingleThread(userIds);

            return new ResponseEntity<>(new UserDetailsWrapper(userDetailsList), HttpStatus.OK);
        }catch (Exception e){
            log.error("getAllUserDetails - exception in fetching all user details. ", e);
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
