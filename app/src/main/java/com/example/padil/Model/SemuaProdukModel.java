package com.example.padil.Model;

import java.io.Serializable;

public class SemuaProdukModel implements Serializable {

    String id, deskripsi,
            img_url, img_url2, img_url3, img_url4, img_url5,
            img_url6, img_url7, img_url8, img_url9, img_url10,
            nama, type, brpVariant, tagline, spinnerVariasi;
    int harga;

    public SemuaProdukModel() {
    }

    public SemuaProdukModel(String id, String deskripsi,
                            String img_url, String img_url2, String img_url3, String img_url4, String img_url5,
                            String img_url6, String img_url7, String img_url8, String img_url9, String img_url10,
                            String nama, String type, String brpVariant, String tagline,String spinnerVariasi, int harga) {
        this.deskripsi = deskripsi;
        this.id = id;
        this.img_url = img_url;
        this.img_url2 = img_url2;
        this.img_url3 = img_url3;
        this.img_url4 = img_url4;
        this.img_url5 = img_url5;
        this.img_url6 = img_url6;
        this.img_url7 = img_url7;
        this.img_url8 = img_url8;
        this.img_url9 = img_url9;
        this.img_url10 = img_url10;
        this.spinnerVariasi = spinnerVariasi;
        this.nama = nama;
        this.tagline = tagline;
        this.type = type;
        this.brpVariant = brpVariant;
        this.harga = harga;
    }

    public String getSpinnerVariasi() {
        return spinnerVariasi;
    }

    public void setSpinnerVariasi(String spinnerVariasi) {
        this.spinnerVariasi = spinnerVariasi;
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

    public String getImg_url2() {
        return img_url2;
    }

    public void setImg_url2(String img_url2) {
        this.img_url2 = img_url2;
    }

    public String getImg_url3() {
        return img_url3;
    }

    public void setImg_url3(String img_url3) {
        this.img_url3 = img_url3;
    }

    public String getImg_url4() {
        return img_url4;
    }
    public void setImg_url4(String img_url4) {
        this.img_url4 = img_url4;
    }

    public String getImg_url5() {
        return img_url5;
    }

    public void setImg_url5(String img_url5) {
        this.img_url5 = img_url5;
    }

    public String getImg_url6() {
        return img_url6;
    }

    public void setImg_url6(String img_url6) {
        this.img_url6 = img_url6;
    }

    public String getImg_url7() {
        return img_url7;
    }

    public void setImg_url7(String img_url7) {
        this.img_url7 = img_url7;
    }

    public String getImg_url8() {
        return img_url8;
    }

    public void setImg_url8(String img_url8) {
        this.img_url8 = img_url8;
    }

    public String getImg_url9() {
        return img_url9;
    }

    public void setImg_url9(String img_url9) {
        this.img_url9 = img_url9;
    }

    public String getImg_url10() {
        return img_url10;
    }

    public void setImg_url10(String img_url10) {
        this.img_url10 = img_url10;
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

    public String getBrpVariant() {
        return brpVariant;
    }

    public void setBrpVariant(String brpVariant) {
        this.brpVariant = brpVariant;
    }
}
