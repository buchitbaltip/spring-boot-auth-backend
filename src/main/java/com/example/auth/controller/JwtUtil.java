package com.example.auth.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // สร้างกุญแจลับสำหรับเซ็นชื่อกำกับ Token (ใช้คีย์ที่สุ่มขึ้นมาใหม่)
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // ตั้งเวลาหมดอายุของ Token เป็น 1 วัน (86,400,000 มิลลิวินาที)
    private static final long EXPIRATION_TIME = 86400000;

    // ฟังก์ชันสำหรับสร้าง Token โดยใช้ Username
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // ใส่ชื่อ User ลงในบัตร
                .setIssuedAt(new Date()) // วันที่ออกบัตร
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // วันหมดอายุ
                .signWith(key) // ประทับตราด้วยกุญแจลับ
                .compact(); // สร้างออกมาเป็นข้อความ String
    }
}