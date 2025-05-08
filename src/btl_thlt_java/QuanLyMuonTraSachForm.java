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

/**
 *
 * @author Khanh
 */
public class QuanLyMuonTraSachForm extends javax.swing.JFrame {

    /**
     * Creates new form QuanLyMuonTraSach
     */
    public QuanLyMuonTraSachForm() {
        initComponents();
        setLocationRelativeTo(null);
        
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
                        rs.getString("maSV"),           // Cột 1: Mã SV
                        rs.getString("tenSinhVien"),    // Cột 2: Tên SV (alias)
                        rs.getString("maS"),            // Cột 3: Mã Sách
                        rs.getString("tenSachValue"),   // Cột 4: Tên Sách (alias)
                        rs.getString("tinhTrangSachSach"), // Cột 6: TT Sách (alias)
                        rs.getTimestamp("ngayMuon") != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("ngayMuon")) : "N/A", // Cột 7: Ngày mượn
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
            // Lấy dữ liệu từ dòng được chọn và điền vào các trường hiển thị chi tiết
            // Đảm bảo chỉ số cột (0, 1, 2...) khớp với thứ tự cột trong JTable Model (11 cột)
            txtMaSinhVien.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 0) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 0).toString() : ""); // <<< Dùng tên biến JTable của bạn
            txtTenSinhVien.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 1) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 1).toString() : ""); // <<< Dùng tên biến JTable của bạn
            txtMaSach.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 2) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 2).toString() : ""); // <<< Dùng tên biến JTable của bạn
            txtTenSach.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 3) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 3).toString() : ""); // <<< Dùng tên biến JTable của bạn
            txtTinhTrangSach.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 4) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 4).toString() : ""); // <<< Dùng tên biến JTable của bạn
            txtNgayMuon.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 5) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 5).toString() : ""); // <<< Dùng tên biến JTable của bạn
            txtNgayTraDuKien.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 6) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 6).toString() : ""); // <<< Dùng tên biến JTable của bạn
            txtNgayTraThucTe.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 7) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 7).toString() : ""); // <<< Dùng tên biến JTable của bạn
            txtPhiMuon.setText(tb_qlMuonTraSach.getValueAt(selectedRow, 8) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 8).toString() : ""); // <<< Dùng tên biến JTable của bạn
            // Chọn giá trị cho ComboBox Tình trạng mượn
            String tinhTrang = tb_qlMuonTraSach.getValueAt(selectedRow, 9) != null ? tb_qlMuonTraSach.getValueAt(selectedRow, 9).toString() : "Đang mượn"; // <<< Dùng tên biến JTable của bạn
            cbTinhTrangMuon.setSelectedItem(tinhTrang); // <<< Dùng tên biến cmbTinhTrangMuon của bạn


            // Bật các trường chi tiết cho phép sửa (chỉ các trường được phép)
            setDetailFieldsEditable(true); // Gọi phương thức setDetailFieldsEditable
            txtMaSinhVien.setEditable(false); // Ô Mã SV chỉ đọc khi đã chọn dòng (<<< Dùng tên biến txtMaSV của bạn)

            // Bật nút Cập nhật/Xác nhận/Xóa khi một dòng được chọn
            btnSua.setEnabled(true); // <<< Dùng tên biến btnSua của bạn
            btnXacNhanTraSach.setEnabled(true); // <<< Dùng tên biến btnXacNhanTraSach của bạn
            // btnXoa.setEnabled(true); // Nút Xóa nếu có (Dùng tên biến của bạn)


        } else {
            // Nếu không có dòng nào được chọn, làm sạch các trường chi tiết và vô hiệu hóa nút
            clearDetailFields(); // Gọi phương thức clearDetailFields
            setDetailFieldsEditable(false); // Tắt chế độ sửa (Gọi phương thức của tôi)
            txtMaSinhVien.setEditable(true); // Cho phép nhập Mã SV để tìm kiếm khi không có dòng nào chọn (<<< Dùng tên biến txtMaSV của bạn)

            btnSua.setEnabled(false); // <<< Dùng tên biến btnSua của bạn
            btnXacNhanTraSach.setEnabled(false); // <<< Dùng tên biến btnXacNhanTraSach của bạn
            // btnXoa.setEnabled(false); // Nút Xóa nếu có (Dùng tên biến của bạn)
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNgayMuon = new javax.swing.JTextField();
        txtTenSinhVien = new javax.swing.JTextField();
        txtNgayTraDuKien = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_qlMuonTraSach = new javax.swing.JTable();
        cbTinhTrangMuon = new javax.swing.JComboBox<>();
        btnXacNhanTraSach = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btn_dau = new javax.swing.JButton();
        btn_truoc = new javax.swing.JButton();
        btn_sau = new javax.swing.JButton();
        btn_cuoi = new javax.swing.JButton();
        txtMaSinhVien = new javax.swing.JTextField();
        txtMaSach = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTinhTrangSach = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNgayTraThucTe = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtPhiMuon = new javax.swing.JTextField();
        btnLamMoi = new javax.swing.JButton();
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
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Quản lý mượn trả sách", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 500));

        jLabel1.setText("Mã sinh viên");

        jLabel2.setText("Mã sách");

        jLabel3.setText("Ngày mượn");

        jLabel4.setText("Tên sinh viên");

        jLabel5.setText("Tình trạng mượn");

        jLabel7.setText("Ngày trả dự kiến");

        tb_qlMuonTraSach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tb_qlMuonTraSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sinh viên", "Tên sinh viên", "Mã sách", "Tên sách", "Tình trạng sách", "Ngày mượn", "Ngày trả dự kiến", "Ngày trả thực tế", "Phí mượn", "Tình trạng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
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
        jScrollPane1.setViewportView(tb_qlMuonTraSach);

        cbTinhTrangMuon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đã trả", "Chưa trả" }));
        cbTinhTrangMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTinhTrangMuonActionPerformed(evt);
            }
        });

        btnXacNhanTraSach.setText("Xác nhận trả sách");
        btnXacNhanTraSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanTraSachActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        jButton4.setText("Thoát");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
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

        txtMaSinhVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSinhVienActionPerformed(evt);
            }
        });

        jLabel6.setText("Tên sách");

        jLabel9.setText("Tình trạng sách");

        jLabel10.setText("Ngày trả thực tế");

        jLabel11.setText("Phí mượn");

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXacNhanTraSach)
                .addGap(165, 165, 165))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_dau, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(btn_truoc, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(78, 78, 78)
                                .addComponent(btn_sau, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMaSach)
                                    .addComponent(txtMaSinhVien)
                                    .addComponent(txtNgayMuon)
                                    .addComponent(txtPhiMuon, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(68, 68, 68)
                                                .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                                                .addComponent(btnLamMoi)
                                                .addGap(86, 86, 86)
                                                .addComponent(btnSua)
                                                .addGap(60, 60, 60))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTinhTrangSach, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbTinhTrangMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addContainerGap(69, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btn_cuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNgayTraDuKien)
                                    .addComponent(txtTenSinhVien)
                                    .addComponent(txtNgayTraThucTe, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                                .addContainerGap())))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(txtTenSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtTinhTrangSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(txtNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayTraDuKien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(cbTinhTrangMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtNgayTraThucTe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtPhiMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSua)
                    .addComponent(jButton4)
                    .addComponent(btnLamMoi))
                .addGap(29, 29, 29)
                .addComponent(btnXacNhanTraSach)
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_dau)
                    .addComponent(btn_truoc)
                    .addComponent(btn_sau)
                    .addComponent(btn_cuoi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1175, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menu_qlsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_qlsvActionPerformed
        
    }//GEN-LAST:event_menu_qlsvActionPerformed

    private void menu_qlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_qlsActionPerformed
        
    }//GEN-LAST:event_menu_qlsActionPerformed

    private void menu_qlmuontraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_qlmuontraActionPerformed
        
    }//GEN-LAST:event_menu_qlmuontraActionPerformed

    private void menu_qlnvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_qlnvActionPerformed
        
    }//GEN-LAST:event_menu_qlnvActionPerformed

    private void menu_tracuusachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_tracuusachActionPerformed
        
    }//GEN-LAST:event_menu_tracuusachActionPerformed

    private void menu_tksvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_tksvActionPerformed
        
    }//GEN-LAST:event_menu_tksvActionPerformed

    private void menu_dangnhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_dangnhapActionPerformed
        
    }//GEN-LAST:event_menu_dangnhapActionPerformed

    private void menu_dangkyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_dangkyActionPerformed
        
    }//GEN-LAST:event_menu_dangkyActionPerformed

    private void btn_dauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dauActionPerformed
        if (tb_qlMuonTraSach.getRowCount() > 0) { tb_qlMuonTraSach.setRowSelectionInterval(0, 0); tbmouseclick(); }
    }//GEN-LAST:event_btn_dauActionPerformed

    private void btn_truocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_truocActionPerformed
        int n = tb_qlMuonTraSach.getSelectedRow(); // <<< Dùng tên biến JTable của bạn
         if (tb_qlMuonTraSach.getRowCount() > 0) { // <<< Dùng tên biến JTable của bạn
             if (n > 0) { tb_qlMuonTraSach.setRowSelectionInterval(n - 1, n - 1); } // <<< Dùng tên biến JTable của bạn
             else { tb_qlMuonTraSach.setRowSelectionInterval(tb_qlMuonTraSach.getRowCount() - 1, tb_qlMuonTraSach.getRowCount() - 1); } // <<< Dùng tên biến JTable của bạn
             tbmouseclick(); // <<< Gọi tbmouseclick()
         }
    }//GEN-LAST:event_btn_truocActionPerformed

    private void btn_sauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sauActionPerformed
        int n = tb_qlMuonTraSach.getSelectedRow(); // <<< Dùng tên biến JTable của bạn
          if (tb_qlMuonTraSach.getRowCount() > 0) { // <<< Dùng tên biến JTable của bạn
              if (n < tb_qlMuonTraSach.getRowCount() - 1) { tb_qlMuonTraSach.setRowSelectionInterval(n + 1, n + 1); } // <<< Dùng tên biến JTable của bạn
              else { tb_qlMuonTraSach.setRowSelectionInterval(0, 0); } // <<< Dùng tên biến JTable của bạn
              tbmouseclick(); // <<< Gọi tbmouseclick()
          }
    }//GEN-LAST:event_btn_sauActionPerformed

    private void btn_cuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cuoiActionPerformed
        if (tb_qlMuonTraSach.getRowCount() > 0) { tb_qlMuonTraSach.setRowSelectionInterval(tb_qlMuonTraSach.getRowCount() - 1, tb_qlMuonTraSach.getRowCount() - 1); tbmouseclick(); }
    }//GEN-LAST:event_btn_cuoiActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // Tải TOÀN BỘ dữ liệu lịch sử mượn khi form mở
        loadTableData(null); // Truyền null để tải tất cả
        // Tùy chọn: Thiết lập hiển thị bảng
        setupTableAppearance(); // <<< Gọi phương thức setupTableAppearance
         // Làm sạch các trường chi tiết ban đầu
         clearDetailFields();
         // Vô hiệu hóa các trường chi tiết và nút chức năng ban đầu (trừ ô Mã SV kiêm tìm kiếm)
         setDetailFieldsEditable(false); // Các ô còn lại
         txtMaSinhVien.setEditable(true); // Riêng ô Mã SV cho phép nhập để tìm kiếm

         btnSua.setEnabled(false);
         btnXacNhanTraSach.setEnabled(false);
         // btnXoa.setEnabled(false); // Nút Xóa
    }//GEN-LAST:event_formWindowOpened

    private void tb_qlMuonTraSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_qlMuonTraSachMouseClicked
        tbmouseclick();
    }//GEN-LAST:event_tb_qlMuonTraSachMouseClicked

    private void txtMaSinhVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSinhVienActionPerformed
        String maSV = txtMaSinhVien.getText().trim();
        loadTableData(maSV); // Tải dữ liệu chỉ cho Mã SV này
         clearDetailFields(); // Làm sạch trường chi tiết sau khi tìm kiếm mới
         // setDetailFieldsEditable(false); // Đã gọi trong clearDetailFields
         // btnSua.setEnabled(false); // Đã gọi trong clearDetailFields
         // btnXacNhanTraSach.setEnabled(false); // Đã gọi trong clearDetailFields
    }//GEN-LAST:event_txtMaSinhVienActionPerformed

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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // Hỏi xác nhận trước khi thoát
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn thoát và quay về Trang chủ?", "Xác nhận Thoát", JOptionPane.YES_NO_OPTION);

        // Nếu người dùng chọn Có (YES)
        if (confirm == JOptionPane.YES_OPTION) {
            // TODO: Đảm bảo class HomePage tồn tại và có thể truy cập được
            // Tạo một instance mới của form HomePage
            HomePage homePage = new HomePage(); // <<< Tạo đối tượng form HomePage của bạn

            // Hiển thị form HomePage
            homePage.setVisible(true); // <<< Hiển thị form HomePage

            // Đóng form QuanLyMuonTraSachForm hiện tại
            this.dispose(); // <<< Đóng form hiện tại
        }
    }//GEN-LAST:event_jButton4ActionPerformed

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
        // btnXoa.setEnabled(false); // Nút Xóa nếu có (Dùng tên biến của bạn)

        // Xóa lựa chọn dòng hiện tại trên bảng (nếu có)
         tb_qlMuonTraSach.clearSelection(); // <<< Dùng tên biến JTable của bạn

        //JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnLamMoiActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLyMuonTraSachForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyMuonTraSachForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyMuonTraSachForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyMuonTraSachForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyMuonTraSachForm().setVisible(true);
            }
        });
    }
    
    // --- Phương thức LƯU CẬP NHẬT bản ghi mượn vào CSDL ---
     // Phương thức này sẽ được gọi bởi btnSua và btnXacNhanTraSach
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
          String maSV_selected = tb_qlMuonTraSach.getValueAt(selectedRow, 0).toString();
          String maS_selected = tb_qlMuonTraSach.getValueAt(selectedRow, 2).toString();
          String ngayMuon_selected_str = tb_qlMuonTraSach.getValueAt(selectedRow, 6).toString(); // Ngày mượn từ bảng (định dạng yyyy-MM-dd HH:mm:ss)


          // Lấy thông tin MỚI từ các trường chi tiết trên form
          String newTinhTrangMuon = cbTinhTrangMuon.getSelectedItem().toString();
          String newNgayTraThucTeStr = txtNgayTraThucTe.getText().trim();
          String newPhiMuonStr = txtPhiMuon.getText().trim();


          // TODO: Kiểm tra dữ liệu mới nhập (định dạng ngày trả thực tế, định dạng số phí mượn)
           Date newNgayTraThucTeDate = null;
           if (!newNgayTraThucTeStr.isEmpty()) {
               try {
                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                   dateFormat.setLenient(false); // Kiểm tra định dạng nghiêm ngặt
                   newNgayTraThucTeDate = dateFormat.parse(newNgayTraThucTeStr);
               } catch (java.text.ParseException e) {
                    JOptionPane.showMessageDialog(this, "Ngày trả thực tế không đúng định dạng (YYYY-MM-DD).", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
                    return; // Dừng nếu định dạng sai
               }
           }
           double newPhiMuon;
           try {
               newPhiMuon = Double.parseDouble(newPhiMuonStr);
               if (newPhiMuon < 0) {
                   JOptionPane.showMessageDialog(this, "Phí mượn không thể âm.", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
                   return; // Dừng nếu phí âm
               }
           } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Phí mượn không đúng định dạng số.", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
                return; // Dừng nếu phí không phải số
           }


          // --- CÂU LỆNH SQL UPDATE ---
          // Cập nhật các cột có thể thay đổi: tinhTrangMuon, ngayTraThucTe, phimMuon
          // Điều kiện WHERE để xác định bản ghi cần cập nhật (sử dụng các cột kết hợp)
          String sqlUpdate = "UPDATE muon_tra_sach SET tinhTrangMuon = ?, ngayTraThucTe = ?, phiMuon = ? " +
                             "WHERE maSV = ? AND maS = ? AND ngayMuon = ?"; // Sử dụng các cột kết hợp làm điều kiện WHERE

          // TODO: NẾU BẢNG muon_tra_sach CÓ CỘT ID VÀ BẠN LẤY ID TRONG SELECT CỦA loadTableData (Ở CỘT 0, ẨN), HÃY DÙNG ID LÀM ĐIỀU KIỆN WHERE để chắc chắn cập nhật đúng bản ghi duy nhất:
          // String sqlUpdate = "UPDATE muon_tra_sach SET tinhTrangMuon = ?, ngayTraThucTe = ?, phimMuon = ? WHERE id = ?";
          // int id_selected = Integer.parseInt(tb_mts.getValueAt(selectedRow, 0).toString()); // Lấy ID từ bảng (thay 0 bằng chỉ số cột của ID)


          Connection con = null;
          try {
              con = KN.KNDL();
              PreparedStatement pstUpdate = con.prepareStatement(sqlUpdate);

              pstUpdate.setString(1, newTinhTrangMuon);
              // Set ngày trả thực tế (NULL nếu ô nhập rỗng, hoặc giá trị Date nếu đã nhập/tự điền)
              if (newNgayTraThucTeDate == null) {
                  pstUpdate.setNull(2, java.sql.Types.DATE);
              } else {
                  pstUpdate.setDate(2, new java.sql.Date(newNgayTraThucTeDate.getTime()));
              }
              pstUpdate.setDouble(3, newPhiMuon); // Set phí mượn mới


              // Set điều kiện WHERE (Nếu dùng maSV, maS, ngayMuon)
              pstUpdate.setString(4, maSV_selected);
              pstUpdate.setString(5, maS_selected);
              // Chuyển String ngày mượn từ bảng sang Timestamp để dùng trong WHERE
              SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Định dạng phải khớp với cách lấy từ DB trong loadTableData
              Date ngayMuon_selected_date = dateTimeFormat.parse(ngayMuon_selected_str);
              pstUpdate.setTimestamp(6, new java.sql.Timestamp(ngayMuon_selected_date.getTime()));
              
              // Hoặc nếu dùng ID:
              // pstUpdate.setInt(4, id_selected); // Chỉ số tham số sẽ khác


              int rowsAffected = pstUpdate.executeUpdate();

              if (rowsAffected > 0) {
                  JOptionPane.showMessageDialog(this, "Cập nhật bản ghi thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                  // Tải lại dữ liệu sau khi cập nhật để bảng hiển thị thông tin mới
                  loadTableData(txtMaSinhVien.getText().trim()); // Tải lại với bộ lọc hiện tại (là Mã SV đang nhập)
                  // Không làm sạch trường chi tiết hay vô hiệu hóa sau khi cập nhật nếu người dùng muốn sửa tiếp hoặc xem lại
                  // clearDetailFields();
                  // setDetailFieldsEditable(false);
                  // btnSua.setEnabled(false);
                  // btnXacNhanTraSach.setEnabled(false);

              } else {
                   JOptionPane.showMessageDialog(this, "Không có bản ghi nào được cập nhật. Có thể không tìm thấy bản ghi phù hợp?", "Thông báo", JOptionPane.WARNING_MESSAGE);
              }

          } catch (java.text.ParseException e) {
              Logger.getLogger(QuanLyMuonTraSachForm.class.getName()).log(Level.SEVERE, "Lỗi parse ngày mượn khi cập nhật", e);
              JOptionPane.showMessageDialog(this, "Lỗi chuyển đổi định dạng ngày mượn từ bảng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
          }
          catch (SQLException ex) {
              Logger.getLogger(QuanLyMuonTraSachForm.class.getName()).log(Level.SEVERE, "Lỗi CSDL khi cập nhật bản ghi", ex);
              JOptionPane.showMessageDialog(this, "Lỗi CSDL khi cập nhật bản ghi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
          } finally {
              // Đóng kết nối (nếu cần quản lý thủ công)
          }
     }
     
     private void setupTableAppearance() {
         // Đảm bảo tên biến JTable ở đây là tên biến của bạn (tb_qlMuonTraSach)
         if (tb_qlMuonTraSach == null) return; // Thoát nếu bảng chưa được khởi tạo

         JTableHeader header = tb_qlMuonTraSach.getTableHeader(); // <<< Dùng tên biến JTable của bạn
         header.setBackground(new Color(70, 130, 180)); // Steel blue
         header.setForeground(Color.WHITE);
         header.setFont(new Font("Segoe UI", Font.BOLD, 14));
         tb_qlMuonTraSach.setSelectionBackground(new Color(135, 206, 250)); // Light sky blue <<< Dùng tên biến JTable của bạn
         tb_qlMuonTraSach.setSelectionForeground(Color.BLACK);
         tb_qlMuonTraSach.setShowGrid(true);
         tb_qlMuonTraSach.setGridColor(new Color(200, 200, 200));
         tb_qlMuonTraSach.setShowHorizontalLines(true);
         tb_qlMuonTraSach.setShowVerticalLines(true);
         tb_qlMuonTraSach.setRowHeight(30); // Chiều cao dòng
         // Tùy chọn: Thiết lập chiều rộng cột
         // tb_qlMuonTraSach.getColumnModel().getColumn(0).setPreferredWidth(50); // Mã SV <<< Dùng tên biến JTable của bạn
         // ...
     }
     
     
     
     
  
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnXacNhanTraSach;
    private javax.swing.JButton btn_cuoi;
    private javax.swing.JButton btn_dau;
    private javax.swing.JButton btn_sau;
    private javax.swing.JButton btn_truoc;
    private javax.swing.JComboBox<String> cbTinhTrangMuon;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JTable tb_qlMuonTraSach;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtMaSinhVien;
    private javax.swing.JTextField txtNgayMuon;
    private javax.swing.JTextField txtNgayTraDuKien;
    private javax.swing.JTextField txtNgayTraThucTe;
    private javax.swing.JTextField txtPhiMuon;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JTextField txtTenSinhVien;
    private javax.swing.JTextField txtTinhTrangSach;
    // End of variables declaration//GEN-END:variables
}
