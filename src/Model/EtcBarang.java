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
public class EtcBarang extends Barang {
    private String ciriKhusus;

    public EtcBarang(String ciriKhusus, String idBarang, String nama, String jenis, int qty, int harga) {
        super(idBarang, nama, jenis, qty, harga);
        this.ciriKhusus = ciriKhusus;
    }


    public String getCiriKhusus() {
        return ciriKhusus;
    }

    @Override
    public String display() {
        return "Nama Barang: "+ getNama()+"\njenis Barang: "+getCiriKhusus()+"\nHarga: "+getHarga();
    }
    
    
}
