package lk.projects.library.dao;

import java.sql.*;

public class CommonDao {

    static ResultSet result;

    public static ResultSet get(String qry){
        try {
            Connection conn = DatabaseCon.getConnection();
            Statement stm = conn.createStatement();
            result = stm.executeQuery(qry);
        } catch (SQLException e) {
            System.out.println("Can't Get Result As : "+e.getMessage());
        }
        return result;
    }

    public static String modify(String qry){

        String msg = "0";

        try {
            Connection dbcon = DatabaseCon.getConnection();
            Statement stm = dbcon.createStatement();
            int rows = stm.executeUpdate(qry);
            if(rows!=0) msg="1";

        } catch (SQLException e) {
            System.out.println("Database Error as : " + e.getMessage());
            msg="Database Error";
        }

        return msg;

    }
}




