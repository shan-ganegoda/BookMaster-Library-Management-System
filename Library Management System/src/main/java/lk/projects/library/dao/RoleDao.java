package lk.projects.library.dao;

import lk.projects.library.entity.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDao {

    public static Role getById(int id){

        Role role = new Role();

        try {

            String qry = "Select * from role where id ="+id;
            ResultSet result = CommonDao.get(qry);

            result.next();
            role.setId(result.getInt("id"));
            role.setName( result.getObject("name").toString() );
        }

        catch(SQLException e){
            System.out.println("Can't Connect as : "+ e.getMessage());

        }
        return role;

    }

    public static List<Role> getAll(){

        List<Role> roles = new ArrayList();

        try {

            String qry = "Select * from role";
            ResultSet result = CommonDao.get(qry);

            while(result.next()){
                Role role = new Role();

                role.setId(result.getInt("id"));
                role.setName( result.getObject("name").toString() );

                roles.add(role);

            }

        }

        catch(SQLException e){
            System.out.println("Can't Connect as : "+ e.getMessage());

        }
        return roles;
    }
}
