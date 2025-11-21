/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pertemuan6_Konstruktor;

/**
 *
 * @author calvi
 */

public class Peternak {
    public String namaPeternak;
    public String jenisAyam;

    public Peternak(String namaPeternak, String jenisAyam){
        this.namaPeternak = namaPeternak;
        this.jenisAyam = jenisAyam;
    }
}

class DemoConstructor{
    public static void main(String[] args) {
        Peternak ayam1 = new Peternak("Budi Ayam", "Ayam Petelur");
        
        System.out.println("Nama Peternak: " + ayam1.namaPeternak);
        System.out.println("Jenis Ayam: " + ayam1.jenisAyam);
    }
}
