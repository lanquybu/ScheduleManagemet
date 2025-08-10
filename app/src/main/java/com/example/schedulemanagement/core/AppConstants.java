package com.example.schedulemanagement.core;

// Nơi tập trung các hằng số toàn app để tái sử dụng

public final class AppConstants {
    private AppConstants() {} // không cho new

    // Domain email bắt buộc cho sinh viên
    public static  final String STUDENT_DOMAIN = "@e.tlu.edu.vn";

    // Tên collection Firestore
    public static final String USERS = "users";

    // Vai trò hợp lệ cho app sinh viên
    public static final String ROLE_STUDENT = "student";
}
