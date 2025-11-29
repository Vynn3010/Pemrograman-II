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
class TransferBank extends Pembayaran {
    @Override
    void prosesPembayaran(double jumlah) {
        System.out.println("Pembayaran melalui Transfer Bank berhasil sebesar Rp " + jumlah);
        System.out.println("Bank: BRI / BCA / Mandiri");
        System.out.println("Kode unik telah ditambahkan.");
    }
}