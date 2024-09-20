package lk.projects.library.dao;

import lk.projects.library.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public static List<User> get(String qry) {
        List<User> userList = new ArrayList<User>();

        try{
            ResultSet result = CommonDao.get(qry);

            while (result.next()) {
                User user = new User();
                user.setId(result.getInt("id"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                user.setFullname(result.getString("fullname"));
                user.setRole( RoleDao.getById( result.getInt("role_id")  )  );
                user.setUserstatus( UserStatusDao.getById( result.getInt("userstatus_id")  )  );
                userList.add(user);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return userList;
    }

    public static User getBy(String qry) {
        User user = new User();

        try{
            ResultSet result = CommonDao.get(qry);

            result.next();
            user.setId(result.getInt("id"));
            user.setUsername(result.getString("username"));
            user.setPassword(result.getString("password"));
            user.setFullname(result.getString("fullname"));
            user.setRole( RoleDao.getById( result.getInt("role_id")  )  );
            user.setUserstatus( UserStatusDao.getById( result.getInt("userstatus_id")  )  );

            return user;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public static User getById(int id) {
        String qry = "select * from user where id=" + id;
        return getBy(qry);
    }

    public static List<User> getAll() {
        String qry = "SELECT * FROM user";
        return get(qry);
    }
}
