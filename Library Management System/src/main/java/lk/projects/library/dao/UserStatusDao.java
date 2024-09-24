package lk.projects.library.dao;

import lk.projects.library.entity.UserStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserStatusDao {

    public static UserStatus getById(int id){

        UserStatus userStatus = new UserStatus();

        try {

            String qry = "Select * from userstatus where id ="+id;
            ResultSet result = CommonDao.get(qry);

            result.next();
            userStatus.setId(result.getInt("id"));
            userStatus.setName( result.getObject("name").toString() );
        }

        catch(SQLException e){
            System.out.println("Can't Connect as : "+ e.getMessage());

        }
        return userStatus;

    }

    public static List<UserStatus> getAll(){

        List<UserStatus> userStatusList = new ArrayList();

        try {

            String qry = "Select * from userstatus";
            ResultSet result = CommonDao.get(qry);

            while(result.next()){
                UserStatus userStatus = new UserStatus();

                userStatus.setId(result.getInt("id"));
                userStatus.setName( result.getObject("name").toString() );

                userStatusList.add(userStatus);

            }

        }

        catch(SQLException e){
            System.out.println("Can't Connect as : "+ e.getMessage());

        }
        return userStatusList;
    }
}
