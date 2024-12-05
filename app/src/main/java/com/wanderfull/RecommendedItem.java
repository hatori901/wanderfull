package com.wanderfull;

public class RecommendedItem {
    private String title;
    private String description;
    private String location;
    private int imageResource;

    public RecommendedItem(String title, String description, String location, int imageResource) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.imageResource = imageResource;
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public int getImageResource() { return imageResource; }
}