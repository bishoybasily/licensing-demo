package com.gmail.bishoybasily.licensing.commons.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingUtils {

    public final static String MD5 = "MD5", SHA1 = "SHA-1";

    public static String generateHash(String text, String algorithm) {
        try {
            final var digest = MessageDigest.getInstance(algorithm);
            final var hashBytes = digest.digest(text.getBytes(StandardCharsets.UTF_8));

            final var hex = new StringBuilder();

            for (byte b : hashBytes)
                hex.append(String.format("%02x", b));

            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
