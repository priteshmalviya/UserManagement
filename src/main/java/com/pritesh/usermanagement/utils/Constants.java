package com.pritesh.usermanagement.utils;

public class Constants {

    public static final String SECRET_KEY = "akshcisdgcousgaiydiudgt8cgsjdhbcskghg8drtf78wend";

    public static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hours

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";


    // response messages

    public static final String SUCCESS_STRING = "success";

    public static final String FAILED_STRING = "failed";

    public static final String ERROR_STRING = "error";

    public static final String STATUS_STRING = "status";

    public static final String MESSAGE_STRING = "message";

    public static final String TOKEN_STRING = "token";

    public static final String USER_CREATED_SUCCESSFULLY = "User created successfully";

    public static final String USER_NOT_FOUND_MESSAGE = "User not found";

    public static final String INVALID_CREDENTIALS = "Invalid credentials";

    public static final String LOGIN_SUCCESSFUL = "Login successful";

    public static final String LOGOUT_SUCCESSFUL = "Logout successful";

    public static final String TOKEN_EXPIRED = "Token expired";

    public static final String PONG = "pong";

    public static final String INVALID_PASSWORD = "Invalid password";

    public static final String INVALID_USERNAME_OR_EMAIL = "Invalid username or email";

    public static final String PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS_LONG = "Password must be at least 8 characters long";

    public static final String USER_ALREADY_EXISTS_WITH_THIS_USERNAME = "User already exists with this username";

    public static final String USER_ALREADY_EXISTS_WITH_THIS_EMAIL = "User already exists with this email";

    public static final String LOGIN_SUCCESSFUL_JSON_RESPONSE = "{\"message\": \"Login successful\", \"token\": \"%s\"}";
    
    public static final String MAIL_SENT_SUCCESSFUL_MESSAGE = "Mail sent successfully";

    public static final String OTP_VERIFIED_SUCCESSFULLY = "OTP verified successfully";

    public static final String OTP_NOT_FOUND_MESSAGE = "OTP not found";

    public static final String INVALID_OTP = "Invalid OTP";

    public static final String MAIL_SEND_FAILED_MESSAGE = "Mail send failed please try again later";

    public static final String USER_UPDATED_SUCCESSFULLY = "User updated successfully";

    public static final String USERNAME_CANNOT_BE_EMPTY = "Username cannot be empty";

    public static final String INVALID_EMAIL = "Invalid email";

    public static final String NAME_CANNOT_BE_EMPTY = "Name cannot be empty";

    public static final String INVALID_TOKEN = "{\"error\": \"Invalid token\"}";

    public static final String UNAUTHORIZED = "{\"error\": \"Unauthorized\"}";
    
    // JSON
    public static final String BLANK_STRING ="";
    public static final String EMPTY_JSON_ARRAY = "[]";
    public static final String NULL_STRING = "null";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";


    // Entity constants
    public static final String USER_STRING = "USERS";
    public static final String ID_STRING = "ID";
    public static final String NAME_STRING = "NAME";
    public static final String USER_NAME_STRING = "USER_NAME";
    public static final String EMAIL_STRING = "EMAIL";
    public static final String PASSWORD_STRING = "PASSWORD";
    public static final String ROLES_STRING = "ROLES";
    public static final String PERMISSIONS_STRING = "PERMISSIONS";

    
    // Endpoints

    public static final String PUBLIC_ENDPOINT_WITH_WILDCARD = "/public/**";
    public static final String PUBLIC_ENDPOINT = "/public";
    public static final String PING_ENDPOINT = "/ping";
    public static final String CREATE_USER_ENDPOINT = "/create-user";
    public static final String LOGIN_ENDPOINT = "/login";
    public static final String VERIFY_EMAIL_ENDPOINT = "/verify-email";
    public static final String SEND_OTP_ENDPOINT = "/send-otp/{email}/{username}";
    public static final String UPDATE_USER_ENDPOINT = "/update-user";

    public static final String USER_ENDPOINT = "/users";
    public static final String GET_ALL_USERS_ENDPOINT = "/get-all-users";
    public static final String GET_USER_BY_ID_ENDPOINT = "/get-user-by-id/{id}";
    public static final String GET_USER_BY_USERNAME_ENDPOINT = "/get-user-by-username/{username}";
    public static final String GET_USER_BY_EMAIL_ENDPOINT = "/get-user-by-email/{email}";
    public static final String VERIFY_OTP_ENDPOINT = "/verify-otp/{email}/{otp}";



    // other constants
    public static final String USERNAME_SMALL_STRING = "username";
    public static final String EMAIL_SMALL_STRING = "email";
    public static final String PASSWORD_SMALL_STRING = "password";
    public static final String TYPE_STRING = "typ";
    public static final String JWT_STRING = "JWT";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String APPLICATION_JSON = "application/json";

    //mailing
    public static final String FORGOT_PASSWORD_MAIL_SUBJECT = "Password Reset Request - Your Account Security";
    public static final String FORGOT_PASSWORD_MAIL_BODY = """
            Dear %s,
            
            We received a request to reset your password. To proceed with the password reset, please use the following One-Time Password (OTP):
            
            OTP: %s
            
            This OTP will expire in 10 minutes for security purposes. If you didn't request this password reset, please ignore this email or contact our support team immediately.
            
            For security reasons, please:
            - Never share this OTP with anyone
            - Make sure to use a strong password
            - Use this OTP only on our official website
            
            Best regards,
            UserManagement Team
            """;

}
