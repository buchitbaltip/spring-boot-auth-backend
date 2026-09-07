package com.example.auth.controller;

import com.example.auth.dto.AuthRequest;
// ... imports ...
import com.example.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
// @CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        boolean isSuccess = authService.registerUser(request.getUsername(), request.getPassword());
        
        if (!isSuccess) {
            return ResponseEntity.badRequest().body(Map.of("message", "\r\n" + //
                                "This username already exists."));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Registration successful!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        String token = authService.loginUser(request.getUsername(), request.getPassword());
        
        if (token != null) {
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful!",
                    "token", token
            ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Incorrect username or password."));
    }
}