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
public class Minuman extends Barang {
    private String rasa;
    private String jenisMinuman;

    public Minuman(String rasa, String jenisMinuman, String idBarang, String nama, String jenis, int qty, int harga) {
        super(idBarang, nama, jenis, qty, harga);
        this.rasa = rasa;
        this.jenisMinuman = jenisMinuman;
    }

    

    public String getRasa() {
        return rasa;
    }

    public String getJenisMinuman() {
        return jenisMinuman;
    }

    @Override
    public String display() {
        return "Nama Barang: "+ getNama()+"\nRasa: "+getRasa()+"\nJenis Makanan: "+getJenisMinuman()+"\nHarga: "+getHarga();
    }
    
    
}
