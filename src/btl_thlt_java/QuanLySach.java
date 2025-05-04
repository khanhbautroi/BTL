/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package btl_thlt_java;

<<<<<<< HEAD
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
=======
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
>>>>>>> 98a313b (toi day)

/**
 *
 * @author Khanh
 */
public class QuanLySach extends javax.swing.JFrame {

    /**
     * Creates new form QuanLySach
     */
    public QuanLySach() {
        initComponents();
    }
    
<<<<<<< HEAD
    public void ht() throws SQLException{
        try {
            Connection kn = KN.KNDL();
            Statement stm = kn.createStatement();
            String sql = "select * from ql_sach";
            ResultSet rs = stm.executeQuery(sql);
            DefaultTableModel dtm = (DefaultTableModel) tb_qlsach.getModel();
            dtm.setRowCount(0);
            
            while(rs.next()){
                Object object[]={
=======
    public void loadTableData() { // Bỏ throws SQLException, xử lý lỗi bên trong
        // Câu lệnh SQL lấy tất cả dữ liệu sách
        String sql = "SELECT maS, tenS, tg, namXB, gia, sl, maNXB, ngonngu, tinhtrang FROM ql_sach"; // Chọn cột tường minh

        // Sử dụng try-with-resources để đảm bảo kết nối, statement, result set được đóng an toàn
        try (Connection con = KN.KNDL(); // Lấy kết nối từ class KN
             // Statement stm = con.createStatement(); // Dùng Statement vì không có tham số
             PreparedStatement pst = con.prepareStatement(sql); // Có thể dùng PreparedStatement cũng được
             ResultSet rs = pst.executeQuery()) { // Thực thi truy vấn và lấy kết quả

            // Lấy DefaultTableModel hiện tại của bảng
            DefaultTableModel dtm = (DefaultTableModel) tb_qlsach.getModel();
            dtm.setRowCount(0); // Xóa tất cả các dòng dữ liệu cũ

            // Duyệt qua các dòng kết quả từ CSDL và thêm vào model
            while (rs.next()) {
                Object object[] = {
>>>>>>> 98a313b (toi day)
                    rs.getString("maS"),
                    rs.getString("tenS"),
                    rs.getString("tg"),
                    rs.getString("namXB"),
<<<<<<< HEAD
                    rs.getInt("gia"),   
                    rs.getString("sl"),
                    rs.getString("maNXB"),
                    rs.getString("ngonngu"),
                    rs.getString("tinhtrang")
                    
            };
                dtm.addRow(object);
                tb_qlsach.setModel(dtm);
            }
        }catch (SQLException ex) {
            Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void them() throws SQLException{
        String ma = txt_maS.getText();
        String ten = txt_tenS.getText();
        String tg = txt_tg.getText();
        String namXB = txt_namXB.getText();
        String gia = txt_gia.getText();
        String sl = txt_sl.getText();
        String maNXB = txt_maNXB.getText();
        String ngonngu = txt_nn.getText();
        String tinhtrang = txt_tt.getText();
        try{
            Connection kn = KN.KNDL();
            String sqlthem = "insert into ql_sach values('"+ma+"', '"+ten+"', '"+tg+"', '"+namXB+"', '"+gia+"', '"+sl+"', '"+maNXB+"', '"+ngonngu+"', '"+tinhtrang+"')";
            Statement stm = kn.createStatement();
            stm.executeUpdate(sqlthem);
            String sql = "select * from ql_sach";
            ResultSet rs = stm.executeQuery(sql);
            DefaultTableModel dtm = (DefaultTableModel) tb_qlsach.getModel();
            dtm.setRowCount(0);
            
            while(rs.next()){
                Object object[]={
                    rs.getString("maS"),
                    rs.getString("tenS"),
                    rs.getString("tg"),
                    rs.getString("namXB"),
                    rs.getInt("gia"),   
                    rs.getString("sl"),
=======
                    rs.getInt("gia"),      // Lấy giá trị kiểu INT từ cột 'gia'
                    rs.getInt("sl"),       // Lấy giá trị kiểu INT từ cột 'sl' (giả định là INT)
>>>>>>> 98a313b (toi day)
                    rs.getString("maNXB"),
                    rs.getString("ngonngu"),
                    rs.getString("tinhtrang")
                };
<<<<<<< HEAD
                dtm.addRow(object);
                tb_qlsach.setModel(dtm);
            }
            
        }catch (SQLException ex) {
            Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sua() throws SQLException{
        String ma = txt_maS.getText();
        String ten = txt_tenS.getText();
        String tg = txt_tg.getText();
        String namXB = txt_namXB.getText();
        String gia = txt_gia.getText();
        String sl = txt_sl.getText();
        String maNXB = txt_maNXB.getText();
        String ngonngu = txt_nn.getText();
        String tinhtrang = txt_tt.getText();
            try{
                Connection kn = KN.KNDL();
                String sqlsua = "update ql_sach set tenS ='"+ten+"',tg ='"+tg+"',NamXB ='"+namXB+"', gia ='"+gia+"', sl ='"+sl+"', maNXB ='"+maNXB+"', ngonngu ='"+ngonngu+"', tinhtrang ='"+tinhtrang+"'where maS='"+ma+"'";
                Statement stm = kn.createStatement();
                stm.executeUpdate(sqlsua);
                String sql = "select * from ql_sach";
                ResultSet rs = stm.executeQuery(sql);
                DefaultTableModel dtm = (DefaultTableModel) tb_qlsach.getModel();
                dtm.setRowCount(0);
                while(rs.next()){
                    Object object[]={
                        rs.getString("maS"),
                        rs.getString("tenS"),
                        rs.getString("tg"),
                        rs.getString("namXB"),
                        rs.getInt("gia"),   
                        rs.getString("sl"),
                        rs.getString("maNXB"),
                        rs.getString("ngonngu"),
                        rs.getString("tinhtrang")
                    };
                dtm.addRow(object);
                tb_qlsach.setModel(dtm);
                }
            
            }catch (SQLException ex) {
            Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void xoa(){
            int row = tb_qlsach.getSelectedRow();
            String macanxoa = txt_maS.getText();
            try{
                Connection kn = KN.KNDL();
                String sql = "delete from ql_sach where maS ='"+macanxoa+"' ";
                Statement stm = kn.createStatement();
                int rowsUpdate = stm.executeUpdate(sql);
                DefaultTableModel dtm = (DefaultTableModel) tb_qlsach.getModel();
                dtm.removeRow(row);
            } catch (SQLException ex) {
                Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    public void tbmouseClick() throws SQLException{
        int row = tb_qlsach.getSelectedRow(); 
        
            String ma = tb_qlsach.getValueAt(row, 0).toString();
            String ten = tb_qlsach.getValueAt(row, 1).toString();
            String tg = tb_qlsach.getValueAt(row, 2).toString();
            String namXB = tb_qlsach.getValueAt(row, 3).toString();
            String gia= tb_qlsach.getValueAt(row, 4).toString();
            String sl = tb_qlsach.getValueAt(row, 5).toString();
            String maNXB= tb_qlsach.getValueAt(row, 6).toString();
            String ngonngu = tb_qlsach.getValueAt(row, 7).toString();
            String tinhtrang= tb_qlsach.getValueAt(row, 8).toString();
            
            txt_maS.setText(ma);
            txt_tenS.setText(ten);
            txt_tg.setText(tg);
            txt_namXB.setText(namXB);
            txt_gia.setText(gia);
            txt_sl.setText(sl);
            txt_maNXB.setText(maNXB);
            txt_nn.setText(ngonngu);
            txt_tt.setText(tinhtrang);
=======
                dtm.addRow(object); // Thêm dòng mới vào model
            }

            // Gán model đã cập nhật lại cho bảng (chỉ cần 1 lần sau khi thêm tất cả dòng)
            // tb_qlsach.setModel(dtm); // Dòng này không cần thiết nếu đã getModel() và thao tác trực tiếp trên dtm

        } catch (SQLException ex) {
            // Bắt và xử lý lỗi CSDL
            Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex); // Ghi log chi tiết lỗi
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu sách: " + ex.getMessage(),
                    "Lỗi CSDL",
                    JOptionPane.ERROR_MESSAGE); // Hiển thị thông báo lỗi cho người dùng
        }
    }
    
    public void them() {
        // Lấy dữ liệu từ các trường nhập liệu
        String ma = txt_maS.getText().trim(); // Dùng trim() để loại bỏ khoảng trắng thừa
        String ten = txt_tenS.getText().trim();
        String tg = txt_tg.getText().trim();
        String namXB = txt_namXB.getText().trim();
        String giaStr = txt_gia.getText().trim(); // Lấy dưới dạng String để kiểm tra
        String slStr = txt_sl.getText().trim();   // Lấy dưới dạng String để kiểm tra
        String maNXB = txt_maNXB.getText().trim();
        String ngonngu = txt_nn.getText().trim();
        String tinhtrang = txt_tt.getText().trim();

        // TODO: Thêm kiểm tra dữ liệu nhập (rỗng, định dạng số cho giá/số lượng...)
        if (ma.isEmpty() || ten.isEmpty() || tg.isEmpty() || namXB.isEmpty() || giaStr.isEmpty() || slStr.isEmpty() || maNXB.isEmpty() || ngonngu.isEmpty() || tinhtrang.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sách.", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
             return;
        }
         int gia, sl;
         try {
             gia = Integer.parseInt(giaStr);
             sl = Integer.parseInt(slStr);
         } catch (NumberFormatException e) {
             JOptionPane.showMessageDialog(this, "Giá và Số lượng phải là số nguyên hợp lệ.", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
             return;
         }

        // Câu lệnh SQL INSERT sử dụng PreparedStatement để chống SQL Injection
        String sqlthem = "INSERT INTO ql_sach (maS, tenS, tg, namXB, gia, sl, maNXB, ngonngu, tinhtrang) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = KN.KNDL(); // Lấy kết nối từ KN
             PreparedStatement pst = con.prepareStatement(sqlthem)) { // Tạo PreparedStatement

            // Gán giá trị cho các tham số (?)
            pst.setString(1, ma);
            pst.setString(2, ten);
            pst.setString(3, tg);
            pst.setString(4, namXB);
            pst.setInt(5, gia); // Gán kiểu INT
            pst.setInt(6, sl);   // Gán kiểu INT
            pst.setString(7, maNXB);
            pst.setString(8, ngonngu);
            pst.setString(9, tinhtrang);

            int rowsAffected = pst.executeUpdate(); // Thực thi lệnh INSERT

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Thêm sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                // Sau khi thêm thành công, tải lại dữ liệu vào bảng
                loadTableData();
                // Xóa nội dung các trường nhập liệu
                clearInputFields(); // Gọi phương thức làm mới
            } else {
                // Trường hợp này ít xảy ra nếu không có lỗi CSDL ngoại trừ lỗi ràng buộc
                 JOptionPane.showMessageDialog(this, "Thêm sách thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
            // Xử lý các lỗi CSDL (ví dụ: trùng mã sách nếu maS là khóa chính)
            if (ex.getSQLState().startsWith("23")) { // Lỗi vi phạm ràng buộc
                 JOptionPane.showMessageDialog(this, "Lỗi: Mã sách đã tồn tại.", "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "Lỗi CSDL khi thêm sách: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void sua() {
        // Lấy dữ liệu từ các trường nhập liệu
        String ma = txt_maS.getText().trim(); // Mã sách dùng trong điều kiện WHERE
        String ten = txt_tenS.getText().trim();
        String tg = txt_tg.getText().trim();
        String namXB = txt_namXB.getText().trim();
        String giaStr = txt_gia.getText().trim();
        String slStr = txt_sl.getText().trim();
        String maNXB = txt_maNXB.getText().trim();
        String ngonngu = txt_nn.getText().trim();
        String tinhtrang = txt_tt.getText().trim();

        // TODO: Thêm kiểm tra dữ liệu nhập (rỗng, định dạng số...)
         if (ma.isEmpty() || ten.isEmpty() || tg.isEmpty() || namXB.isEmpty() || giaStr.isEmpty() || slStr.isEmpty() || maNXB.isEmpty() || ngonngu.isEmpty() || tinhtrang.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sách để sửa.", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
             return;
         }
         int gia, sl;
         try {
             gia = Integer.parseInt(giaStr);
             sl = Integer.parseInt(slStr);
         } catch (NumberFormatException e) {
             JOptionPane.showMessageDialog(this, "Giá và Số lượng phải là số nguyên hợp lệ.", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
             return;
         }


        // Câu lệnh SQL UPDATE sử dụng PreparedStatement để chống SQL Injection
        // Cập nhật tất cả các cột TRỪ cột khóa chính (maS)
        String sqlsua = "UPDATE ql_sach SET tenS = ?, tg = ?, NamXB = ?, gia = ?, sl = ?, maNXB = ?, ngonngu = ?, tinhtrang = ? WHERE maS = ?";

        try (Connection con = KN.KNDL(); // Lấy kết nối từ KN
             PreparedStatement pst = con.prepareStatement(sqlsua)) { // Tạo PreparedStatement

            // Gán giá trị cho các tham số (?) theo đúng thứ tự
            pst.setString(1, ten);
            pst.setString(2, tg);
            pst.setString(3, namXB);
            pst.setInt(4, gia); // Gán kiểu INT
            pst.setInt(5, sl);   // Gán kiểu INT
            pst.setString(6, maNXB);
            pst.setString(7, ngonngu);
            pst.setString(8, tinhtrang);
            pst.setString(9, ma); // Gán giá trị cho điều kiện WHERE (maS)

            int rowsAffected = pst.executeUpdate(); // Thực thi lệnh UPDATE

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                // Sau khi sửa thành công, tải lại dữ liệu vào bảng
                loadTableData();
                // Xóa nội dung các trường nhập liệu
                clearInputFields(); // Gọi phương thức làm mới
            } else {
                // rowsAffected = 0 có nghĩa là không có dòng nào khớp với điều kiện WHERE
                // Thường xảy ra nếu mã sách trong txt_maS không tồn tại trong DB
                 JOptionPane.showMessageDialog(this, "Không tìm thấy Mã sách để cập nhật hoặc có lỗi.", "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Lỗi CSDL khi sửa sách: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void xoa() {
            // Lấy mã sách cần xóa từ trường nhập Mã sách
            String macanxoa = txt_maS.getText().trim();

             // TODO: Thêm kiểm tra rỗng hoặc kiểm tra xem có dòng nào đang được chọn trong bảng không
             // Cách lấy mã từ dòng chọn trong bảng thường dùng hơn là lấy từ JTextField
             int selectedRow = tb_qlsach.getSelectedRow();
             if (selectedRow == -1) {
                 JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng trong bảng để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                 return;
             }
             // Lấy mã sách từ dòng được chọn trong bảng
             macanxoa = tb_qlsach.getValueAt(selectedRow, 0).toString(); // Cột đầu tiên (0) là Mã sách

             // Hỏi xác nhận trước khi xóa (rất quan trọng!)
             int confirm = JOptionPane.showConfirmDialog(this,
                     "Bạn có chắc chắn muốn xóa sách có Mã sách là '" + macanxoa + "'?",
                     "Xác nhận Xóa",
                     JOptionPane.YES_NO_OPTION);

             if (confirm != JOptionPane.YES_OPTION) {
                 return; // Dừng nếu người dùng không xác nhận
             }


            // Câu lệnh SQL DELETE sử dụng PreparedStatement để chống SQL Injection
            String sql = "DELETE FROM ql_sach WHERE maS = ?";

            try (Connection con = KN.KNDL(); // Lấy kết nối từ KN
                 PreparedStatement pst = con.prepareStatement(sql)) { // Tạo PreparedStatement

                pst.setString(1, macanxoa); // Gán mã sách cần xóa vào tham số (?)

                int rowsAffected = pst.executeUpdate(); // Thực thi lệnh DELETE

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    // Sau khi xóa thành công, tải lại dữ liệu vào bảng để cập nhật hiển thị
                    loadTableData();
                    // Xóa nội dung các trường nhập liệu sau khi xóa
                    clearInputFields(); // Gọi phương thức làm mới
                } else {
                    // rowsAffected = 0 có nghĩa là không có dòng nào bị xóa
                    // Thường xảy ra nếu mã sách trong JTextField không tồn tại (nhưng đã check chọn dòng)
                     JOptionPane.showMessageDialog(this, "Không tìm thấy Mã sách để xóa hoặc có lỗi.", "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
                // Xử lý các lỗi CSDL (ví dụ: lỗi khóa ngoại nếu sách đang được mượn)
                 if (ex.getSQLState().startsWith("23")) { // Lỗi vi phạm ràng buộc (khóa ngoại)
                     JOptionPane.showMessageDialog(this, "Lỗi: Không thể xóa sách này vì nó đang được tham chiếu ở bảng khác (ví dụ: đang có trong danh sách mượn).", "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
                 } else {
                     JOptionPane.showMessageDialog(this, "Lỗi CSDL khi xóa sách: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
                 }
            }
        }
    
    // --- Phương thức xử lý click vào dòng trong bảng (cải tiến tbmouseClick()) ---
    // Đổi tên phương thức để rõ ràng hơn và bỏ throws SQLException
    public void populateFieldsFromTable() {
        int row = tb_qlsach.getSelectedRow();

        // Kiểm tra xem có dòng nào được chọn không
        if (row == -1) {
            // Không làm gì nếu không có dòng nào được chọn
            return;
        }

        try {
            // Lấy dữ liệu từ các cột của dòng được chọn và hiển thị lên JTextFields
            // Đảm bảo thứ tự cột và tên cột trong bảng khớp với dữ liệu bạn lấy từ CSDL
            // và gán vào JTextFields tương ứng. Sử dụng toString() để đảm bảo là String.
            txt_maS.setText(tb_qlsach.getValueAt(row, 0).toString());
            txt_tenS.setText(tb_qlsach.getValueAt(row, 1).toString());
            txt_tg.setText(tb_qlsach.getValueAt(row, 2).toString());
            txt_namXB.setText(tb_qlsach.getValueAt(row, 3).toString());
            txt_gia.setText(tb_qlsach.getValueAt(row, 4).toString()); // Cột giá
            txt_sl.setText(tb_qlsach.getValueAt(row, 5).toString());   // Cột số lượng
            txt_maNXB.setText(tb_qlsach.getValueAt(row, 6).toString());
            txt_nn.setText(tb_qlsach.getValueAt(row, 7).toString());
            txt_tt.setText(tb_qlsach.getValueAt(row, 8).toString());

        } catch (Exception ex) { // Bắt Exception chung hơn phòng lỗi get/toString
            Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, "Lỗi khi lấy dữ liệu từ bảng", ex);
            // Tùy chọn: Thông báo lỗi cho người dùng nếu có lỗi khi đọc từ bảng
             JOptionPane.showMessageDialog(this, "Lỗi khi đọc dữ liệu từ bảng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void clearInputFields() {
        txt_maS.setText("");
        txt_tenS.setText("");
        txt_tg.setText("");
        txt_namXB.setText("");
        txt_gia.setText("");
        txt_sl.setText("");
        txt_maNXB.setText("");
        txt_nn.setText("");
        txt_tt.setText("");
        // Tùy chọn: Bỏ chọn dòng trong bảng
        tb_qlsach.clearSelection();
>>>>>>> 98a313b (toi day)
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
        jLabel10 = new javax.swing.JLabel();
        txt_tt = new javax.swing.JTextField();
        txt_maS = new javax.swing.JTextField();
        btn_them = new javax.swing.JButton();
        txt_tenS = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_qlsach = new javax.swing.JTable();
        btn_sua = new javax.swing.JButton();
        txt_tg = new javax.swing.JTextField();
        btn_xoa = new javax.swing.JButton();
        txt_namXB = new javax.swing.JTextField();
        btn_thoat = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_gia = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txt_sl = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txt_maNXB = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txt_nn = new javax.swing.JTextField();
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
        setTitle("Quản lý sách");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Quản lý sách", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 500));

        jLabel10.setText("Mã NXB");

        txt_maS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_maSActionPerformed(evt);
            }
        });

        btn_them.setText("Thêm");
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });

        txt_tenS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tenSActionPerformed(evt);
            }
        });

        tb_qlsach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sách", "Tên sách", "Tác giả", "Năm xuất bản", "Giá", "Số lượng", "Mã NXB", "Ngôn ngữ", "Tình trạng"
            }
        ));
        tb_qlsach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_qlsachMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb_qlsach);

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

        jLabel11.setText("Mã sách");

        jLabel12.setText("Ngôn ngữ");

        jLabel13.setText("Tên sách");

        jLabel14.setText("Tình trạng");

        jLabel15.setText("Tác giả");

        jLabel16.setText("Giá");

        txt_sl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_slActionPerformed(evt);
            }
        });

        jLabel17.setText("Năm xuất bản");

        jLabel18.setText("Số lượng");

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
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(364, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_dau, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_truoc, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_sau, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_maNXB, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_sl, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_nn, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_tt, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(125, 125, 125)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_lm, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                            .addComponent(btn_xoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                            .addComponent(btn_thoat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_sua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_them, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(186, 186, 186))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
                        .addComponent(jLabel17)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(27, 27, 27)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txt_tenS, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_maS, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_tg, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_namXB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txt_gia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(742, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_them)
                    .addComponent(txt_sl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_sua)
                    .addComponent(txt_maNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_xoa)
                    .addComponent(txt_nn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_thoat)
                    .addComponent(txt_tt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addComponent(btn_lm)
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_dau)
                    .addComponent(btn_truoc)
                    .addComponent(btn_sau)
                    .addComponent(btn_cuoi))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txt_maS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(txt_tenS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(txt_tg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(txt_namXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(txt_gia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(308, 308, 308)))
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_tenSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tenSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tenSActionPerformed

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
<<<<<<< HEAD
        try {
            them();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
        }
=======
        them();
>>>>>>> 98a313b (toi day)
    }//GEN-LAST:event_btn_themActionPerformed

    private void txt_maSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_maSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_maSActionPerformed

    private void menu_qlsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_qlsvActionPerformed
        new QuanLySinhVien().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_qlsvActionPerformed

    private void menu_qlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_qlsActionPerformed
<<<<<<< HEAD
        new QuanLySach().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_qlsActionPerformed

    private void menu_qlmuontraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_qlmuontraActionPerformed
        new QuanLyMuonTraSach().setVisible(true);
=======
        loadTableData();
        JOptionPane.showMessageDialog(this, "Bạn đang ở form Quản lý Sách.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_menu_qlsActionPerformed

    private void menu_qlmuontraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_qlmuontraActionPerformed
        new QuanLyMuonTraSachForm().setVisible(true);
>>>>>>> 98a313b (toi day)
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
<<<<<<< HEAD
        new Login().setVisible(true);
        this.dispose();
=======
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn quay về trang Đăng nhập?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new Login().setVisible(true);
            this.dispose();
        }
>>>>>>> 98a313b (toi day)
    }//GEN-LAST:event_menu_dangnhapActionPerformed

    private void menu_dangkyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_dangkyActionPerformed
        new SignUp().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_dangkyActionPerformed

    private void btn_thoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_thoatActionPerformed
<<<<<<< HEAD
        System.exit(0);
=======
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát ứng dụng?", "Xác nhận Thoát", JOptionPane.YES_NO_OPTION);
         if (confirm == JOptionPane.YES_OPTION) {
             System.exit(0); 
         }
>>>>>>> 98a313b (toi day)
    }//GEN-LAST:event_btn_thoatActionPerformed

    private void txt_slActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_slActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_slActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
<<<<<<< HEAD
        try {
            ht();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowActivated

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        try {
            sua();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_suaActionPerformed

    private void btn_lmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lmActionPerformed
            txt_maS.setText("");
            txt_tenS.setText("");
            txt_tg.setText("");
            txt_namXB.setText("");
            txt_gia.setText("");
            txt_sl.setText("");
            txt_maNXB.setText("");
            txt_nn.setText("");
            txt_tt.setText("");
    }//GEN-LAST:event_btn_lmActionPerformed

    private void tb_qlsachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_qlsachMouseClicked
        try {
            tbmouseClick();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
        }
=======
        // Khi form mở, tải dữ liệu vào bảng
        loadTableData(); // Gọi phương thức tải dữ liệu đã cải tiến

        // Tùy chọn: Thiết lập Header và màu cho bảng ngay khi form activated
        JTableHeader header = tb_qlsach.getTableHeader();
        header.setBackground(new Color(70, 130, 180)); // Steel blue
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tb_qlsach.setSelectionBackground(new Color(135, 206, 250)); // Light sky blue
        tb_qlsach.setSelectionForeground(Color.BLACK);
        tb_qlsach.setShowGrid(true);
        tb_qlsach.setGridColor(new Color(200, 200, 200));
        tb_qlsach.setShowHorizontalLines(true);
        tb_qlsach.setShowVerticalLines(true);
         // Đảm bảo bạn đã import java.awt.Color và java.awt.Font
    }//GEN-LAST:event_formWindowActivated

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        sua();
    }//GEN-LAST:event_btn_suaActionPerformed

    private void btn_lmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lmActionPerformed
            clearInputFields();
    }//GEN-LAST:event_btn_lmActionPerformed

    private void tb_qlsachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_qlsachMouseClicked
        populateFieldsFromTable();
>>>>>>> 98a313b (toi day)
    }//GEN-LAST:event_tb_qlsachMouseClicked

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        xoa();
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void menu_tknvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_tknvActionPerformed
        new TimKiemNhanVien().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_tknvActionPerformed

    private void btn_cuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cuoiActionPerformed
<<<<<<< HEAD
        tb_qlsach.setRowSelectionInterval(tb_qlsach.getRowCount()-1, tb_qlsach.getRowCount()-1);
        try {
            tbmouseClick();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
=======
        if (tb_qlsach.getRowCount() > 0) {
            tb_qlsach.setRowSelectionInterval(tb_qlsach.getRowCount() - 1, tb_qlsach.getRowCount() - 1); // Chọn dòng cuối
            populateFieldsFromTable(); // Hiển thị dữ liệu dòng đó
>>>>>>> 98a313b (toi day)
        }
    }//GEN-LAST:event_btn_cuoiActionPerformed

    private void btn_dauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dauActionPerformed
<<<<<<< HEAD
        tb_qlsach.setRowSelectionInterval(0,0);
        try {
            tbmouseClick();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_dauActionPerformed

    private void btn_truocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_truocActionPerformed
        int n = tb_qlsach.getSelectedRow();
        if(n>0){
            tb_qlsach.setRowSelectionInterval(n-1, n-1);
        }else{
            tb_qlsach.setRowSelectionInterval(tb_qlsach.getRowCount()-1,tb_qlsach.getRowCount()-1);
        }
        try {
            tbmouseClick();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_truocActionPerformed

    private void btn_sauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sauActionPerformed
        int n = tb_qlsach.getSelectedRow();
        if(n==tb_qlsach.getRowCount()-1){
            tb_qlsach.setRowSelectionInterval(0, 0);
        }else{
            tb_qlsach.setRowSelectionInterval(n+1, n+1);
        }
        try {
            tbmouseClick();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
        }
=======
        if (tb_qlsach.getRowCount() > 0) { // Kiểm tra bảng có dữ liệu không
            tb_qlsach.setRowSelectionInterval(0, 0); // Chọn dòng đầu tiên
             populateFieldsFromTable(); // Hiển thị dữ liệu dòng đó lên JTextFields
         }
    }//GEN-LAST:event_btn_dauActionPerformed

    private void btn_truocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_truocActionPerformed
        int n = tb_qlsach.getSelectedRow(); // Lấy chỉ số dòng đang chọn
         if (tb_qlsach.getRowCount() > 0) {
             if (n > 0) {
                 tb_qlsach.setRowSelectionInterval(n - 1, n - 1); // Chọn dòng trước đó
             } else {
                 tb_qlsach.setRowSelectionInterval(tb_qlsach.getRowCount() - 1, tb_qlsach.getRowCount() - 1); // Nếu đang ở đầu, về cuối
             }
             populateFieldsFromTable(); // Hiển thị dữ liệu dòng mới được chọn
         }
    }//GEN-LAST:event_btn_truocActionPerformed

    private void btn_sauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sauActionPerformed
        int n = tb_qlsach.getSelectedRow(); // Lấy chỉ số dòng đang chọn
          if (tb_qlsach.getRowCount() > 0) {
              if (n < tb_qlsach.getRowCount() - 1) {
                  tb_qlsach.setRowSelectionInterval(n + 1, n + 1); // Chọn dòng tiếp theo
              } else {
                  tb_qlsach.setRowSelectionInterval(0, 0); // Nếu đang ở cuối, về đầu
              }
              populateFieldsFromTable(); // Hiển thị dữ liệu dòng mới được chọn
          }
>>>>>>> 98a313b (toi day)
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
            java.util.logging.Logger.getLogger(QuanLySach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLySach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLySach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLySach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLySach().setVisible(true);
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
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
    private javax.swing.JTable tb_qlsach;
    private javax.swing.JTextField txt_gia;
    private javax.swing.JTextField txt_maNXB;
    private javax.swing.JTextField txt_maS;
    private javax.swing.JTextField txt_namXB;
    private javax.swing.JTextField txt_nn;
    private javax.swing.JTextField txt_sl;
    private javax.swing.JTextField txt_tenS;
    private javax.swing.JTextField txt_tg;
    private javax.swing.JTextField txt_tt;
    // End of variables declaration//GEN-END:variables
}
