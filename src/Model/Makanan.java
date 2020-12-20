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
public class Makanan extends Barang {
    private String rasa;
    private String jenisMakanan;

    public Makanan(String rasa, String jenisMakanan, String idBarang, String nama, String jenis, int qty, int harga) {
        super(idBarang, nama, jenis, qty, harga);
        this.rasa = rasa;
        this.jenisMakanan = jenisMakanan;
    }
    
    public String getRasa() {
        return rasa;
    }

    public String getJenisMakanan() {
        return jenisMakanan;
    }

    @Override
    public String display() {
        return "Nama Barang: "+ getNama()+"\nRasa: "+getRasa()+"\nJenis Makanan: "+getJenisMakanan()+"\nHarga: "+getHarga();
    }
    
    
}
