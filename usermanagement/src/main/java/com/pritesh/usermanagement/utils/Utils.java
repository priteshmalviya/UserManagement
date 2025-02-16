package com.pritesh.usermanagement.utils;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static Map<String, Object> generateResponse(String status, Object message) {
        Map<String, Object> response = new HashMap<>();
        response.put(Constants.STATUS_STRING, status);
        response.put(Constants.MESSAGE_STRING, message);
        return response;
    }
}
