package lk.projects.library.dao;

import lk.projects.library.entity.FineStatus;
import lk.projects.library.entity.Gender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FineStatusDao {
    public static List<FineStatus> getAll() {
        List<FineStatus> fineStatusList = new ArrayList<FineStatus>();

        try{
            String qry = "SELECT * FROM finestatus";
            ResultSet result = CommonDao.get(qry);

            while (result.next()) {
                FineStatus fineStatus = new FineStatus();
                fineStatus.setId(result.getInt("id"));
                fineStatus.setName(result.getString("name"));
                fineStatusList.add(fineStatus);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return fineStatusList;
    }

    public static FineStatus getById(int id) {
        FineStatus fineStatus = new FineStatus();

        try {

            String qry = "Select * from finestatus where id ="+id;
            ResultSet result = CommonDao.get(qry);

            result.next();
            fineStatus.setId(result.getInt("id"));
            fineStatus.setName(result.getString("name"));
        }

        catch(SQLException e){
            System.out.println("Can't Connect as : "+ e.getMessage());

        }
        return fineStatus;
    }
}
