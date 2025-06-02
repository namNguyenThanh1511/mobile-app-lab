package com.example.lab05_bai2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<Module> moduleList;
    private ModuleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        moduleList = new ArrayList<>();

        // Add sample data
        moduleList.add(new Module(
                "ListView trong Android",
                "Listview trong Android là một thành phần dùng để nhóm nhiều mục (item) v...",
                "Android"
        ));
        moduleList.add(new Module(
                "Xử lý sự kiện trong iOS",
                "Xử lý sự kiện trong iOS. Sau khi các bạn đã biết cách thiết kế giao diện cho các ứng...",
                "iOS"
        ));

        adapter = new ModuleAdapter(this, moduleList);
        listView.setAdapter(adapter);

        setupListeners();
    }

    private void setupListeners() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            showEditDialog(position);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            showDeleteDialog(position);
            return true;
        });

        // Add FAB for adding new items
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> showAddDialog());
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_edit_module, null);

        EditText edtTitle = dialogView.findViewById(R.id.edtTitle);
        EditText edtDescription = dialogView.findViewById(R.id.edtDescription);
        Spinner spinnerPlatform = dialogView.findViewById(R.id.spinnerPlatform);

        builder.setView(dialogView)
                .setTitle("Thêm Module Mới")
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String title = edtTitle.getText().toString();
                    String description = edtDescription.getText().toString();
                    String platform = spinnerPlatform.getSelectedItem().toString();

                    moduleList.add(new Module(title, description, platform));
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showEditDialog(int position) {
        Module module = moduleList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_edit_module, null);

        EditText edtTitle = dialogView.findViewById(R.id.edtTitle);
        EditText edtDescription = dialogView.findViewById(R.id.edtDescription);
        Spinner spinnerPlatform = dialogView.findViewById(R.id.spinnerPlatform);

        edtTitle.setText(module.getTitle());
        edtDescription.setText(module.getDescription());
        // Set spinner selection based on platform

        builder.setView(dialogView)
                .setTitle("Sửa Module")
                .setPositiveButton("Cập nhật", (dialog, which) -> {
                    module.setTitle(edtTitle.getText().toString());
                    module.setDescription(edtDescription.getText().toString());
                    module.setPlatform(spinnerPlatform.getSelectedItem().toString());
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showDeleteDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa Module")
                .setMessage("Bạn có chắc muốn xóa module này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    moduleList.remove(position);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
