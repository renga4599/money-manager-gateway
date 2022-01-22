package com.renga.money.manager.gateway.service.impl;

import com.renga.money.manager.common.models.UserDetails;
import com.renga.money.manager.common.models.entities.UserDetailsEntity;
import com.renga.money.manager.common.repositories.UserDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.renga.money.manager.common.models.UserDetailsRequestWrapper;
import com.renga.money.manager.gateway.service.UserDetailsService;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;
    private final ExecutorService mmFixedCachedThreadPoolExecutor;


    @Autowired
    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository,
                                  @Qualifier("mmFixedCachedThreadPoolExecutor") ExecutorService mmFixedCachedThreadPoolExecutor) {
        this.userDetailsRepository = userDetailsRepository;
        this.mmFixedCachedThreadPoolExecutor = mmFixedCachedThreadPoolExecutor;
    }

    @Override
    public UserDetails findUserDetailsByEmail(String email) {
        UserDetailsEntity userDetailsEntity = userDetailsRepository.findByEmail(email);
        if(Objects.nonNull(userDetailsEntity)){
            return new UserDetails(userDetailsEntity);
        }
        return null;
    }

    @Override
    public List<UserDetails> fetchAllUserDetails() {
        Iterable<UserDetailsEntity> userDetailsEntities = userDetailsRepository.findAll();
        return StreamSupport.stream(userDetailsEntities.spliterator(), false)
                .map(UserDetails::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails addUser(UserDetailsRequestWrapper userDetailsRequestWrapper) {
        UserDetailsEntity userDetailsEntity = userDetailsRepository.save(new UserDetailsEntity(userDetailsRequestWrapper));
        return new UserDetails(userDetailsEntity);
    }


/*
    @Override
    public List<UserDetails> fetchAllUserDetails(List<Integer> userIds) throws ExecutionException, InterruptedException, TimeoutException {
        LocalDateTime startTime = LocalDateTime.now();
        Future<List<UserDetails>> userDetailsFuture = mmFixedCachedThreadPoolExecutor.submit(() ->
                userIds.parallelStream()
                        .map(this::getUserDetails)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
        log.info("fetchAllUserDetails - userDetailsFuture - waiting to get the future object");
        List<UserDetails> userDetailsList = userDetailsFuture.get(300, TimeUnit.SECONDS);
        log.info("Time taken to complete using executor service threads {}", ChronoUnit.SECONDS.between(startTime, LocalDateTime.now()));
        return userDetailsList;
    }
*/
}
