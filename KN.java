/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl_thlt_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Khanh
 */
public class KN {

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
        }
    }
}
