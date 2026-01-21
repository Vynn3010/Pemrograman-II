/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package register_pasien;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author calvi
 */
public class Register_Pasien extends javax.swing.JFrame {

    /**
     * Creates new form RegisterPasienGUI
     */
private final java.awt.Color NORMAL = new java.awt.Color(255, 102, 255);
private final java.awt.Color HOVER  = new java.awt.Color(255, 180, 255);


    private void bukaFrame(javax.swing.JFrame frame) {
    frame.setVisible(true);
    this.dispose(); // tutup frame sekarang
    }
    public Register_Pasien(int idKunjungan) {
    this(); // panggil constructor default biar initComponents + load combobox dll jalan
    java.awt.EventQueue.invokeLater(() -> {
        loadByIdKunjungan(idKunjungan);
    });
}

    public Register_Pasien() {

    initComponents();
    
    // WAJIB: loadStatus kalau kamu butuh
    loadStatus();
    loadPerawat();

    // baru isi data

    bgJK.add(rbLaki);
    bgJK.add(rbPerempuan);

    
   java.awt.EventQueue.invokeLater(() -> {
    loadPerawat();
    // loadStatus();

    });
    // cursor tangan
    lblHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    lblRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    lblList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    lblInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    
    pnlHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    pnlRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    pnlList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    pnlInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    // semua label bisa diwarnai
    pnlHome.setOpaque(true);
    pnlRegister.setOpaque(true);
    pnlList.setOpaque(true);
    pnlInfo.setOpaque(true);
    
    java.awt.EventQueue.invokeLater(() -> {
    setSize(950, 835);          // ukuran awal FIX
    setMinimumSize(new java.awt.Dimension(950, 835)); // KUNCI ukuran
    setLocationRelativeTo(null); // tengah layar
    setResizable(false);        // tidak bisa resize
    });
}
private String getJK() {
    if (rbLaki.isSelected()) return "L";
    if (rbPerempuan.isSelected()) return "P";
    return null;
}
private void resetForm() {
    txtNoRm.setText("");
    txtNama.setText("");
    bgJK.clearSelection();
    txtAlamat.setText("");
    txtTglLahir.setText("");
    txtNoHp.setText("");
    txtTglKunjungan.setText("");
    txtKeluhan.setText("");
    txtTerapi.setText("");
    cmbPerawat.setSelectedIndex(0);
    cmbStatus.setSelectedIndex(0);

    selectedIdKunjungan = 0; // penting
}

private void loadPerawat() {
    cmbPerawat.removeAllItems();
    cmbPerawat.addItem("- Pilih Perawat -");

    try (Connection c = Koneksi.getConnection();
         Statement s = c.createStatement();
         ResultSet r = s.executeQuery("SELECT id_perawat, nama_perawat FROM perawat ORDER BY nama_perawat")) {

        while (r.next()) {
            cmbPerawat.addItem(r.getInt("id_perawat") + " - " + r.getString("nama_perawat"));
        }
    } catch (Exception e) {
        System.out.println("Load perawat error: " + e.getMessage());
    }
}
private void loadStatus() {
    cmbStatus.removeAllItems();
    cmbStatus.addItem("- Pilih Status -");
    cmbStatus.addItem("Terdaftar");
    cmbStatus.addItem("Diperiksa");
    cmbStatus.addItem("Selesai");
    cmbStatus.addItem("Batal");
}
public void setSelectedIdKunjungan(int id) {
    this.selectedIdKunjungan = id;
    loadByIdKunjungan(id);  // supaya form langsung terisi
}

public void loadByIdKunjungan(int idKunjungan) {  //untuk mengambil 1 data dari DB berdasarkan id_kunjungan
    this.selectedIdKunjungan = idKunjungan;

    try (Connection c = Koneksi.getConnection();
         PreparedStatement ps = c.prepareStatement(
             "SELECT k.id_kunjungan, k.tanggal_kunjungan, k.keluhan, k.terapi, k.status, " +
             "p.no_rm, p.nama, p.jenis_kelamin, p.tanggal_lahir, p.no_hp, p.alamat, " +
             "pr.id_perawat, pr.nama_perawat " +
             "FROM kunjungan k " +
             "JOIN pasien p ON k.id_pasien = p.id_pasien " +
             "JOIN perawat pr ON k.id_perawat = pr.id_perawat " +
             "WHERE k.id_kunjungan = ?"
         )) {

        ps.setInt(1, idKunjungan);
        ResultSet r = ps.executeQuery();

        if (r.next()) {
            // isi field pasien
            txtNoRm.setText(r.getString("no_rm"));
            txtNama.setText(r.getString("nama"));
            txtAlamat.setText(r.getString("alamat"));
            txtTglLahir.setText(r.getDate("tanggal_lahir").toString());
            txtNoHp.setText(r.getString("no_hp") == null ? "" : r.getString("no_hp"));

            // radio JK
            String jk = r.getString("jenis_kelamin");
            if ("L".equalsIgnoreCase(jk)) rbLaki.setSelected(true);
            else rbPerempuan.setSelected(true);

            // isi kunjungan
            txtTglKunjungan.setText(r.getDate("tanggal_kunjungan").toString());
            txtKeluhan.setText(r.getString("keluhan"));
            txtTerapi.setText(r.getString("terapi"));
            cmbStatus.setSelectedItem(r.getString("status"));

            // set combobox perawat berdasarkan id_perawat
            int idPerawat = r.getInt("id_perawat");
            for (int i = 0; i < cmbPerawat.getItemCount(); i++) {
                String item = cmbPerawat.getItemAt(i).toString();
                if (item.startsWith(idPerawat + " -")) {
                    cmbPerawat.setSelectedIndex(i);
                    break;
                }
            }

            // opsional: kunci no_rm agar tidak diubah
            txtNoRm.setEditable(false);

        } else {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan.");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage());
    }
}

private int selectedIdKunjungan = 0;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgJK = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblWDC = new javax.swing.JLabel();
        pnlHome = new javax.swing.JPanel();
        lblIconHome = new javax.swing.JLabel();
        lblHome = new javax.swing.JLabel();
        pnlRegister = new javax.swing.JPanel();
        lblIconRegister = new javax.swing.JLabel();
        lblRegister = new javax.swing.JLabel();
        pnlList = new javax.swing.JPanel();
        lblIconList = new javax.swing.JLabel();
        lblList = new javax.swing.JLabel();
        pnlInfo = new javax.swing.JPanel();
        lblIconInfo = new javax.swing.JLabel();
        lblInfo = new javax.swing.JLabel();
        lblRegister1 = new javax.swing.JLabel();
        pnlForm = new javax.swing.JPanel();
        lblNoRm = new javax.swing.JLabel();
        lblNama = new javax.swing.JLabel();
        txtNoRm = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        lblJK = new javax.swing.JLabel();
        txtTglLahir = new javax.swing.JTextField();
        rbPerempuan = new javax.swing.JRadioButton();
        rbLaki = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        lblAlamat = new javax.swing.JLabel();
        lblTglLahir = new javax.swing.JLabel();
        txtNoHp = new javax.swing.JTextField();
        txtTglKunjungan = new javax.swing.JTextField();
        cmbPerawat = new javax.swing.JComboBox<>();
        txtKeluhan = new javax.swing.JTextField();
        txtTerapi = new javax.swing.JTextField();
        cmbStatus = new javax.swing.JComboBox<>();
        lblNoHP = new javax.swing.JLabel();
        lblTglKunjungan = new javax.swing.JLabel();
        lblPerawat = new javax.swing.JLabel();
        lblKeluhan = new javax.swing.JLabel();
        lblTerapi = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnCari = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        cmbCari = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 204, 255));

        jPanel4.setBackground(new java.awt.Color(255, 102, 255));

        lblWDC.setFont(new java.awt.Font("Vladimir Script", 1, 36)); // NOI18N
        lblWDC.setText("Widan Health Care");

        pnlHome.setBackground(new java.awt.Color(255, 102, 255));
        pnlHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlHomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlHomeMouseExited(evt);
            }
        });

        lblIconHome.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblIconHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/register_pasien/Home2.png"))); // NOI18N
        lblIconHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIconHomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblIconHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblIconHomeMouseExited(evt);
            }
        });

        lblHome.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblHome.setText("Home");

        javax.swing.GroupLayout pnlHomeLayout = new javax.swing.GroupLayout(pnlHome);
        pnlHome.setLayout(pnlHomeLayout);
        pnlHomeLayout.setHorizontalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblIconHome)
                .addGap(18, 18, 18)
                .addComponent(lblHome, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHomeLayout.setVerticalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblIconHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlRegister.setBackground(new java.awt.Color(255, 102, 255));
        pnlRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlRegisterMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlRegisterMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlRegisterMouseExited(evt);
            }
        });

        lblIconRegister.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblIconRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/register_pasien/register50px.png"))); // NOI18N
        lblIconRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIconRegisterMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblIconRegisterMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblIconRegisterMouseExited(evt);
            }
        });

        lblRegister.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblRegister.setText("Register");

        javax.swing.GroupLayout pnlRegisterLayout = new javax.swing.GroupLayout(pnlRegister);
        pnlRegister.setLayout(pnlRegisterLayout);
        pnlRegisterLayout.setHorizontalGroup(
            pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegisterLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblIconRegister)
                .addGap(18, 18, 18)
                .addComponent(lblRegister)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlRegisterLayout.setVerticalGroup(
            pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblIconRegister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlRegisterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlList.setBackground(new java.awt.Color(255, 102, 255));
        pnlList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlListMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlListMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlListMouseExited(evt);
            }
        });

        lblIconList.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblIconList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/register_pasien/Documentt.png"))); // NOI18N
        lblIconList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIconListMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblIconListMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblIconListMouseExited(evt);
            }
        });

        lblList.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblList.setText("List Pasien");

        javax.swing.GroupLayout pnlListLayout = new javax.swing.GroupLayout(pnlList);
        pnlList.setLayout(pnlListLayout);
        pnlListLayout.setHorizontalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblIconList)
                .addGap(18, 18, 18)
                .addComponent(lblList)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlListLayout.setVerticalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblIconList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblList, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlInfo.setBackground(new java.awt.Color(255, 102, 255));
        pnlInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlInfoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlInfoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlInfoMouseExited(evt);
            }
        });

        lblIconInfo.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblIconInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/register_pasien/Info1.png"))); // NOI18N
        lblIconInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIconInfoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblIconInfoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblIconInfoMouseExited(evt);
            }
        });

        lblInfo.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblInfo.setText("Information");

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblIconInfo)
                .addGap(18, 18, 18)
                .addComponent(lblInfo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblIconInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(lblWDC)
                .addGap(34, 34, 34))
            .addComponent(pnlHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlRegister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(lblWDC)
                .addGap(63, 63, 63)
                .addComponent(pnlHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlRegister, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(413, Short.MAX_VALUE))
        );

        lblRegister1.setFont(new java.awt.Font("Vladimir Script", 1, 36)); // NOI18N
        lblRegister1.setText("Register");

        pnlForm.setBackground(new java.awt.Color(255, 240, 255));
        pnlForm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlForm.setMaximumSize(new java.awt.Dimension(571, 193));
        pnlForm.setMinimumSize(new java.awt.Dimension(571, 193));

        lblNoRm.setFont(new java.awt.Font("Serif", 1, 21)); // NOI18N
        lblNoRm.setText("No RM");

        lblNama.setFont(new java.awt.Font("Serif", 1, 21)); // NOI18N
        lblNama.setText("Nama");

        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });

        lblJK.setFont(new java.awt.Font("Serif", 1, 21)); // NOI18N
        lblJK.setText("Jenis Kelamin");

        txtTglLahir.setText("YYYY-MM-DD");
        txtTglLahir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTglLahirActionPerformed(evt);
            }
        });

        bgJK.add(rbPerempuan);
        rbPerempuan.setText("Perempuan");
        rbPerempuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbPerempuanActionPerformed(evt);
            }
        });

        bgJK.add(rbLaki);
        rbLaki.setText("Laki-laki");
        rbLaki.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbLakiActionPerformed(evt);
            }
        });

        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        jScrollPane1.setViewportView(txtAlamat);

        lblAlamat.setFont(new java.awt.Font("Serif", 1, 21)); // NOI18N
        lblAlamat.setText("Alamat");

        lblTglLahir.setFont(new java.awt.Font("Serif", 1, 21)); // NOI18N
        lblTglLahir.setText("Tgl Lahir");

        txtNoHp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoHpActionPerformed(evt);
            }
        });

        txtTglKunjungan.setText("YYYY-MM-DD");
        txtTglKunjungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTglKunjunganActionPerformed(evt);
            }
        });

        cmbPerawat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Pilih Perawat -", "Danang Priyo Utomo, A.md, Kep.", "Candra Pratiwi, S.kep. Ns", " " }));

        txtKeluhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKeluhanActionPerformed(evt);
            }
        });

        txtTerapi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTerapiActionPerformed(evt);
            }
        });

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Pilih Status -", "Terdaftar", "Diperiksa", "Selesai", "Batal", " " }));
        cmbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStatusActionPerformed(evt);
            }
        });

        lblNoHP.setFont(new java.awt.Font("Serif", 1, 21)); // NOI18N
        lblNoHP.setText("No HP");

        lblTglKunjungan.setFont(new java.awt.Font("Serif", 1, 21)); // NOI18N
        lblTglKunjungan.setText("Tgl Kunjungan");

        lblPerawat.setFont(new java.awt.Font("Serif", 1, 21)); // NOI18N
        lblPerawat.setText("Perawat");

        lblKeluhan.setFont(new java.awt.Font("Serif", 1, 21)); // NOI18N
        lblKeluhan.setText("Keluhan");

        lblTerapi.setFont(new java.awt.Font("Serif", 1, 21)); // NOI18N
        lblTerapi.setText("Terapi");

        lblStatus.setFont(new java.awt.Font("Serif", 1, 21)); // NOI18N
        lblStatus.setText("Status");

        btnTambah.setBackground(new java.awt.Color(255, 102, 255));
        btnTambah.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnUbah.setBackground(new java.awt.Color(255, 102, 255));
        btnUbah.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(255, 102, 255));
        btnHapus.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnCari.setBackground(new java.awt.Color(255, 102, 255));
        btnCari.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(255, 102, 255));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        cmbCari.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Pilih Pencarian -", "No RM", "Nama", " ", " " }));
        cmbCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFormLayout = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pnlFormLayout);
        pnlFormLayout.setHorizontalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNama)
                            .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnlFormLayout.createSequentialGroup()
                                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblNoRm)
                                            .addComponent(lblStatus))
                                        .addGap(83, 83, 83))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlFormLayout.createSequentialGroup()
                                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblJK, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblAlamat, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblTglLahir, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblNoHP, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblTglKunjungan, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblPerawat, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblKeluhan, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblTerapi, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbPerawat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNama)
                                    .addComponent(txtNoRm)
                                    .addComponent(txtTglLahir)
                                    .addGroup(pnlFormLayout.createSequentialGroup()
                                        .addComponent(rbLaki, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbPerempuan, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane1)
                                    .addComponent(txtNoHp)
                                    .addComponent(txtTglKunjungan)
                                    .addComponent(txtKeluhan)
                                    .addComponent(txtTerapi)
                                    .addComponent(cmbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbCari, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(btnTambah)))
                        .addGap(20, 20, 20))))
        );
        pnlFormLayout.setVerticalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNoRm)
                    .addComponent(lblNoRm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblNama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNama))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJK)
                    .addComponent(rbLaki, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbPerempuan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAlamat))
                .addGap(18, 18, 18)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTglLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTglLahir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoHP)
                    .addComponent(txtNoHp, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTglKunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTglKunjungan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbPerawat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPerawat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addComponent(lblKeluhan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTerapi))
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addComponent(txtKeluhan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTerapi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatus)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCari)
                    .addComponent(cmbCari, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUbah)
                    .addComponent(btnTambah)
                    .addComponent(btnHapus)
                    .addComponent(btnReset))
                .addGap(42, 42, 42))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(lblRegister1)
                        .addGap(453, 453, 453))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(pnlForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(17, 17, 17))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(lblRegister1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblIconHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconHomeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblIconHomeMouseClicked

    private void lblIconHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconHomeMouseEntered
        // TODO add your handling code here:
        pnlHome.setBackground(HOVER);
    }//GEN-LAST:event_lblIconHomeMouseEntered

    private void lblIconHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconHomeMouseExited
        // TODO add your handling code here:
        pnlHome.setBackground(NORMAL);
    }//GEN-LAST:event_lblIconHomeMouseExited

    private void pnlHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHomeMouseClicked
        // TODO add your handling code here:
        bukaFrame(new Home());
    }//GEN-LAST:event_pnlHomeMouseClicked

    private void pnlHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHomeMouseEntered
        // TODO add your handling code here:
        pnlHome.setBackground(HOVER);
    }//GEN-LAST:event_pnlHomeMouseEntered

    private void lblIconRegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconRegisterMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblIconRegisterMouseClicked

    private void lblIconRegisterMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconRegisterMouseEntered
        // TODO add your handling code here:
        pnlRegister.setBackground(HOVER);
    }//GEN-LAST:event_lblIconRegisterMouseEntered

    private void lblIconRegisterMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconRegisterMouseExited
        // TODO add your handling code here:
        pnlRegister.setBackground(NORMAL);
    }//GEN-LAST:event_lblIconRegisterMouseExited

    private void pnlRegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlRegisterMouseClicked
        // TODO add your handling code here:
        bukaFrame(new Register_Pasien());
    }//GEN-LAST:event_pnlRegisterMouseClicked

    private void pnlRegisterMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlRegisterMouseEntered
        // TODO add your handling code here:
        pnlRegister.setBackground(HOVER);
    }//GEN-LAST:event_pnlRegisterMouseEntered

    private void lblIconListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconListMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblIconListMouseClicked

    private void lblIconListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconListMouseEntered
        // TODO add your handling code here:
        pnlList.setBackground(HOVER);
    }//GEN-LAST:event_lblIconListMouseEntered

    private void lblIconListMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconListMouseExited
        // TODO add your handling code here:
        pnlList.setBackground(NORMAL);
    }//GEN-LAST:event_lblIconListMouseExited

    private void pnlListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlListMouseClicked
        // TODO add your handling code here:
        bukaFrame(new List_Pasien());
    }//GEN-LAST:event_pnlListMouseClicked

    private void pnlListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlListMouseEntered
        // TODO add your handling code here:
        pnlList.setBackground(HOVER);
    }//GEN-LAST:event_pnlListMouseEntered

    private void lblIconInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconInfoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblIconInfoMouseClicked

    private void lblIconInfoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconInfoMouseEntered
        // TODO add your handling code here:
        pnlInfo.setBackground(HOVER);
    }//GEN-LAST:event_lblIconInfoMouseEntered

    private void lblIconInfoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconInfoMouseExited
        // TODO add your handling code here:
        pnlInfo.setBackground(NORMAL);
    }//GEN-LAST:event_lblIconInfoMouseExited

    private void pnlInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlInfoMouseClicked
        // TODO add your handling code here:
        bukaFrame(new Information());
    }//GEN-LAST:event_pnlInfoMouseClicked

    private void pnlInfoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlInfoMouseEntered
        // TODO add your handling code here:
        pnlInfo.setBackground(HOVER);
    }//GEN-LAST:event_pnlInfoMouseEntered

    private void pnlHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHomeMouseExited
        // TODO add your handling code here:
        pnlHome.setBackground(NORMAL);
    }//GEN-LAST:event_pnlHomeMouseExited

    private void pnlRegisterMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlRegisterMouseExited
        // TODO add your handling code here:
        pnlRegister.setBackground(NORMAL);
    }//GEN-LAST:event_pnlRegisterMouseExited

    private void pnlListMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlListMouseExited
        // TODO add your handling code here:
        pnlList.setBackground(NORMAL);
    }//GEN-LAST:event_pnlListMouseExited

    private void pnlInfoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlInfoMouseExited
        // TODO add your handling code here:
        pnlInfo.setBackground(NORMAL);
    }//GEN-LAST:event_pnlInfoMouseExited

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

    private void txtTglLahirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTglLahirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTglLahirActionPerformed

    private void rbLakiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbLakiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbLakiActionPerformed

    private void rbPerempuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbPerempuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbPerempuanActionPerformed

    private void txtNoHpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoHpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoHpActionPerformed

    private void txtTglKunjunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTglKunjunganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTglKunjunganActionPerformed

    private void txtKeluhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKeluhanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKeluhanActionPerformed

    private void txtTerapiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTerapiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTerapiActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        // TODO add your handling code here:
    if (selectedIdKunjungan == 0) {
        JOptionPane.showMessageDialog(this, "Pilih data dulu dari List Pasien.");
        return;
    }

    String noRm = txtNoRm.getText().trim();
    String nama = txtNama.getText().trim();
    String jk = getJK();
    String alamat = txtAlamat.getText().trim();
    String tglLahir = txtTglLahir.getText().trim();
    String noHp = txtNoHp.getText().trim();

    String tglKunjungan = txtTglKunjungan.getText().trim();
    String keluhan = txtKeluhan.getText().trim();
    String terapi = txtTerapi.getText().trim();

    if (cmbPerawat.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(this, "Pilih perawat dulu.");
        return;
    }
    if (cmbStatus.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(this, "Pilih status dulu.");
        return;
    }
    int idPerawat = Integer.parseInt(cmbPerawat.getSelectedItem().toString().split(" - ")[0]);
    String status = cmbStatus.getSelectedItem().toString();

    try (Connection c = Koneksi.getConnection()) {
        c.setAutoCommit(false);

        // 1) ambil id_pasien dari id_kunjungan
        int idPasien = 0;
        try (PreparedStatement ps = c.prepareStatement(
                "SELECT id_pasien FROM kunjungan WHERE id_kunjungan=?")) {
            ps.setInt(1, selectedIdKunjungan);
            ResultSet r = ps.executeQuery();
            if (r.next()) idPasien = r.getInt("id_pasien");
        }

        if (idPasien == 0) {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan.");
            return;
        }

        // 2) update pasien
        try (PreparedStatement ps = c.prepareStatement(
                "UPDATE pasien SET nama=?, jenis_kelamin=?, tanggal_lahir=?, no_hp=?, alamat=? WHERE id_pasien=?")) {
            ps.setString(1, nama);
            ps.setString(2, jk);
            ps.setString(3, tglLahir);
            ps.setString(4, noHp.isEmpty() ? null : noHp);
            ps.setString(5, alamat);
            ps.setInt(6, idPasien);
            ps.executeUpdate();
        }

        // 3) update kunjungan
        try (PreparedStatement ps = c.prepareStatement(
                "UPDATE kunjungan SET tanggal_kunjungan=?, id_perawat=?, keluhan=?, terapi=?, status=? WHERE id_kunjungan=?")) {
            ps.setString(1, tglKunjungan);
            ps.setInt(2, idPerawat);
            ps.setString(3, keluhan);
            ps.setString(4, terapi);
            ps.setString(5, status);
            ps.setInt(6, selectedIdKunjungan);
            ps.executeUpdate();
        }

        c.commit();

        JOptionPane.showMessageDialog(this, "Data berhasil diubah!");
        selectedIdKunjungan = 0;
        txtNoRm.setEditable(true);
        resetForm();
        bukaFrame(new List_Pasien());

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal ubah: " + e.getMessage());
    }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
    String pilihan = cmbCari.getSelectedItem().toString();  // ✅ TAMBAH INI

    if (pilihan.equals("- Pilih Pencarian -")) {
        JOptionPane.showMessageDialog(this, "Pilih jenis pencarian dulu.");
        return;
    }

    if (pilihan.equals("No RM")) {
        String noRm = txtNoRm.getText().trim();
        if (noRm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi No RM dulu.");
            return;
        }
        bukaFrame(new List_Pasien("no_rm", noRm));

    } else if (pilihan.equals("Nama")) {
        String nama = txtNama.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi Nama dulu.");
            return;
        }
        bukaFrame(new List_Pasien("nama", nama));

    } else {
        JOptionPane.showMessageDialog(this, "Pilihan pencarian tidak dikenal.");
    }
    }//GEN-LAST:event_btnCariActionPerformed

    private void cmbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatusActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        resetForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        String noRm = txtNoRm.getText().trim();
        String nama = txtNama.getText().trim();
        String jk = getJK();
        String alamat = txtAlamat.getText().trim();
        String tglLahir = txtTglLahir.getText().trim();       // YYYY-MM-DD
        String noHp = txtNoHp.getText().trim();               // boleh kosong
        String tglKunjungan = txtTglKunjungan.getText().trim(); // YYYY-MM-DD
        String keluhan = txtKeluhan.getText().trim();
        String terapi = txtTerapi.getText().trim();
        String status = cmbStatus.getSelectedItem().toString();

        // validasi minimal (biar tidak error SQL)
        if (noRm.isEmpty() || nama.isEmpty() || jk == null || alamat.isEmpty() ||
            tglLahir.isEmpty() || tglKunjungan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lengkapi data wajib (No RM, Nama, JK, Alamat, Tgl Lahir, Tgl Kunjungan).");
            return;
        }
        if (cmbPerawat.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Pilih perawat dulu.");
            return;
        }
        if (cmbStatus.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Pilih status dulu.");
            return;
        }

        int idPerawat = Integer.parseInt(cmbPerawat.getSelectedItem().toString().split(" - ")[0]);

        try (Connection c = Koneksi.getConnection()) {

        // 1) cek pasien sudah ada?
        int idPasien = 0;
        PreparedStatement cek = c.prepareStatement("SELECT id_pasien FROM pasien WHERE no_rm=?");
        cek.setString(1, noRm);
        ResultSet rc = cek.executeQuery();
        if (rc.next()) {
            idPasien = rc.getInt("id_pasien");
        } else {
            // insert pasien
            PreparedStatement ps = c.prepareStatement(
                "INSERT INTO pasien (no_rm, nama, jenis_kelamin, tanggal_lahir, no_hp, alamat) VALUES (?,?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, noRm);
            ps.setString(2, nama);
            ps.setString(3, jk);
            ps.setString(4, tglLahir);
            ps.setString(5, noHp.isEmpty() ? null : noHp);
            ps.setString(6, alamat);
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) idPasien = keys.getInt(1);
        }

        // 2) insert kunjungan
        PreparedStatement pk = c.prepareStatement(
            "INSERT INTO kunjungan (id_pasien, id_perawat, tanggal_kunjungan, keluhan, terapi, status) VALUES (?,?,?,?,?,?)"
        );
        pk.setInt(1, idPasien);
        pk.setInt(2, idPerawat);
        pk.setString(3, tglKunjungan);
        pk.setString(4, keluhan);
        pk.setString(5, terapi);
        pk.setString(6, status);
        pk.executeUpdate();

        JOptionPane.showMessageDialog(this, "Data berhasil ditambah!");
        resetForm();

        // buka List Pasien (akan tampil data terbaru)
        bukaFrame(new List_Pasien());

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal tambah: " + e.getMessage());
    }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void cmbCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCariActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
    if (selectedIdKunjungan == 0) {
        JOptionPane.showMessageDialog(this, "Pilih data dulu dari List Pasien.");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Yakin mau hapus data ini?\n(Kunjungan akan dihapus. Pasien ikut terhapus jika tidak ada kunjungan lain)",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION
    );
    if (confirm != JOptionPane.YES_OPTION) return;

    try (Connection c = Koneksi.getConnection()) {
        c.setAutoCommit(false); // transaksi biar aman

        // 1) Ambil id_pasien dari kunjungan
        int idPasien = 0;
        try (PreparedStatement ps = c.prepareStatement(
                "SELECT id_pasien FROM kunjungan WHERE id_kunjungan=?")) {
            ps.setInt(1, selectedIdKunjungan);
            ResultSet r = ps.executeQuery();
            if (r.next()) idPasien = r.getInt("id_pasien");
        }

        if (idPasien == 0) {
            c.rollback();
            JOptionPane.showMessageDialog(this, "Data kunjungan tidak ditemukan.");
            return;
        }

        // 2) Hapus kunjungan yang dipilih
        try (PreparedStatement ps = c.prepareStatement(
                "DELETE FROM kunjungan WHERE id_kunjungan=?")) {
            ps.setInt(1, selectedIdKunjungan);
            ps.executeUpdate();
        }

        // 3) Cek apakah pasien masih punya kunjungan lain
        int sisa = 0;
        try (PreparedStatement ps = c.prepareStatement(
                "SELECT COUNT(*) AS jml FROM kunjungan WHERE id_pasien=?")) {
            ps.setInt(1, idPasien);
            ResultSet r = ps.executeQuery();
            if (r.next()) sisa = r.getInt("jml");
        }

        // 4) Kalau tidak ada kunjungan lagi, hapus pasien
        if (sisa == 0) {
            try (PreparedStatement ps = c.prepareStatement(
                    "DELETE FROM pasien WHERE id_pasien=?")) {
                ps.setInt(1, idPasien);
                ps.executeUpdate();
            }
        }

        c.commit();

        JOptionPane.showMessageDialog(this,
                (sisa == 0) ? "Kunjungan & pasien berhasil dihapus!" : "Kunjungan berhasil dihapus (pasien masih punya kunjungan lain).");

        selectedIdKunjungan = 0;
        resetForm();
        bukaFrame(new List_Pasien());

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal hapus: " + e.getMessage());
    }
    }//GEN-LAST:event_btnHapusActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Register_Pasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Register_Pasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Register_Pasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Register_Pasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Register_Pasien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgJK;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox<String> cmbCari;
    private javax.swing.JComboBox<String> cmbPerawat;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAlamat;
    private javax.swing.JLabel lblHome;
    private javax.swing.JLabel lblIconHome;
    private javax.swing.JLabel lblIconInfo;
    private javax.swing.JLabel lblIconList;
    private javax.swing.JLabel lblIconRegister;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblJK;
    private javax.swing.JLabel lblKeluhan;
    private javax.swing.JLabel lblList;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblNoHP;
    private javax.swing.JLabel lblNoRm;
    private javax.swing.JLabel lblPerawat;
    private javax.swing.JLabel lblRegister;
    private javax.swing.JLabel lblRegister1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTerapi;
    private javax.swing.JLabel lblTglKunjungan;
    private javax.swing.JLabel lblTglLahir;
    private javax.swing.JLabel lblWDC;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlHome;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlRegister;
    private javax.swing.JRadioButton rbLaki;
    private javax.swing.JRadioButton rbPerempuan;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtKeluhan;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNoHp;
    private javax.swing.JTextField txtNoRm;
    private javax.swing.JTextField txtTerapi;
    private javax.swing.JTextField txtTglKunjungan;
    private javax.swing.JTextField txtTglLahir;
    // End of variables declaration//GEN-END:variables
}
