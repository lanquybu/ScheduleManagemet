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

    public void signIn(String email, String password, ResultCallback<UserProfile> cb) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            cb.onError("Vui lòng nhập email và mật khẩu");
            return;
        }

        final String emailNorm = email.trim().toLowerCase(Locale.ROOT);
        final String passNorm  = password.trim();

        // UI đã kiểm tra format; ở đây không kiểm domain
        source.auth().signInWithEmailAndPassword(emailNorm, passNorm)
                .addOnSuccessListener((AuthResult authResult) -> {
                    if (authResult == null || authResult.getUser() == null) {
                        cb.onError("Không lấy được thông tin người dùng");
                        return;
                    }
                    final String uid = authResult.getUser().getUid();
                    final String authedEmail = authResult.getUser().getEmail();
                    Log.d(TAG, "Login OK uid=" + uid + ", email=" + authedEmail);

                    // Lấy hồ sơ
                    source.users().document(uid).get()
                            .addOnSuccessListener((DocumentSnapshot doc) -> {
                                Log.d(TAG, "users/" + uid + " exists=" + doc.exists() + " data=" + doc.getData());

                                if (!doc.exists()) {
                                    // Auto-create CHỈ CHO STUDENT (nếu muốn lecturer/admin thì tạo qua web).
                                    // Mặc định coi user mới là student nếu dùng app SV:
                                    UserProfile newProfile = new UserProfile(uid, authedEmail, "Sinh viên", AppConstants.ROLE_STUDENT);
                                    source.users().document(uid).set(newProfile)
                                            .addOnSuccessListener(v -> {
                                                Log.d(TAG, "Auto-created STUDENT profile for " + uid);
                                                // Với student mới tạo -> vẫn kiểm domain
                                                if (!EmailValidator.isStudentEmail(emailNorm)) {
                                                    source.auth().signOut();
                                                    cb.onError("Sinh viên phải dùng email có đuôi " + AppConstants.STUDENT_DOMAIN);
                                                    return;
                                                }
                                                cb.onSuccess(newProfile);
                                            })
                                            .addOnFailureListener(e -> {
                                                source.auth().signOut();
                                                cb.onError("Không tạo được hồ sơ: " + e.getMessage());
                                            });
                                    return;
                                }

                                UserProfile profile = doc.toObject(UserProfile.class);
                                if (profile == null || profile.role == null) {
                                    source.auth().signOut();
                                    cb.onError("Hồ sơ không hợp lệ");
                                    return;
                                }

                                String roleNorm = profile.role.trim().toLowerCase(Locale.ROOT);

                                // Nếu là student -> bắt buộc domain @e.tlu.edu.vn
                                if (AppConstants.ROLE_STUDENT.equals(roleNorm)) {
                                    if (!EmailValidator.isStudentEmail(emailNorm)) {
                                        source.auth().signOut();
                                        cb.onError("Sinh viên phải dùng email có đuôi " + AppConstants.STUDENT_DOMAIN);
                                        return;
                                    }
                                } else if (!AppConstants.ROLE_LECTURER.equals(roleNorm)
                                        && !AppConstants.ROLE_ADMIN.equals(roleNorm)) {
                                    source.auth().signOut();
                                    cb.onError("Vai trò không được hỗ trợ: " + profile.role);
                                    return;
                                }

                                // Cảnh báo nếu email Firestore khác Auth
                                if (profile.email == null ||
                                        !emailNorm.equals(profile.email.trim().toLowerCase(Locale.ROOT))) {
                                    Log.w(TAG, "Email Firestore khác Auth: fs=" + profile.email + ", auth=" + emailNorm);
                                }

                                cb.onSuccess(profile);
                            })
                            .addOnFailureListener(e -> {
                                source.auth().signOut();
                                cb.onError("Lỗi tải hồ sơ: " + e.getMessage());
                            });

                })
                .addOnFailureListener(e -> {
                    String msg = (e instanceof FirebaseAuthException) ? ((FirebaseAuthException) e).getMessage() : e.getMessage();
                    cb.onError("Đăng nhập thất bại: " + msg);
                });
    }
}