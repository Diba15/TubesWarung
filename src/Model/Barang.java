/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Diba15
 */
public abstract class Barang {

    private final String idBarang;
    private final String nama, jenis;
    int harga;
    private int qty;

    public Barang(String idBarang, String nama, String jenis, int qty, int harga) {
        this.idBarang = idBarang;
        this.nama = nama;
        this.jenis = jenis;
        this.qty = qty;
        this.harga = harga;
    }

    public String getIdBarang() {
        return idBarang;
    }

    public String getNama() {
        return nama;
    }

    public String getJenis() {
        return jenis;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getHarga() {
        return harga;
    }

    public abstract String display();
}
