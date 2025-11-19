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
public class Main {
    public static void main(String[] args) {
        // Membuat objek siswa
        Siswa s1 = new Siswa("Celvyn", 10234);

        // Menampilkan data awal
        s1.tampilkanData();

        // Mengubah nama
        s1.ubahNama("Revaldo");

        System.out.println("\nSetelah nama diubah:");
        s1.tampilkanData();
    }
}
