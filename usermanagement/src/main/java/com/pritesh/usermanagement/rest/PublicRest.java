package com.pritesh.usermanagement.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pritesh.usermanagement.dto.LoginUserDto;
import com.pritesh.usermanagement.dto.SignUpUserDto;
import com.pritesh.usermanagement.service.UserServiceImpl;
import com.pritesh.usermanagement.utils.Constants;
import jakarta.validation.Valid;
@RestController()
@RequestMapping(path = Constants.PUBLIC_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicRest {
    
    @Autowired
    private UserServiceImpl userService;

    @GetMapping(Constants.PING_ENDPOINT)
    public ResponseEntity<String> getHelloWorld() {
        return ResponseEntity.ok(Constants.PONG);
    }

    @PostMapping(Constants.CREATE_USER_ENDPOINT)
    public ResponseEntity<Map<String, Object>> createUser(@Valid @RequestBody SignUpUserDto user) {
        return userService.createUser(user);
    }

    @PostMapping(Constants.UPDATE_USER_ENDPOINT)
    public ResponseEntity<Map<String, Object>> updateUser(@Valid @RequestBody SignUpUserDto user) {
        return userService.updateUser(user);
    }

    @PostMapping(Constants.LOGIN_ENDPOINT)
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        return userService.login(loginUserDto);
    }

    @PostMapping(Constants.SEND_OTP_ENDPOINT)
    public ResponseEntity<Map<String, Object>> sendOtp(@PathVariable String email) {
        return userService.sendOtp(email);
    }

    @PostMapping(Constants.VERIFY_OTP_ENDPOINT)
    public ResponseEntity<Map<String, Object>> verifyOtp(@PathVariable String email,@PathVariable String otp) {
        return userService.verifyOtp(email, otp);
    }

}
