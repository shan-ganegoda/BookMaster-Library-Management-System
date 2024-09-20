package lk.projects.library.service;

import lk.projects.library.dao.UserDao;
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
}
