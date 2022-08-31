package com.example.wallpaper;

public class CategoryRVModal {

    private String category;
    private String categoryIVUrl;

    public CategoryRVModal(String category, String categoryIVUrl) {
        this.category = category;
        this.categoryIVUrl = categoryIVUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory() {
        this.category = category;
    }

    public String getCategoryIVUrl() {
        return categoryIVUrl;
    }

    public void setCategoryIVUrl() {
        this.categoryIVUrl = categoryIVUrl;
    }
}
