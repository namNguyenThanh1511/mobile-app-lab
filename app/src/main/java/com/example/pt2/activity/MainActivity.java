package com.example.pt2.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pt2.R;
import com.example.pt2.model.AttendanceData;
import com.example.pt2.utils.AttendanceUtils;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_PERMISSIONS = 10;

    // UI Components
    private EditText etStudentId;
    private Spinner spinnerTimeSlot;
    private PreviewView previewView;
    private ImageView ivCapturedPhoto;
    private TextView tvWifiInfo, tvTimestamp;
    private Button btnCapture, btnSubmit;

    // Camera
    private ImageCapture imageCapture;
    private String capturedPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupSpinner();
        updateWifiInfo();
        updateTimestamp();

        if (allPermissionsGranted()) {
            startCamera();
        } else {
            requestPermissions();
        }

        setupClickListeners();
    }

    private void initViews() {
        etStudentId = findViewById(R.id.etStudentId);
        spinnerTimeSlot = findViewById(R.id.spinnerTimeSlot);
        previewView = findViewById(R.id.previewView);
        ivCapturedPhoto = findViewById(R.id.ivCapturedPhoto);
        tvWifiInfo = findViewById(R.id.tvWifiInfo);
        tvTimestamp = findViewById(R.id.tvTimestamp);
        btnCapture = findViewById(R.id.btnCapture);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setEnabled(false);
    }

    private void setupSpinner() {
        String[] timeSlots = {
                "Ca 1: 7:00 - 9:30",
                "Ca 2: 9:45 - 12:15",
                "Ca 3: 13:00 - 15:30",
                "Ca 4: 15:45 - 18:15",
                "Ca 5: 18:30 - 21:00"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, timeSlots);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeSlot.setAdapter(adapter);
    }

    private void updateWifiInfo() {
        String ssid = AttendanceUtils.getCurrentWifiSSID(this);
        String bssid = AttendanceUtils.getCurrentWifiBSSID(this);
        tvWifiInfo.setText("WiFi: " + ssid + "\nBSSID: " + bssid);
    }

    private void updateTimestamp() {
        tvTimestamp.setText("Thời gian: " + AttendanceUtils.getCurrentTimestamp());
    }

    private void setupClickListeners() {
        btnCapture.setOnClickListener(v -> capturePhoto());
        btnSubmit.setOnClickListener(v -> submitAttendance());

        // Refresh WiFi info khi click
        tvWifiInfo.setOnClickListener(v -> updateWifiInfo());

        // Refresh timestamp khi click
        tvTimestamp.setOnClickListener(v -> updateTimestamp());
    }

    private String[] getRequiredPermissions() {
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.CAMERA);

        // Chỉ yêu cầu WRITE_EXTERNAL_STORAGE cho Android 12 trở xuống

        return permissions.toArray(new String[0]);
    }

    private boolean allPermissionsGranted() {
        String[] requiredPermissions = getRequiredPermissions();
        for (String permission : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, getRequiredPermissions(), REQUEST_CODE_PERMISSIONS);
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "Error starting camera", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        imageCapture = new ImageCapture.Builder().build();

        // Sử dụng camera trước để selfie
        CameraSelector cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA;

        try {
            cameraProvider.unbindAll();
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
        } catch (Exception e) {
            Log.e(TAG, "Use case binding failed", e);
        }
    }

    private void capturePhoto() {
        if (imageCapture == null) {
            Toast.makeText(this, "Camera chưa sẵn sàng", Toast.LENGTH_SHORT).show();
            return;
        }

        String studentId = etStudentId.getText().toString().trim();
        if (studentId.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã sinh viên", Toast.LENGTH_SHORT).show();
            return;
        }

        // Sử dụng internal storage thay vì external storage
        String fileName = AttendanceUtils.generatePhotoFileName(studentId);
        File photoFile;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ - sử dụng internal storage
            photoFile = new File(getFilesDir(), fileName);
        } else {
            // Android 12 trở xuống - có thể sử dụng external storage
            File externalDir = getExternalFilesDir(null);
            photoFile = new File(externalDir != null ? externalDir : getFilesDir(), fileName);
        }

        capturedPhotoPath = photoFile.getAbsolutePath();

        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults output) {
                        // Hiển thị ảnh đã chụp
                        Uri photoUri = Uri.fromFile(photoFile);
                        ivCapturedPhoto.setImageURI(photoUri);
                        ivCapturedPhoto.setVisibility(View.VISIBLE);

                        btnSubmit.setEnabled(true);
                        Toast.makeText(MainActivity.this, "Chụp ảnh thành công!", Toast.LENGTH_SHORT).show();

                        Log.d(TAG, "Photo saved: " + capturedPhotoPath);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.e(TAG, "Photo capture failed: " + exception.getMessage(), exception);
                        Toast.makeText(MainActivity.this, "Lỗi chụp ảnh: " + exception.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void submitAttendance() {
        String studentId = etStudentId.getText().toString().trim();
        String timeSlot = spinnerTimeSlot.getSelectedItem().toString();

        if (studentId.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã sinh viên", Toast.LENGTH_SHORT).show();
            return;
        }

        if (capturedPhotoPath == null) {
            Toast.makeText(this, "Vui lòng chụp ảnh trước", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cập nhật thông tin mới nhất
        updateWifiInfo();
        updateTimestamp();

        // Tạo đối tượng dữ liệu điểm danh
        AttendanceData attendanceData = new AttendanceData(
                studentId,
                timeSlot,
                AttendanceUtils.getCurrentTimestamp(),
                capturedPhotoPath,
                AttendanceUtils.getCurrentWifiSSID(this),
                AttendanceUtils.getCurrentWifiBSSID(this)
        );

        // Lưu dữ liệu (hiện tại chỉ in ra Log)
        AttendanceUtils.saveAttendanceData(attendanceData);

        Toast.makeText(this, "Điểm danh thành công!\nKiểm tra Logcat để xem thông tin",
                Toast.LENGTH_LONG).show();

        // Reset form
        resetForm();
    }

    private void resetForm() {
        etStudentId.setText("");
        spinnerTimeSlot.setSelection(0);
        ivCapturedPhoto.setVisibility(View.GONE);
        btnSubmit.setEnabled(false);
        capturedPhotoPath = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this, "Cần cấp quyền camera để sử dụng ứng dụng", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}