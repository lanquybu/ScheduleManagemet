package com.example.schedulemanagement.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schedulemanagement.R;
import com.example.schedulemanagement.core.AppConstants;
import com.example.schedulemanagement.ui.home.MainActivity;
import com.example.schedulemanagement.data.model.UserProfile;
import com.example.schedulemanagement.data.repository.AuthRepository;
import com.example.schedulemanagement.util.EmailValidator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Màn hình đăng nhập cho sinh viên
 * Validate rỗng và domain email sinh viên
 * Gọi AuthRepository để đăng nhập
 * TODO: chuyển sang màn hình Home sinh viên sau khi đăng nhập thành công
 */
public class LoginActivity extends AppCompatActivity {
    private TextInputLayout tilEmail, tilPassword;
    private TextInputEditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView tvForgot;
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
        tvForgot = findViewById(R.id.tvForgot);
        progress = findViewById(R.id.progress);

        btnLogin.setOnClickListener(v -> onLoginClicked());
        tvForgot.setOnClickListener(v -> onForgotClicked());
    }

    private void onLoginClicked() {
        clearError();

        String email = val(edtEmail);
        String pass = val(edtPassword);

        // validate rỗng
        if (TextUtils.isEmpty(email)) {
            tilEmail.setError("Vui lòng nhập email");
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            tilPassword.setError("Vui lòng nhập mật khẩu");
            return;
        }

        // validate email sinh viên
        if (!EmailValidator.isStudentEmail(email)) {
            tilEmail.setError("Chỉ chấp nhận email sinh viên có đuôi " + AppConstants.STUDENT_DOMAIN);
            return;
        }

        // gọi repository để đăng nhập
        setLoading(true);
        authRepo.signInStudent(email, pass, new AuthRepository.ResultCallback<UserProfile>() {
            @Override
            public void onSuccess(UserProfile data) {
                setLoading(false);
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("studentName", data.fullName);
                intent.putExtra("studentId", data.uid);
                startActivity(intent);
                finish();
                // TODO: Điều hướng sang màn hình Home của sinh viên
                // startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                // finish();
            }

            @Override
            public void onError(String message) {
                setLoading(false);
                showError(message);
            }
        });
    }

    private void onForgotClicked() {
        clearError();
        String email = val(edtEmail);
        if (TextUtils.isEmpty(email)) {
            tilEmail.setError("Vui lòng nhập email");
            return;
        }
        if (!EmailValidator.isStudentEmail(email)) {
            tilEmail.setError("Chỉ gửi email cho sinh viên " + AppConstants.STUDENT_DOMAIN);
            return;
        }

        setLoading(true);
        authRepo.sendResetPassword(email, new AuthRepository.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean ok) {
                setLoading(false);
                Toast.makeText(LoginActivity.this, "Gửi email thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {
                setLoading(false);
                showError(message);

            }
        });
    }

    private void setLoading(boolean loading) {
        btnLogin.setEnabled(!loading);
        progress.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    private void clearError() {
        tilEmail.setError(null);
        tilPassword.setError(null);
    }

    private void showError(String msg) {
        // Ưu tiên gắn lỗi vào ô tương ứng nếu đoán được
        if (msg != null && msg.toLowerCase().contains("email")) {
            tilEmail.setError(msg);
        } else if (msg != null && (msg.toLowerCase().contains("mật khẩu") || msg.toLowerCase().contains("password"))) {
            tilPassword.setError(msg);
        } else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private String val(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }
}
