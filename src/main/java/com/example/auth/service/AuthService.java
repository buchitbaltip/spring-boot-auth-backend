package com.example.auth.service; // ปรับชื่อ package ให้ตรงกับโปรเจกต์ของคุณ

import com.example.auth.controller.JwtUtil;
// import User, UserRepository, PasswordEncoder ให้ครบถ้วน
import com.example.auth.entity.User;
import com.example.auth.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ลอจิกการสมัครสมาชิก (คืนค่า true ถ้าสำเร็จ, false ถ้าชื่อซ้ำ)
    public boolean registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return false;
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);
        return true;
    }

    // ลอจิกการล็อกอิน (คืนค่า Token ถ้าสำเร็จ, คืนค่า null ถ้ารหัสผิด)
    public String loginUser(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(user.getUsername());
        }
        return null; 
    }
}