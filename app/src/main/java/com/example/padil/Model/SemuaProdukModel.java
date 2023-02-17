package com.example.padil.Model;

import java.io.Serializable;

public class SemuaProdukModel implements Serializable {

    String id, deskripsi, img_url, nama, type, tagline;
    int harga;

    public SemuaProdukModel() {
    }

    public SemuaProdukModel(String id, String deskripsi, String img_url, String nama, String type, String tagline, int harga) {
        this.deskripsi = deskripsi;
        this.id = id;
        this.img_url = img_url;
        this.nama = nama;
        this.tagline = tagline;
        this.type = type;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
}
