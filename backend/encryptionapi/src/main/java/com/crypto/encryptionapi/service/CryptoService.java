package com.crypto.encryptionapi.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Service
public class CryptoService {

    private static final String SECRET = "my-super-secret-password"; // Password used to generate AES key
    private static final String SALT = "my-static-salt";             // Salt protects against rainbow-table attacks

    //secret and salt stored securely
    private SecretKeySpec getSecretKey() throws Exception {
        PBEKeySpec spec = new PBEKeySpec(SECRET.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }

    //Encrypt with AES 
    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);

        IvParameterSpec ivspec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(), ivspec);

        byte[] encrypted = cipher.doFinal(plainText.getBytes());

        // Prepend IV to ciphertext
        byte[] encryptedWithIV = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, encryptedWithIV, 0, iv.length);
        System.arraycopy(encrypted, 0, encryptedWithIV, iv.length, encrypted.length);

        return Base64.getEncoder().encodeToString(encryptedWithIV);
    }

    //Decrypt with AES
    public String decrypt(String cipherText) {
    try {
        byte[] decoded = Base64.getDecoder().decode(cipherText.trim());

        byte[] iv = Arrays.copyOfRange(decoded, 0, 16);
        byte[] encrypted = Arrays.copyOfRange(decoded, 16, decoded.length);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), new IvParameterSpec(iv));

        return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
    } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Decryption failed", e);
    }
}
   }

