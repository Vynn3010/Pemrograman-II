/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pertemuan9;

/**
 *
 * @author calvi
 */

/*import java.sql.PreparedStatement;*/
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;

public class Insert {

    Koneksi konek = new Koneksi();
   

    public void insert(int nim, String nama, String alamat, String jk) {

        try {
            konek.koneksi();
            String sql = "INSERT INTO datamahasiswa (nim, nama, alamat, jenis_kelamin) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = konek.con.prepareStatement(sql);
            ps.setInt(1, nim);
            ps.setString(2, nama);
            ps.setString(3, alamat);
            ps.setString(4, jk);
            ps.executeUpdate();
            ps.close();


            JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
    }
    public static void main(String[] args) {
        Insert tambah = new Insert();
        tambah.insert (2455,"Celvyn","Wonotirto","L");
        tambah.insert (2456,"Celvyn","Wonotirto","L");
    }
}