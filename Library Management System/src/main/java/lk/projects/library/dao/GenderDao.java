package lk.projects.library.dao;


import lk.projects.library.entity.Gender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenderDao {

    public static List<Gender> getAll() {
        List<Gender> genderList = new ArrayList<Gender>();

        try{
            String qry = "SELECT * FROM gender";
            ResultSet result = CommonDao.get(qry);

            while (result.next()) {
                Gender gender = new Gender();
                gender.setId(result.getInt("id"));
                gender.setName(result.getString("name"));
                genderList.add(gender);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return genderList;
    }

    public static Gender getById(int id){

        Gender gender = new Gender();

        try {

            String qry = "Select * from gender where id ="+id;
            ResultSet result = CommonDao.get(qry);

            result.next();
            gender.setId(result.getInt("id"));
            gender.setName( result.getObject("name").toString() );
        }

        catch(SQLException e){
            System.out.println("Can't Connect as : "+ e.getMessage());

        }
        return gender;

    }
}
