package com.pritesh.usermanagement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pritesh.usermanagement.dto.LoginUserDto;
import com.pritesh.usermanagement.dto.SignUpUserDto;
import com.pritesh.usermanagement.model.User;
import com.pritesh.usermanagement.repository.IUserRepository;
import com.pritesh.usermanagement.utils.Constants;
import com.pritesh.usermanagement.utils.JwtUtils;
import com.pritesh.usermanagement.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public ResponseEntity<Map<String, Object>> createUser(SignUpUserDto userDto) {
        log.debug("Attempting to create user with username: {}", userDto.getUserName());

        User existingUser = userRepository.findByUserName(userDto.getUserName());
        if (existingUser != null) {
            log.error("User with username {} already exists", userDto.getUserName());
            return ResponseEntity.status(400).body(
                Utils.generateResponse(Constants.FAILED_STRING, Constants.USER_ALREADY_EXISTS_WITH_THIS_USERNAME)
            );
        }

        existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser != null) {
            log.error("User with email {} already exists", userDto.getEmail());
            return ResponseEntity.status(400).body(
                Utils.generateResponse(Constants.FAILED_STRING, Constants.USER_ALREADY_EXISTS_WITH_THIS_EMAIL)
            );
        }
        
        String validationResult = Utils.user(userDto);
        if (validationResult != Constants.SUCCESS_STRING) {
            log.warn("Validation failed for user: {}", userDto.getUserName());
            return ResponseEntity.status(400).body(
                Utils.generateResponse(Constants.FAILED_STRING, validationResult)
            );
        }

        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());

        log.debug("User before saving: {}", user);
        user = userRepository.save(user);
        log.info("User created successfully: {}", user);

        return ResponseEntity.status(200).body(
            Utils.generateResponse(Constants.SUCCESS_STRING, Constants.USER_CREATED_SUCCESSFULLY)
        );
    }

    public ResponseEntity<Map<String, Object>> getAllUsers() {
        log.debug("Fetching all users");
        List<User> users = userRepository.findAll();
        log.info("Number of users fetched: {}", users.size());

        if(users.isEmpty()) {
            return ResponseEntity.status(404).body(
                Utils.generateResponse(Constants.FAILED_STRING, Constants.USER_NOT_FOUND_MESSAGE)
            );
        }

        return ResponseEntity.status(200).body(
            Utils.generateResponse(Constants.SUCCESS_STRING, users)
        );
    }

    public ResponseEntity<Map<String, Object>> getUserById(Long id) {
        log.debug("Fetching user by ID: {}", id);
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.warn("User with ID {} not found", id);
            return ResponseEntity.status(404).body(
                Utils.generateResponse(Constants.FAILED_STRING, Constants.USER_NOT_FOUND_MESSAGE)
            );
        }
        log.info("User found: {}", user);
        return ResponseEntity.status(200).body(
            Utils.generateResponse(Constants.SUCCESS_STRING, user)
        );
    }

    public ResponseEntity<Map<String, Object>> getUserByUsername(String username) {
        Map<String, Object> response = new HashMap<>();
        log.debug("Fetching user by username: {}", username);
        User user = userRepository.findByUserName(username);
        if (user == null) {
            log.warn("User with username {} not found", username);
            response.put(Constants.STATUS_STRING, Constants.FAILED_STRING);
            return ResponseEntity.status(404).body(
                Utils.generateResponse(Constants.FAILED_STRING, Constants.USER_NOT_FOUND_MESSAGE)
            );
        }
        log.info("User found: {}", user);
        return ResponseEntity.status(200).body(
            Utils.generateResponse(Constants.SUCCESS_STRING, user)
        );
    }

    public ResponseEntity<Map<String, Object>> getUserByEmail(String email) {
        log.debug("Fetching user by email: {}", email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.warn("User with email {} not found", email);
            return ResponseEntity.status(404).body(
                Utils.generateResponse(Constants.FAILED_STRING, Constants.USER_NOT_FOUND_MESSAGE)
            );
        }
        log.info("User found: {}", user);
        return ResponseEntity.status(200).body(
            Utils.generateResponse(Constants.SUCCESS_STRING, user)
        );
    }

    public ResponseEntity<Map<String, Object>> login(LoginUserDto loginUserDto) {
        Map<String, Object> response = new HashMap<>();

        log.debug("Attempting login for username: {}",loginUserDto.getUserName());
        log.debug("Attempting login for email: {}",loginUserDto.getEmail());
        log.debug("Attempting login for password: {}",loginUserDto.getPassword());
        
        User user = null;
        if (loginUserDto.getUserName() != null) {
            user = userRepository.findByUserName(loginUserDto.getUserName());
        } else if (loginUserDto.getEmail() != null) {
            user = userRepository.findByEmail(loginUserDto.getEmail());
        } else {
            log.error("Invalid login attempt: no username or email provided");
            return ResponseEntity.status(400).body(
                Utils.generateResponse(Constants.FAILED_STRING, Constants.INVALID_USERNAME_OR_EMAIL)
            );
        }
        
        if (user == null) {
            log.warn("Login failed: user not found");
            return ResponseEntity.status(400).body(
                Utils.generateResponse(Constants.FAILED_STRING, Constants.USER_NOT_FOUND_MESSAGE)
            );
        }

        if (passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUserName());
            log.info("Login successful for user: {}", user.getUserName());
            log.info("Token: {}", token);
            response = Utils.generateResponse(Constants.SUCCESS_STRING, Constants.LOGIN_SUCCESSFUL);
            response.put(Constants.TOKEN_STRING, token);
            return ResponseEntity.status(200).body(response);
        } else {
            log.warn("Login failed: invalid password for user: {}", user.getUserName());
            return ResponseEntity.status(400).body(
                Utils.generateResponse(Constants.FAILED_STRING, Constants.INVALID_PASSWORD)
            );
        }
    }

    public ResponseEntity<Map<String, Object>> sendOtp(String email,String username) {
        log.debug("Attempting to send otp for email: {}", email);
        // User user = userRepository.findByEmail(email);
        if (email.matches(Constants.EMAIL_REGEX)) {
            String otp = Utils.generateOtp(6);
            String message = String.format(Constants.FORGOT_PASSWORD_MAIL_BODY, username, otp);
            boolean isEmailSent = emailService.sendEmail(email, Constants.FORGOT_PASSWORD_MAIL_SUBJECT, message);
            if (isEmailSent) {
                redisTemplate.opsForValue().set(email, otp);
                log.info("Forgot password successful for user: {}", email);
                return ResponseEntity.status(200).body(
                    Utils.generateResponse(Constants.SUCCESS_STRING, Constants.MAIL_SENT_SUCCESSFUL_MESSAGE)
                );
            }else{
                log.warn("Forgot password failed for user: {}", email);
                return ResponseEntity.status(400).body(
                    Utils.generateResponse(Constants.FAILED_STRING, Constants.MAIL_SEND_FAILED_MESSAGE)
                );
            }
        }
        return ResponseEntity.status(404).body(
            Utils.generateResponse(Constants.FAILED_STRING, Constants.INVALID_EMAIL)
        );
    }

    public ResponseEntity<Map<String, Object>> verifyOtp(String email, String otp) {
        log.debug("Attempting to verify OTP for email: {}", email);
        log.debug("Attempting to verify OTP for otp: {}", otp);
        Object otpFromRedis = redisTemplate.opsForValue().get(email);
        log.debug("otpFromRedis is {}",otpFromRedis);
        if (otpFromRedis != null) {
            if (otpFromRedis.equals(otp)) {
                log.info("OTP verified successfully for user: {}", email);
                redisTemplate.opsForValue().getAndDelete(email);
                return ResponseEntity.status(200).body(
                    Utils.generateResponse(Constants.SUCCESS_STRING, Constants.OTP_VERIFIED_SUCCESSFULLY)
                );
            }else{
                log.warn("OTP verification failed for user: {}", email);
                return ResponseEntity.status(400).body(
                    Utils.generateResponse(Constants.FAILED_STRING, Constants.INVALID_OTP)
                );
            }
        }
        log.info("OTP verified successfully for user: {}",email);
        return ResponseEntity.status(404).body(
            Utils.generateResponse(Constants.FAILED_STRING, Constants.OTP_NOT_FOUND_MESSAGE)
        );
    }

    public ResponseEntity<Map<String, Object>> updateUser(SignUpUserDto user) {
        log.debug("Attempting to update user: {}", user);
        User existingUser = userRepository.findByUserName(user.getUserName());
        if (existingUser == null) {
            log.warn("Update user failed: user not found");
            return ResponseEntity.status(404).body(
                Utils.generateResponse(Constants.FAILED_STRING, Constants.USER_NOT_FOUND_MESSAGE)
            );
        }
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setName(user.getName());
        String validationResult = Utils.user(user);
        if (validationResult != Constants.SUCCESS_STRING) {
            log.warn("Validation failed for user: {}", user.getUserName());
            return ResponseEntity.status(400).body(
                Utils.generateResponse(Constants.FAILED_STRING, validationResult)
            );
        }
        userRepository.save(existingUser);
        log.info("User updated successfully: {}", existingUser);
        return ResponseEntity.status(200).body(
            Utils.generateResponse(Constants.SUCCESS_STRING, Constants.USER_UPDATED_SUCCESSFULLY)
        );
    }
}
