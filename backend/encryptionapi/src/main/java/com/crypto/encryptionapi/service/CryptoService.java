package com.crypto.encryptionapi.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
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

    public String decrypt(String cipherText) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(cipherText);

        byte[] iv = new byte[16];
        byte[] encrypted = new byte[decoded.length - 16];

        System.arraycopy(decoded, 0, iv, 0, 16);
        System.arraycopy(decoded, 16, encrypted, 0, encrypted.length);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), new IvParameterSpec(iv));

        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted);
    }
}

