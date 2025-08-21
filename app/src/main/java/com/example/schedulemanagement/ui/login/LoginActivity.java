package com.example.schedulemanagement.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.core.AppConstants;
import com.example.schedulemanagement.data.model.UserProfile;
import com.example.schedulemanagement.data.repository.AuthRepository;
import com.example.schedulemanagement.ui.home.MainActivity; // <-- import đúng package MainActivity
import com.example.schedulemanagement.util.EmailValidator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Màn hình đăng nhập sinh viên.
 * - Validate rỗng + domain @e.tlu.edu.vn
 * - Gọi AuthRepository.signInStudent
 * - Thành công → chuyển sang MainActivity (ui.home)
 */
public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilEmail, tilPassword;
    private TextInputEditText edtEmail, edtPassword;
    private Button btnLogin;
    private ProgressBar progress;

    private final AuthRepository authRepo = new AuthRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progress = findViewById(R.id.progress);

        btnLogin.setOnClickListener(v -> onLoginClicked());
    }

    private void onLoginClicked() {
        clearErrors();

        String email = val(edtEmail);
        String pass  = val(edtPassword);

        // 1) Validate rỗng
        if (TextUtils.isEmpty(email)) { tilEmail.setError("Vui lòng nhập email"); return; }
        if (TextUtils.isEmpty(pass))  { tilPassword.setError("Vui lòng nhập mật khẩu"); return; }

        // 2) Chỉ kiểm tra ĐỊNH DẠNG email, KHÔNG chặn theo domain ở UI
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Email không hợp lệ");
            return;
        }

        setLoading(true);
        authRepo.signIn(email, pass, new AuthRepository.ResultCallback<UserProfile>() {
            @Override public void onSuccess(UserProfile profile) {
                setLoading(false);
                // Điều hướng theo role
                Intent intent;
                String role = profile.role == null ? "" : profile.role.trim().toLowerCase(java.util.Locale.ROOT);
                switch (role) {
                    case AppConstants.ROLE_LECTURER:  // "lecturer"
                        intent = new Intent(LoginActivity.this,
                                com.example.schedulemanagement.ui_teacher.teacher_home.TeacherMenuActivity.class);
                        break;
                    case AppConstants.ROLE_ADMIN:      // "admin"
                        // TODO: tạo AdminActivity khi sẵn sàng
                        // intent = new Intent(LoginActivity.this, com.example.schedulemanagement.ui_admin.AdminActivity.class);
                        // break;
                        Toast.makeText(LoginActivity.this, "Admin tạm thời chưa có màn hình.", Toast.LENGTH_SHORT).show();
                        return;
                    default:                           // "student"
                        intent = new Intent(LoginActivity.this,
                                com.example.schedulemanagement.ui.home.MainActivity.class);
                        break;
                }
                startActivity(intent);
                finish();
            }

            @Override public void onError(String message) {
                setLoading(false);
                showError(message);
            }
        });
    }


    private void setLoading(boolean loading) {
        btnLogin.setEnabled(!loading);
        progress.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    private void clearErrors() {
        tilEmail.setError(null);
        tilPassword.setError(null);
    }

    private void showError(String msg) {
        if (msg != null && msg.toLowerCase().contains("email")) {
            tilEmail.setError(msg);
        } else if (msg != null && (msg.toLowerCase().contains("mật khẩu") || msg.toLowerCase().contains("password"))) {
            tilPassword.setError(msg);
        } else {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }

    private String val(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }
}
