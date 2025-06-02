package com.example.lab02;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtFullName;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private Button btnSignUp;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize views
        initViews();

        // Set click listeners
        setClickListeners();
    }

    private void initViews() {
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLogin = findViewById(R.id.tvLogin);
    }

    private void setClickListeners() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ EditText
                String fullName = edtFullName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String confirmPassword = edtConfirmPassword.getText().toString().trim();

                // Kiểm tra dữ liệu
                if (validateInput(fullName, email, password, confirmPassword)) {
                    // TODO: Thực hiện đăng ký
                    registerUser(fullName, email, password);
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay về màn hình đăng nhập
                finish();
            }
        });
    }

    private boolean validateInput(String fullName, String email, String password, String confirmPassword) {
        // Kiểm tra tên
        if (fullName.isEmpty()) {
            edtFullName.setError("Vui lòng nhập họ tên");
            return false;
        }

        // Kiểm tra email
        if (email.isEmpty()) {
            edtEmail.setError("Vui lòng nhập email");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email không hợp lệ");
            return false;
        }

        // Kiểm tra mật khẩu
        if (password.isEmpty()) {
            edtPassword.setError("Vui lòng nhập mật khẩu");
            return false;
        }
        if (password.length() < 6) {
            edtPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        }

        // Kiểm tra xác nhận mật khẩu
        if (confirmPassword.isEmpty()) {
            edtConfirmPassword.setError("Vui lòng xác nhận mật khẩu");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            edtConfirmPassword.setError("Mật khẩu xác nhận không khớp");
            return false;
        }

        return true;
    }

    private void registerUser(String fullName, String email, String password) {
        // TODO: Implement your registration logic here
        // Ví dụ: Gọi API đăng ký hoặc lưu vào database local

        // Hiển thị thông báo thành công
        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

        // Quay về màn hình đăng nhập
        finish();
    }
}
