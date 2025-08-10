package com.example.schedulemanagement.data.repository;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentSnapshot;

import com.example.schedulemanagement.core.AppConstants;
import com.example.schedulemanagement.data.model.UserProfile;
import com.example.schedulemanagement.data.remote.FirebaseSource;
import com.example.schedulemanagement.util.EmailValidator;

/**
 * Chứa logic đăng nhập:
 * - Chỉ cho phép email sinh viên (@e.tlu.edu.vn)
 * - Sau khi signIn, kiểm tra profile trong Firestore có role = "student"
 */
public class AuthRepository {

    // Callback gọn cho UI
    public interface ResultCallback<T> {
        void onSuccess(T data);
        void onError(String message);
    }

    private final FirebaseSource source = new FirebaseSource();

    public void signInStudent(String email, String password, ResultCallback<UserProfile> cb) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            cb.onError("Vui lòng nhập email và mật khẩu");
            return;
        }
        if (!EmailValidator.isStudentEmail(email)) {
            cb.onError("Chỉ chấp nhận email sinh viên có đuôi " + AppConstants.STUDENT_DOMAIN);
            return;
        }

        // 1) Đăng nhập Firebase Auth
        source.auth().signInWithEmailAndPassword(email.trim(), password.trim())
                .addOnSuccessListener((OnSuccessListener<AuthResult>) authResult -> {
                    String uid = authResult.getUser().getUid();

                    // 2) Lấy hồ sơ từ Firestore để kiểm tra role
                    source.users().document(uid).get()
                            .addOnSuccessListener((OnSuccessListener<DocumentSnapshot>) doc -> {
                                if (!doc.exists()) {
                                    source.auth().signOut();
                                    cb.onError("Không tìm thấy hồ sơ sinh viên trong hệ thống");
                                    return;
                                }

                                UserProfile profile = doc.toObject(UserProfile.class);
                                if (profile == null || profile.role == null) {
                                    source.auth().signOut();
                                    cb.onError("Hồ sơ không hợp lệ");
                                    return;
                                }

                                if (!AppConstants.ROLE_STUDENT.equalsIgnoreCase(profile.role)) {
                                    // Không phải sinh viên => chặn
                                    source.auth().signOut();
                                    cb.onError("Tài khoản không phải sinh viên. Vui lòng dùng email sinh viên.");
                                    return;
                                }

                                // OK
                                cb.onSuccess(profile);
                            })
                            .addOnFailureListener(e -> {
                                source.auth().signOut();
                                cb.onError("Lỗi tải hồ sơ: " + e.getMessage());
                            });
                })
                .addOnFailureListener((OnFailureListener) e -> {
                    String msg = (e instanceof FirebaseAuthException) ? ((FirebaseAuthException) e).getMessage() : e.getMessage();
                    cb.onError("Đăng nhập thất bại: " + msg);
                });
    }

    public void sendResetPassword(String email, ResultCallback<Boolean> cb) {
        if (!EmailValidator.isStudentEmail(email)) {
            cb.onError("Chỉ gửi cho email sinh viên " + AppConstants.STUDENT_DOMAIN);
            return;
        }
        source.auth().sendPasswordResetEmail(email.trim())
                .addOnSuccessListener(unused -> cb.onSuccess(true))
                .addOnFailureListener(e -> cb.onError("Gửi email đặt lại mật khẩu thất bại: " + e.getMessage()));
    }
}
