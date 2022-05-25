package com.example.myapplication;

public class ProductModelClass {
    String title,price,image;

    public ProductModelClass(String title, String price, String image) {
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public ProductModelClass() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
