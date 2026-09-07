package com.example.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // ตัวเข้ารหัสผ่านก่อนลง Database
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults()) // 1. เปิดใช้งาน CORS ระดับ Security
            .csrf(csrf -> csrf.disable()) // ปิด CSRF สำหรับ API
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // 2. อนุญาต Preflight (OPTIONS request) ก่อนยิง API จริง
                .requestMatchers("/api/auth/**").permitAll() // ยอมให้ทุกคนเข้าถึง API หมวด auth ได้
                .anyRequest().authenticated()
            );
        return http.build();
    }
}