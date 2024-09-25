package lk.projects.library.service;

import lk.projects.library.dao.BorrowingsDao;
import lk.projects.library.entity.Borrowings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BorrowingsService {

    public static List<Borrowings> get(HashMap<String, String> params) {

        List<Borrowings> borrowings = new ArrayList<Borrowings>();

        if(params.isEmpty()){
            borrowings = BorrowingsDao.getAll();
        }

        String ssmember = params.get("ssmember");
        String sscode = params.get("sscode");
        String ssbook = params.get("ssbook");

        if(ssmember.isEmpty() && sscode.isEmpty() && ssbook.isEmpty()){
            borrowings = BorrowingsDao.getAll();
        }

        if(!ssmember.isEmpty() && sscode.isEmpty() && ssbook.isEmpty()){
            borrowings = BorrowingsDao.getAllByMember(Integer.parseInt(ssmember));
        }

        if(ssmember.isEmpty() && !sscode.isEmpty() && ssbook.isEmpty()){
            borrowings = BorrowingsDao.getAllByCode(sscode);
        }

        if(ssmember.isEmpty() && sscode.isEmpty() && !ssbook.isEmpty()){
            borrowings = BorrowingsDao.getAllByBook(Integer.parseInt(ssbook));
        }

        if(!ssmember.isEmpty() && !sscode.isEmpty() && ssbook.isEmpty()){
            borrowings = BorrowingsDao.getByMemberAndCode(Integer.parseInt(ssmember),sscode);
        }

        if(!ssmember.isEmpty() && sscode.isEmpty() && !ssbook.isEmpty()){
            borrowings = BorrowingsDao.getByMemberAndBook(Integer.parseInt(ssmember),Integer.parseInt(ssbook));
        }

        if(ssmember.isEmpty() && !sscode.isEmpty() && !ssbook.isEmpty()){
            borrowings = BorrowingsDao.getByCodeAndBook(sscode,Integer.parseInt(ssbook));
        }

        if(!ssmember.isEmpty() && !sscode.isEmpty() && !ssbook.isEmpty()){
            borrowings = BorrowingsDao.getAllByMemberAndCodeAndBook(Integer.parseInt(ssmember),sscode,Integer.parseInt(ssbook));
        }

        return borrowings;
    }

    public static String post(Borrowings borrowings) {
        String msg = "";
        String err = "";

        Borrowings res = BorrowingsDao.getByCode(borrowings.getCode());

        if(res != null) err = err + "\nCode Exists";

        if(err.isEmpty()) {
            String status = BorrowingsDao.save(borrowings);
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

    public static String put(Borrowings borrowings) {
        String msg = "";
        String err = "";

        Borrowings res = BorrowingsDao.getByCode(borrowings.getCode());

        if( res != null && !res.getCode().equals(borrowings.getCode()) ) err = err + "\nCode Exists";

        if(err.isEmpty()) {
            String status = BorrowingsDao.update(borrowings);
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

    public static String delete(Borrowings oldBorrowings) {
        String msg = "";

        String dberror = BorrowingsDao.delete(oldBorrowings.getId());
        if(dberror.equals("1")) {
            msg = "Success";
        }else{
            msg = "Fail caused by :" + dberror;
        }

        return msg;
    }
}
