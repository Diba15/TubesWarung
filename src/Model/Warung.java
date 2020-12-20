/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.*;

/**
 *
 * @author Diba15
 */
public class Warung {

    ArrayList<Barang> listBarang = new ArrayList<>();

    public void addMakanMinum(String rasa, String jenis, String idBarang, String namaBarang, String jenisBarang, int qty, int hargaBarang) {
        if (jenisBarang.equalsIgnoreCase("Makanan")) {
            listBarang.add(new Makanan(rasa, jenis, idBarang, namaBarang, jenisBarang, qty, hargaBarang));
        } else if (jenisBarang.equalsIgnoreCase("Makanan")) {
            listBarang.add(new Minuman(rasa, jenis, idBarang, namaBarang, jenisBarang, qty, hargaBarang));
        }
    }
    
    public void addBarangLain(String jenis, String idBarang, String namaBarang,String jenisBarang, int qty, int hargaBarang) {
        listBarang.add(new EtcBarang(jenis, idBarang, namaBarang, jenisBarang, qty, hargaBarang));
    }
    
    public String display(int i) {
        return listBarang.get(i).display();
    }
    
    public String getJenis(int i) {
        return listBarang.get(i).getJenis();
    }
    
    public void clearList() {
        listBarang.clear();
    }
    
    public String getID(int i) {
        return listBarang.get(i).getIdBarang();
    }
    
    public void setQty(int i, int jumlahBeli) {
        listBarang.get(i).setQty(listBarang.get(i).getQty() - jumlahBeli);
    }
    
    public int getQty(int i) {
        return listBarang.get(i).getQty();
    }
    
    public int getHarga(int i) {
        return listBarang.get(i).getHarga();
    }
    
    public int getSize() {
        return listBarang.size();
    }
    
    public String getIdForSearch(String namaBarang) {
        String idBarang = null;
        for (Barang barang : listBarang) {
            if (namaBarang.equalsIgnoreCase(barang.getNama())) {
                idBarang = barang.getIdBarang();
            }
        }
        return idBarang;
    }
    
    public void resetList() {
        listBarang.clear();
    }
}
