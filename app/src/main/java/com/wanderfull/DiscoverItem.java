package com.wanderfull;


public class DiscoverItem {
    private String title;
    private String description;
    private int imageResource;

    public DiscoverItem(String title, String description, int imageResource) {
        this.title = title;
        this.description = description;
        this.imageResource = imageResource;
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getImageResource() { return imageResource; }
}