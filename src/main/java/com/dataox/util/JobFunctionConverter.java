package com.dataox.util;

import java.util.Base64;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for converting job functions to encoded values.
 */
public class JobFunctionConverter {

    /**
     * Converts the provided job function to an encoded value.
     *
     * @param jobFunction The job function to convert.
     * @return The encoded value of the job function.
     * @throws RuntimeException if there is an error encoding the job function.
     */
    public static String convertToEncodedValue(String jobFunction) {
        // JSON encoding
        String jsonEncoded = "{\"job_functions\":[\"" + jobFunction + "\"]}";

        // Base64 encoding
        String base64Encoded = Base64.getEncoder().encodeToString(jsonEncoded.getBytes(StandardCharsets.UTF_8));

        // URL encoding
        return URLEncoder.encode(base64Encoded, StandardCharsets.UTF_8);
    }
}
