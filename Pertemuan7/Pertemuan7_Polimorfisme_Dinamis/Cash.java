/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pertemuan7_Polimorfisme_Dinamis;

/**
 *
 * @author calvi
 */
class Cash extends Pembayaran {
    @Override
    void prosesPembayaran(double jumlah) {
        System.out.println("Pembayaran cash berhasil sebesar Rp " + jumlah);
        System.out.println("Silakan ambil struk pembayaran.");
    }
}
