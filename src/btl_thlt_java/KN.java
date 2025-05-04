/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl_thlt_java;

<<<<<<< HEAD
import java.sql.*;
=======
>>>>>>> 98a313b (toi day)
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Khanh
 */
public class KN {
<<<<<<< HEAD
    public static Connection KNDL() throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlthuvien", "root", "");
        return con;
    }
    
    public static void main(String[] args) throws SQLException {
        Connection kn = KN.KNDL();
        if(kn!=null){
            System.out.println("ket thoi thanh cong"+kn);
        }
        else{
            System.out.println("ket noi that bai");
=======
    // Khai báo các hằng số cho thông tin kết nối (vẫn hardcoded như yêu cầu)
    // Sử dụng 'static final' giúp code rõ ràng hơn là các hằng số

    private static final String DB_URL = "jdbc:mysql://localhost:3307/qlthuvien";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static Connection KNDL() throws SQLException {

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void main(String[] args) {

        try (Connection kn = KN.KNDL()) {
            System.out.println("Kết nối CSDL thành công!");

        } catch (SQLException e) {
            System.err.println("Lỗi kết nối CSDL:");
            e.printStackTrace();

        } finally {
            System.out.println("Kết thúc quy trình kiểm tra kết nối.");
>>>>>>> 98a313b (toi day)
        }
    }
}
