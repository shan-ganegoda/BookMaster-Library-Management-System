package lk.projects.library.service;

import lk.projects.library.dao.BookDao;
import lk.projects.library.dao.CategoryDao;
import lk.projects.library.entity.Books;
import lk.projects.library.entity.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookService {

    public static List<Books> get(HashMap<String, String> params) {

        List<Books> books = new ArrayList<Books>();

        if(params.isEmpty()){
            books = BookDao.getAll();
        }

//        String ssname = params.get("ssname");
//        String sscode = params.get("sscode");
//
//        if(ssname.equals("") && sscode.equals("")){
//            categories = CategoryDao.getAll();
//        }
//
//        if(!ssname.equals("") && sscode.equals("")){
//            categories = CategoryDao.getAllByName(ssname);
//        }
//
//        if(ssname.equals("") && !sscode.equals("")){
//            categories = CategoryDao.getAllByCode(sscode);
//        }
//
//        if(!ssname.equals("") && !sscode.equals("")){
//            categories = CategoryDao.getAllByNameAndCode(ssname,sscode);
//        }

        return books;
    }

    public static String post(Books currentBook) {
        String msg = "";
        String err = "";

        Books res = BookDao.getByCode(currentBook.getCode());

        if(res != null) err = err + "\nCode Exists";

        if(err.isEmpty()) {
            String status = BookDao.save(currentBook);
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
}
