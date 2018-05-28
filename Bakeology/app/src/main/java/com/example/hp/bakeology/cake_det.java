package com.example.hp.bakeology;

import android.widget.ArrayAdapter;

import java.io.Serializable;


public class cake_det implements Serializable {
    private String baker;
    private String flavour;
    private String price;
    private String discription;
    private String category;
    private String image_path;
    private String image_name;

    public String getBaker() {
        return baker;
    }

    public void setBaker(String baker) {
        this.baker = baker;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

     String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }
}
