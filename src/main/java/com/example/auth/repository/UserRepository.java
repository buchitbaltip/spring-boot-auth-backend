package com.example.auth.repository; // เปลี่ยนให้ตรงกับโปรเจกต์ของคุณ

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auth.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // ฟังก์ชันค้นหา User ในฐานข้อมูลด้วย Username
    Optional<User> findByUsername(String username);
}