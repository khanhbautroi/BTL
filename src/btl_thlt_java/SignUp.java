
package btl_thlt_java;

import btl_thlt_java.Login;
import java.awt.Color;
import java.awt.Cursor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Image;
import org.mindrot.bcrypt.BCrypt;




public class SignUp extends javax.swing.JFrame {
    
//    private static final String DB_URL = "jdbc:mysql://localhost:3307/qlthuvien";
//    private static final String USER = "root";
//    private static final String PASSWORD = "";

 
    public SignUp() {
        initComponents();
        this.setLocationRelativeTo(null);
        if (switchLogin != null) {
             switchLogin.setForeground(Color.BLUE);
        }
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        signup_btn = new javax.swing.JButton();
        switchLogin = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_mk = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        txt_tk = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Right = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sign Up");
        setIconImage(new ImageIcon(getClass().getResource("/Icon/Title.png")).getImage());
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(null);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setText("Đã có tài khoản?");

        signup_btn.setBackground(new java.awt.Color(51, 102, 255));
        signup_btn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        signup_btn.setForeground(new java.awt.Color(255, 255, 255));
        signup_btn.setText("Đăng kí");
        signup_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signup_btnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signup_btnMouseExited(evt);
            }
        });
        signup_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signup_btnActionPerformed(evt);
            }
        });

        switchLogin.setForeground(new java.awt.Color(0, 0, 153));
        switchLogin.setText("Đăng nhập");
        switchLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                switchLoginMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                switchLoginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                switchLoginMouseExited(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 204));
        jLabel2.setText("ĐĂNG KÍ");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txt_mk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_mkActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(102, 102, 102));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Mật khẩu");

        txt_tk.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_tk.setForeground(new java.awt.Color(102, 102, 102));
        txt_tk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tkActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(102, 102, 102));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Tài khoản");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(129, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(121, 121, 121))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4)
                        .addComponent(jLabel3)
                        .addComponent(txt_mk)
                        .addComponent(txt_tk, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(switchLogin))
                    .addComponent(signup_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel2)
                .addGap(34, 34, 34)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_tk, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_mk, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(signup_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(switchLogin))
                .addContainerGap(111, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);
        jPanel3.setBounds(400, 0, 400, 500);

        Right.setBackground(new java.awt.Color(0, 51, 102));
        Right.setPreferredSize(new java.awt.Dimension(400, 500));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Phần mềm quản lý ");

        jLabel11.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 204, 204));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Logo-DH-Kinh-te-Ky-thuat-Cong-nghiep-UNETI (1).png"))); // NOI18N

        javax.swing.GroupLayout RightLayout = new javax.swing.GroupLayout(Right);
        Right.setLayout(RightLayout);
        RightLayout.setHorizontalGroup(
            RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RightLayout.createSequentialGroup()
                .addGap(0, 36, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(40, 40, 40))
            .addGroup(RightLayout.createSequentialGroup()
                .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RightLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9)))
                    .addGroup(RightLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel12)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RightLayout.setVerticalGroup(
            RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel10)
                .addGap(32, 32, 32)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jPanel1.add(Right);
        Right.setBounds(0, 0, 400, 500);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void signup_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signup_btnActionPerformed
        String username = txt_tk.getText(); // Lấy dữ liệu từ trường tên đăng nhập (tên biến của bạn)
        String password = new String(txt_mk.getPassword()); // Lấy dữ liệu từ trường mật khẩu (tên biến của bạn)

        // --- BƯỚC 1: KIỂM TRA TÊN ĐĂNG NHẬP CÓ CHỨA KÝ TỰ KHÔNG HỢP LỆ (VD: TIẾNG VIỆT CÓ DẤU) ---
        // Sử dụng biểu thức chính quy. Ví dụ này chỉ cho phép chữ cái không dấu (a-z, A-Z), số (0-9) và gạch dưới (_).
        String allowedUsernamePattern = "^[a-zA-Z0-9_]+$";

        if (!username.matches(allowedUsernamePattern)) {
            JOptionPane.showMessageDialog(this,
                    "Tên đăng nhập chỉ được chứa chữ cái không dấu (a-z, A-Z), số (0-9) và gạch dưới (_).",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            // Tùy chọn: Xóa nội dung trường username
            txt_tk.setText("");
            return; // Dừng xử lý nếu tên đăng nhập không hợp lệ
        }
        // --- HẾT BƯỚC 1 ---

        // --- BƯỚC 2: KIỂM TRA RỖNG (Sau khi đã kiểm tra pattern) ---
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tài khoản và mật khẩu", "Lỗi đăng kí", JOptionPane.ERROR_MESSAGE);
            return; // Dừng xử lý nếu rỗng
        }
        // --- HẾT BƯỚC 2 ---


        // --- BƯỚC 3: GỌI PHƯƠNG THỨC ĐĂNG KÝ VÀ XỬ LÝ KẾT QUẢ ---
        if (registerUser(username, password)) {
            // --- ĐĂNG KÝ THÀNH CÔNG ---
            // BƯỚC 4: Hiển thị pop-up thông báo thành công
            JOptionPane.showMessageDialog(this, "Đăng kí thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            // Tùy chọn: Xóa nội dung các trường sau khi đăng ký thành công (trước khi chuyển form)
            txt_tk.setText("");
            txt_mk.setText("");

            // BƯỚC 5: Tự động nhảy về trang Đăng nhập
            Login LoginFrame = new Login(); // Tạo instance form Đăng nhập
            LoginFrame.setVisible(true); // Hiển thị form Đăng nhập
            LoginFrame.pack(); // Điều chỉnh kích thước form
            LoginFrame.setLocationRelativeTo(null); // Đặt form Đăng nhập ở giữa màn hình

            this.dispose(); // Đóng form Đăng ký hiện tại

        } else {
            // --- ĐĂNG KÝ THẤT BẠI ---
            // Phương thức registerUser đã hiển thị lỗi CSDL nếu có.
            // Thông báo dưới đây sẽ hiển thị nếu registerUser trả về false do tài khoản đã tồn tại
            // hoặc nếu có lỗi CSDL (trong trường hợp đó sẽ có 2 thông báo lỗi hiện ra, bạn có thể cải tiến sau).
             JOptionPane.showMessageDialog(this, "Đăng kí thất bại. Tài khoản có thể đã tồn tại hoặc có lỗi CSDL.", "Lỗi đăng kí", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_signup_btnActionPerformed

    private void switchLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_switchLoginMouseClicked
        // Xử lý khi click vào Label "Đăng nhập" (ví dụ)
        // Logic này giống với việc chuyển form sau khi đăng ký thành công,
        // nhưng nó được gọi bởi click của người dùng.
        Login LoginFrame = new Login(); // Tạo instance form Đăng nhập
        LoginFrame.setVisible(true); // Hiển thị form Đăng nhập
        LoginFrame.pack(); // Điều chỉnh kích thước form
        LoginFrame.setLocationRelativeTo(null); // Đặt form Đăng nhập ở giữa màn hình

        this.dispose(); // Đóng form Đăng ký hiện tại
    }//GEN-LAST:event_switchLoginMouseClicked

    private void txt_mkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_mkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_mkActionPerformed

    private void txt_tkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tkActionPerformed

    private void switchLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_switchLoginMouseEntered
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (switchLogin != null) {
             switchLogin.setForeground(Color.BLACK); // Đổi màu khi di chuột vào
         }
    }//GEN-LAST:event_switchLoginMouseEntered

    private void switchLoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_switchLoginMouseExited
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        if (switchLogin != null) {
             switchLogin.setForeground(Color.BLUE); // Trả lại màu mặc định (ví dụ: xanh dương)
         }
    }//GEN-LAST:event_switchLoginMouseExited

    private void signup_btnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signup_btnMouseEntered
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_signup_btnMouseEntered

    private void signup_btnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signup_btnMouseExited
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_signup_btnMouseExited
private boolean registerUser(String username, String password) {
        // --- BƯỚC BĂM MẬT KHẨU ---
    // Tạo salt mới mỗi lần đăng ký
    String salt = BCrypt.gensalt();
    // Băm mật khẩu bằng salt vừa tạo
    String hashedPassword = BCrypt.hashpw(password, salt);
    // --- HẾT BƯỚC BĂM ---


    // Cách bạn lưu vào CSDL (dựa vào UNIQUE constraint):
    String insertSql = "INSERT INTO accounts (username, password) VALUES (?, ?)";

    try (Connection con = KN.KNDL()) { // Lấy kết nối từ class KN

        try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
            insertStmt.setString(1, username);
            // LƯU MẬT KHẨU ĐÃ BĂM VÀO CSDL
            insertStmt.setString(2, hashedPassword); // <-- Sử dụng kết quả băm ở đây

            int rowsAffected = insertStmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu INSERT thành công

        }

    } catch (SQLException e) {
        // Xử lý lỗi CSDL (bao gồm lỗi UNIQUE constraint nếu tài khoản đã tồn tại)
        if (e.getSQLState().startsWith("23")) {
            System.err.println("Lỗi SQL State 23xxx (tài khoản đã tồn tại): " + e.getMessage());
            // Không hiển thị JOptionPane ở đây, để signup_btnActionPerformed xử lý thông báo "Tài khoản đã tồn tại"
        } else {
            // Xử lý các lỗi CSDL khác
            JOptionPane.showMessageDialog(null, // Sử dụng null nếu gọi từ phương thức không phải GUI
                                         "Lỗi CSDL khi đăng ký: " + e.getMessage(),
                                         "Lỗi Database",
                                         JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return false; // Đăng ký thất bại
    }
}
    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Right;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JButton signup_btn;
    private javax.swing.JLabel switchLogin;
    private javax.swing.JPasswordField txt_mk;
    private javax.swing.JTextField txt_tk;
    // End of variables declaration//GEN-END:variables
}
