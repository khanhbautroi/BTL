/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl_thlt_java;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Khanh
 */
public class KN {
    public static Connection KNDL() throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlbenhnhan", "root", "");
        return con;
    }
    
    public static void main(String[] args) throws SQLException {
        Connection kn = KN.KNDL();
        if(kn!=null){
            System.out.println("ket thoi thanh cong"+kn);
        }
        else{
            System.out.println("ket noi that bai");
        }
    }
}
