package com.dataox.util;

import java.util.Base64;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class JobFunctionConverter {

    public static String convertToEncodedValue(String jobFunction) {
        try {
            // JSON encoding
            String jsonEncoded = "{\"job_functions\":[\"" + jobFunction + "\"]}";

            // Base64 encoding
            String base64Encoded = Base64.getEncoder().encodeToString(jsonEncoded.getBytes(StandardCharsets.UTF_8));

            // URL encoding
            String urlEncoded = URLEncoder.encode(base64Encoded, StandardCharsets.UTF_8.toString());

            return urlEncoded;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding jobFunction", e);
        }
    }
}
