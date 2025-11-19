/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pertemuan5_Keyword_This_pada_Class;

/**
 *
 * @author calvi
 */
public class Siswa {
    // Atribut
    private String nama;
    private int nis;
    private String kelas;

    // Constructor menggunakan keyword this
    public Siswa(String nama, int nis) {
        this.nama = nama; 
        this.nis = nis;
        this.kelas = "TKJ1";
    }

    // Method untuk menampilkan data
    public void tampilkanData() {
        System.out.println("=== Data Siswa TKJ1 ===");
        System.out.println("Nama  : " + this.nama);
        System.out.println("NIS   : " + this.nis);
        System.out.println("Kelas : " + this.kelas);
    }

    // Method untuk mengubah nama menggunakan this
    public void ubahNama(String nama) {
        this.nama = nama;
    }
}
