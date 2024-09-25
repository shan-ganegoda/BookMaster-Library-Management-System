package lk.projects.library.dao;

import lk.projects.library.entity.Books;
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

    public static Member getById(int id) {
        String qry = "select * from member where id=" + id;
        return getBy(qry);
    }

    public static List<Member> getAll(){
        String qry = "select * from member";
        return get(qry);
    }


    public static List<Member> getAllByFullName(String ssfullname) {
        String qry = "select * from member where fullname like '"+ssfullname+"%'";
        return get(qry);
    }

    public static List<Member> getAllByCode(String sscode) {
        String qry = "select * from member where code = '"+sscode+"'";
        return get(qry);
    }

    public static List<Member> getAllByNic(String ssnic) {
        String qry = "select * from member where nic = '"+ssnic+"'";
        return get(qry);
    }

    public static List<Member> getByFullNameAndCode(String ssfullname, String sscode) {
        String qry = "select * from member where fullname Like '"+ssfullname+"%' and code = '"+sscode+"'";
        return get(qry);
    }

    public static List<Member> getByFullNameAndNic(String ssfullname, String ssnic) {
        String qry = "select * from member where fullname like '" + ssfullname + "%' and code = '"+ssnic +"'";
        return get(qry);
    }

    public static List<Member> getByCodeAndNic(String sscode, String ssnic) {
        String qry = "select * from member where code ='"+sscode+"' and nic = '"+ssnic +"'";
        return get(qry);
    }

    public static List<Member> getAllByFullNameAndCodeAndNic(String ssfullname, String sscode, String ssnic) {
        String qry = "select * from member where fullname like '" + ssfullname  + "%' and code = '"+ssnic +"'and nic = '"+ssnic +"'";
        return get(qry);
    }

    public static Member getByCode(String code) {
        String qry = "Select * from member where code = '"+code+"'";
        return getBy(qry);
    }

    public static Member getByNic(String nic) {
        String qry = "Select * from member where nic = '"+nic+"'";
        return getBy(qry);
    }

    public static String save(Member currentMember) {
        String qry = "INSERT INTO member (fullname,code,dob,nic,address,doregistered,gender_id,memberstatus_id,user_id) VALUES('" +
                currentMember.getFullname() +"','" +
                currentMember.getCode()+"','" +
                currentMember.getDob()+"','" +
                currentMember.getNic()+"','" +
                currentMember.getAddress()+"','" +
                currentMember.getDoregistered()+"','" +
                currentMember.getGender().getId()+"','" +
                currentMember.getMemberstatus().getId()+"','" +
                currentMember.getUser().getId() +"')";

        return CommonDao.modify(qry);
    }

    public static String update(Member currentMember) {
        String qry = "UPDATE member set fullname='" + currentMember.getFullname() +
                "',code='" + currentMember.getCode() +
                "',nic='" + currentMember.getNic() +
                "',dob='" + currentMember.getDob() +
                "',address='" + currentMember.getAddress() +
                "',doregistered='" + currentMember.getDoregistered() +
                "',gender_id='" + currentMember.getGender().getId() +
                "',memberstatus_id='" + currentMember.getMemberstatus().getId() +
                "',user_id='" + currentMember.getUser().getId() +
                "' WHERE id=" + currentMember.getId();
        return CommonDao.modify(qry);
    }

    public static String delete(Integer id) {
        String qry = "DELETE FROM member WHERE id=" + id;
        return CommonDao.modify(qry);
    }
}
