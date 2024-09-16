package lk.projects.library.dao;

import java.sql.*;

public class CommonDao {

    static ResultSet result;

    public static ResultSet get(String qry){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookmaster","root","1234");
            Statement stm = conn.createStatement();
            result = stm.executeQuery(qry);
        } catch (SQLException e) {
            System.out.println("Can't Get Result As : "+e.getMessage());
        }
        return result;
    }
}




