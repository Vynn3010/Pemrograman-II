/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package register_pasien;

/**
 *
 * @author calvi
 */

import java.sql.Connection;

public class TestKoneksi {
    public static void main(String[] args) {
        try (Connection c = Koneksi.getConnection()) {
            System.out.println("Berhasil konek: " + (c != null));
        } catch (Exception e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }
}


