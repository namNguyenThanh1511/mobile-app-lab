package com.example.bai01;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private EditText edtInput;
    private Button btnAdd;
    private ArrayList<String> itemList;
    private ArrayAdapter<String> adapter;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view
        initViews();

        // Khởi tạo dữ liệu
        itemList = new ArrayList<>();
        itemList.add("Item 1");
        itemList.add("Item 2");
        itemList.add("Item 3");

        // Tạo adapter
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                itemList);
        listView.setAdapter(adapter);

        // Xử lý sự kiện click button Thêm
        btnAdd.setOnClickListener(v -> {
            String input = edtInput.getText().toString().trim();
            if (!input.isEmpty()) {
                if (selectedPosition == -1) {
                    // Thêm mới
                    addItem(input);
                } else {
                    // Cập nhật
                    updateItem(selectedPosition, input);
                }
                edtInput.setText("");
                selectedPosition = -1;
                btnAdd.setText("Thêm");
            }
        });

        // Xử lý sự kiện click item trong ListView
        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            edtInput.setText(itemList.get(position));
            btnAdd.setText("Sửa");
        });

        // Xử lý sự kiện long click để xóa
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            showDeleteDialog(position);
            return true;
        });
    }

    private void initViews() {
        listView = findViewById(R.id.listView);
        edtInput = findViewById(R.id.edtInput);
        btnAdd = findViewById(R.id.btnAdd);
    }

    private void addItem(String item) {
        itemList.add(item);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
    }

    private void updateItem(int position, String newItem) {
        itemList.set(position, newItem);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Đã cập nhật thành công", Toast.LENGTH_SHORT).show();
    }

    private void deleteItem(int position) {
        itemList.remove(position);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Đã xóa thành công", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc muốn xóa item này?");
        builder.setPositiveButton("Có", (dialog, which) -> deleteItem(position));
        builder.setNegativeButton("Không", null);
        builder.show();
    }
}
