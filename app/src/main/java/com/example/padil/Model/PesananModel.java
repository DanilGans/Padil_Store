package com.example.padil.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class PesananModel implements Serializable {

    String order_id, alamat, totalharga, tanggal;
    ArrayList<String> list_produk;

    public PesananModel() {
    }

    public PesananModel(String order_id, String alamat, String totalharga, String tanggal, ArrayList<String> list_produk) {
        this.order_id = order_id;
        this.alamat = alamat;
        this.totalharga = totalharga;
        this.tanggal = tanggal;
        this.list_produk = list_produk;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTotalharga() {
        return totalharga;
    }

    public void setTotalharga(String totalharga) {
        this.totalharga = totalharga;
    }

    public ArrayList<String> getList_produk() {
        return list_produk;
    }

    public void setList_produk(ArrayList<String> list_produk) {
        this.list_produk = list_produk;
    }
}
