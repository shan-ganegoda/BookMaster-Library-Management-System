package lk.projects.library.service;

import lk.projects.library.dao.BookDao;
import lk.projects.library.entity.Books;

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
}
