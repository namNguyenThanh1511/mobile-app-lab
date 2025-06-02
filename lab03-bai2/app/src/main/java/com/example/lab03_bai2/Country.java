package com.example.lab03_bai2;

public class Country {
    private int imageId;
    private String title;
    private String description;

    public Country(int imageId, String title, String description) {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
    }

    // Getters and Setters
    public int getImageId() { return imageId; }
    public void setImageId(int imageId) { this.imageId = imageId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

