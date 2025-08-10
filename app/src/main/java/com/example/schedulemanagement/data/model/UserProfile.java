package com.example.schedulemanagement.data.model;

/**
 * POJO ánh xạ document người dùng trong Firestore (collection "users").
 * Bắt Buộc phải có contructor rỗng để Firestore đọc được
 */
public class UserProfile {
    public String uid;
    public String email;
    public String fullName;
    public String role; // "student" | "lecturer" | "admin"

    public UserProfile() {
        // Bắt buộc để Firestore đọc được
    }

    public UserProfile(String uid, String email, String fullName, String role) {
        this.uid = uid;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
    }
}

