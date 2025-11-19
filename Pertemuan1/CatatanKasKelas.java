/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pertemuan1;

/**
 *
 * @author calvi
 */
import java.util.Scanner;
import java.util.HashMap;

public class CatatanKasKelas {

    // Daftar mahasiswa dan status pembayaran
    private static HashMap<String, Boolean> daftarMahasiswa = new HashMap<>();
    private static HashMap<String, String> tanggalPembayaran = new HashMap<>(); // Menyimpan tanggal pembayaran
    private static double saldoKas = 0.0;
    private static double biayaPerMahasiswa = 50000;  // Biaya per mahasiswa
    
    // Inisialisasi mahasiswa yang belum membayar
    static {
        daftarMahasiswa.put("Kepin", false);
        daftarMahasiswa.put("Syihab", false);
        daftarMahasiswa.put("Dandi", false);
        daftarMahasiswa.put("Ilham", false);
        daftarMahasiswa.put("Radit", false);
    }

    // Method untuk menampilkan menu
    public static void tampilkanMenu() {
        System.out.println("\n===== Catatan Uang Kas Kelas =====");
        System.out.println("1. Pembayaran Mahasiswa");
        System.out.println("2. Lihat Saldo Kas Kelas");
        System.out.println("3. Lihat Daftar Mahasiswa Belum Membayar");
        System.out.println("4. Lihat Daftar Mahasiswa Sudah Membayar");
        System.out.println("5. Keluar");
        System.out.print("Pilih menu (1/2/3/4/5): ");
    }

    // Method untuk menambah pembayaran mahasiswa
    public static void bayarMahasiswa(String nama, double jumlahBayar, String tanggal, String bulan) {
        if (daftarMahasiswa.containsKey(nama)) {
            if (jumlahBayar >= biayaPerMahasiswa) {
                daftarMahasiswa.put(nama, true); // Tandai mahasiswa sudah membayar
                saldoKas += jumlahBayar;
                String tanggalPembayaranStr = tanggal + " " + bulan; // Menyimpan tanggal pembayaran
                tanggalPembayaran.put(nama, tanggalPembayaranStr); // Menyimpan tanggal pembayaran
                System.out.println(nama + " telah membayar. Terima kasih!");
            } else {
                System.out.println("Jumlah bayar tidak mencukupi. Biaya per mahasiswa adalah " + biayaPerMahasiswa);
            }
        } else {
            System.out.println("Mahasiswa tidak terdaftar.");
        }
    }

    // Method untuk melihat saldo kas kelas
    public static void lihatSaldo() {
        System.out.println("Saldo kas kelas saat ini: " + saldoKas);
    }

    // Method untuk melihat daftar mahasiswa yang belum membayar
    public static void lihatMahasiswaBelumBayar() {
        boolean adaYangBelumBayar = false;
        System.out.println("\nDaftar Mahasiswa Belum Membayar:");
        for (String nama : daftarMahasiswa.keySet()) {
            if (!daftarMahasiswa.get(nama)) {
                System.out.println("- " + nama);
                adaYangBelumBayar = true;
            }
        }
        if (!adaYangBelumBayar) {
            System.out.println("Semua mahasiswa sudah membayar.");
        }
    }

    // Method untuk melihat daftar mahasiswa yang sudah membayar beserta tanggal pembayaran
    public static void lihatMahasiswaSudahBayar() {
        boolean adaYangSudahBayar = false;
        System.out.println("\nDaftar Mahasiswa Sudah Membayar:");
        for (String nama : daftarMahasiswa.keySet()) {
            if (daftarMahasiswa.get(nama)) {
                String tanggal = tanggalPembayaran.get(nama);
                System.out.println("- " + nama + " pada tanggal " + tanggal);
                adaYangSudahBayar = true;
            }
        }
        if (!adaYangSudahBayar) {
            System.out.println("Belum ada mahasiswa yang membayar.");
        }
    }

    // Main method untuk menjalankan program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pilihan;
        String nama;
        double jumlahBayar;
        String tanggal, bulan;

        // Looping utama program
        do {
            tampilkanMenu();
            pilihan = scanner.nextInt();
            scanner.nextLine();  // Membersihkan newline setelah input angka

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan nama mahasiswa: ");
                    nama = scanner.nextLine();
                    System.out.print("Masukkan jumlah pembayaran: ");
                    jumlahBayar = scanner.nextDouble();
                    scanner.nextLine(); // Membersihkan newline setelah input angka
                    System.out.print("Masukkan tanggal pembayaran (1-31): ");
                    tanggal = scanner.nextLine();
                    System.out.print("Masukkan bulan pembayaran (misal: Januari): ");
                    bulan = scanner.nextLine();
                    bayarMahasiswa(nama, jumlahBayar, tanggal, bulan);
                    break;
                case 2:
                    lihatSaldo();
                    break;
                case 3:
                    lihatMahasiswaBelumBayar();
                    break;
                case 4:
                    lihatMahasiswaSudahBayar();
                    break;
                case 5:
                    System.out.println("Terima kasih! Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih antara 1-5.");
            }
        } while (pilihan != 5);

        scanner.close();
    }
}
