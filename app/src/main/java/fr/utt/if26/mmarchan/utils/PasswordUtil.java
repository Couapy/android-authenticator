package fr.utt.if26.mmarchan.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PasswordUtil {
    /**
     * Return the hash of the password with SHA-256.
     *
     * If this algorithm isn't implemented on the device, it will use the plain text password. /!\
     * @param password password to hash
     * @return password hash
     */
    public static String getHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return Arrays.toString(md.digest(password.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            return password;
        }
    }
}
