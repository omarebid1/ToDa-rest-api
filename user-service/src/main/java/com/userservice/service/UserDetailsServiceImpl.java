package com.userservice.service;

import com.userservice.entity.User;
import com.userservice.exception.UserNotFoundEx;
import com.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundEx {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundEx("User not found with email: " + email);
        }
        return user;
    }
}
