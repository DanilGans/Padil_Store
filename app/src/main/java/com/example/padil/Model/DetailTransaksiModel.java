package com.example.padil.Model;

import java.io.Serializable;

public class DetailTransaksiModel implements Serializable {

    String namaProduk, hargaProduk, totalKuantiti, img_url, variasi, cicilan;
    int totalHarga, subtotal, ongkir, totalsemua;

    public DetailTransaksiModel() {
    }

    public DetailTransaksiModel(String namaProduk, String hargaProduk, String totalKuantiti, String variasi, String cicilan, String img_url, int subtotal, int ongkir, int totalsemua, int totalHarga) {
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.totalKuantiti = totalKuantiti;
        this.img_url = img_url;
        this.cicilan = cicilan;
        this.variasi = variasi;
        this.subtotal = subtotal;
        this.totalHarga = totalHarga;
        this.ongkir = ongkir;
        this.totalsemua = totalsemua;
    }

    public String getCicilan() {
        return cicilan;
    }

    public void setCicilan(String cicilan) {
        this.cicilan = cicilan;
    }

    public String getVariasi() {
        return variasi;
    }

    public void setVariasi(String variasi) {
        this.variasi = variasi;
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

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getOngkir() {
        return ongkir;
    }

    public void setOngkir(int ongkir) {
        this.ongkir = ongkir;
    }

    public int getTotalsemua() {
        return totalsemua;
    }

    public void setTotalsemua(int totalsemua) {
        this.totalsemua = totalsemua;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }
}
