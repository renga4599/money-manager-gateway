package com.renga.money.manager.gateway.service;

import com.renga.money.manager.common.models.UserDetails;
import com.renga.money.manager.common.models.UserDetailsRequestWrapper;


import java.util.List;

public interface UserDetailsService {
    UserDetails findUserDetailsByEmail(String email);
    List<UserDetails> fetchAllUserDetails();
    UserDetails addUser(UserDetailsRequestWrapper userDetailsRequestWrapper);
}
