package com.cloud.yagodev.testando_api_springsecurity.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class MyController {

    @GetMapping
    public ResponseEntity<String> mensagemTeste() {
        return ResponseEntity.ok("Acesso liberado");
    }
}
