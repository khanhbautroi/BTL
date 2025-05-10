/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package btl_thlt_java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JScrollPane; // Cần cho JScrollPane
import javax.swing.JTable; // Cần cho JTable
import javax.swing.table.DefaultTableModel; // Cần cho DefaultTableModel
import java.awt.event.MouseAdapter; // Cần cho MouseAdapter
import java.awt.event.MouseEvent; // Cần cho MouseEvent
import java.awt.Color; // Cần cho Color (nếu muốn thiết lập màu bảng trong code)
import java.awt.Font; // Cần cho Font (nếu muốn thiết lập font bảng trong code)
import javax.swing.table.JTableHeader; // Cần cho JTableHeader

/**
 *
 * @author Admin
 */
public class DangKyMuonSachForm extends javax.swing.JFrame {

    /**
     * Creates new form DangKyMuonSachForm
     */
    private boolean isBookAvailable = false;
    
    public DangKyMuonSachForm() {
        initComponents();
        setLocationRelativeTo(null);
        
        btnLuuMuon.setEnabled(false);
        
        setupTableAppearance(tb_sachDangMuon);
    }
    
    private void setupTableAppearance(JTable nameJTable) {
        if (nameJTable == null) return; // Thoát nếu bảng chưa được khởi tạo

        JTableHeader header = nameJTable.getTableHeader();
        header.setBackground(new Color(70, 130, 180)); // Steel blue
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameJTable.setSelectionBackground(new Color(135, 206, 250)); // Light sky blue
        nameJTable.setSelectionForeground(Color.BLACK);
        nameJTable.setShowGrid(true);
        nameJTable.setGridColor(new Color(200, 200, 200));
        nameJTable.setShowHorizontalLines(true);
        nameJTable.setShowVerticalLines(true);
    }
    
