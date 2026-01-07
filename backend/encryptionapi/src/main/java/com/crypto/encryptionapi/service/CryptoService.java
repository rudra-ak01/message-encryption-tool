package com.crypto.encryptionapi.service;

import org.springframework.stereotype.Service;

@Service
public class CryptoService {

    private static final int SHIFT = 3;

    public String encrypt(String message) {
        StringBuilder encrypted = new StringBuilder();

        for (char ch : message.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                char encryptedChar = (char) ((ch - base + SHIFT) % 26 + base);
                encrypted.append(encryptedChar);
            } else {
                encrypted.append(ch);
            }
        }
        return encrypted.toString();
    }

    public String decrypt(String message) {
        StringBuilder decrypted = new StringBuilder();

        for (char ch : message.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                char decryptedChar = (char) ((ch - base - SHIFT + 26) % 26 + base);
                decrypted.append(decryptedChar);
            } else {
                decrypted.append(ch);
            }
        }
        return decrypted.toString();
    }
}
