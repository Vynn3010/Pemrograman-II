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
public class Main {
    public static void main(String[] args) {

        Pembayaran bayar;

        System.out.println("=== POLIMORFISME PEMBAYARAN ===");

        bayar = new Cash();
        bayar.prosesPembayaran(50000);

        System.out.println("------------------------------");

        bayar = new TransferBank();
        bayar.prosesPembayaran(150000);

        System.out.println("------------------------------");

        bayar = new Ewallet();
        bayar.prosesPembayaran(75000);
    }
}