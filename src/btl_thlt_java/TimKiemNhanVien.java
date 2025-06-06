/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package btl_thlt_java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Khanh
 */
public class TimKiemNhanVien extends javax.swing.JFrame {

    /**
     * Creates new form TimKiemNhanVien
     */
    public TimKiemNhanVien() {
        initComponents();
    }
    
    public void ht() throws SQLException{
        try {
            Connection kn = KN.KNDL();
            Statement stm = kn.createStatement();
            String sql = "select * from ql_nv";
            ResultSet rs = stm.executeQuery(sql);
            DefaultTableModel dtm = (DefaultTableModel) tb_tknv.getModel();
            dtm.setRowCount(0);
            
            while(rs.next()){
                Object object[]={
                    rs.getString("maNV"),
                    rs.getString("tenNV"),
                    rs.getString("tuoi"),
                    rs.getString("gt"),
                    rs.getString("dc"),
                    rs.getString("sdt"),
                    rs.getString("email")
            };
                dtm.addRow(object);
                tb_tknv.setModel(dtm);
            }
        }catch (SQLException ex) {
            Logger.getLogger(TimKiemNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void timkiem(){
        String tk = txt_tknv.getText();
            try{
                Connection kn = KN.KNDL();
                Statement stm = kn.createStatement();
                String sql = "select * from ql_nv where maNV like'"+tk+"' ";
                ResultSet rs = stm.executeQuery(sql);
                DefaultTableModel dtm = (DefaultTableModel) tb_tknv.getModel();
                dtm.setRowCount(0);
                while(rs.next()){
                    Object object[]={
                        rs.getString("maNV"),
                        rs.getString("tenNV"),
                        rs.getString("tuoi"),
                        rs.getString("gt"),
                        rs.getString("dc"),
                        rs.getString("sdt"),
                        rs.getString("email")
                    };
                    dtm.addRow(object);
                    tb_tknv.setModel(dtm);
                }
            } catch (SQLException ex) {
                Logger.getLogger(TimKiemNhanVien.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    public void tbmouseClick() throws SQLException{
        int row = tb_tknv.getSelectedRow();
        
            tb_tknv.getValueAt(row, 0).toString();
            tb_tknv.getValueAt(row, 1).toString();
            tb_tknv.getValueAt(row, 2).toString();
            tb_tknv.getValueAt(row, 3).toString();
            tb_tknv.getValueAt(row, 4).toString();
            tb_tknv.getValueAt(row, 5).toString();
            tb_tknv.getValueAt(row, 6).toString();   
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_tknv = new javax.swing.JTextField();
        btn_tk = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_tknv = new javax.swing.JTable();
        btn_ht = new javax.swing.JButton();
        btn_dau = new javax.swing.JButton();
        btn_truoc = new javax.swing.JButton();
        btn_sau = new javax.swing.JButton();
        btn_cuoi = new javax.swing.JButton();
        btn_thoat = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        menu_qlsv1 = new javax.swing.JMenuItem();
        menu_qls1 = new javax.swing.JMenuItem();
        menu_qlmuontra1 = new javax.swing.JMenuItem();
        menu_qlnv1 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        menu_tracuusach1 = new javax.swing.JMenuItem();
        menu_tksv1 = new javax.swing.JMenuItem();
        menu_tknv = new javax.swing.JMenuItem();
        menu_tt1 = new javax.swing.JMenu();
        menu_dangnhap1 = new javax.swing.JMenuItem();
        menu_dangky = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tìm kiếm nhân viên");
        setPreferredSize(new java.awt.Dimension(1000, 500));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel1.setText("Tìm kiếm theo mã nhân viên");

        btn_tk.setText("Tìm kiếm");
        btn_tk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tkActionPerformed(evt);
            }
        });

        tb_tknv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Họ tên", "Tuổi", "Giới tính", "Địa chỉ", "Sđt", "Email"
            }
        ));
        tb_tknv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_tknvMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_tknv);

