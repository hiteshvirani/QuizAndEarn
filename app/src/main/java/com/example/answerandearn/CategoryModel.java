package com.example.answerandearn;

public class CategoryModel {
     private String categoryId,categoryName, categoryImage;

     public CategoryModel(String categoryId,String catagoryName,String categoryImage)
     {
         this.categoryId=categoryId;
         this.categoryImage=categoryImage;
         this.categoryName=catagoryName;
     }

     public CategoryModel(){}

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
}
