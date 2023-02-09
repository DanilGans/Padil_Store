package com.example.padil.Model;

import java.io.Serializable;

public class SemuaProdukModel implements Serializable {

    String nama, deskripsi, tagline, img_url, type;
    int harga;

    public SemuaProdukModel() {
    }

    public SemuaProdukModel(String nama, String deskripsi, String tagline, String img_url, String type, int harga) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.tagline = tagline;
        this.img_url = img_url;
        this.type = type;
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
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

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
