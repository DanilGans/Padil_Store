package com.example.padil.Model;

import java.io.Serializable;

public class KeranjangModel implements Serializable{

    String documentId, namaProduk, hargaProduk, totalKuantiti, img_url, variasi, cicilan;
    int totalHarga;

    public KeranjangModel() {
    }

    public KeranjangModel(String documentId, String namaProduk, String hargaProduk, String totalKuantiti, String cicilan, String img_url, String variasi, int totalHarga) {
        this.documentId = documentId;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.totalKuantiti = totalKuantiti;
        this.variasi = variasi;
        this.cicilan = cicilan;
        this.img_url = img_url;
        this.totalHarga = totalHarga;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
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
