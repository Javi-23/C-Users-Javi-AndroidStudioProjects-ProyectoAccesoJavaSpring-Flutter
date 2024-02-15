package com.vedruna.security.services;

import com.vedruna.security.model.AuthResponse;
import com.vedruna.security.model.LoginRequest;
import com.vedruna.security.model.RegisterRequest;

public interface AuthServiceI {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
}
