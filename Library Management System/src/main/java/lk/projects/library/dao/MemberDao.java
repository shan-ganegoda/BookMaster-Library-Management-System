package lk.projects.library.dao;

import lk.projects.library.entity.Member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberDao {

    public static List<Member> get(String qry) {
        List<Member> memberList = new ArrayList<Member>();

        try{
            ResultSet result = CommonDao.get(qry);

            while (result.next()) {
                Member member = new Member();
                member.setId(result.getInt("id"));
                member.setFullname(result.getString("fullname"));
                member.setCode(result.getString("code"));
                member.setDob(LocalDate.parse( result.getObject("dob").toString() )  );
                member.setNic(result.getString("nic"));
                member.setAddress(result.getString("address"));
                member.setDoregistered(LocalDate.parse( result.getObject("doregistered").toString() )  );
                member.setGender( GenderDao.getById( result.getInt("gender_id")  )  );
                member.setMemberstatus( MemberStatusDao.getById( result.getInt("memberstatus_id")  )  );
                member.setUser( UserDao.getById( result.getInt("user_id")  )  );
                memberList.add(member);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return memberList;
    }

    public static Member getBy(String qry) {
        Member member = new Member();

        try{
            ResultSet result = CommonDao.get(qry);

            result.next();

            member.setId(result.getInt("id"));
            member.setFullname(result.getString("fullname"));
            member.setCode(result.getString("code"));
            member.setDob(LocalDate.parse( result.getObject("dob").toString() )  );
            member.setNic(result.getString("nic"));
            member.setAddress(result.getString("address"));
            member.setDoregistered(LocalDate.parse( result.getObject("doregistered").toString() )  );
            member.setGender( GenderDao.getById( result.getInt("gender_id")  )  );
            member.setMemberstatus( MemberStatusDao.getById( result.getInt("memberstatus_id")  )  );
            member.setUser( UserDao.getById( result.getInt("user_id")  )  );
            return member;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public static List<Member> getAll(){
        String qry = "select * from member";
        return get(qry);
    }


}
