/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pertemuan5_inheritance_pewarisan;

/**
 *
 * @author calvi
 */
public class Main {
    public static void main(String[] args) {
        
        //Objek Buku
        Buku buku = new Buku();

        Novel novel = new Novel();
        novel.jumlahHalaman = 350f;


        Komik komik = new Komik();
        komik.jumlahGambar = 500f;


        Ensiklopedia ensi = new Ensiklopedia();
        ensi.kategoriKode = 7f;
        
        //Menunjukksn Inheritance
        buku.kodeBuku();
        buku.tahunTerbit();
        
        novel.kodeBuku();
        novel.tahunTerbit();
        
        komik.kodeBuku();
        komik.tahunTerbit();
        
        ensi.kodeBuku();
        ensi.tahunTerbit();
    }
}

