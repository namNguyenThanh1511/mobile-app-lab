package com.example.lab05_bai2;

public class Module {
    private String title;
    private String description;
    private String platform;

    public Module(String title, String description, String platform) {
        this.title = title;
        this.description = description;
        this.platform = platform;
    }

    // Getters và Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }
}

