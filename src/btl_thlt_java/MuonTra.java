/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package btl_thlt_java;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import javax.swing.JTable;

/**
 *
 * @author Admin
 */
public class MuonTra extends javax.swing.JFrame {
    private boolean isBookAvailable = false;
    private int mouseX, mouseY;
     private int selectedRecordId = -1;

    /**
     * Creates new form HomePage
     */
    public MuonTra() {
        initComponents();
        setLocationRelativeTo(null);
        displayUsername.setText(UserInfo.loggedInUsername);
        btnLuuMuon.setEnabled(false);
        setupTableAppearance(tb_sachDangMuon);
        // Ví dụ: Thiết lập dữ liệu ban đầu cho ComboBox Tình trạng mượn
         setupTinhTrangMuonComboBox();
        // Vô hiệu hóa các nút Cập nhật/Xóa ban đầu nếu chưa chọn dòng
         btnSua.setEnabled(false); // Nút Sửa
         // btnXoa.setEnabled(false); // Nút Xóa nếu có
         btnXacNhanTraSach.setEnabled(false); // Nút Xác nhận trả sách
         // Làm cho các trường hiển thị chi tiết KHÔNG cho phép nhập trực tiếp
          setDetailFieldsEditable(false);
          clearDetailFields();
    }
    private void saveUpdatedBorrowRecord() {
         int selectedRow = tb_qlMuonTraSach.getSelectedRow();
          if (selectedRow == -1) {
              // Nên kiểm tra lại ở các nút gọi phương thức này
              return;
          }

          // Lấy ID bản ghi mượn từ dòng được chọn (GIẢ ĐỊNH BẠN ĐÃ LẤY ID TRONG CÂU TRUY VẤN SELECT VÀ LƯU ĐÂU ĐÓ KHÔNG HIỂN THỊ)
          // Nếu không lấy ID, bạn cần sử dụng các cột kết hợp (maSV, maS, ngayMuon) làm điều kiện WHERE.
          // Lấy giá trị ID (ví dụ từ cột ẩn đầu tiên nếu bạn đã thêm ID vào SELECT)
          // Cách tốt nhất là thêm cột ID (PRIMARY KEY) vào câu SELECT của loadTableData
          // và lưu nó vào Object[] ở cột đầu tiên (chỉ số 0), và ẩn cột này trong Designer
          // Giả định bạn cần lấy ID từ CSDL dựa vào maSV, maS, ngayMuon
//          String maSV_selected = tb_qlMuonTraSach.getValueAt(selectedRow, 0).toString();
//          String maS_selected = tb_qlMuonTraSach.getValueAt(selectedRow, 2).toString();
//          String ngayMuon_selected_str = tb_qlMuonTraSach.getValueAt(selectedRow, 6).toString(); // Ngày mượn từ bảng (định dạng yyyy-MM-dd HH:mm:ss)


          // Lấy thông tin MỚI từ các trường chi tiết trên form
          String newTinhTrangMuon = cbTinhTrangMuon.getSelectedItem().toString();
          String newNgayTraThucTeStr = txtNgayTraThucTe.getText().trim();
          String newPhiMuonStr = txtPhiMuon.getText().trim();


          // TODO: Kiểm tra dữ liệu mới nhập (định dạng ngày trả thực tế, định dạng số phí mượn)
           Date newNgayTraThucTeDate = null;
         if (!newNgayTraThucTeStr.isEmpty()) {
             try {
                 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                 dateFormat.setLenient(false);
                 newNgayTraThucTeDate = dateFormat.parse(newNgayTraThucTeStr);
             } catch (java.text.ParseException e) {
                  JOptionPane.showMessageDialog(this, "Ngày trả thực tế không đúng định dạng (YYYY-MM-DD).", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
                  return;
             }
         }
         double newPhiMuon;
         try {
             newPhiMuon = Double.parseDouble(newPhiMuonStr);
             if (newPhiMuon < 0) {
                 JOptionPane.showMessageDialog(this, "Phí mượn không thể âm.", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
                 return;
             }
         } catch (NumberFormatException e) {
              JOptionPane.showMessageDialog(this, "Phí mượn không đúng định dạng số.", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
              return;
         }


        // --- CÂU LỆNH SQL UPDATE - SỬ DỤNG ID TRONG WHERE ---
        String sqlUpdate = "UPDATE muon_tra_sach SET tinhTrangMuon = ?, ngayTraThucTe = ?, phiMuon = ? " +
                           "WHERE id = ?"; // <<< Sửa điều kiện WHERE // Sử dụng các cột kết hợp làm điều kiện WHERE

          // TODO: NẾU BẢNG muon_tra_sach CÓ CỘT ID VÀ BẠN LẤY ID TRONG SELECT CỦA loadTableData (Ở CỘT 0, ẨN), HÃY DÙNG ID LÀM ĐIỀU KIỆN WHERE để chắc chắn cập nhật đúng bản ghi duy nhất:
          // String sqlUpdate = "UPDATE muon_tra_sach SET tinhTrangMuon = ?, ngayTraThucTe = ?, phimMuon = ? WHERE id = ?";
          // int id_selected = Integer.parseInt(tb_mts.getValueAt(selectedRow, 0).toString()); // Lấy ID từ bảng (thay 0 bằng chỉ số cột của ID)


          Connection con = null;
        try {
            con = KN.KNDL();
            PreparedStatement pstUpdate = con.prepareStatement(sqlUpdate);

            pstUpdate.setString(1, newTinhTrangMuon);
            if (newNgayTraThucTeDate == null) {
                pstUpdate.setNull(2, java.sql.Types.DATE);
            } else {
                pstUpdate.setDate(2, new java.sql.Date(newNgayTraThucTeDate.getTime()));
            }
            pstUpdate.setDouble(3, newPhiMuon);

            // Set tham số cho điều kiện WHERE (ID bản ghi được chọn)
            pstUpdate.setInt(4, selectedRecordId); // <<< Set ID vào tham số thứ 4

            int rowsAffected = pstUpdate.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật bản ghi thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                // Tải lại dữ liệu sau khi cập nhật
                loadTableData(txtMaSinhVien.getText().trim()); // Tải lại với bộ lọc hiện tại
                // Các ô chi tiết vẫn giữ nguyên, hoặc làm sạch tùy ý
            } else {
                 // Trường hợp này ít xảy ra khi dùng ID, trừ khi bản ghi đã bị xóa
                 JOptionPane.showMessageDialog(this, "Không có bản ghi nào được cập nhật. Có thể bản ghi đã bị xóa?", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(QuanLyMuonTraSachForm.class.getName()).log(Level.SEVERE, "Lỗi CSDL khi cập nhật bản ghi", ex);
            JOptionPane.showMessageDialog(this, "Lỗi CSDL khi cập nhật bản ghi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Đóng kết nối (nếu cần quản lý thủ công)
        }
     }
    // Phương thức cho phép/không cho phép chỉnh sửa các trường chi tiết
    
     private void setDetailFieldsEditable(boolean editable) {
         // Chỉ cho phép sửa các trường cần thiết cho việc cập nhật trạng thái/trả sách
         // (Ví dụ: Tình trạng mượn, Ngày trả thực tế, Phí mượn)
         // Các trường khác (Mã SV, Tên SV, Mã sách, Tên sách...) nên là chỉ đọc (false)

         txtMaSinhVien.setEditable(false); // Mã SV (chỉ xem)
         txtTenSinhVien.setEditable(false); // Tên SV (chỉ xem)
         txtMaSach.setEditable(false); // Mã Sách (chỉ xem)
         txtTenSach.setEditable(false); // Tên Sách (chỉ xem)
//         txtTacGia.setEditable(false); // Tác giả (chỉ xem)
         txtTinhTrangSach.setEditable(false); // TT Sách (chỉ xem)
         txtNgayMuon.setEditable(false); // Ngày mượn (chỉ xem)
         txtNgayTraDuKien.setEditable(false); // Ngày trả DK (chỉ xem)

         // Các trường này có thể cho phép sửa khi ở chế độ cập nhật
         txtNgayTraThucTe.setEditable(editable); // Ngày trả TT (cho phép nhập/sửa khi cần)
         txtPhiMuon.setEditable(editable); // Phí mượn (cho phép nhập/sửa khi cần)
         cbTinhTrangMuon.setEnabled(editable); // ComboBox TT mượn (cho phép chọn khi cần)

         // Ban đầu, các trường này nên là false
         if (!editable) {
             txtNgayTraThucTe.setEditable(false);
             txtPhiMuon.setEditable(false);
             cbTinhTrangMuon.setEnabled(false);
             btnXoa.setEnabled(false);
         }
     }
     
     // Phương thức thiết lập dữ liệu cho ComboBox Tình trạng mượn
     private void setupTinhTrangMuonComboBox() {
         cbTinhTrangMuon.removeAllItems(); // Xóa các mục mặc định (nếu có)
         cbTinhTrangMuon.addItem("Đang mượn");
         cbTinhTrangMuon.addItem("Đã trả");
         cbTinhTrangMuon.addItem("Quá hạn");
         // Thêm các trạng thái khác nếu có trong CSDL của bạn
     }
     
     // --- Phương thức tải dữ liệu vào bảng ---
    // Nhận tham số maSVFilter để lọc (null nếu muốn tải tất cả)
    public void loadTableData(String maSVFilter) {
        DefaultTableModel dtm = (DefaultTableModel) tb_qlMuonTraSach.getModel();
        dtm.setRowCount(0); // Xóa tất cả các dòng dữ liệu cũ

        // --- CÂU LỆNH SQL TỔNG HỢP (có JOIN) ---
        // Lấy 11 cột dữ liệu đầy đủ từ 3 bảng (hoặc VIEW nếu bạn đã tạo VIEW)
        String sql = "SELECT " +
                     "mts.id, " +
                     "mts.maSV, sv.ten AS tenSinhVien, " +
                     "mts.maS, qls.tenS AS tenSachValue, qls.tinhtrang AS tinhTrangSachSach, " +
                     "mts.ngayMuon, mts.ngayTraDuKien, mts.ngayTraThucTe, mts.phiMuon, mts.tinhTrangMuon " + // Đã sửa phiMuon
                     "FROM muon_tra_sach mts " +
                     "JOIN ql_sv sv ON mts.maSV = sv.ma " + // JOIN với bảng sinh viên
                     "JOIN ql_sach qls ON mts.maS = qls.maS "; // JOIN với bảng sách

        // Hoặc, nếu bạn đã tạo VIEW tên là vw_lich_su_muon_tra_tong_hop
        // String sql = "SELECT * FROM vw_lich_su_muon_tra_tong_hop";


        // Thêm điều kiện WHERE nếu có Mã SV để tìm kiếm
        if (maSVFilter != null && !maSVFilter.trim().isEmpty()) {
            sql += " WHERE mts.maSV = ?"; // Hoặc " WHERE maSV = ?" nếu dùng VIEW
        }

        sql += " ORDER BY mts.ngayMuon DESC"; // Sắp xếp


        try (Connection con = KN.KNDL();
             PreparedStatement pst = con.prepareStatement(sql)) {

            // Set tham số cho WHERE clause nếu có
            if (maSVFilter != null && !maSVFilter.trim().isEmpty()) {
                pst.setString(1, maSVFilter.trim());
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    // Lấy dữ liệu từ ResultSet theo tên cột/alias trong câu SELECT
                    // Đảm bảo thứ tự các giá trị trong mảng này khớp với thứ tự cột trong JTable Model
                    Object object[] = {
                        rs.getObject("id"),
                        rs.getString("maSV"),           // Cột 1: Mã SV
                        rs.getString("tenSinhVien"),    // Cột 2: Tên SV (alias)
                        rs.getString("maS"),            // Cột 3: Mã Sách
                        rs.getString("tenSachValue"),   // Cột 4: Tên Sách (alias)
                        rs.getString("tinhTrangSachSach"), // Cột 6: TT Sách (alias)
                        rs.getTimestamp("ngayMuon") != null ? new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("ngayMuon")) : "N/A", // Cột 7: Ngày mượn
                        rs.getDate("ngayTraDuKien") != null ? rs.getDate("ngayTraDuKien").toString() : "N/A", // Cột 8: Ngày trả DK
                        rs.getDate("ngayTraThucTe") != null ? rs.getDate("ngayTraThucTe").toString() : "", // Cột 9: Ngày trả TT (Để trống nếu NULL)
                        rs.getString("phiMuon"),        // Cột 10: Phí mượn (Đảm bảo kiểu dữ liệu từ DB)
                        rs.getString("tinhTrangMuon")   // Cột 11: TT mượn
                    };
                    dtm.addRow(object);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(QuanLyMuonTraSachForm.class.getName()).log(Level.SEVERE, "Lỗi khi tải dữ liệu lịch sử mượn", ex);
            JOptionPane.showMessageDialog(this, "Lỗi CSDL khi tải dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Phương thức làm sạch các trường hiển thị chi tiết
     private void clearDetailFields() {
         txtMaSinhVien.setText("");
         txtTenSinhVien.setText("");
         txtMaSach.setText("");
         txtTenSach.setText("");
         txtTinhTrangSach.setText("");
         txtNgayMuon.setText("");
         txtNgayTraDuKien.setText("");
         txtNgayTraThucTe.setText("");
         txtPhiMuon.setText("");
         // cmbTinhTrangMuon.setSelectedItem("Đang mượn"); // Không set nếu không có dòng chọn
          if (cbTinhTrangMuon.getItemCount() > 0) {
              cbTinhTrangMuon.setSelectedIndex(0); // Chọn mục đầu tiên
          }
          txtMaSinhVien.setEditable(true);
     }
     
     // Phương thức xử lý click bảng chính (được gọi bởi tbmouseclick(MouseEvent evt) và các nút điều hướng)
     private void tbmouseclick() { // <<< Phương thức logic chính (không parameter MouseEvent)
        // Đảm bảo tên biến JTable ở đây là tên biến của bạn (tb_qlMuonTraSach)
        int selectedRow = tb_qlMuonTraSach.getSelectedRow(); // <<< Dùng tên biến JTable của bạn
        
        if (selectedRow >= 0) {
            // Lấy ID từ cột đầu tiên (chỉ số 0) và lưu vào biến thành viên
            // Đảm bảo ID trong CSDL là NOT NULL và kiểu int/serial
             Object idObject = tb_qlMuonTraSach.getValueAt(selectedRow, 0);
             if (idObject != null) {
                 selectedRecordId = Integer.parseInt(idObject.toString());
             } else {
                 selectedRecordId = -1; // Hoặc xử lý lỗi nếu ID bị NULL
                 JOptionPane.showMessageDialog(this, "Không lấy được ID bản ghi từ bảng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                 clearDetailFields();
                 setDetailFieldsEditable(false);
                 btnXoa.setEnabled(false);
                 btnSua.setEnabled(false);
                 btnXacNhanTraSach.setEnabled(false);
                 return;
             }

        if (selectedRow >= 0) {
            // Lấy dữ liệu từ dòng được chọn và điền vào các trường hiển thị chi tiết
            // Đảm bảo chỉ số cột (0, 1, 2...) khớp với thứ tự cột trong JTable Model (11 cột)
            txtMaSinhVien.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 1) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 1).toString() : ""); // <<< Dùng tên biến JTable của bạn
            txtTenSinhVien.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 2) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 2).toString() : ""); // <<< Dùng tên biến JTable của bạn
            txtMaSach.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 3) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 3).toString() : ""); // <<< Dùng tên biến JTable của bạn
            txtTenSach.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 4) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 4).toString() : ""); // <<< Dùng tên biến JTable của bạn
            txtTinhTrangSach.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 5) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 5).toString() : ""); // <<< Dùng tên biến JTable của bạn
            txtNgayMuon.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 6) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 6).toString() : ""); // <<< Dùng tên biến JTable của bạn
            txtNgayTraDuKien.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 7) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 7).toString() : ""); // <<< Dùng tên biến JTable của bạn
            txtNgayTraThucTe.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 8) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 8).toString() : ""); // <<< Dùng tên biến JTable của bạn
            txtPhiMuon.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 9) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 9).toString() : ""); // <<< Dùng tên biến JTable của bạn
            // Chọn giá trị cho ComboBox Tình trạng mượn
            String tinhTrang = tb_qlMuonTraSach.getValueAt(selectedRow, 10) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 10).toString() : "Đang mượn"; // <<< Dùng tên biến JTable của bạn
            cbTinhTrangMuon.setSelectedItem(tinhTrang); // <<< Dùng tên biến cmbTinhTrangMuon của bạn


            // Bật các trường chi tiết cho phép sửa (chỉ các trường được phép)
            setDetailFieldsEditable(true); // Gọi phương thức setDetailFieldsEditable
            txtMaSinhVien.setEditable(false); // Ô Mã SV chỉ đọc khi đã chọn dòng (<<< Dùng tên biến txtMaSV của bạn)
            btnXoa.setEnabled(true);
            // Bật nút Cập nhật/Xác nhận/Xóa khi một dòng được chọn
            btnSua.setEnabled(true); // <<< Dùng tên biến btnSua của bạn
            btnXacNhanTraSach.setEnabled(true); // <<< Dùng tên biến btnXacNhanTraSach của bạn
            // btnXoa.setEnabled(true); // Nút Xóa nếu có (Dùng tên biến của bạn)


        } else {
            // Nếu không có dòng nào được chọn, làm sạch các trường chi tiết và vô hiệu hóa nút
            clearDetailFields(); // Gọi phương thức clearDetailFields
            setDetailFieldsEditable(false); // Tắt chế độ sửa (Gọi phương thức của tôi)
            txtMaSinhVien.setEditable(true); // Cho phép nhập Mã SV để tìm kiếm khi không có dòng nào chọn (<<< Dùng tên biến txtMaSV của bạn)
            btnXoa.setEnabled(false); 
            btnSua.setEnabled(false); // <<< Dùng tên biến btnSua của bạn
            btnXacNhanTraSach.setEnabled(false); // <<< Dùng tên biến btnXacNhanTraSach của bạn
            // btnXoa.setEnabled(false); // Nút Xóa nếu có (Dùng tên biến của bạn)
            
            selectedRecordId = -1;
        }
    }}
     
        public static void setupTableAppearance(JTable nameJTable) {
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
        txtNewTenSV.setText(""); // Xóa tên SV cũ
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
                    txtNewTenSV.setText(rs.getString("ten"));
                    // --- GỌI HÀM TẢI LỊCH SỬ MƯỢN SAU KHI TRA CỨU SINH VIÊN THÀNH CÔNG ---
                    loadBorrowHistoryForStudent(maSV);
                    // isBookAvailable không đổi trạng thái ở đây, chỉ liên quan đến sách
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy Sinh Viên có Mã: " + maSV, "Lỗi Tra cứu", JOptionPane.WARNING_MESSAGE);
                    txtNewMaSV.setText("");
                    txtNewTenSV.setText("");
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
        txtNewTenSach.setText("");
        txtNewGiaSach.setText("");
        txtNewTinhTrangSach.setText("");
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
                    txtNewTenSach.setText(rs.getString("tenS"));               
                    txtNewGiaSach.setText(String.valueOf(rs.getInt("gia"))); // Giả định 'gia' là số
                    txtNewTinhTrangSach.setText(rs.getString("tinhtrang"));

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
                    txtNewMaSach.setText("");
                    txtNewTenSach.setText("");
                    txtNewGiaSach.setText("");
                    txtNewTinhTrangSach.setText("");
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

         boolean studentValid = !txtNewMaSV.getText().trim().isEmpty() && !txtNewTenSV.getText().isEmpty();
         boolean bookValidAndAvailable = !txtNewMaSach.getText().trim().isEmpty() && !txtNewTenSach.getText().isEmpty() && isBookAvailable; // Sử dụng biến isBookAvailable
         boolean returnDateEntered = !txtNewNgayTraDuKien.getText().trim().isEmpty();
         boolean phiMuonEntered = !txtNewPhiMuon.getText().trim().isEmpty();

         // TODO: Thêm kiểm tra định dạng ngày cho txtNgayTraDuKien (ví dụ: dùng SimpleDateFormat.parse)
         // TODO: Thêm kiểm tra định dạng số cho txtPhiMuon (ví dụ: dùng Double.parseDouble và bắt NumberFormatException)

         boolean canEnableLuu = studentValid && bookValidAndAvailable && returnDateEntered && phiMuonEntered;

         btnLuuMuon.setEnabled(canEnableLuu);

     }

    // --- Phương thức làm sạch các trường nhập liệu chính (tùy chọn) ---
    // Có thể gọi sau khi lưu thành công hoặc khi muốn reset form
    public void clearInputFields() {
        txtMaSinhVien.setText("");
        txtTenSinhVien.setText("");
        txtMaSach.setText("");
        txtTenSach.setText("");
        txtPhiMuon.setText("");
        txtTinhTrangSach.setText("");
        // Ngày mượn nên giữ lại hoặc set lại ngày hiện tại
        txtNgayMuon.setText("");
        txtNgayTraThucTe.setText("");
        txtNgayTraDuKien.setText("");
        txtPhiMuon.setText("");
        cbTinhTrangMuon.setSelectedItem(null);
        
        txtNewMaSV.setText("");
        txtNewTenSV.setText("");
        txtNewMaSach.setText("");
        txtNewTenSach.setText("");
        txtNewGiaSach.setText("");
        txtNewTinhTrangSach.setText("");
        // Ngày mượn nên giữ lại hoặc set lại ngày hiện tại
        // txtNgayMuon.setText("");
        txtNewNgayTraDuKien.setText("");
        txtNewPhiMuon.setText("");

        // Xóa dữ liệu bảng lịch sử mượn
        DefaultTableModel dtm = (DefaultTableModel) tb_sachDangMuon.getModel();
        dtm.setRowCount(0);

        isBookAvailable = false; // Reset trạng thái sách có sẵn
        updateLuuButtonState();
    }


    // --- Phương thức tải lịch sử mượn cho sinh viên vào JTable ---
    // Phương thức này được gọi sau khi tra cứu sinh viên thành công
    private  void updateQuaHan() {
    try {
        Connection con = KN.KNDL();
        
        String sqlUpdateQuaHan = "UPDATE muon_tra_sach SET tinhTrangMuon = 'Quá hạn' WHERE tinhTrangMuon = 'Đang mượn' AND ngayTraDuKien < CURDATE()";
        PreparedStatement statement = con.prepareStatement(sqlUpdateQuaHan);
        int rowsAffected = statement.executeUpdate();
        
        // Optional: Log or display the number of updated rows
        System.out.println("Đã cập nhật" + rowsAffected + " tình trạng");
        
        // Close resources
        statement.close();
        con.close();
        
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exception appropriately
    }
    
}
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

        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel2 = new javax.swing.JPanel();
        btnReturn = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        signout = new javax.swing.JLabel();
        displayUsername = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        QLMuon = new javax.swing.JLabel();
        DangKyMuon = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNewMaSV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNewTenSV = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_sachDangMuon = new javax.swing.JTable();
        txtNewMaSach = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNewTenSach = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNewGiaSach = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNewTinhTrangSach = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNewNgayMuon = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNewNgayTraDuKien = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNewPhiMuon = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnLuuMuon = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtNgayMuon = new javax.swing.JTextField();
        txtTenSinhVien = new javax.swing.JTextField();
        txtNgayTraDuKien = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_qlMuonTraSach = new javax.swing.JTable();
        cbTinhTrangMuon = new javax.swing.JComboBox<>();
        txtMaSinhVien = new javax.swing.JTextField();
        txtMaSach = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtTinhTrangSach = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtNgayTraThucTe = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtPhiMuon = new javax.swing.JTextField();
        btnXacNhanTraSach = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(251, 249, 228));
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));
        jPanel2.setForeground(new java.awt.Color(0, 51, 102));
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnReturn.setBackground(new java.awt.Color(0, 51, 102));
        btnReturn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/colorful-icons/back.png"))); // NOI18N
        btnReturn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReturn.setOpaque(true);
        btnReturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReturnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReturnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReturnMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnReturnMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnReturnMouseReleased(evt);
            }
        });
        jPanel2.add(btnReturn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 60));

        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 5, 40));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Quản lý mượn trả sách");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, -1, 60));

        close.setBackground(new java.awt.Color(0, 51, 102));
        close.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        close.setForeground(new java.awt.Color(255, 255, 255));
        close.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        close.setText("X");
        close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        close.setOpaque(true);
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeMouseExited(evt);
            }
        });
        jPanel2.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 0, 60, 60));

        signout.setBackground(new java.awt.Color(0, 51, 102));
        signout.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        signout.setForeground(new java.awt.Color(255, 255, 255));
        signout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        signout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/colorful-icons/arrow.png"))); // NOI18N
        signout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signout.setOpaque(true);
        signout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signoutMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                signoutMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                signoutMouseReleased(evt);
            }
        });
        jPanel2.add(signout, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 0, 60, 60));

        displayUsername.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        displayUsername.setForeground(new java.awt.Color(255, 255, 255));
        displayUsername.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/adminIcons/male_user_50px.png"))); // NOI18N
        displayUsername.setText("Username");
        jPanel2.add(displayUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 0, 200, 60));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 60));

        jTabbedPane1.setBackground(new java.awt.Color(251, 249, 228));

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));

        QLMuon.setBackground(new java.awt.Color(255, 255, 255));
        QLMuon.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        QLMuon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        QLMuon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/colorful-icons/manage.png"))); // NOI18N
        QLMuon.setText("Quản lý lịch sử ");
        QLMuon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        QLMuon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        QLMuon.setMaximumSize(new java.awt.Dimension(200, 125));
        QLMuon.setMinimumSize(new java.awt.Dimension(200, 125));
        QLMuon.setOpaque(true);
        QLMuon.setPreferredSize(new java.awt.Dimension(200, 125));
        QLMuon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                QLMuonMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                QLMuonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                QLMuonMouseReleased(evt);
            }
        });

        DangKyMuon.setBackground(new java.awt.Color(255, 255, 255));
        DangKyMuon.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        DangKyMuon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DangKyMuon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/colorful-icons/addfixed.png"))); // NOI18N
        DangKyMuon.setText("Đăng kí mượn sách");
        DangKyMuon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        DangKyMuon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DangKyMuon.setMaximumSize(new java.awt.Dimension(200, 125));
        DangKyMuon.setMinimumSize(new java.awt.Dimension(200, 125));
        DangKyMuon.setOpaque(true);
        DangKyMuon.setPreferredSize(new java.awt.Dimension(200, 125));
        DangKyMuon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DangKyMuonMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DangKyMuonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                DangKyMuonMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(166, Short.MAX_VALUE)
                .addComponent(DangKyMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102)
                .addComponent(QLMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(QLMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DangKyMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(252, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab3", jPanel5);

        jPanel1.setBackground(new java.awt.Color(251, 249, 228));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Mã Sinh Viên");

        txtNewMaSV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtNewMaSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewMaSVActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Tên Sinh Viên");

        txtNewTenSV.setEditable(false);
        txtNewTenSV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtNewTenSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewTenSVActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
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

        txtNewMaSach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtNewMaSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewMaSachActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Tên sách");

        txtNewTenSach.setEditable(false);
        txtNewTenSach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Giá");

        txtNewGiaSach.setEditable(false);
        txtNewGiaSach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Tình trạng sách");

        txtNewTinhTrangSach.setEditable(false);
        txtNewTinhTrangSach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Ngày mượn");

        txtNewNgayMuon.setEditable(false);
        txtNewNgayMuon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtNewNgayMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewNgayMuonActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Ngày trả dự kiến");

        txtNewNgayTraDuKien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtNewNgayTraDuKien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewNgayTraDuKienActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Phí mượn");

        txtNewPhiMuon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtNewPhiMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewPhiMuonActionPerformed(evt);
            }
        });

        jLabel12.setText("(tra sinh viên mượn bằng mã)");

        btnLuuMuon.setBackground(new java.awt.Color(51, 102, 255));
        btnLuuMuon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLuuMuon.setForeground(new java.awt.Color(255, 255, 255));
        btnLuuMuon.setText("Thêm");
        btnLuuMuon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLuuMuon.setPreferredSize(new java.awt.Dimension(100, 27));
        btnLuuMuon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLuuMuonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLuuMuonMouseExited(evt);
            }
        });
        btnLuuMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuMuonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(txtNewTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(159, 159, 159)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNewPhiMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNewNgayTraDuKien, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtNewNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75)
                                .addComponent(btnLuuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(210, 210, 210)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNewMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNewTenSV, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNewMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(159, 159, 159)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNewGiaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNewTinhTrangSach, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtNewMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNewTenSV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtNewGiaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtNewTinhTrangSach, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtNewMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(txtNewNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(btnLuuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNewTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtNewNgayTraDuKien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNewPhiMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jTabbedPane1.addTab("tab1", jPanel1);

        jPanel4.setBackground(new java.awt.Color(251, 249, 228));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Mã sinh viên");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setText("Mã sách");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setText("Ngày mượn");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setText("Tên sinh viên");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setText("Tình trạng mượn");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setText("Ngày trả dự kiến");

        tb_qlMuonTraSach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tb_qlMuonTraSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã sinh viên", "Tên sinh viên", "Mã sách", "Tên sách", "Tình trạng sách", "Ngày mượn", "Ngày trả dự kiến", "Ngày trả thực tế", "Phí mượn", "Tình trạng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_qlMuonTraSach.setRowHeight(30);
        tb_qlMuonTraSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_qlMuonTraSachMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb_qlMuonTraSach);

        cbTinhTrangMuon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đã trả", "Chưa trả" }));
        cbTinhTrangMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTinhTrangMuonActionPerformed(evt);
            }
        });

        txtMaSinhVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSinhVienActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setText("Tên sách");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setText("Tình trạng sách");

        txtTinhTrangSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTinhTrangSachActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setText("Ngày trả thực tế");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setText("Phí mượn");

        txtPhiMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhiMuonActionPerformed(evt);
            }
        });

        btnXacNhanTraSach.setBackground(new java.awt.Color(51, 102, 255));
        btnXacNhanTraSach.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXacNhanTraSach.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhanTraSach.setText("Xác nhận trả");
        btnXacNhanTraSach.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXacNhanTraSach.setPreferredSize(new java.awt.Dimension(100, 27));
        btnXacNhanTraSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnXacNhanTraSachMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnXacNhanTraSachMouseExited(evt);
            }
        });
        btnXacNhanTraSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanTraSachActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(51, 102, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoa.setPreferredSize(new java.awt.Dimension(100, 27));
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnXoaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnXoaMouseExited(evt);
            }
        });
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(51, 102, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLamMoi.setPreferredSize(new java.awt.Dimension(100, 27));
        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLamMoiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLamMoiMouseExited(evt);
            }
        });
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(51, 102, 255));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSua.setPreferredSize(new java.awt.Dimension(100, 27));
        btnSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSuaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSuaMouseExited(evt);
            }
        });
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPhiMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTinhTrangSach, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cbTinhTrangMuon, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtNgayTraDuKien)
                                            .addComponent(txtTenSinhVien)
                                            .addComponent(txtNgayTraThucTe, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
                                .addGap(52, 52, 52)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(btnXacNhanTraSach, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(70, 70, 70)
                                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(70, 70, 70)
                                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(25, 25, 25))))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtMaSach, txtMaSinhVien, txtNgayMuon, txtNgayTraDuKien, txtNgayTraThucTe, txtPhiMuon, txtTenSach, txtTenSinhVien});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(108, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(txtMaSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(txtNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtPhiMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22))
                                .addGap(18, 19, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel20)
                                    .addComponent(txtTinhTrangSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnXacNhanTraSach, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(74, 74, 74))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(192, 192, 192)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(cbTinhTrangMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(txtTenSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(txtNgayTraDuKien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNgayTraThucTe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21))))
                        .addGap(12, 82, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbTinhTrangMuon, txtMaSach, txtMaSinhVien, txtNgayMuon, txtNgayTraDuKien, txtNgayTraThucTe, txtPhiMuon, txtTenSach, txtTenSinhVien, txtTinhTrangSach});

        jTabbedPane1.addTab("tab2", jPanel4);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1200, 680));

        setSize(new java.awt.Dimension(1200, 700));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeMouseClicked

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
            mouseX = evt.getX();
            mouseY = evt.getY();
    }//GEN-LAST:event_jPanel2MousePressed

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
            int x = evt.getXOnScreen();
    int y = evt.getYOnScreen();
    this.setLocation(x - mouseX, y - mouseY);
    }//GEN-LAST:event_jPanel2MouseDragged

    private void btnReturnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReturnMouseClicked
        if(jTabbedPane1.getSelectedIndex() == 0){
        new HomePage().setVisible(true);
        this.dispose();
        } else {
            jTabbedPane1.setSelectedIndex(0);
        }
        clearInputFields();
    }//GEN-LAST:event_btnReturnMouseClicked

    private void btnReturnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReturnMouseEntered
        btnReturn.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_btnReturnMouseEntered

    private void btnReturnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReturnMouseExited
        btnReturn.setBackground(new Color(0,51,102));
    }//GEN-LAST:event_btnReturnMouseExited

    private void closeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseEntered
         close.setBackground(new Color(255,0,0));
    }//GEN-LAST:event_closeMouseEntered

    private void closeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseExited
        close.setBackground(new Color(0,51,102));
    }//GEN-LAST:event_closeMouseExited

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateTimeString = formatter.format(now);
        txtNewNgayMuon.setText(dateTimeString);
                // Tải TOÀN BỘ dữ liệu lịch sử mượn khi form mở
        updateQuaHan();
        loadTableData(null); // Truyền null để tải tất cả
        // Tùy chọn: Thiết lập hiển thị bảng
        setupTableAppearance(tb_qlMuonTraSach); // <<< Gọi phương thức setupTableAppearance
         // Làm sạch các trường chi tiết ban đầu
         clearDetailFields();
         // Vô hiệu hóa các trường chi tiết và nút chức năng ban đầu (trừ ô Mã SV kiêm tìm kiếm)
         setDetailFieldsEditable(false); // Các ô còn lại
         txtMaSinhVien.setEditable(true); // Riêng ô Mã SV cho phép nhập để tìm kiếm
         btnSua.setEnabled(false);
         btnXacNhanTraSach.setEnabled(false);
          btnXoa.setEnabled(false); // Nút Xóa
         
    }//GEN-LAST:event_formWindowActivated

    private void tb_qlMuonTraSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_qlMuonTraSachMouseClicked
        tbmouseclick();
    }//GEN-LAST:event_tb_qlMuonTraSachMouseClicked

    private void cbTinhTrangMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTinhTrangMuonActionPerformed
         // Lấy trạng thái được chọn từ ComboBox
        String selectedStatus = cbTinhTrangMuon.getSelectedItem() != null ? cbTinhTrangMuon.getSelectedItem().toString() : "";
        int selectedRow = tb_qlMuonTraSach.getSelectedRow(); // Lấy dòng đang chọn trong bảng

        // Chỉ xử lý tự động điền ngày nếu có dòng đang chọn VÀ ComboBox đang được phép chỉnh sửa (editable = true)
        if (selectedRow >= 0 && cbTinhTrangMuon.isEnabled()) {
            if ("Đã trả".equals(selectedStatus)) {
                // Lấy giá trị Ngày trả thực tế HIỆN TẠI từ trường nhập liệu chi tiết
                String currentNgayTraThucTe = txtNgayTraThucTe.getText().trim();

                // Chỉ tự động điền ngày hiện tại nếu trường Ngày trả thực tế đang rỗng
                // hoặc nếu giá trị gốc từ CSDL cho dòng này là NULL/trống
                if (currentNgayTraThucTe.isEmpty() || tb_qlMuonTraSach.getValueAt(selectedRow, 8) == null || tb_qlMuonTraSach.getValueAt(selectedRow, 8).toString().isEmpty()) {
                     Date now = new Date();
                     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Chỉ lấy ngày, không lấy giờ
                     txtNgayTraThucTe.setText(dateFormat.format(now));
                }
            } else {
                 // Nếu chọn trạng thái khác (như "Đang mượn", "Quá hạn") và trường Ngày trả thực tế đang hiển thị ngày tự động
                 // Có thể xóa ngày tự động nếu muốn, hoặc giữ nguyên giá trị cũ từ CSDL
                 // Tạm thời không làm gì ở đây, giữ nguyên giá trị trong trường
            }
        }
    }//GEN-LAST:event_cbTinhTrangMuonActionPerformed

    private void txtMaSinhVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSinhVienActionPerformed
        String maSV = txtMaSinhVien.getText().trim();
        loadTableData(maSV); // Tải dữ liệu chỉ cho Mã SV này
         clearDetailFields(); // Làm sạch trường chi tiết sau khi tìm kiếm mới
    }//GEN-LAST:event_txtMaSinhVienActionPerformed

    private void txtNewMaSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewMaSVActionPerformed

        lookupStudent(txtNewMaSV.getText().trim());
    }//GEN-LAST:event_txtNewMaSVActionPerformed

    private void tb_sachDangMuonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_sachDangMuonMouseClicked
        int selectedRow = tb_sachDangMuon.getSelectedRow(); // Lấy chỉ số dòng được chọn

        if (selectedRow == -1) {
            // Không làm gì nếu không có dòng nào được chọn

        }
    }//GEN-LAST:event_tb_sachDangMuonMouseClicked

    private void txtNewMaSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewMaSachActionPerformed

        lookupBook(txtNewMaSach.getText().trim());
    }//GEN-LAST:event_txtNewMaSachActionPerformed

    private void txtNewNgayTraDuKienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewNgayTraDuKienActionPerformed
        updateLuuButtonState();
    }//GEN-LAST:event_txtNewNgayTraDuKienActionPerformed

    private void txtNewPhiMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewPhiMuonActionPerformed
        updateLuuButtonState();
    }//GEN-LAST:event_txtNewPhiMuonActionPerformed

    private void txtNewTenSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewTenSVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNewTenSVActionPerformed

    private void txtNewNgayMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewNgayMuonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNewNgayMuonActionPerformed

    private void txtPhiMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhiMuonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhiMuonActionPerformed

    private void btnXacNhanTraSachMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXacNhanTraSachMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXacNhanTraSachMouseEntered

    private void btnXacNhanTraSachMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXacNhanTraSachMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXacNhanTraSachMouseExited

    private void btnXacNhanTraSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanTraSachActionPerformed
                int selectedRow = tb_qlMuonTraSach.getSelectedRow();
          if (selectedRow == -1) {
              JOptionPane.showMessageDialog(this, "Vui lòng chọn một bản ghi để xác nhận trả sách.", "Thông báo", JOptionPane.WARNING_MESSAGE);
              return;
          }

         // Đặt trạng thái mượn là "Đã trả" trong ComboBox
         cbTinhTrangMuon.setSelectedItem("Đã trả");
         // Logic tự động điền ngày trả thực tế đã có trong cmbTinhTrangMuonActionPerformed,
         // nó sẽ được kích hoạt khi setSelectedItem làm thay đổi giá trị.

         // Hỏi xác nhận trước khi lưu
         int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận đánh dấu bản ghi này là 'Đã trả'?", "Xác nhận Trả sách", JOptionPane.YES_NO_OPTION);
         if (confirm == JOptionPane.YES_OPTION) {
             // Sau khi set trạng thái và ngày (nếu cần) và xác nhận, gọi phương thức lưu/cập nhật
             saveUpdatedBorrowRecord(); // Gọi phương thức lưu thay đổi
         }
    }//GEN-LAST:event_btnXacNhanTraSachActionPerformed

    private void btnXoaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaMouseEntered

    private void btnXoaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaMouseExited

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
int selectedRow = tb_qlMuonTraSach.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Get the ID from the selected row (assuming ID is at column 0)
    // The ID is an int in the database, so we get it as an Integer from the table model.
    Integer recordId = (Integer) tb_qlMuonTraSach.getValueAt(selectedRow, 0); 
    
    // Optional: Also get other details for a more descriptive confirmation message
    String maSV = tb_qlMuonTraSach.getValueAt(selectedRow, 1).toString(); 
    String tenSV = tb_qlMuonTraSach.getValueAt(selectedRow, 2).toString();
    String tenSach = tb_qlMuonTraSach.getValueAt(selectedRow, 4).toString();
    String ngayMuon = tb_qlMuonTraSach.getValueAt(selectedRow, 6).toString();


    int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn xóa bản ghi mượn sách ID: " + recordId + 
            " (SV: " + tenSV + " - Sách: " + tenSach + " - Ngày mượn: " + ngayMuon + ") không?",
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

    if (confirm == JOptionPane.YES_OPTION) {
        String sql = "DELETE FROM muon_tra_sach WHERE id = ?";

        try (Connection con = KN.KNDL();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, recordId); // Set the ID for deletion

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Xóa bản ghi mượn trả thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadTableData(null); // Refresh the table
                clearInputFields(); // Clear related input fields
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy bản ghi để xóa hoặc có lỗi xảy ra.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MuonTra.class.getName()).log(Level.SEVERE, "Lỗi CSDL khi xóa bản ghi mượn trả", ex);
            JOptionPane.showMessageDialog(this, "Lỗi CSDL khi xóa bản ghi: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLamMoiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLamMoiMouseEntered

    private void btnLamMoiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLamMoiMouseExited

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
    // Xóa nội dung trường tìm kiếm Mã SV (kiêm luôn hiển thị)
        txtMaSinhVien.setText(""); // <<< Dùng tên biến trường Mã SV của bạn

        // Tải lại toàn bộ dữ liệu vào bảng (không lọc theo Mã SV nào)
        loadTableData(null); // Gọi phương thức loadTableData với tham số null

        // Làm sạch các trường chi tiết bên dưới
        clearDetailFields(); // Gọi phương thức làm sạch

        // Đảm bảo ô Mã SV cho phép nhập để tìm kiếm
        txtMaSinhVien.setEditable(true); // <<< Dùng tên biến trường Mã SV của bạn

        // Vô hiệu hóa các nút cập nhật/xóa vì không có dòng nào đang được chọn sau khi làm mới
        btnSua.setEnabled(false); // <<< Dùng tên biến nút Sửa của bạn
        btnXacNhanTraSach.setEnabled(false); // <<< Dùng tên biến nút Xác nhận trả sách của bạn
         btnXoa.setEnabled(false); // Nút Xóa nếu có (Dùng tên biến của bạn)

        // Xóa lựa chọn dòng hiện tại trên bảng (nếu có)
         tb_qlMuonTraSach.clearSelection(); // <<< Dùng tên biến JTable của bạn

        //JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnSuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaMouseEntered

    private void btnSuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaMouseExited

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int selectedRow = tb_qlMuonTraSach.getSelectedRow();
          if (selectedRow == -1) {
              JOptionPane.showMessageDialog(this, "Vui lòng chọn một bản ghi để cập nhật.", "Thông báo", JOptionPane.WARNING_MESSAGE);
              return;
          }

         // Hỏi xác nhận trước khi lưu
         int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận lưu các thay đổi?", "Xác nhận Cập nhật", JOptionPane.YES_NO_OPTION);
          if (confirm == JOptionPane.YES_OPTION) {
              // Sử dụng nút Sửa để lưu các thay đổi đã thực hiện ở các trường chi tiết
              // (Ví dụ: sửa phí mượn, sửa ngày trả thực tế nhập tay, sửa trạng thái mượn khác "Đã trả")
              saveUpdatedBorrowRecord(); // Gọi phương thức lưu thay đổi
          }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void txtTinhTrangSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTinhTrangSachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTinhTrangSachActionPerformed

    private void btnLuuMuonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLuuMuonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLuuMuonMouseEntered

    private void btnLuuMuonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLuuMuonMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLuuMuonMouseExited

    private void btnLuuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuMuonActionPerformed
        // TODO: Lấy dữ liệu từ form (đã làm ở đầu phương thức)
          String maSV = txtNewMaSV.getText().trim();
          String maSach = txtNewMaSach.getText().trim();
          String tenSach = txtNewTenSach.getText().trim();
          String ngayMuon = txtNewNgayMuon.getText().trim();
          String ngayTraDuKienStr = txtNewNgayTraDuKien.getText().trim();
          String phiMuonStr = txtNewPhiMuon.getText().trim();

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
              SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
              dateFormat.setLenient(false);
              pstInsert.setString(1, maSV);
              pstInsert.setString(2, maSach);
              pstInsert.setString(3, tenSach);
              Date ngayMuonDate = dateFormat.parse(ngayMuon);
              pstInsert.setDate(4, new java.sql.Date(ngayMuonDate.getTime()));
              // Chuyển String ngày trả dự kiến sang java.sql.Date
               
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

    private void QLMuonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_QLMuonMouseClicked
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_QLMuonMouseClicked

    private void QLMuonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_QLMuonMousePressed
        QLMuon.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_QLMuonMousePressed

    private void QLMuonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_QLMuonMouseReleased
        QLMuon.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_QLMuonMouseReleased

    private void DangKyMuonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DangKyMuonMouseClicked
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_DangKyMuonMouseClicked

    private void DangKyMuonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DangKyMuonMousePressed
        DangKyMuon.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_DangKyMuonMousePressed

    private void DangKyMuonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DangKyMuonMouseReleased
        DangKyMuon.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_DangKyMuonMouseReleased

    private void signoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signoutMouseClicked
        new Login() .setVisible(true);
        this.dispose();
    }//GEN-LAST:event_signoutMouseClicked

    private void btnReturnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReturnMouseReleased
        btnReturn.setBackground(new Color(0,51,102));
    }//GEN-LAST:event_btnReturnMouseReleased

    private void btnReturnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReturnMousePressed
        btnReturn.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnReturnMousePressed

    private void signoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signoutMouseEntered
        signout.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_signoutMouseEntered

    private void signoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signoutMouseExited
        signout.setBackground(new Color(0,51,102));
    }//GEN-LAST:event_signoutMouseExited

    private void signoutMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signoutMousePressed
        signout.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_signoutMousePressed

    private void signoutMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signoutMouseReleased
        signout.setBackground(new Color(0,51,102));
    }//GEN-LAST:event_signoutMouseReleased

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
            java.util.logging.Logger.getLogger(MuonTra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MuonTra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MuonTra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MuonTra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new MuonTra().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DangKyMuon;
    private javax.swing.JLabel QLMuon;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLuuMuon;
    private javax.swing.JLabel btnReturn;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnXacNhanTraSach;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbTinhTrangMuon;
    private javax.swing.JLabel close;
    private javax.swing.JLabel displayUsername;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel signout;
    private javax.swing.JTable tb_qlMuonTraSach;
    private javax.swing.JTable tb_sachDangMuon;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtMaSinhVien;
    private javax.swing.JTextField txtNewGiaSach;
    private javax.swing.JTextField txtNewMaSV;
    private javax.swing.JTextField txtNewMaSach;
    private javax.swing.JTextField txtNewNgayMuon;
    private javax.swing.JTextField txtNewNgayTraDuKien;
    private javax.swing.JTextField txtNewPhiMuon;
    private javax.swing.JTextField txtNewTenSV;
    private javax.swing.JTextField txtNewTenSach;
    private javax.swing.JTextField txtNewTinhTrangSach;
    private javax.swing.JTextField txtNgayMuon;
    private javax.swing.JTextField txtNgayTraDuKien;
    private javax.swing.JTextField txtNgayTraThucTe;
    private javax.swing.JTextField txtPhiMuon;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JTextField txtTenSinhVien;
    private javax.swing.JTextField txtTinhTrangSach;
    // End of variables declaration//GEN-END:variables
}
