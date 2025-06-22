package com.example.pt2.model;

public class AttendanceData {
    private String studentId;
    private String timeSlot;
    private String timestamp;
    private String photoPath;
    private String wifiSSID;
    private String wifiBSSID;

    public AttendanceData() {
    }

    public AttendanceData(String studentId, String timeSlot, String timestamp,
                          String photoPath, String wifiSSID, String wifiBSSID) {
        this.studentId = studentId;
        this.timeSlot = timeSlot;
        this.timestamp = timestamp;
        this.photoPath = photoPath;
        this.wifiSSID = wifiSSID;
        this.wifiBSSID = wifiBSSID;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getWifiSSID() {
        return wifiSSID;
    }

    public void setWifiSSID(String wifiSSID) {
        this.wifiSSID = wifiSSID;
    }

    public String getWifiBSSID() {
        return wifiBSSID;
    }

    public void setWifiBSSID(String wifiBSSID) {
        this.wifiBSSID = wifiBSSID;
    }

    @Override
    public String toString() {
        return "AttendanceData{" +
                "studentId='" + studentId + '\'' +
                ", timeSlot='" + timeSlot + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", wifiSSID='" + wifiSSID + '\'' +
                ", wifiBSSID='" + wifiBSSID + '\'' +
                '}';
    }
}
