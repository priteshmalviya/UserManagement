package com.pritesh.usermanagement.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.pritesh.usermanagement.dto.SignUpUserDto;

public class Utils {

    public static Map<String, Object> generateResponse(String status, Object message) {
        Map<String, Object> response = new HashMap<>();
        response.put(Constants.STATUS_STRING, status);
        response.put(Constants.MESSAGE_STRING, message);
        return response;
    }

    public static String generateOtp(Integer length) {
        // Handle invalid length
        if (length == null || length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }
        
        // Create a random number generator
        Random random = new Random();
        
        // StringBuilder to store the OTP
        StringBuilder otp = new StringBuilder();
        
        // Generate random digits
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }
        
        return otp.toString();
    }

    public static String user(SignUpUserDto signUpUserDto) {
        if (signUpUserDto.getUserName() == null || signUpUserDto.getUserName().isEmpty()) {
            return Constants.USERNAME_CANNOT_BE_EMPTY;
        }
        if (signUpUserDto.getEmail() == null || signUpUserDto.getEmail().isEmpty() || !signUpUserDto.getEmail().matches(Constants.EMAIL_REGEX)) {
            return Constants.INVALID_EMAIL;
        }
        if (signUpUserDto.getPassword() == null || signUpUserDto.getPassword().isEmpty() || signUpUserDto.getPassword().length() < 8) {
            return Constants.PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS_LONG;
        }
        if (signUpUserDto.getName() == null || signUpUserDto.getName().isEmpty()) {
            return Constants.NAME_CANNOT_BE_EMPTY;
        }
        return Constants.SUCCESS_STRING;
    }
}
