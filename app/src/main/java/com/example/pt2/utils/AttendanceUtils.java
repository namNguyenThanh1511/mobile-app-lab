package com.example.pt2.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import com.example.pt2.model.AttendanceData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AttendanceUtils {
    private static final String TAG = "AttendanceUtils";

    /**
     * Lấy thông tin WiFi SSID - Tương thích với tất cả API levels
     */
    public static String getCurrentWifiSSID(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                // Android 12+ - Sử dụng ConnectivityManager
                return getWifiSSIDFromConnectivityManager(context);
            } else {
                // Android 11 trở xuống - Sử dụng WifiManager
                return getWifiSSIDFromWifiManager(context);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting WiFi SSID", e);
            return "Không thể lấy SSID";
        }
    }

    /**
     * Lấy thông tin WiFi BSSID - Tương thích với tất cả API levels
     */
    public static String getCurrentWifiBSSID(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                // Android 12+ - Sử dụng ConnectivityManager
                return getWifiBSSIDFromConnectivityManager(context);
            } else {
                // Android 11 trở xuống - Sử dụng WifiManager
                return getWifiBSSIDFromWifiManager(context);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting WiFi BSSID", e);
            return "Không thể lấy BSSID";
        }
    }

    /**
     * Phương thức cho Android 12+ sử dụng ConnectivityManager
     */
    private static String getWifiSSIDFromConnectivityManager(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivityManager != null) {
                Network activeNetwork = connectivityManager.getActiveNetwork();
                if (activeNetwork != null) {
                    NetworkCapabilities networkCapabilities =
                            connectivityManager.getNetworkCapabilities(activeNetwork);

                    if (networkCapabilities != null &&
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

                        // Vẫn cần WifiManager để lấy SSID, nhưng kiểm tra kết nối qua ConnectivityManager
                        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                        if (wifiManager != null && wifiManager.isWifiEnabled()) {
                            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                            if (wifiInfo != null) {
                                String ssid = wifiInfo.getSSID();
                                return ssid != null ? ssid.replace("\"", "") : "Không có SSID";
                            }
                        }
                    }
                }
            }
        }
        return "WiFi không kết nối";
    }

    /**
     * Phương thức cho Android 12+ sử dụng ConnectivityManager
     */
    private static String getWifiBSSIDFromConnectivityManager(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivityManager != null) {
                Network activeNetwork = connectivityManager.getActiveNetwork();
                if (activeNetwork != null) {
                    NetworkCapabilities networkCapabilities =
                            connectivityManager.getNetworkCapabilities(activeNetwork);

                    if (networkCapabilities != null &&
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

                        // Vẫn cần WifiManager để lấy BSSID
                        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                        if (wifiManager != null && wifiManager.isWifiEnabled()) {
                            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                            if (wifiInfo != null) {
                                return wifiInfo.getBSSID() != null ? wifiInfo.getBSSID() : "Không có BSSID";
                            }
                        }
                    }
                }
            }
        }
        return "WiFi không kết nối";
    }

    /**
     * Phương thức cho Android 11 trở xuống sử dụng WifiManager
     */
    @SuppressWarnings("deprecation")
    private static String getWifiSSIDFromWifiManager(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                String ssid = wifiInfo.getSSID();
                return ssid != null ? ssid.replace("\"", "") : "Không có SSID";
            }
        }
        return "WiFi tắt";
    }

    /**
     * Phương thức cho Android 11 trở xuống sử dụng WifiManager
     */
    @SuppressWarnings("deprecation")
    private static String getWifiBSSIDFromWifiManager(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                return wifiInfo.getBSSID() != null ? wifiInfo.getBSSID() : "Không có BSSID";
            }
        }
        return "WiFi tắt";
    }

    /**
     * Lấy thời gian hiện tại
     */
    public static String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    /**
     * Tạo tên file ảnh duy nhất
     */
    public static String generatePhotoFileName(String studentId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        return "attendance_" + studentId + "_" + sdf.format(new Date()) + ".jpg";
    }

    /**
     * Lưu dữ liệu điểm danh
     */
    public static void saveAttendanceData(AttendanceData data) {
        Log.d(TAG, "=== THÔNG TIN ĐIỂM DANH ===");
        Log.d(TAG, "Mã sinh viên: " + data.getStudentId());
        Log.d(TAG, "Ca học: " + data.getTimeSlot());
        Log.d(TAG, "Thời gian: " + data.getTimestamp());
        Log.d(TAG, "Đường dẫn ảnh: " + data.getPhotoPath());
        Log.d(TAG, "WiFi SSID: " + data.getWifiSSID());
        Log.d(TAG, "WiFi BSSID: " + data.getWifiBSSID());
        Log.d(TAG, "========================");

    }

    /**
     * Kiểm tra WiFi có kết nối không - Tương thích với tất cả API levels
     */
    public static boolean isWifiConnected(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Android 6.0+ - Sử dụng ConnectivityManager
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                if (connectivityManager != null) {
                    Network activeNetwork = connectivityManager.getActiveNetwork();
                    if (activeNetwork != null) {
                        NetworkCapabilities networkCapabilities =
                                connectivityManager.getNetworkCapabilities(activeNetwork);

                        return networkCapabilities != null &&
                                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
                    }
                }
            } else {
                // Android 5.1 trở xuống - Sử dụng WifiManager
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                return wifiManager != null && wifiManager.isWifiEnabled() &&
                        wifiManager.getConnectionInfo().getNetworkId() != -1;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error checking WiFi connection", e);
        }
        return false;
    }

    /**
     * Lấy thông tin chi tiết về kết nối WiFi
     */
    public static String getWifiConnectionDetails(Context context) {
        StringBuilder details = new StringBuilder();

        try {
            String ssid = getCurrentWifiSSID(context);
            String bssid = getCurrentWifiBSSID(context);
            boolean isConnected = isWifiConnected(context);

            details.append("SSID: ").append(ssid).append("\n");
            details.append("BSSID: ").append(bssid).append("\n");
            details.append("Trạng thái: ").append(isConnected ? "Đã kết nối" : "Chưa kết nối").append("\n");
            details.append("Android Version: ").append(Build.VERSION.SDK_INT);

        } catch (Exception e) {
            Log.e(TAG, "Error getting WiFi details", e);
            details.append("Lỗi khi lấy thông tin WiFi");
        }

        return details.toString();
    }
}