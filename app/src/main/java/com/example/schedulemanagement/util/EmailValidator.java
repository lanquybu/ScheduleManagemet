package com.example.schedulemanagement.util;

import com.example.schedulemanagement.core.AppConstants;

/**
 * Hàm kiểm tra email có phải email sinh viên hay không
 */
public class EmailValidator {
    /**
     * @return true nếu email có đuôi @e.tlu.edu.vn
     */
    public static boolean isStudentEmail(String raw) {
        if(raw == null) return false;
        String email = raw.trim().toLowerCase();
        // Điều kiện tối thiểu: có ký tự @ và kết thúc bằng domain sinh viên
        return email.contains("@") && email.endsWith(AppConstants.STUDENT_DOMAIN);
    }
}
