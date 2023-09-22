package br.com.mt.models;

public class MaisVend {
    String name;
    String descriptions;
    String img_url;
    String type;

    public MaisVend() {
    }
    public MaisVend(String name, String type, String descriptions, String img_url, int price) {
        this.name = name;
        this.descriptions = descriptions;
        this.img_url = img_url;
        this.type = type;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() { return type; }

    public void setType(String type) {this.type = type;}
}
