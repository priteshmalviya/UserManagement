package com.pritesh.usermanagement.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pritesh.usermanagement.service.UserServiceImpl;
import com.pritesh.usermanagement.utils.Constants;

@RestController()
@RequestMapping(path = Constants.USER_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestImple {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping(Constants.GET_ALL_USERS_ENDPOINT)
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(Constants.GET_USER_BY_ID_ENDPOINT)
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping(Constants.GET_USER_BY_USERNAME_ENDPOINT)
    public ResponseEntity<Map<String, Object>> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping(Constants.GET_USER_BY_EMAIL_ENDPOINT)
    public ResponseEntity<Map<String, Object>> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

}
