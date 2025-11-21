/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pertemuan6_Polimorfisme_Statis;

/**
 *
 * @author calvi
 */

public class Diskon {

    // Menghitung harga setelah diskon (persentase tetap 10%)
    int hargaDiskon(int hargaNormal) {
        return hargaNormal - (hargaNormal * 10 / 100);   // diskon 10%
    }

    // Mengembalikan harga setelah diskon (langsung diberikan)
    int hargaDiskon(int hargaNormal, int hargaSetelahDiskon) {
        return hargaSetelahDiskon;
    }

    public static void main(String[] args) {

        Diskon d = new Diskon();

        int hargaNormal = 30000;
        int potongan = 2010;

        // menghitung harga setelah dikurangi potongan biasa
        int hargaSetelahDiskon = hargaNormal - potongan;

        System.out.println("Harga Setelah Diskon 10% = " + d.hargaDiskon(hargaNormal));
        System.out.println("Harga Setelah Diskon potongan = " + hargaSetelahDiskon);
    }
}


