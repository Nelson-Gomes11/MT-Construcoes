package br.com.mt.models;

public class PromoModel {
     String name;
    String description;
    String img_url;
    String type;
    int price;

    public PromoModel(){
    }

    public PromoModel(String name, String description, String type, String img_url, int price) {
        this.name = name;
        this.description = description;
        this.img_url = img_url;
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