    // --- Phương thức tra cứu Sinh Viên ---
    private void lookupStudent(String maSV) {
        txtTenSV.setText(""); // Xóa tên SV cũ
        // Xóa dữ liệu bảng lịch sử mượn khi tra cứu SV mới
        DefaultTableModel dtm = (DefaultTableModel) tb_sachDangMuon.getModel();
        dtm.setRowCount(0);

        if (maSV.isEmpty()) {
            // Nếu mã SV rỗng
            btnLuuMuon.setEnabled(false); // Vô hiệu hóa nút Lưu
            isBookAvailable = false; // Reset trạng thái sách
            updateLuuButtonState(); // Cập nhật trạng thái nút Lưu
            return;
        }

        String sql = "SELECT ten FROM ql_sv WHERE ma = ?";

        try (Connection con = KN.KNDL();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, maSV);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    txtTenSV.setText(rs.getString("ten"));
                    // --- GỌI HÀM TẢI LỊCH SỬ MƯỢN SAU KHI TRA CỨU SINH VIÊN THÀNH CÔNG ---
                    loadBorrowHistoryForStudent(maSV);
                    // isBookAvailable không đổi trạng thái ở đây, chỉ liên quan đến sách
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy Sinh Viên có Mã: " + maSV, "Lỗi Tra cứu", JOptionPane.WARNING_MESSAGE);
                    txtMaSV.setText("");
                    txtTenSV.setText("");
                     // Xóa dữ liệu bảng lịch sử mượn nếu không tìm thấy SV (đã làm ở đầu phương thức)
                     btnLuuMuon.setEnabled(false); // Vô hiệu hóa nút Lưu
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DangKyMuonSachForm.class.getName()).log(Level.SEVERE, "Lỗi tra cứu Sinh Viên", ex);
            JOptionPane.showMessageDialog(this, "Lỗi CSDL khi tra cứu Sinh Viên: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
             // Xóa dữ liệu bảng lịch sử mượn và vô hiệu hóa nút Lưu nếu có lỗi CSDL (đã làm ở đầu)
             btnLuuMuon.setEnabled(false);
        } finally {
             // Luôn cập nhật trạng thái nút Lưu sau khi tra cứu SV hoàn tất
             updateLuuButtonState();
        }
    }
    
    // --- Phương thức tra cứu Sách ---
    private void lookupBook(String maSach) {
        txtTenSach.setText("");
        txtTacGia.setText("");
        txtGiaSach.setText("");
        txtTinhTrangSach.setText("");
        isBookAvailable = false; // Reset trạng thái sách có sẵn

        if (maSach.isEmpty()) {
             btnLuuMuon.setEnabled(false);
             updateLuuButtonState();
            return;
        }

        String sql = "SELECT tenS, tg, gia, tinhtrang, sl FROM ql_sach WHERE maS = ?";

        try (Connection con = KN.KNDL();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, maSach);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    txtTenSach.setText(rs.getString("tenS"));
                    txtTacGia.setText(rs.getString("tg"));
                    txtGiaSach.setText(String.valueOf(rs.getInt("gia"))); // Giả định 'gia' là số
                    txtTinhTrangSach.setText(rs.getString("tinhtrang"));

                    // --- KIỂM TRA SỐ LƯỢNG CÒN (Sử dụng rs.getInt() vì cột đã là INT) ---
                    int soLuongCon = rs.getInt("sl"); // Lấy giá trị trực tiếp dưới dạng INT

                    if (soLuongCon > 0) {
                        isBookAvailable = true; // Sách còn hàng nếu số lượng > 0
                    } else {
                        // Nếu số lượng <= 0, thông báo sách hết và đặt isBookAvailable = false
                        JOptionPane.showMessageDialog(this, "Sách này hiện đã hết (Số lượng: " + soLuongCon + ").", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        isBookAvailable = false;
                    }
                    // --- KẾT THÚC KIỂM TRA SL ---

                } else {
                    // Không tìm thấy sách
                    JOptionPane.showMessageDialog(this, "Không tìm thấy Sách có Mã: " + maSach, "Lỗi Tra cứu", JOptionPane.WARNING_MESSAGE);
                    txtMaSach.setText("");
                    txtTenSach.setText("");
                    txtTacGia.setText("");
                    txtGiaSach.setText("");
                    txtTinhTrangSach.setText("");
                    isBookAvailable = false; // Không tìm thấy sách
                    btnLuuMuon.setEnabled(false); // Vô hiệu hóa nút Lưu
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DangKyMuonSachForm.class.getName()).log(Level.SEVERE, "Lỗi tra cứu Sách", ex);
            JOptionPane.showMessageDialog(this, "Lỗi CSDL khi tra cứu Sách: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
             isBookAvailable = false; // Lỗi CSDL
             btnLuuMuon.setEnabled(false); // Vô hiệu hóa nút Lưu
        } finally {
             // Luôn cập nhật trạng thái nút Lưu sau khi tra cứu Sách hoàn tất
             updateLuuButtonState();
        }
    }
    
    // --- Phương thức helper để cập nhật trạng thái nút Lưu Mượn ---
     private void updateLuuButtonState() {
         // Nút Lưu chỉ được bật khi:
         // 1. Đã tra cứu thành công Sinh Viên (txtTenSV có dữ liệu và txtMaSV không rỗng)
         // 2. Đã tra cứu thành công Sách (txtTenSach có dữ liệu và txtMaSach không rỗng)
         // 3. Sách đó còn hàng (biến isBookAvailable = true)
         // 4. Ngày trả dự kiến đã được nhập (kiểm tra rỗng, TBD: kiểm tra định dạng ngày)
         // 5. Phí mượn đã được nhập (kiểm tra rỗng, TBD: kiểm tra định dạng số)

         boolean studentValid = !txtMaSV.getText().trim().isEmpty() && !txtTenSV.getText().isEmpty();
         boolean bookValidAndAvailable = !txtMaSach.getText().trim().isEmpty() && !txtTenSach.getText().isEmpty() && isBookAvailable; // Sử dụng biến isBookAvailable
         boolean returnDateEntered = !txtNgayTraDuKien.getText().trim().isEmpty();
         boolean phiMuonEntered = !txtPhiMuon.getText().trim().isEmpty();

         // TODO: Thêm kiểm tra định dạng ngày cho txtNgayTraDuKien (ví dụ: dùng SimpleDateFormat.parse)
         // TODO: Thêm kiểm tra định dạng số cho txtPhiMuon (ví dụ: dùng Double.parseDouble và bắt NumberFormatException)

         boolean canEnableLuu = studentValid && bookValidAndAvailable && returnDateEntered && phiMuonEntered;

         btnLuuMuon.setEnabled(canEnableLuu);

     }

    // --- Phương thức làm sạch các trường nhập liệu chính (tùy chọn) ---
    // Có thể gọi sau khi lưu thành công hoặc khi muốn reset form
    public void clearInputFields() {
        txtMaSV.setText("");
        txtTenSV.setText("");
        txtMaSach.setText("");
        txtTenSach.setText("");
        txtTacGia.setText("");
        txtGiaSach.setText("");
        txtTinhTrangSach.setText("");
        // Ngày mượn nên giữ lại hoặc set lại ngày hiện tại
        // txtNgayMuon.setText("");
        txtNgayTraDuKien.setText("");
        txtPhiMuon.setText("");

        // Xóa dữ liệu bảng lịch sử mượn
        DefaultTableModel dtm = (DefaultTableModel) tb_sachDangMuon.getModel();
        dtm.setRowCount(0);

        isBookAvailable = false; // Reset trạng thái sách có sẵn
        updateLuuButtonState();
    }


    // --- Phương thức tải lịch sử mượn cho sinh viên vào JTable ---
    // Phương thức này được gọi sau khi tra cứu sinh viên thành công
     private void loadBorrowHistoryForStudent(String maSV) {
          DefaultTableModel dtm = (DefaultTableModel) tb_sachDangMuon.getModel();
          dtm.setRowCount(0); // Xóa tất cả các dòng dữ liệu cũ

          if (maSV.isEmpty()) {
              return; // Không tải nếu mã SV rỗng
          }

          String sql = "SELECT mts.maSV, mts.maS, qls.tenS, mts.ngayMuon, mts.ngayTraDuKien, mts.phiMuon, mts.tinhTrangMuon " +
                       "FROM muon_tra_sach mts " +
                       "JOIN ql_sach qls ON mts.maS = qls.maS " + // JOIN để lấy Tên sách (tenS)
                       "WHERE mts.maSV = ? " +
                       "ORDER BY mts.ngayMuon DESC";
          // --- KẾT THÚC CẬP NHẬT SQL ---


          try (Connection con = KN.KNDL();
               PreparedStatement pst = con.prepareStatement(sql)) {

              pst.setString(1, maSV);

              try (ResultSet rs = pst.executeQuery()) {
                  while (rs.next()) {
                      // --- LẤY DỮ LIỆU TỪ ResultSet (Khớp với các cột trong SELECT) ---
                      String maSinhVien = rs.getString("maSV"); // Lấy Mã SV
                      String maSach = rs.getString("maS");     // Lấy Mã Sách (từ mts.maS)
                      String tenSach = rs.getString("tenS");    // Lấy Tên Sách (từ qls.tenS)
                      String ngayMuon = rs.getTimestamp("ngayMuon") != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("ngayMuon")) : "N/A";
                      String ngayTraDuKien = rs.getDate("ngayTraDuKien") != null ? rs.getDate("ngayTraDuKien").toString() : "N/A";
                      // Lấy phí mượn. Nếu cột phimMuon trong DB là DECIMAL, nên dùng getDouble hoặc getBigDecimal.
                      // Nếu là VARCHAR, vẫn dùng getString.
                      String phiMuon = rs.getString("phiMuon"); // Lấy Phí mượn
                      String tinhTrang = rs.getString("tinhTrangMuon");
                      // --- KẾT THÚC LẤY DỮ LIỆU ---


                      // --- TẠO MẢNG DỮ LIỆU CHO DÒNG (7 phần tử, khớp với 7 cột SELECT và Design) ---
                      // Đảm bảo thứ tự các phần tử trong mảng này khớp với thứ tự cột trong Designer
                      Object[] rowData = {
                          maSinhVien,    // Cột 1: Mã sinh viên
                          maSach,        // Cột 2: Mã sách
                          tenSach,       // Cột 3: Tên sách
                          ngayMuon,      // Cột 4: Ngày mượn
                          ngayTraDuKien, // Cột 5: Ngày trả dự kiến
                          phiMuon,       // Cột 6: Phí mượn
                          tinhTrang      // Cột 7: Tình trạng
                      };
                      // --- KẾT THÚC TẠO MẢNG DỮ LIỆU ---

                      dtm.addRow(rowData); // Thêm dòng vào model
                  }
              }

          } catch (SQLException ex) {
              Logger.getLogger(DangKyMuonSachForm.class.getName()).log(Level.SEVERE, "Lỗi tải lịch sử mượn", ex);
              JOptionPane.showMessageDialog(this, "Lỗi CSDL khi tải lịch sử mượn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
          }
      }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtMaSV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTenSV = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_sachDangMuon = new javax.swing.JTable();
        txtMaSach = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTacGia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtGiaSach = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTinhTrangSach = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNgayMuon = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNgayTraDuKien = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtPhiMuon = new javax.swing.JTextField();
        btnLuuMuon = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Mã Sinh Viên");

        txtMaSV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSVActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Tên Sinh Viên");

        txtTenSV.setEditable(false);
        txtTenSV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Mã sách");

        tb_sachDangMuon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tb_sachDangMuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sinh viên", "Mã sách", "Tên sách ", "Ngày mượn", "Ngày trả dự kiến", "Phí mượn", "Tình trạng mượn"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_sachDangMuon.setRowHeight(30);
        tb_sachDangMuon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_sachDangMuonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_sachDangMuon);

        txtMaSach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSachActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Tên sách");

        txtTenSach.setEditable(false);
        txtTenSach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Tác giả");

        txtTacGia.setEditable(false);
        txtTacGia.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Giá");

        txtGiaSach.setEditable(false);
        txtGiaSach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Tình trạng sách");

        txtTinhTrangSach.setEditable(false);
        txtTinhTrangSach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Ngày mượn");

        txtNgayMuon.setEditable(false);
        txtNgayMuon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Ngày trả dự kiến");

        txtNgayTraDuKien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtNgayTraDuKien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayTraDuKienActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Phí mượn");

        txtPhiMuon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtPhiMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhiMuonActionPerformed(evt);
            }
        });

        btnLuuMuon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnLuuMuon.setText("Lưu Mượn");
        btnLuuMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuMuonActionPerformed(evt);
            }
        });

        btnHuy.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnHuy.setText("Quay lại");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        jLabel11.setText("(tra sinh viên mượn bằng mã)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(81, 81, 81)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTacGia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtTenSach, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                                .addComponent(txtTenSV, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtMaSach, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtMaSV, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(122, 122, 122)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTinhTrangSach, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                                    .addComponent(txtGiaSach)
                                    .addComponent(txtNgayMuon)
                                    .addComponent(txtNgayTraDuKien)
                                    .addComponent(txtPhiMuon))))
                        .addGap(0, 279, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnLuuMuon)
                        .addGap(69, 69, 69))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnHuy)
                        .addGap(78, 78, 78))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnLuuMuon)
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txtTinhTrangSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(30, 30, 30)
                        .addComponent(btnHuy)
                        .addGap(1, 1, 1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtGiaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(145, 145, 145)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtNgayTraDuKien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtPhiMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = formatter.format(now);
        txtNgayMuon.setText(dateTimeString);

        // TODO: Các xử lý khác khi form mở
        // Làm sạch các trường nhập liệu chính ban đầu
        clearInputFields();
        // Nút Lưu Mượn ban đầu đã vô hiệu hóa trong constructor
    }//GEN-LAST:event_formWindowOpened

    private void txtMaSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSVActionPerformed

        lookupStudent(txtMaSV.getText().trim());
    }//GEN-LAST:event_txtMaSVActionPerformed

    private void txtMaSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSachActionPerformed

        lookupBook(txtMaSach.getText().trim());
    }//GEN-LAST:event_txtMaSachActionPerformed

    private void tb_sachDangMuonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_sachDangMuonMouseClicked
        int selectedRow = tb_sachDangMuon.getSelectedRow(); // Lấy chỉ số dòng được chọn

        if (selectedRow == -1) {
            // Không làm gì nếu không có dòng nào được chọn
            return;
        }
    }//GEN-LAST:event_tb_sachDangMuonMouseClicked

    private void btnLuuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuMuonActionPerformed
       // TODO: Lấy dữ liệu từ form (đã làm ở đầu phương thức)
          String maSV = txtMaSV.getText().trim();
          String maSach = txtMaSach.getText().trim();
          String tenSach = txtTenSach.getText().trim();
          String ngayMuon = txtNgayMuon.getText().trim();
          String ngayTraDuKienStr = txtNgayTraDuKien.getText().trim();
          String phiMuonStr = txtPhiMuon.getText().trim();

         // TODO: Kiểm tra dữ liệu nhập (đã làm ở trên)

         // TODO: Kiểm tra lại Mã SV và Mã sách có tồn tại và sách còn hàng (đã làm ở trên)

         // --- THỰC HIỆN TRANSACTION: INSERT vào muon_tra_sach VÀ UPDATE giảm số lượng sách ---
          Connection con = null;
          try {
              con = KN.KNDL();

              // Bắt đầu Transaction
              con.setAutoCommit(false);

              // 1. INSERT bản ghi mượn vào bảng muon_tra_sach
              String sqlInsert = "INSERT INTO muon_tra_sach (maSV, maS, tenSach , ngayMuon, ngayTraDuKien, phiMuon, tinhTrangMuon) VALUES (?, ?, ?, ?, ?, ?,?)";
              PreparedStatement pstInsert = con.prepareStatement(sqlInsert);

              pstInsert.setString(1, maSV);
              pstInsert.setString(2, maSach);
              pstInsert.setString(3, tenSach);
              pstInsert.setTimestamp(4, java.sql.Timestamp.valueOf(ngayMuon));
              // Chuyển String ngày trả dự kiến sang java.sql.Date
               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
               dateFormat.setLenient(false);
               Date ngayTraDuKienDate = dateFormat.parse(ngayTraDuKienStr); // Chuyển String sang Date
               pstInsert.setDate(5, new java.sql.Date(ngayTraDuKienDate.getTime())); // Chuyển java.util.Date sang java.sql.Date

              pstInsert.setDouble(6, Double.parseDouble(phiMuonStr)); // Chuyển String sang Double
              pstInsert.setString(7, "Đang mượn");

              pstInsert.executeUpdate();
              pstInsert.close();

              // 2. UPDATE giảm số lượng sách trong bảng ql_sach
              String sqlUpdate = "UPDATE ql_sach SET sl = sl - 1 WHERE maS = ?";
              PreparedStatement pstUpdate = con.prepareStatement(sqlUpdate);
              pstUpdate.setString(1, maSach);
              int rowsUpdated = pstUpdate.executeUpdate();
              pstUpdate.close();

              if (rowsUpdated > 0) {
                 con.commit();
                 JOptionPane.showMessageDialog(this, "Đăng ký mượn sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                 // Làm sạch form sau khi lưu thành công
                 clearInputFields();
                 // Tải lại bảng lịch sử mượn cho sinh viên đó sau khi lưu thành công
                 // loadBorrowHistoryForStudent(maSV); // Được gọi trong clearInputFields -> lookupStudent
              } else {
                 con.rollback();
                 JOptionPane.showMessageDialog(this, "Cập nhật số lượng sách thất bại. Đã hoàn tác giao dịch mượn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
              }

          } catch (java.text.ParseException e) { // Bắt lỗi định dạng ngày trả
              JOptionPane.showMessageDialog(this, "Ngày trả dự kiến không đúng định dạng (ví dụ:YYYY-MM-DD).", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
          } catch (NumberFormatException e) { // Bắt lỗi định dạng số phí mượn
               JOptionPane.showMessageDialog(this, "Phí mượn không đúng định dạng số.", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
          }
          catch (SQLException ex) {
              Logger.getLogger(DangKyMuonSachForm.class.getName()).log(Level.SEVERE, "Lỗi Transaction Mượn Sách", ex);
              if (con != null) {
                  try {
                      con.rollback();
                  } catch (SQLException rollbackEx) {
                      Logger.getLogger(DangKyMuonSachForm.class.getName()).log(Level.SEVERE, "Lỗi khi rollback transaction", rollbackEx);
                  }
              }
              JOptionPane.showMessageDialog(this, "Lỗi CSDL khi thực hiện giao dịch mượn sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);

          } finally {
              if (con != null) {
                  try {
                      con.setAutoCommit(true);
                      con.close();
                  } catch (SQLException closeEx) {
                      Logger.getLogger(DangKyMuonSachForm.class.getName()).log(Level.SEVERE, "Lỗi khi đóng kết nối", closeEx);
                  }
              }
          }
         // --- KẾT THÚC TRANSACTION ---
    }//GEN-LAST:event_btnLuuMuonActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        HomePage homePage = new HomePage();
        homePage.setVisible(true);
        homePage.setLocationRelativeTo(null);
        this.dispose(); // Đóng form hiện tại
    }//GEN-LAST:event_btnHuyActionPerformed

    private void txtNgayTraDuKienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayTraDuKienActionPerformed
        updateLuuButtonState();
    }//GEN-LAST:event_txtNgayTraDuKienActionPerformed

    private void txtPhiMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhiMuonActionPerformed
        updateLuuButtonState();
    }//GEN-LAST:event_txtPhiMuonActionPerformed

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
            java.util.logging.Logger.getLogger(DangKyMuonSachForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DangKyMuonSachForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DangKyMuonSachForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DangKyMuonSachForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DangKyMuonSachForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLuuMuon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_sachDangMuon;
    private javax.swing.JTextField txtGiaSach;
    private javax.swing.JTextField txtMaSV;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtNgayMuon;
    private javax.swing.JTextField txtNgayTraDuKien;
    private javax.swing.JTextField txtPhiMuon;
    private javax.swing.JTextField txtTacGia;
    private javax.swing.JTextField txtTenSV;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JTextField txtTinhTrangSach;
    // End of variables declaration//GEN-END:variables
}
