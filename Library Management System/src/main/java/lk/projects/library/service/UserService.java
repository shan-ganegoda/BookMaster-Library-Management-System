package lk.projects.library.service;

import lk.projects.library.dao.BookDao;
import lk.projects.library.dao.UserDao;
import lk.projects.library.entity.Books;
import lk.projects.library.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserService {

    public static List<User> get(HashMap<String,String> params){
        List<User> users = new ArrayList<User>();

        if(params.isEmpty()){
            users = UserDao.getAll();
        }else{
            users = null;
        }

        return users;
    }

    public static String post(User currentUser) {
        String msg = "";
        String err = "";

        User res = UserDao.getByUsername(currentUser.getUsername());

        if(res != null) err = err + "\nUsername Exists";

        if(err.isEmpty()) {
            String status = UserDao.save(currentUser);
            if(status.equals("1")) {
                msg = "Success";
            }else{
                msg = "Fail caused by :" + status;
            }
        }else{
            msg = "Fail caused by :" + err;
        }

        return msg;
    }

    public static String put(User currentUser) {
        String msg = "";
        String err = "";

        User res = UserDao.getByUsername(currentUser.getUsername());


        if( res != null && !res.getUsername().equals(currentUser.getUsername()) ) err = err + "\nUsername Exists";

        if(err.isEmpty()) {
            String status = UserDao.update(currentUser);
            if(status.equals("1")) {
                msg = "Success";
            }else{
                msg = "Fail caused by :" + status;
            }
        }else{
            msg = "Fail caused by :" + err;
        }

        return msg;
    }

    public static String delete(User oldUser) {
        String msg = "";

        String dberror = UserDao.delete(oldUser.getId());
        if(dberror.equals("1")) {
            msg = "Success";
        }else{
            msg = "Fail caused by :" + dberror;
        }

        return msg;
    }
}
