package lk.projects.library.service;

import lk.projects.library.dao.BorrowStatusDao;
import lk.projects.library.dao.BorrowingsDao;
import lk.projects.library.dao.FineDao;
import lk.projects.library.entity.Borrowings;
import lk.projects.library.entity.Fine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FineService {

    public static String post(Fine fine) {
        String msg = "";
        String err = "";

        Fine res = FineDao.getByBorrowing(fine.getBorrowings().getId());
        if(res != null) err = err + "\nBorrowing Exists";

        if(err.isEmpty()) {
            String status = FineDao.save(fine);
            if(status.equals("1")) {

                Borrowings br = BorrowingsDao.getById(fine.getBorrowings().getId());
                br.setBorrowStatus(BorrowStatusDao.getById(3));
                BorrowingsDao.update(br);

                msg = "Success";
            }else{
                msg = "Fail caused by :" + status;
            }
        }else{
            msg = "Fail caused by :" + err;
        }

        return msg;
    }

    public static String put(Fine fine) {
        String msg = "";
        String err = "";

        Fine res = FineDao.getByBorrowing(fine.getBorrowings().getId());
        if( res != null && !res.getBorrowings().getId().equals(fine.getBorrowings().getId()) ) err = err + "\nBorrowing Exists";

        if(err.isEmpty()) {
            String status = FineDao.update(fine);
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

    public static String delete(Fine fine) {
        String msg = "";

        String dberror = FineDao.delete(fine.getId());
        if(dberror.equals("1")) {
            msg = "Success";
        }else{
            msg = "Fail caused by :" + dberror;
        }

        return msg;
    }

    public static List<Fine> get(HashMap<String, String> params) {

        List<Fine> fines = new ArrayList<Fine>();

        if(params.isEmpty()){
            fines = FineDao.getAll();
        }

        String ssborrowing = params.get("ssborrowing");

        if(!ssborrowing.isEmpty()){
            fines = FineDao.getAllByBorrowing(Integer.parseInt(ssborrowing));
        }

        return fines;
    }
}
