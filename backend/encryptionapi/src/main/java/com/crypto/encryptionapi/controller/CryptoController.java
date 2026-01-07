package com.crypto.encryptionapi.controller;

import com.crypto.encryptionapi.service.CryptoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crypto")
@CrossOrigin(origins = "*") // frontend access
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @PostMapping("/encrypt")
    public String encrypt(@RequestBody String message) {
        return cryptoService.encrypt(message);
    }

    @PostMapping("/decrypt")
    public String decrypt(@RequestBody String message) {
        return cryptoService.decrypt(message);
    }
}
