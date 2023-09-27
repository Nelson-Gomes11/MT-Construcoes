package br.com.mt.models;

import java.io.Serializable;

public class NewProductsModel implements Serializable {
    String descriptions;
    String name;
    int price;
    String img_url;

    public NewProductsModel() {
    }
    public NewProductsModel(String descriptions, String name, int price, String img_url) {
        this.descriptions = descriptions;
        this.name = name;
        this.price = price;
        this.img_url = img_url;
    }

    public String getDescription() {
        return descriptions;
    }

    public void setDescription(String description) {
        this.descriptions = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}