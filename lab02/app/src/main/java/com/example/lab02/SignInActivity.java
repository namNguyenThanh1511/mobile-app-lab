package com.example.lab02;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnSignIn;
    private TextView tvCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Khởi tạo views
        initViews();

        // Thiết lập click listeners
        setClickListeners();
    }

    private void initViews() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
    }

    private void setClickListeners() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ EditText
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                // Kiểm tra dữ liệu
                if (validateInput(email, password)) {
                    // Thực hiện đăng nhập
                    signInUser(email, password);
                }
            }
        });

        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình đăng ký
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateInput(String email, String password) {
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

        return true;
    }

    private void signInUser(String email, String password) {

        // Ví dụ: Kiểm tra thông tin đăng nhập với database hoặc API

        // Giả lập đăng nhập thành công
        if (email.equals("example@email.com") && password.equals("123456")) {
            // Đăng nhập thành công
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

            // TODO: Chuyển đến màn hình chính của ứng dụng
             Intent intent = new Intent(SignInActivity.this, MainActivity.class);
             startActivity(intent);
             finish();
        } else {
            // Đăng nhập thất bại
            Toast.makeText(this, "Email hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
        }
    }
}
