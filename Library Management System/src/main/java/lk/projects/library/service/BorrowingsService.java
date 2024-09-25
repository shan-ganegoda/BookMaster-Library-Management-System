package lk.projects.library.service;

import lk.projects.library.dao.BorrowingsDao;
import lk.projects.library.entity.Borrowings;

public class BorrowingsService {

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
