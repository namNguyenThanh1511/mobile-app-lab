package com.example.lab05;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<User> userList;
    private EditText edtUsername, edtFullname, edtEmail;
    private Button btnAdd;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main   );

        initViews();
        setupRecyclerView();
        setupListeners();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        edtUsername = findViewById(R.id.edtUsername);
        edtFullname = findViewById(R.id.edtFullname);
        edtEmail = findViewById(R.id.edtEmail);
        btnAdd = findViewById(R.id.btnAdd);

        userList = new ArrayList<>();
        // Thêm một số dữ liệu mẫu
        userList.add(new User("john_doe", "John Doe", "john@example.com"));
        userList.add(new User("jane_smith", "Jane Smith", "jane@example.com"));
    }

    private void setupRecyclerView() {
        adapter = new UserAdapter(this, userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupListeners() {
        btnAdd.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String fullname = edtFullname.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();

            if (username.isEmpty() || fullname.isEmpty() || email.isEmpty()) {
                // Thêm thông báo lỗi nếu các trường trống
                if (username.isEmpty()) {
                    edtUsername.setError("Vui lòng nhập username");
                }
                if (fullname.isEmpty()) {
                    edtFullname.setError("Vui lòng nhập fullname");
                }
                if (email.isEmpty()) {
                    edtEmail.setError("Vui lòng nhập email");
                }
                return;
            }

            if (selectedPosition == -1) {
                // Thêm mới
                userList.add(new User(username, fullname, email));
                adapter.notifyItemInserted(userList.size() - 1);
            } else {
                // Cập nhật
                User user = userList.get(selectedPosition);
                user.setUsername(username);
                user.setFullname(fullname);
                user.setEmail(email);
                adapter.notifyItemChanged(selectedPosition);
                selectedPosition = -1;
                btnAdd.setText("Thêm");
            }

            clearInputs();
        });
    }


    public void editUser(int position) {
        User user = userList.get(position);
        edtUsername.setText(user.getUsername());
        edtFullname.setText(user.getFullname());
        edtEmail.setText(user.getEmail());
        selectedPosition = position;
        btnAdd.setText("Cập nhật");
    }

    public void deleteUser(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa người dùng này?")
                .setPositiveButton("Có", (dialog, which) -> {
                    userList.remove(position);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private void clearInputs() {
        edtUsername.setText("");
        edtFullname.setText("");
        edtEmail.setText("");
    }
}
