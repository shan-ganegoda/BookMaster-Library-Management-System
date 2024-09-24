package lk.projects.library.dao;

import lk.projects.library.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
                user.setDoregistered(LocalDate.parse( result.getObject("doregistered").toString() )  );
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
            user.setDoregistered(LocalDate.parse( result.getObject("doregistered").toString() )  );
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

    public static User getByUsername(String username) {
        String qry = "select * from user where username='" + username + "'";
        return getBy(qry);
    }

    public static String save(User currentUser) {
        String qry = "INSERT INTO user (fullname,username,password,doregistered,userstatus_id,role_id) VALUES('" +
                currentUser.getFullname() +"','" +
                currentUser.getUsername()+"','" +
                currentUser.getPassword()+"','" +
                currentUser.getDoregistered()+"','" +
                currentUser.getUserstatus().getId()+"','" +
                currentUser.getRole().getId() +"')";

        return CommonDao.modify(qry);
    }

    public static String update(User currentUser) {
        String qry = "UPDATE user set fullname='" + currentUser.getFullname() +
                "',username='" + currentUser.getUsername() +
                "',password='" + currentUser.getPassword() +
                "',doregistered='" + currentUser.getDoregistered() +
                "',role_id='" + currentUser.getRole().getId() +
                "',userstatus_id='" + currentUser.getUserstatus().getId() +
                "' WHERE id=" + currentUser.getId();
        return CommonDao.modify(qry);
    }

    public static String delete(Integer id) {
        String qry = "DELETE FROM user WHERE id=" + id;
        return CommonDao.modify(qry);
    }

    public static List<User> getByFullName(String ssfullname) {
        String qry = "SELECT * FROM user where fullname like '" + ssfullname + "%'";
        return get(qry);
    }

    public static List<User> getAllByUsername(String ssusername) {
        String qry = "SELECT * FROM user where username ='" + ssusername + "'";
        return get(qry);
    }

    public static List<User> getAllByRole(int i) {
        String qry = "SELECT * FROM user where role_id = " + i;
        return get(qry);
    }

    public static List<User> getByFullNameAndUsername(String ssfullname, String ssusername) {
        String qry = "SELECT * FROM user where fullname like '" + ssfullname + "%' and username ='" + ssusername + "'";
        return get(qry);
    }

    public static List<User> getByFullNameAndRole(String ssfullname, int i) {
        String qry = "SELECT * FROM user where fullname like '" + ssfullname + "%' and userrole_id = " + i;
        return get(qry);
    }

    public static List<User> getByUsernameAndRole(String ssusername, int i) {
        String qry = "SELECT * FROM user where username = '" + ssusername + "' and userrole_id = " + i;
        return get(qry);
    }

    public static List<User> getByFullNameAndUsernameAndRole(String ssfullname, String ssusername, int i) {
        String qry = "SELECT * FROM user where fullname like '" + ssfullname + "%' and username ='" + ssusername + "' and userrole_id = " + i;
        return get(qry);
    }
}
