package com.vedruna.security.controllers;

import com.vedruna.security.model.AuthResponse;
import com.vedruna.security.model.LoginRequest;
import com.vedruna.security.model.RegisterRequest;
import com.vedruna.security.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@Tag(name = "Autenticacion", description = "Operaciones relacionadas con los comentarios")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login")
    @Operation(summary = "Loguearse")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "/register")
    @Operation(summary = "Registrarse")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

}
