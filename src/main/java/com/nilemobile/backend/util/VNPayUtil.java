package com.nilemobile.backend.util;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class VNPayUtil {
    public static String hmacSHA512(final String key, final String data) {
//        try {
//            if (key == null || data == null)
//                throw new NullPointerException();
//
//            final Mac hmac512 = Mac.getInstance("HMacSHA512");
//            byte[] hmacKeyBytes = key.getBytes();
//            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
//            hmac512.init(secretKey);
//            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
//            byte[] result = hmac512.doFinal(dataBytes);
//            StringBuilder stringBuilder = new StringBuilder(2 * result.length);
//            for (byte b : result) {
//                stringBuilder.append(String.format("%02x", b & 0xff));
//
//            }
//            return stringBuilder.toString();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            if (key == null || data == null)
//                throw new NullPointerException();
//
//            final Mac hmac512 = Mac.getInstance("HMacSHA512");
//            byte[] hmacKeyBytes = key.getBytes(StandardCharsets.UTF_8); // Chỉ định UTF-8
//            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
//            hmac512.init(secretKey);
//            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
//            byte[] result = hmac512.doFinal(dataBytes);
//            StringBuilder stringBuilder = new StringBuilder(2 * result.length);
//            for (byte b : result) {
//                stringBuilder.append(String.format("%02x", b & 0xff));
//            }
//            return stringBuilder.toString();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }

    public static String getIpAddress(HttpServletRequest req) {
//        String ipAddress;
//        try {
//            ipAddress = req.getHeader("X-FORWARDED-FOR");
//            if (ipAddress == null) {
//                ipAddress = req.getRemoteAddr();
//            }
//        } catch (Exception e) {
//            ipAddress = "Invalid IP" + e.getMessage();
//        }
//        return ipAddress;
        String ipAdress;
        try {
            ipAdress = req.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = req.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }

    public static String getRandomNumber(int len) {
        Random random = new Random();
        String chars = "0123456789";
        StringBuilder stringBuilder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            stringBuilder.append(chars.charAt(random.nextInt(chars.length())));
        }
        return stringBuilder.toString();
    }

    public static String getPaymentUrl(Map<String, String> paramsMap, boolean encodeKey) {
//        return paramsMap.entrySet().stream()
//                .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
//                .sorted(Map.Entry.comparingByKey())
//                .map(entry -> (encodeKey ? URLEncoder.encode(entry.getKey(), StandardCharsets.US_ASCII) : entry.getKey()) + "=" +
//                        URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII)).collect(Collectors.joining("&"));


//        List<String> fieldNames = new ArrayList<>(paramsMap.keySet());
//        Collections.sort(fieldNames); // Sắp xếp theo alphabet
//        StringBuilder query = new StringBuilder();
//        for (String fieldName : fieldNames) {
//            String fieldValue = paramsMap.get(fieldName);
//            if (fieldValue != null && !fieldValue.isEmpty()) {
//                if (encodeKey) {
//                    try {
//                        query.append(URLEncoder.encode(fieldName, "UTF-8"))
//                                .append("=")
//                                .append(URLEncoder.encode(fieldValue, "UTF-8"));
//                    } catch (UnsupportedEncodingException e) {
//                        throw new RuntimeException("Encoding error", e);
//                    }
//                } else {
//                    query.append(fieldName).append("=").append(fieldValue);
//                }
//                query.append("&");
//            }
//        }
//        // Xóa ký tự "&" cuối cùng
//        if (query.length() > 0) {
//            query.setLength(query.length() - 1);
//        }
//        return query.toString();
        return paramsMap.entrySet().stream()
                .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
                .sorted(Map.Entry.comparingByKey())
                .map(entry ->
                        (encodeKey ? URLEncoder.encode(entry.getKey(),
                                StandardCharsets.US_ASCII)
                                : entry.getKey()) + "=" +
                                URLEncoder.encode(entry.getValue()
                                        , StandardCharsets.US_ASCII))
                .collect(Collectors.joining("&"));
    }

}

