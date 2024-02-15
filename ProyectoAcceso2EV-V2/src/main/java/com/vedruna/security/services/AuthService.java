package com.vedruna.security.services;

import com.vedruna.persistence.model.Role;
import com.vedruna.persistence.model.User;
import com.vedruna.persistence.model.Repository.UserRepository;
import com.vedruna.security.model.AuthResponse;
import com.vedruna.security.model.LoginRequest;
import com.vedruna.security.model.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthServiceI {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTServiceI jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        return new AuthResponse(jwtService.getToken(user));
    }

    public AuthResponse register(RegisterRequest request) {
        User user = new User(request.getUsername(), request.getEmail(), request.getDescription(), passwordEncoder.encode(request.getPassword()), Role.USER);
        userRepository.save(user);
        return new AuthResponse(jwtService.getToken(user));
    }

}
