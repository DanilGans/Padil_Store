package com.example.padil.Model;

import java.io.Serializable;
import java.lang.reflect.Array;

public class ProdukPopulerModel implements Serializable {

    String nama, deskripsi, tagline, img_url;
    int harga;

    public ProdukPopulerModel() {
    }

    public ProdukPopulerModel(String nama, String deskripsi, String tagline, String img_url, int harga) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.tagline = tagline;
        this.img_url = img_url;
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

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
