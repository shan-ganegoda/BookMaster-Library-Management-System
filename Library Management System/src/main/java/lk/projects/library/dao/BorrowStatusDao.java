package lk.projects.library.dao;

import lk.projects.library.entity.BorrowStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowStatusDao {

    public static List<BorrowStatus> getAll() {
        List<BorrowStatus> borrowStatuses = new ArrayList<BorrowStatus>();

        try{
            String qry = "SELECT * FROM borrowstatus";
            ResultSet result = CommonDao.get(qry);

            while (result.next()) {
                BorrowStatus borrowStatus = new BorrowStatus();
                borrowStatus.setId(result.getInt("id"));
                borrowStatus.setName(result.getString("name"));
                borrowStatuses.add(borrowStatus);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return borrowStatuses;
    }

    public static BorrowStatus getById(int id){

        BorrowStatus borrowStatus = new BorrowStatus();

        try {

            String qry = "Select * from borrowstatus where id ="+id;
            ResultSet result = CommonDao.get(qry);

            result.next();
            borrowStatus.setId(result.getInt("id"));
            borrowStatus.setName( result.getObject("name").toString() );
        }

        catch(SQLException e){
            System.out.println("Can't Connect as : "+ e.getMessage());

        }
        return borrowStatus;

    }
}
