package com.example.lab03_bai2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private EditText edtTitle, edtDescription;
    private Button btnAdd;
    private List<Country> countryList;
    private CountryAdapter adapter;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupListData();
        setupListView();
        setupListeners();
    }

    private void initViews() {
        listView = findViewById(R.id.listView);
        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);
        btnAdd = findViewById(R.id.btnAdd);
    }

    private void setupListData() {
        countryList = new ArrayList<>();
        countryList.add(new Country(R.drawable.vietnam, "Việt Nam", "Thủ đô: Hà Nội"));
        countryList.add(new Country(R.drawable.china, "Trung Quốc", "Thủ đô: Bắc Kinh"));
        // Thêm các quốc gia khác
    }

    private void setupListView() {
        adapter = new CountryAdapter(this, countryList);
        listView.setAdapter(adapter);
    }

    private void setupListeners() {
        btnAdd.setOnClickListener(v -> {
            String title = edtTitle.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();

            if (!title.isEmpty() && !description.isEmpty()) {
                if (selectedPosition == -1) {
                    // Thêm mới
                    countryList.add(new Country(R.drawable.default_flag, title, description));
                } else {
                    // Cập nhật
                    Country country = countryList.get(selectedPosition);
                    country.setTitle(title);
                    country.setDescription(description);
                    selectedPosition = -1;
                    btnAdd.setText("Thêm");
                }
                adapter.notifyDataSetChanged();
                clearInputs();
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Country country = countryList.get(position);
            edtTitle.setText(country.getTitle());
            edtDescription.setText(country.getDescription());
            selectedPosition = position;
            btnAdd.setText("Cập nhật");
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            showDeleteDialog(position);
            return true;
        });
    }

    private void clearInputs() {
        edtTitle.setText("");
        edtDescription.setText("");
    }

    private void showDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc muốn xóa item này?");
        builder.setPositiveButton("Có", (dialog, which) -> {
            countryList.remove(position);
            adapter.notifyDataSetChanged();
        });
        builder.setNegativeButton("Không", null);
        builder.show();
    }
}
