/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package btl_thlt_java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent; 
import javax.swing.JPasswordField; 
import javax.swing.JTextField; 
import javax.swing.JButton; 
import javax.swing.JTable; 
import javax.swing.JLabel; 
/**
 *
 * @author Admin
 */
public class QuanLyNguoiDung extends javax.swing.JFrame {
    
    private int selectedUserId = -1;
    private boolean isAddingNew = false;

    /**
     * Creates new form QuanLyNguoiDung
     */
    public QuanLyNguoiDung() {
        initComponents();
        setLocationRelativeTo(null); // Đặt form ra giữa màn hình

        // --- CÁC THIẾT LẬP BAN ĐẦU ---
        loadTableData(); // Tải dữ liệu lên bảng khi form mở
        clearInputFields(); // Làm sạch các trường nhập liệu
        setEditMode(false); // Bắt đầu ở chế độ không chỉnh sửa

        // Thêm MouseListener cho bảng để xử lý click
        tbUsers.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tbUsersMouseClicked(evt);
            }
        });
    }
    

    // --- PHƯƠNG THỨC TẢI DỮ LIỆU VÀO BẢNG ---
    public void loadTableData() {
        DefaultTableModel dtm = (DefaultTableModel) tbUsers.getModel(); // Lấy model của JTable
        dtm.setRowCount(0); // Xóa tất cả các dòng dữ liệu hiện có

        String sql = "SELECT id, username, password, quyen_han FROM nguoi_dung"; // Lấy tất cả dữ liệu từ bảng nguoi_dung

        try (Connection con = KN.KNDL(); // Lấy kết nối CSDL
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                // Lấy dữ liệu từ từng cột của bảng nguoi_dung
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password"); // Lấy mật khẩu (đang lưu plain text)
                String quyenHan = rs.getString("quyen_han"); // Lấy quyền hạn

                // Tạo mảng Object cho một dòng dữ liệu và thêm vào model
                Object[] rowData = {id, username, password, quyenHan};
                dtm.addRow(rowData);
            }

        } catch (SQLException ex) {
            Logger.getLogger(QuanLyNguoiDung.class.getName()).log(Level.SEVERE, "Lỗi khi tải dữ liệu người dùng", ex);
            JOptionPane.showMessageDialog(this, "Lỗi CSDL khi tải dữ liệu người dùng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- PHƯƠNG THỨC XỬ LÝ SỰ KIỆN CLICK TRÊN BẢNG ---
    private void tbUsersMouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = tbUsers.getSelectedRow(); // Lấy chỉ số dòng được chọn

        if (selectedRow >= 0) { // Nếu có dòng được chọn
            // Lấy dữ liệu từ các cột của dòng được chọn trong bảng
            // Đảm bảo chỉ số cột (0, 1, 2, 3...) khớp với thứ tự cột trong JTable Model
            selectedUserId = (int) tbUsers.getValueAt(selectedRow, 0); // Cột 0 là ID
            String username = (String) tbUsers.getValueAt(selectedRow, 1); // Cột 1 là Username
            String password = (String) tbUsers.getValueAt(selectedRow, 2); // Cột 2 là Password
            String quyenHan = (String) tbUsers.getValueAt(selectedRow, 3); // Cột 3 là Quyền hạn

            // Điền dữ liệu vào các trường nhập liệu chi tiết
            txtId.setText(String.valueOf(selectedUserId));
            txtUsername.setText(username);
            txtPassword.setText(password); // Hiển thị mật khẩu (plain text)
            txtQuyenHan.setText(quyenHan);

            // Chuyển sang chế độ xem chi tiết (hoặc sẵn sàng sửa/xóa)
            setEditMode(false); // Đặt về false để vô hiệu hóa các nút Lưu/Hủy ban đầu
            btnThem.setEnabled(true); // Luôn cho phép thêm mới
            btnSua.setEnabled(true); // Cho phép sửa khi có dòng được chọn
            btnXoa.setEnabled(true); // Cho phép xóa khi có dòng được chọn


        } else { // Nếu không có dòng nào được chọn (ví dụ: click vào khoảng trống của bảng)
            clearInputFields(); // Làm sạch các trường nhập liệu
            setEditMode(false); // Trở về chế độ không chỉnh sửa
            btnThem.setEnabled(true); // Luôn cho phép thêm mới
            btnSua.setEnabled(false); // Vô hiệu hóa sửa/xóa
            btnXoa.setEnabled(false);
            selectedUserId = -1; // Reset ID
        }
    }

    // --- PHƯƠNG THỨC LÀM SẠCH CÁC TRƯỜNG NHẬP LIỆU ---
    private void clearInputFields() {
        txtId.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtQuyenHan.setText("");
        selectedUserId = -1; // Reset ID khi làm sạch trường
    }


    private void setEditMode(boolean editable) {
        txtUsername.setEditable(editable);
        txtPassword.setEditable(editable); 
        txtQuyenHan.setEditable(editable);
        txtId.setEditable(false); 

        btnLuu.setEnabled(editable); 

        
    }




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbUsers = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        txtQuyenHan = new javax.swing.JTextField();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbUsers.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tbUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Username", "Password", "Quyền hạn"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbUsers);

        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Id");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Username");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Password");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Quyền hạn");

        txtId.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtQuyenHan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLuu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton5.setText("Thoát");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtId)
                    .addComponent(txtUsername)
                    .addComponent(txtPassword)
                    .addComponent(txtQuyenHan, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
                .addGap(117, 117, 117)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSua)
                            .addComponent(btnThem))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoa)
                            .addComponent(btnLuu))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtQuyenHan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)))
                .addGap(15, 15, 15)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        clearInputFields(); 
        setEditMode(true); 

        isAddingNew = true; 

        btnThem.setEnabled(false); 
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (tbUsers.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng cần sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        setEditMode(true); 
        isAddingNew = false; 

        btnThem.setEnabled(false);
        btnXoa.setEnabled(false);
        btnSua.setEnabled(false);
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if (selectedUserId == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng cần xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa người dùng ID: " + selectedUserId + " (" + txtUsername.getText() + ") không?",
                "Xác nhận Xóa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {

            String sql = "DELETE FROM nguoi_dung WHERE id = ?";

            try (Connection con = KN.KNDL(); 
                 PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setInt(1, selectedUserId);

                int rowsAffected = pst.executeUpdate(); 

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa người dùng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadTableData(); 
                    clearInputFields(); 
                    setEditMode(false); 
                    btnThem.setEnabled(true); 
                    btnSua.setEnabled(false); 
                    btnXoa.setEnabled(false);

                } else {

                    JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng để xóa hoặc có lỗi xảy ra.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                Logger.getLogger(QuanLyNguoiDung.class.getName()).log(Level.SEVERE, "Lỗi CSDL khi xóa người dùng", ex);
                JOptionPane.showMessageDialog(this, "Lỗi CSDL khi xóa người dùng: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String quyenHan = txtQuyenHan.getText().trim();

        if (username.isEmpty() || password.isEmpty() || quyenHan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Tài khoản, Mật khẩu và Quyền hạn.", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
            return; 
        }

        String sql;
        boolean success = false;

        if (isAddingNew) { 

            sql = "INSERT INTO nguoi_dung (username, password, quyen_han) VALUES (?, ?, ?)";
            try (Connection con = KN.KNDL();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setString(1, username);
                pst.setString(2, password); 
                pst.setString(3, quyenHan);

                int rowsAffected = pst.executeUpdate();
                success = rowsAffected > 0;

                if (success) {
                    JOptionPane.showMessageDialog(this, "Thêm người dùng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    JOptionPane.showMessageDialog(this, "Thêm người dùng thất bại. Tài khoản có thể đã tồn tại?", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                 if (ex.getSQLState().startsWith("23")) { 
                    JOptionPane.showMessageDialog(this, "Thêm người dùng thất bại. Tài khoản '" + username + "' đã tồn tại.", "Lỗi Trùng lặp", JOptionPane.ERROR_MESSAGE);
                 } else {
                    Logger.getLogger(QuanLyNguoiDung.class.getName()).log(Level.SEVERE, "Lỗi CSDL khi thêm người dùng", ex);
                    JOptionPane.showMessageDialog(this, "Lỗi CSDL khi thêm người dùng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                 }
            }

        } else { 

            if (selectedUserId == -1) {
                 JOptionPane.showMessageDialog(this, "Không xác định được người dùng cần sửa. Vui lòng chọn lại.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                 return;
            }

            sql = "UPDATE nguoi_dung SET username = ?, password = ?, quyen_han = ? WHERE id = ?";
             try (Connection con = KN.KNDL();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setString(1, username);
                pst.setString(2, password);
                pst.setString(3, quyenHan);
                pst.setInt(4, selectedUserId); 

                int rowsAffected = pst.executeUpdate();
                success = rowsAffected > 0;

                if (success) {
                    JOptionPane.showMessageDialog(this, "Cập nhật người dùng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    JOptionPane.showMessageDialog(this, "Cập nhật người dùng thất bại. Có thể người dùng không tồn tại hoặc không có thay đổi nào?", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }

            } catch (SQLException ex) {
                 Logger.getLogger(QuanLyNguoiDung.class.getName()).log(Level.SEVERE, "Lỗi CSDL khi cập nhật người dùng", ex);
                 JOptionPane.showMessageDialog(this, "Lỗi CSDL khi cập nhật người dùng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }


        loadTableData(); 
        clearInputFields(); 
        setEditMode(false);
        isAddingNew = false; 
        btnThem.setEnabled(true); 
        btnSua.setEnabled(false); 
        btnXoa.setEnabled(false);
    }//GEN-LAST:event_btnLuuActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLyNguoiDung.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguoiDung.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguoiDung.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguoiDung.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyNguoiDung().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbUsers;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtQuyenHan;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