        btn_ht.setText("Hiển thị");
        btn_ht.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_htActionPerformed(evt);
            }
        });

        btn_dau.setText("|<");
        btn_dau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dauActionPerformed(evt);
            }
        });

        btn_truoc.setText("<<");
        btn_truoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_truocActionPerformed(evt);
            }
        });

        btn_sau.setText(">>");
        btn_sau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sauActionPerformed(evt);
            }
        });

        btn_cuoi.setText(">|");
        btn_cuoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cuoiActionPerformed(evt);
            }
        });

        btn_thoat.setText("Thoát");
        btn_thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_thoatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 994, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(373, 373, 373)
                .addComponent(btn_dau, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_truoc, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_sau, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_ht)
                .addGap(66, 66, 66)
                .addComponent(btn_thoat)
                .addGap(152, 152, 152))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(67, 67, 67)
                    .addComponent(jLabel1)
                    .addGap(36, 36, 36)
                    .addComponent(txt_tknv, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(64, 64, 64)
                    .addComponent(btn_tk, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(431, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_ht)
                    .addComponent(btn_thoat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_dau)
                    .addComponent(btn_truoc)
                    .addComponent(btn_sau)
                    .addComponent(btn_cuoi))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(13, 13, 13)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txt_tknv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_tk))
                    .addContainerGap(401, Short.MAX_VALUE)))
        );

        jMenu4.setText("Quản Lý");

        menu_qlsv1.setText("Quản lý sinh viên");
        menu_qlsv1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_qlsv1ActionPerformed(evt);
            }
        });
        jMenu4.add(menu_qlsv1);

        menu_qls1.setText("Quản lý sách");
        menu_qls1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_qls1ActionPerformed(evt);
            }
        });
        jMenu4.add(menu_qls1);

        menu_qlmuontra1.setText("Quản lý mượn trả sách");
        menu_qlmuontra1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_qlmuontra1ActionPerformed(evt);
            }
        });
        jMenu4.add(menu_qlmuontra1);

        menu_qlnv1.setText("Quản lý nhân viên");
        menu_qlnv1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_qlnv1ActionPerformed(evt);
            }
        });
        jMenu4.add(menu_qlnv1);

        jMenuBar2.add(jMenu4);

        jMenu5.setText("Tra cứu");

        menu_tracuusach1.setText("Tra cứu sách");
        menu_tracuusach1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_tracuusach1ActionPerformed(evt);
            }
        });
        jMenu5.add(menu_tracuusach1);

        menu_tksv1.setText("Tìm kiếm sinh viên");
        menu_tksv1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_tksv1ActionPerformed(evt);
            }
        });
        jMenu5.add(menu_tksv1);

        menu_tknv.setText("Tìm kiếm nhân viên");
        menu_tknv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_tknvActionPerformed(evt);
            }
        });
        jMenu5.add(menu_tknv);

        jMenuBar2.add(jMenu5);

        menu_tt1.setText("Thông tin");

        menu_dangnhap1.setText("Đăng nhập");
        menu_dangnhap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_dangnhap1ActionPerformed(evt);
            }
        });
        menu_tt1.add(menu_dangnhap1);

        menu_dangky.setText("Đăng ký");
        menu_dangky.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_dangkyActionPerformed(evt);
            }
        });
        menu_tt1.add(menu_dangky);

        jMenuBar2.add(menu_tt1);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menu_qlsv1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_qlsv1ActionPerformed
        new QuanLySinhVien().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_qlsv1ActionPerformed

    private void menu_qls1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_qls1ActionPerformed
        new QuanLySach().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_qls1ActionPerformed

    private void menu_qlmuontra1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_qlmuontra1ActionPerformed
        new QuanLyMuonTraSachForm().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_qlmuontra1ActionPerformed

    private void menu_qlnv1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_qlnv1ActionPerformed
        new QuanLyNhanVien().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_qlnv1ActionPerformed

    private void menu_tracuusach1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_tracuusach1ActionPerformed
        new TraCuuSach().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_tracuusach1ActionPerformed

    private void menu_tksv1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_tksv1ActionPerformed
        new TimKiemSinhVien().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_tksv1ActionPerformed

    private void menu_tknvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_tknvActionPerformed
        new TimKiemNhanVien().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_tknvActionPerformed

    private void menu_dangnhap1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_dangnhap1ActionPerformed
        new Login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_dangnhap1ActionPerformed

    private void menu_dangkyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_dangkyActionPerformed
        new SignUp().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_dangkyActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        try {
            ht();
        } catch (SQLException ex) {
            Logger.getLogger(TimKiemNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowActivated

    private void btn_tkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tkActionPerformed
        timkiem();
    }//GEN-LAST:event_btn_tkActionPerformed

    private void btn_htActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_htActionPerformed
        try {
            ht();
        } catch (SQLException ex) {
            Logger.getLogger(TimKiemNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_htActionPerformed

    private void btn_dauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dauActionPerformed
        tb_tknv.setRowSelectionInterval(0,0);
        try {
            tbmouseClick();
        } catch (SQLException ex) {
            Logger.getLogger(TimKiemNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_dauActionPerformed

    private void btn_truocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_truocActionPerformed
        int n = tb_tknv.getSelectedRow();
        if(n>0){
            tb_tknv.setRowSelectionInterval(n-1, n-1);
        }else{
            tb_tknv.setRowSelectionInterval(tb_tknv.getRowCount()-1,tb_tknv.getRowCount()-1);
        }
        try {
            tbmouseClick();
        } catch (SQLException ex) {
            Logger.getLogger(TimKiemNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_truocActionPerformed

    private void btn_sauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sauActionPerformed
        int n = tb_tknv.getSelectedRow();
        if(n==tb_tknv.getRowCount()-1){
            tb_tknv.setRowSelectionInterval(0, 0);
        }else{
            tb_tknv.setRowSelectionInterval(n+1, n+1);
        }
        try {
            tbmouseClick();
        } catch (SQLException ex) {
            Logger.getLogger(TimKiemNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_sauActionPerformed

    private void btn_cuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cuoiActionPerformed
        tb_tknv.setRowSelectionInterval(tb_tknv.getRowCount()-1, tb_tknv.getRowCount()-1);
        try {
            tbmouseClick();
        } catch (SQLException ex) {
            Logger.getLogger(TimKiemNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_cuoiActionPerformed

    private void tb_tknvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_tknvMouseClicked
        try {
            tbmouseClick();
        } catch (SQLException ex) {
            Logger.getLogger(TimKiemNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tb_tknvMouseClicked

    private void btn_thoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_thoatActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btn_thoatActionPerformed

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
            java.util.logging.Logger.getLogger(TimKiemNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TimKiemNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TimKiemNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TimKiemNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TimKiemNhanVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cuoi;
    private javax.swing.JButton btn_dau;
    private javax.swing.JButton btn_ht;
    private javax.swing.JButton btn_sau;
    private javax.swing.JButton btn_thoat;
    private javax.swing.JButton btn_tk;
    private javax.swing.JButton btn_truoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem menu_dangky;
    private javax.swing.JMenuItem menu_dangnhap1;
    private javax.swing.JMenuItem menu_qlmuontra1;
    private javax.swing.JMenuItem menu_qlnv1;
    private javax.swing.JMenuItem menu_qls1;
    private javax.swing.JMenuItem menu_qlsv1;
    private javax.swing.JMenuItem menu_tknv;
    private javax.swing.JMenuItem menu_tksv1;
    private javax.swing.JMenuItem menu_tracuusach1;
    private javax.swing.JMenu menu_tt1;
    private javax.swing.JTable tb_tknv;
    private javax.swing.JTextField txt_tknv;
    // End of variables declaration//GEN-END:variables
}
