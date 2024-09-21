package lk.projects.library.service;

import lk.projects.library.dao.BookDao;
import lk.projects.library.dao.MemberDao;
import lk.projects.library.entity.Books;
import lk.projects.library.entity.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemberService {

    public static List<Member> get(HashMap<String, String> params) {

        List<Member> members = new ArrayList<Member>();

        if(params.isEmpty()){
            members = MemberDao.getAll();
        }

        String ssfullname = params.get("ssfullname");
        String sscode = params.get("sscode");
        String ssnic = params.get("ssnic");

        if(ssfullname.isEmpty() && sscode.isEmpty() && ssnic.isEmpty()){
            members = MemberDao.getAll();
        }

        if(!ssfullname.isEmpty() && sscode.isEmpty() && ssnic.isEmpty()){
            members = MemberDao.getAllByFullName(ssfullname);
        }

        if(ssfullname.isEmpty() && !sscode.isEmpty() && ssnic.isEmpty()){
            members = MemberDao.getAllByCode(sscode);
        }

        if(ssfullname.isEmpty() && sscode.isEmpty() && !ssnic.isEmpty()){
            members = MemberDao.getAllByNic(ssnic);
        }

        if(!ssfullname.isEmpty() && !sscode.isEmpty() && ssnic.isEmpty()){
            members = MemberDao.getByFullNameAndCode(ssfullname,sscode);
        }

        if(!ssfullname.isEmpty() && sscode.isEmpty() && !ssnic.isEmpty()){
            members = MemberDao.getByFullNameAndNic(ssfullname,ssnic);
        }

        if(ssfullname.isEmpty() && !sscode.isEmpty() && !ssnic.isEmpty()){
            members = MemberDao.getByCodeAndNic(sscode,ssnic);
        }

        if(!ssfullname.isEmpty() && !sscode.isEmpty() && !ssnic.isEmpty()){
            members = MemberDao.getAllByFullNameAndCodeAndNic(ssfullname,sscode,ssnic);
        }

        return members;
    }

    public static String post(Member currentMember) {
        String msg = "";
        String err = "";

        Member res = MemberDao.getByCode(currentMember.getCode());
        Member rest = MemberDao.getByNic(currentMember.getNic());

        if(res != null) err = err + "\nCode Exists";
        if(rest != null) err = err + "\nNic Exists";

        if(err.isEmpty()) {
            String status = MemberDao.save(currentMember);
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

    public static String put(Member currentMember) {
        String msg = "";
        String err = "";

        Member res = MemberDao.getByCode(currentMember.getCode());
        Member rest = MemberDao.getByNic(currentMember.getNic());

        if( res != null && !res.getCode().equals(currentMember.getCode()) ) err = err + "\nCode Exists";
        if( rest != null && !rest.getNic().equals(currentMember.getNic()) ) err = err + "\nNic Exists";

        if(err.isEmpty()) {
            String status = MemberDao.update(currentMember);
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

    public static String delete(Member oldMember) {
        String msg = "";

        String dberror = MemberDao.delete(oldMember.getId());
        if(dberror.equals("1")) {
            msg = "Success";
        }else{
            msg = "Fail caused by :" + dberror;
        }

        return msg;
    }
}
