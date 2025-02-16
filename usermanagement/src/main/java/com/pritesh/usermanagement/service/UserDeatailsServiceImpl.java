package com.pritesh.usermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.pritesh.usermanagement.model.User;
import com.pritesh.usermanagement.repository.IUserRepository;
import com.pritesh.usermanagement.utils.Constants;

@Component
public class UserDeatailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user == null) {
            throw new UsernameNotFoundException(Constants.USER_NOT_FOUND_MESSAGE);
        }
        return org.springframework.security.core.userdetails.User.builder().username(user.getUserName()).password(user.getPassword()).roles(new String[0]).build();
    }
}
