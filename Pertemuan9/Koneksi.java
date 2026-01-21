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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Koneksi {
    public Connection con=null;
    public void koneksi()
    {
        try
        {
            String connectionURL = "jdbc:mysql://localhost:3307/mahasiswa";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            con = DriverManager.getConnection(connectionURL, username, password);
            JOptionPane.showMessageDialog(null, "Sukses Koneksi");
        }

        catch(Exception e) {
        JOptionPane.showMessageDialog(null, e);
        System.exit(0);
        }
}
    public static void main(String[] args) {
        Koneksi koneksi = new Koneksi();
        koneksi.koneksi();
    }
}