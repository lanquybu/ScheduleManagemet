package com.example.schedulemanagement.data.repository;

import android.util.Log;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentSnapshot;

import com.example.schedulemanagement.core.AppConstants;
import com.example.schedulemanagement.data.model.UserProfile;
import com.example.schedulemanagement.data.remote.FirebaseSource;
import com.example.schedulemanagement.util.EmailValidator;

import java.util.Locale;

/**
 * Repository xử lý ĐĂNG NHẬP cho SINH VIÊN.
 * - Chỉ cho phép email có đuôi @e.tlu.edu.vn
 * - Kiểm tra Firestore role = "student"
 * - Lần đầu đăng nhập: tự tạo hồ sơ sinh viên trong users/{uid}
 */
public class AuthRepository {

    public interface ResultCallback<T> {
        void onSuccess(T data);
        void onError(String message);
    }

    private static final String TAG = "AuthRepo";
    private final FirebaseSource source = new FirebaseSource();

    public void signInStudent(String email, String password, ResultCallback<UserProfile> cb) {
        // 1) Validate cơ bản
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            cb.onError("Vui lòng nhập email và mật khẩu");
            return;
        }

        // 2) Chuẩn hoá chuỗi
        final String emailNorm = email.trim().toLowerCase(Locale.ROOT);
        final String passNorm  = password.trim();

        // 3) Chặn nếu không đúng domain sinh viên
        if (!EmailValidator.isStudentEmail(emailNorm)) {
            cb.onError("Chỉ chấp nhận email sinh viên có đuôi " + AppConstants.STUDENT_DOMAIN);
            return;
        }

        // 4) Đăng nhập Firebase Auth
        source.auth().signInWithEmailAndPassword(emailNorm, passNorm)
                .addOnSuccessListener(authResult -> {
                    if (authResult == null || authResult.getUser() == null) {
                        cb.onError("Không lấy được thông tin người dùng");
                        return;
                    }

                    final String uid = authResult.getUser().getUid();
                    final String authedEmail = authResult.getUser().getEmail();
                    Log.d(TAG, "Login OK uid=" + uid + ", email=" + authedEmail);

                    // 5) Đọc hồ sơ Firestore
                    source.users().document(uid).get()
                            .addOnSuccessListener((DocumentSnapshot doc) -> {
                                Log.d(TAG, "users/" + uid + " exists=" + doc.exists() + " data=" + doc.getData());

                                if (!doc.exists()) {
                                    // 5a) Lần đầu: auto tạo hồ sơ sinh viên
                                    UserProfile newProfile = new UserProfile(
                                            uid,
                                            authedEmail,
                                            "Sinh viên",
                                            AppConstants.ROLE_STUDENT  // "student"
                                    );
                                    source.users().document(uid).set(newProfile)
                                            .addOnSuccessListener(v -> {
                                                Log.d(TAG, "Auto-created profile for " + uid);
                                                cb.onSuccess(newProfile);
                                            })
                                            .addOnFailureListener(e -> {
                                                source.auth().signOut();
                                                cb.onError("Không tạo được hồ sơ: " + e.getMessage());
                                            });
                                    return;
                                }

                                // 5b) Có hồ sơ: parse & kiểm tra
                                UserProfile profile = doc.toObject(UserProfile.class);
                                if (profile == null) {
                                    source.auth().signOut();
                                    cb.onError("Hồ sơ không hợp lệ (không parse được)");
                                    return;
                                }

                                // Chuẩn hoá role để so sánh an toàn
                                String roleNorm = (profile.role == null ? "" : profile.role.trim().toLowerCase(Locale.ROOT));
                                String mustBe   = (AppConstants.ROLE_STUDENT == null ? "student"
                                        : AppConstants.ROLE_STUDENT.trim().toLowerCase(Locale.ROOT));

                                if (!mustBe.equals(roleNorm)) {
                                    source.auth().signOut();
                                    cb.onError("Tài khoản không phải sinh viên (role=" + profile.role + ")");
                                    return;
                                }

                                // Cảnh báo nếu email Firestore khác Auth (không chặn)
                                if (profile.email == null ||
                                        !emailNorm.equals(profile.email.trim().toLowerCase(Locale.ROOT))) {
                                    Log.w(TAG, "Email Firestore khác Auth: fs=" + profile.email + ", auth=" + emailNorm);
                                }

                                // ✅ Thành công
                                cb.onSuccess(profile);
                            })
                            .addOnFailureListener(e -> {
                                source.auth().signOut();
                                cb.onError("Lỗi tải hồ sơ: " + e.getMessage());
                            });
                })
                .addOnFailureListener(e -> {
                    String msg = (e instanceof FirebaseAuthException)
                            ? ((FirebaseAuthException) e).getMessage()
                            : e.getMessage();
                    cb.onError("Đăng nhập thất bại: " + msg);
                });
    }
}
