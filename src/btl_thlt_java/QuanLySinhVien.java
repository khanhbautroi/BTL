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
public class QuanLySinhVien extends javax.swing.JFrame {

    /**
     * Creates new form QuanLySinhVien
     */
    public QuanLySinhVien() {
        initComponents();
    }
    
    public void ht() throws SQLException{
        try {
            Connection kn = KN.KNDL();
            Statement stm = kn.createStatement();
            String sql = "select * from ql_sv";
            ResultSet rs = stm.executeQuery(sql);
            DefaultTableModel dtm = (DefaultTableModel) tb_qlsv.getModel();
            dtm.setRowCount(0);
            
            while(rs.next()){
                Object object[]={
                    rs.getString("ma"),
                    rs.getString("ten"),
                    rs.getString("gt"),
                    rs.getString("tuoi"),
                    rs.getString("dc"),
                    rs.getString("sdt"),
                    rs.getString("email")
            };
                dtm.addRow(object);
                tb_qlsv.setModel(dtm);
            }
        }catch (SQLException ex) {
            Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void them() throws SQLException{
        String ma = txt_ma.getText();
        String ten = txt_ht.getText();
        String dc = txt_dc.getText();
        String sdt = txt_sdt.getText();
        String gt = cbb_gt.getSelectedItem().toString();
        String tuoi = txt_tuoi.getText();
        String email = txt_email.getText();
        try{
            Connection kn = KN.KNDL();
            String sqlthem = "insert into ql_sv values('"+ma+"', '"+ten+"', '"+gt+"', '"+tuoi+"', '"+dc+"', '"+sdt+"', '"+email+"')";
            Statement stm = kn.createStatement();
            stm.executeUpdate(sqlthem);
            String sql = "select * from ql_sv";
            ResultSet rs = stm.executeQuery(sql);
            DefaultTableModel dtm = (DefaultTableModel) tb_qlsv.getModel();
            dtm.setRowCount(0);
            
            while(rs.next()){
                Object object[]={
                    rs.getString("ma"),
                    rs.getString("ten"),
                    rs.getString("gt"),
                    rs.getString("tuoi"),
                    rs.getString("dc"),
                    rs.getString("sdt"),
                    rs.getString("email")
                };
                dtm.addRow(object);
                tb_qlsv.setModel(dtm);
            }
            
        }catch (SQLException ex) {
            Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void xoa(){
            int row = tb_qlsv.getSelectedRow();
            String macanxoa = txt_ma.getText();
            try{
                Connection kn = KN.KNDL();
                String sql = "delete from ql_sv where ma ='"+macanxoa+"' ";
                Statement stm = kn.createStatement();
                int rowsUpdate = stm.executeUpdate(sql);
                DefaultTableModel dtm = (DefaultTableModel) tb_qlsv.getModel();
                dtm.removeRow(row);
            } catch (SQLException ex) {
                Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    public void sua() throws SQLException{
        String ma = txt_ma.getText();
        String ten = txt_ht.getText();
        String dc = txt_dc.getText();
        String sdt = txt_sdt.getText();
        String gt = cbb_gt.getSelectedItem().toString();
        String tuoi = txt_tuoi.getText();
        String email = txt_email.getText();
            try{
                Connection kn = KN.KNDL();
                String sqlsua = "update ql_sv set ten ='"+ten+"',dc ='"+dc+"',sdt ='"+sdt+"', gt ='"+gt+"', tuoi ='"+tuoi+"', email ='"+email+"'where ma ='"+ma+"'";
                Statement stm = kn.createStatement();
                stm.executeUpdate(sqlsua);
                String sql = "select * from ql_sv";
                ResultSet rs = stm.executeQuery(sql);
                DefaultTableModel dtm = (DefaultTableModel) tb_qlsv.getModel();
                dtm.setRowCount(0);
                while(rs.next()){
                    Object object[]={
                        rs.getString("ma"),
                        rs.getString("ten"),
                        rs.getString("gt"),
                        rs.getString("tuoi"),
                        rs.getString("dc"),
                        rs.getString("sdt"),
                        rs.getString("email")
                    };
                dtm.addRow(object);
                tb_qlsv.setModel(dtm);
                }
            
            }catch (SQLException ex) {
            Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void tbmouseClick() throws SQLException{
        int row = tb_qlsv.getSelectedRow();
        
            String ma = tb_qlsv.getValueAt(row, 0).toString();
            String ten = tb_qlsv.getValueAt(row, 1).toString();
            String gt = tb_qlsv.getValueAt(row, 2).toString();
            String tuoi = tb_qlsv.getValueAt(row, 3).toString();
            String dc= tb_qlsv.getValueAt(row, 4).toString();
            String sdt = tb_qlsv.getValueAt(row, 5).toString();
            String email= tb_qlsv.getValueAt(row, 6).toString();
            
            txt_ma.setText(ma);
            txt_ht.setText(ten);
            cbb_gt.setSelectedItem(gt);
            txt_tuoi.setText(tuoi);
            txt_dc.setText(dc);
            txt_sdt.setText(sdt);
            txt_email.setText(email);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_qlsv = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_ma = new javax.swing.JTextField();
        txt_ht = new javax.swing.JTextField();
        txt_tuoi = new javax.swing.JTextField();
        txt_dc = new javax.swing.JTextField();
        txt_sdt = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        btn_them = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        btn_thoat = new javax.swing.JButton();
        cbb_gt = new javax.swing.JComboBox<>();
        btn_lm = new javax.swing.JButton();
        btn_cuoi = new javax.swing.JButton();
        btn_dau = new javax.swing.JButton();
        btn_truoc = new javax.swing.JButton();
        btn_sau = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menu_qlsv = new javax.swing.JMenuItem();
        menu_qls = new javax.swing.JMenuItem();
        menu_qlmuontra = new javax.swing.JMenuItem();
        menu_qlnv = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menu_tracuusach = new javax.swing.JMenuItem();
        menu_tksv = new javax.swing.JMenuItem();
        menu_tknv = new javax.swing.JMenuItem();
        menu_tt = new javax.swing.JMenu();
        menu_dangnhap = new javax.swing.JMenuItem();
        menu_dangky = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý sinh viên");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Quản lý sinh viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 14))); // NOI18N
        jPanel1.setToolTipText("");
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 500));

        tb_qlsv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SV", "Họ tên", "Giới tính", "Tuổi", "Địa chỉ", "Sđt", "Email"
            }
        ));
        tb_qlsv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_qlsvMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_qlsv);

        jLabel1.setText("Mã sinh viên");

        jLabel2.setText("Họ tên");

        jLabel3.setText("Giới tính");

        jLabel4.setText("Tuổi");

        jLabel5.setText("Địa chỉ");

        jLabel6.setText("Số điện thoại");

        jLabel7.setText("Email");

        btn_them.setText("Thêm");
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });

        btn_sua.setText("Sửa");
        btn_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaActionPerformed(evt);
            }
        });

        btn_xoa.setText("Xóa");
        btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaActionPerformed(evt);
            }
        });

        btn_thoat.setText("Thoát");
        btn_thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_thoatActionPerformed(evt);
            }
        });

        cbb_gt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        btn_lm.setText("Làm mới");
        btn_lm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lmActionPerformed(evt);
            }
        });

        btn_cuoi.setText(">|");
        btn_cuoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cuoiActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_ma)
                                .addComponent(txt_ht)
                                .addComponent(txt_tuoi, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
                            .addComponent(cbb_gt, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(94, 94, 94)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_dau, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_truoc, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_sau, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_cuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(56, 56, 56)
                                        .addComponent(txt_dc, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(btn_them, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(93, 93, 93))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btn_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btn_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btn_thoat, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btn_lm))
                                        .addContainerGap())))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(txt_ma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_dc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_them))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(txt_ht, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_sua))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_xoa)
                    .addComponent(cbb_gt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_tuoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_thoat))
                .addGap(18, 18, 18)
                .addComponent(btn_lm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_dau)
                    .addComponent(btn_truoc)
                    .addComponent(btn_sau)
                    .addComponent(btn_cuoi))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        jMenu1.setText("Quản Lý");

        menu_qlsv.setText("Quản lý sinh viên");
        menu_qlsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_qlsvActionPerformed(evt);
            }
        });
        jMenu1.add(menu_qlsv);

        menu_qls.setText("Quản lý sách");
        menu_qls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_qlsActionPerformed(evt);
            }
        });
        jMenu1.add(menu_qls);

        menu_qlmuontra.setText("Quản lý mượn trả sách");
        menu_qlmuontra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_qlmuontraActionPerformed(evt);
            }
        });
        jMenu1.add(menu_qlmuontra);

        menu_qlnv.setText("Quản lý nhân viên");
        menu_qlnv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_qlnvActionPerformed(evt);
            }
        });
        jMenu1.add(menu_qlnv);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Tra cứu");

        menu_tracuusach.setText("Tra cứu sách");
        menu_tracuusach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_tracuusachActionPerformed(evt);
            }
        });
        jMenu2.add(menu_tracuusach);

        menu_tksv.setText("Tìm kiếm sinh viên");
        menu_tksv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_tksvActionPerformed(evt);
            }
        });
        jMenu2.add(menu_tksv);

        menu_tknv.setText("Tìm kiếm nhân viên");
        menu_tknv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_tknvActionPerformed(evt);
            }
        });
        jMenu2.add(menu_tknv);

        jMenuBar1.add(jMenu2);

        menu_tt.setText("Thông tin");

        menu_dangnhap.setText("Đăng nhập");
        menu_dangnhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_dangnhapActionPerformed(evt);
            }
        });
        menu_tt.add(menu_dangnhap);

        menu_dangky.setText("Đăng ký");
        menu_dangky.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_dangkyActionPerformed(evt);
            }
        });
        menu_tt.add(menu_dangky);

        jMenuBar1.add(menu_tt);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_thoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_thoatActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btn_thoatActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        try {
            ht();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowActivated

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        try {
            them();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        xoa();
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void tb_qlsvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_qlsvMouseClicked
        try {
            tbmouseClick();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }//GEN-LAST:event_tb_qlsvMouseClicked

    private void btn_lmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lmActionPerformed
            txt_ma.setText("");
            txt_ht.setText("");
            cbb_gt.setSelectedItem("");
            txt_tuoi.setText("");
            txt_dc.setText("");
            txt_sdt.setText("");
            txt_email.setText("");
    }//GEN-LAST:event_btn_lmActionPerformed

    private void menu_qlsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_qlsvActionPerformed
        new QuanLySinhVien().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_qlsvActionPerformed

    private void menu_qlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_qlsActionPerformed
        new QuanLySach().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_qlsActionPerformed

    private void menu_qlmuontraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_qlmuontraActionPerformed
        new QuanLyMuonTraSach().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_qlmuontraActionPerformed

    private void menu_qlnvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_qlnvActionPerformed
        new QuanLyNhanVien().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_qlnvActionPerformed

    private void menu_tracuusachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_tracuusachActionPerformed
        new TraCuuSach().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_tracuusachActionPerformed

    private void menu_tksvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_tksvActionPerformed
        new TimKiemSinhVien().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_tksvActionPerformed

    private void menu_dangnhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_dangnhapActionPerformed
        new Login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_dangnhapActionPerformed

    private void menu_dangkyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_dangkyActionPerformed
        new SignUp().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_dangkyActionPerformed

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        try {
            sua();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_suaActionPerformed

    private void menu_tknvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_tknvActionPerformed
        new TimKiemNhanVien().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_tknvActionPerformed

    private void btn_cuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cuoiActionPerformed
        tb_qlsv.setRowSelectionInterval(tb_qlsv.getRowCount()-1, tb_qlsv.getRowCount()-1);
        try {
            tbmouseClick();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_cuoiActionPerformed

    private void btn_dauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dauActionPerformed
        tb_qlsv.setRowSelectionInterval(0,0);
        try {
            tbmouseClick();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_dauActionPerformed

    private void btn_truocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_truocActionPerformed
        int n = tb_qlsv.getSelectedRow();
        if(n>0){
            tb_qlsv.setRowSelectionInterval(n-1, n-1);
        }else{
            tb_qlsv.setRowSelectionInterval(tb_qlsv.getRowCount()-1,tb_qlsv.getRowCount()-1);
        }
        try {
            tbmouseClick();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_truocActionPerformed

    private void btn_sauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sauActionPerformed
        int n = tb_qlsv.getSelectedRow();
        if(n==tb_qlsv.getRowCount()-1){
            tb_qlsv.setRowSelectionInterval(0, 0);
        }else{
            tb_qlsv.setRowSelectionInterval(n+1, n+1);
        }
        try {
            tbmouseClick();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_sauActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLySinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLySinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLySinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLySinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLySinhVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cuoi;
    private javax.swing.JButton btn_dau;
    private javax.swing.JButton btn_lm;
    private javax.swing.JButton btn_sau;
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_thoat;
    private javax.swing.JButton btn_truoc;
    private javax.swing.JButton btn_xoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbb_gt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem menu_dangky;
    private javax.swing.JMenuItem menu_dangnhap;
    private javax.swing.JMenuItem menu_qlmuontra;
    private javax.swing.JMenuItem menu_qlnv;
    private javax.swing.JMenuItem menu_qls;
    private javax.swing.JMenuItem menu_qlsv;
    private javax.swing.JMenuItem menu_tknv;
    private javax.swing.JMenuItem menu_tksv;
    private javax.swing.JMenuItem menu_tracuusach;
    private javax.swing.JMenu menu_tt;
    private javax.swing.JTable tb_qlsv;
    private javax.swing.JTextField txt_dc;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_ht;
    private javax.swing.JTextField txt_ma;
    private javax.swing.JTextField txt_sdt;
    private javax.swing.JTextField txt_tuoi;
    // End of variables declaration//GEN-END:variables

    
}
