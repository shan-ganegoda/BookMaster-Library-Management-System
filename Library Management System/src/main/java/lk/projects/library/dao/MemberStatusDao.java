package lk.projects.library.dao;

import lk.projects.library.entity.MemberStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberStatusDao {

    public static List<MemberStatus> getAll() {
        List<MemberStatus> memberStatusList = new ArrayList<MemberStatus>();

        try{
            String qry = "SELECT * FROM memberstatus";
            ResultSet result = CommonDao.get(qry);

            while (result.next()) {
                MemberStatus memberStatus = new MemberStatus();
                memberStatus.setId(result.getInt("id"));
                memberStatus.setName(result.getString("name"));
                memberStatusList.add(memberStatus);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return memberStatusList;
    }

    public static MemberStatus getById(int id){

        MemberStatus memberStatus = new MemberStatus();

        try {

            String qry = "Select * from memberstatus where id ="+id;
            ResultSet result = CommonDao.get(qry);

            result.next();
            memberStatus.setId(result.getInt("id"));
            memberStatus.setName( result.getObject("name").toString() );
        }

        catch(SQLException e){
            System.out.println("Can't Connect as : "+ e.getMessage());

        }
        return memberStatus;

    }
}
