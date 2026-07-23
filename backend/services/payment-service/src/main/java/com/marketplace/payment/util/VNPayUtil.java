package com.marketplace.payment.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class VNPayUtil {

    private VNPayUtil() {
    }

    /**
     * Ký HMAC SHA512
     */
    public static String hmacSHA512(
            String key,
            String data) {

        try {

            Mac hmac512 = Mac.getInstance("HmacSHA512");

            SecretKeySpec secretKey = new SecretKeySpec(
                    key.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA512");

            hmac512.init(secretKey);

            byte[] bytes = hmac512.doFinal(
                    data.getBytes(StandardCharsets.UTF_8));

            StringBuilder hash = new StringBuilder();

            for (byte b : bytes) {

                hash.append(String.format("%02x", b));

            }

            return hash.toString();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Cannot generate HMAC SHA512",
                    ex);

        }

    }

    /**
     * URL Encode
     */
    public static String encode(String value) {

        return URLEncoder.encode(
                value,
                StandardCharsets.UTF_8);

    }

    /**
     * Sắp xếp tham số theo alphabet
     */
    public static Map<String, String> sortParams(
            Map<String, String> params) {

        return new TreeMap<>(params);

    }

    /**
     * Tạo query string theo đúng chuẩn VNPay
     */
    public static String buildQueryString(
            Map<String, String> params) {

        StringBuilder query = new StringBuilder();

        Map<String, String> sortedParams = sortParams(params);

        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {

            if (entry.getValue() == null
                    || entry.getValue().isBlank()) {
                continue;
            }

            if (!query.isEmpty()) {
                query.append("&");
            }

            query.append(encode(entry.getKey()))
                    .append("=")
                    .append(encode(entry.getValue()));

        }

        return query.toString();

    }

    /**
     * Sinh Secure Hash cho VNPay
     */
    public static String buildSecureHash(
            String secretKey,
            Map<String, String> params) {

        String queryString = buildQueryString(params);

        return hmacSHA512(
                secretKey,
                queryString);

    }

    /**
     * Verify SecureHash từ VNPay
     */
    public static boolean verifySignature(
            Map<String, String> params,
            String secretKey) {

        Map<String, String> copied = new TreeMap<>(params);

        String receivedHash = copied.remove("vnp_SecureHash");

        copied.remove("vnp_SecureHashType");

        String calculatedHash = buildSecureHash(secretKey, copied);

        return calculatedHash.equalsIgnoreCase(receivedHash);

    }

}
