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
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class List_Pasien extends javax.swing.JFrame {

    /**
     * Creates new form RegisterPasienGUI
     */
private final java.awt.Color NORMAL = new java.awt.Color(255, 102, 255);
private final java.awt.Color HOVER  = new java.awt.Color(255, 180, 255);
private String filterColumn = null; //variable buat constructor menerima filter
private String filterValue = null;


private void bukaFrame(javax.swing.JFrame frame) {
    frame.setVisible(true);
    this.dispose(); // tutup frame sekarang
    }
public List_Pasien() {
    this(null, null); // panggil constructor bawah
}
public List_Pasien(String col, String val) {
    initComponents();

    System.out.println("List_Pasien kebuka | col=" + col + " | val=" + val);

    this.filterColumn = col;
    this.filterValue  = val;

    java.awt.EventQueue.invokeLater(() -> {
        System.out.println("loadTable dipanggil");
        if (filterColumn == null || filterValue == null || filterValue.trim().isEmpty()) {
            loadTable();              // ✅ tampil semua data
        } else {
            loadTableFiltered();      // ✅ tampil sesuai filter
        }
    });
    // UI setup (biar sama kaya yang default)
    lblHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    lblRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    lblList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    lblInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    pnlHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    pnlRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    pnlList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    pnlInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    pnlHome.setOpaque(true);
    pnlRegister.setOpaque(true);
    pnlList.setOpaque(true);
    pnlInfo.setOpaque(true);

}


private void loadTable() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID");
    model.addColumn("Tanggal");
    model.addColumn("No RM");
    model.addColumn("Nama");
    model.addColumn("Umur");
    model.addColumn("Alamat");
    model.addColumn("Keluhan");
    model.addColumn("Terapi");
    model.addColumn("Perawat");
    model.addColumn("Status");

    try (Connection c = Koneksi.getConnection();
         Statement s = c.createStatement();
         ResultSet r = s.executeQuery(
             "SELECT * FROM v_register_pasien ORDER BY tanggal_kunjungan DESC, id_kunjungan DESC"
         )) {

        while (r.next()) {
            model.addRow(new Object[]{
                r.getInt("id_kunjungan"),
                r.getDate("tanggal_kunjungan"),
                r.getString("no_rm"),
                r.getString("nama"),
                r.getInt("umur"),
                r.getString("alamat"),
                r.getString("keluhan"),
                r.getString("terapi"),
                r.getString("nama_perawat"),
                r.getString("status")
            });
        }

        jTable1.setModel(model);

        // sembunyikan kolom ID (opsional)
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);

    } catch (Exception e) {
        System.out.println("Load table error: " + e.getMessage());
    }
}

private void setupUI() {
    setSize(950, 835);
    setMinimumSize(new java.awt.Dimension(950, 835));
    setLocationRelativeTo(null);
    setResizable(false);
}

private void loadTableFiltered() { //
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID");
    model.addColumn("Tanggal");
    model.addColumn("No RM");
    model.addColumn("Nama");
    model.addColumn("Umur");
    model.addColumn("Alamat");
    model.addColumn("Keluhan");
    model.addColumn("Terapi");
    model.addColumn("Perawat");
    model.addColumn("Status");

    String sql;
    boolean isNama = "nama".equalsIgnoreCase(filterColumn);

    if (isNama) {
        sql = "SELECT * FROM v_register_pasien WHERE nama LIKE ? " +
              "ORDER BY tanggal_kunjungan DESC, id_kunjungan DESC";
    } else {
        sql = "SELECT * FROM v_register_pasien WHERE " + filterColumn + " = ? " +
              "ORDER BY tanggal_kunjungan DESC, id_kunjungan DESC";
    }

    try (Connection c = Koneksi.getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {

        if (isNama) {
            ps.setString(1, "%" + filterValue + "%"); // cari nama sebagian
        } else {
            ps.setString(1, filterValue);
        }

        ResultSet r = ps.executeQuery();
        while (r.next()) {
            model.addRow(new Object[]{
                r.getInt("id_kunjungan"),
                r.getDate("tanggal_kunjungan"),
                r.getString("no_rm"),
                r.getString("nama"),
                r.getInt("umur"),
                r.getString("alamat"),
                r.getString("keluhan"),
                r.getString("terapi"),
                r.getString("nama_perawat"),
                r.getString("status")
            });
        }

        jTable1.setModel(model);

        // sembunyikan ID
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);

    } catch (Exception e) {
        System.out.println("Load filtered error: " + e.getMessage());
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
        lblWDC1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 204, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(950, 835));
        jPanel2.setMinimumSize(new java.awt.Dimension(950, 835));

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblWDC1.setFont(new java.awt.Font("Vladimir Script", 1, 36)); // NOI18N
        lblWDC1.setText("List Pasien");

        jPanel1.setBackground(new java.awt.Color(255, 204, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(576, 0));
        jPanel1.setMinimumSize(new java.awt.Dimension(576, 0));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Tanggal", "No RM", "Nama", "Umur", "Alamat", "Keluhan", "Terapi", "Perawat", "Title 9"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblWDC1)
                        .addGap(404, 404, 404))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(lblWDC1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int row = jTable1.getSelectedRow();
        if (row < 0) return;

        int idKunjungan = Integer.parseInt(jTable1.getModel().getValueAt(row, 0).toString());

        Register_Pasien rp = new Register_Pasien(idKunjungan);
        rp.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(List_Pasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(List_Pasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(List_Pasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(List_Pasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new List_Pasien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblHome;
    private javax.swing.JLabel lblIconHome;
    private javax.swing.JLabel lblIconInfo;
    private javax.swing.JLabel lblIconList;
    private javax.swing.JLabel lblIconRegister;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblList;
    private javax.swing.JLabel lblRegister;
    private javax.swing.JLabel lblWDC;
    private javax.swing.JLabel lblWDC1;
    private javax.swing.JPanel pnlHome;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlRegister;
    // End of variables declaration//GEN-END:variables
}
