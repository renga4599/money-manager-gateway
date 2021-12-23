package renga.money.manager.gateway.service;

import renga.money.manager.common.models.UserDetails;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface UserDetailsService {
    List<UserDetails> fetchAllUserDetails(List<Integer> userIds) throws ExecutionException, InterruptedException, TimeoutException;
    List<UserDetails> fetchAllUserDetailsWithSingleThread(List<Integer> userIds);
}
