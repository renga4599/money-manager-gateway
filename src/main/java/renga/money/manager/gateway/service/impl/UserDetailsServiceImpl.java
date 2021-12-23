package renga.money.manager.gateway.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import renga.money.manager.common.models.UserDetails;
import renga.money.manager.gateway.service.UserDetailsService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ExecutorService mmFixedCachedThreadPoolExecutor;

    @Autowired
    public UserDetailsServiceImpl(@Qualifier("mmFixedCachedThreadPoolExecutor") ExecutorService mmFixedCachedThreadPoolExecutor) {
        this.mmFixedCachedThreadPoolExecutor = mmFixedCachedThreadPoolExecutor;
    }

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

    @Override
    public List<UserDetails> fetchAllUserDetailsWithSingleThread(List<Integer> userIds) {
        LocalDateTime startTime = LocalDateTime.now();
        List<UserDetails> userDetailsList =  userIds.stream()
                .map(this::getUserDetails)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        log.info("Time taken to complete using single thread {}", ChronoUnit.SECONDS.between(startTime, LocalDateTime.now()));
        return userDetailsList;
    }

    private UserDetails getUserDetails(int id) {
        try {
            log.info("getUserDetails - thread {} is going to sleep 3 seconds", Thread.currentThread().getName());
            Thread.sleep(3000);
            log.info("getUserDetails - thread {} is waking after 3 seconds", Thread.currentThread().getName());
            return new UserDetails("name_" + id, id);

        } catch (InterruptedException e) {
            log.error("getUserDetails exception while creating user id {}", id, e);
            return null;
        }
    }
}
