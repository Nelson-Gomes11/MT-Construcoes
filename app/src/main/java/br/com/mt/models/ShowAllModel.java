package br.com.mt.models;

import java.io.Serializable;

public class ShowAllModel implements Serializable {
    String descriptions;
    String name;
    int price;
    String img_url;
    String type;

    public ShowAllModel() {
    }

    public String getDescription() {
        return descriptions;
    }

    public void setDescriptions(String description) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ShowAllModel(String descriptions, String name, int price, String img_url, String type) {
        this.descriptions = descriptions;
        this.name = name;
        this.price = price;
        this.img_url = img_url;
        this.type = type;
    }
}