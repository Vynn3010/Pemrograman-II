/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UTS;

/**
 *
 * @author calvi
 */

public class AkunGame {
    private String judul;
    private String email;
    private String password;
    private String loginVia;
    private String catatan;
    private String status; // "Belum Terjual" / "Sold Out"
    private int harga;
private boolean buyerUnlocked = false;

    public AkunGame(String judul, String email, String password, String loginVia,
                    String catatan, String status, int harga) {
        this.judul = judul;
        this.email = email;
        this.password = password;
        this.loginVia = loginVia;
        this.catatan = catatan;
        this.status = status;
        this.harga = harga;
    }

    public String getJudul() { return judul; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getLoginVia() { return loginVia; }
    public String getCatatan() { return catatan; }
    public String getStatus() { return status; }
    public int getHarga() { return harga; }
public boolean isBuyerUnlocked() {
    return buyerUnlocked;
}

public void setBuyerUnlocked(boolean buyerUnlocked) {
    this.buyerUnlocked = buyerUnlocked;
}

    public void setStatus(String status) { this.status = status; }
}
