package com.example.padil.Model;

public class KeranjangModel {

    String namaProduk, hargaProduk, totalKuantiti, img_url;
    int totalHarga;

    public KeranjangModel() {
    }

    public KeranjangModel(String namaProduk, String hargaProduk, String totalKuantiti, String img_url, int totalHarga) {
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.totalKuantiti = totalKuantiti;
        this.img_url = img_url;
        this.totalHarga = totalHarga;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getHargaProduk() {
        return hargaProduk;
    }

    public void setHargaProduk(String hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public String getTotalKuantiti() {
        return totalKuantiti;
    }

    public void setTotalKuantiti(String totalKuantiti) {
        this.totalKuantiti = totalKuantiti;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
