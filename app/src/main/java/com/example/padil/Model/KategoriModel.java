package com.example.padil.Model;

public class KategoriModel {

    String img_url;
    String type;

    public KategoriModel() {
    }

    public KategoriModel(String img_url, String type) {
        this.img_url = img_url;
        this.type = type;
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
}

