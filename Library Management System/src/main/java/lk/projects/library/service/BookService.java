package lk.projects.library.service;

import lk.projects.library.dao.BookDao;
import lk.projects.library.dao.MemberDao;
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

        String sstitle = params.get("sstitle");
        String sscode = params.get("sscode");
        String ssauthor = params.get("ssauthor");

        if(sstitle.isEmpty() && sscode.isEmpty() && ssauthor.isEmpty()){
            books = BookDao.getAll();
        }

        if(!sstitle.isEmpty() && sscode.isEmpty() && ssauthor.isEmpty()){
            books = BookDao.getAllByTitle(sstitle);
        }

        if(sstitle.isEmpty() && !sscode.isEmpty() && ssauthor.isEmpty()){
            books = BookDao.getAllByCode(sscode);
        }

        if(sstitle.isEmpty() && sscode.isEmpty() && !ssauthor.isEmpty()){
            books = BookDao.getAllByAuthor(ssauthor);
        }

        if(!sstitle.isEmpty() && !sscode.isEmpty() && ssauthor.isEmpty()){
            books = BookDao.getByTitleAndCode(sstitle,sscode);
        }

        if(!sstitle.isEmpty() && sscode.isEmpty() && !ssauthor.isEmpty()){
            books = BookDao.getByTitleAndAuthor(sstitle,ssauthor);
        }

        if(sstitle.isEmpty() && !sscode.isEmpty() && !ssauthor.isEmpty()){
            books = BookDao.getByCodeAndAuthor(sscode,ssauthor);
        }

        if(!sstitle.isEmpty() && !sscode.isEmpty() && !ssauthor.isEmpty()){
            books = BookDao.getAllByTitleAndCodeAndAuthor(sstitle,sscode,ssauthor);
        }

        return books;
    }

    public static String post(Books currentBook) {
        String msg = "";
        String err = "";

        Books res = BookDao.getByCode(currentBook.getCode());
        Books rest = BookDao.getByIsbn(currentBook.getIsbn());

        if(res != null) err = err + "\nCode Exists";
        if(rest != null) err = err + "\nIsbn Exists";

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

    public static String put(Books currentBook) {
        String msg = "";
        String err = "";

        Books res = BookDao.getByCode(currentBook.getCode());
        Books rest = BookDao.getByIsbn(currentBook.getIsbn());

        if( res != null && !res.getCode().equals(currentBook.getCode()) ) err = err + "\nCode Exists";
        if( rest != null && !rest.getIsbn().equals(currentBook.getIsbn()) ) err = err + "\nIsbn Exists";

        if(err.isEmpty()) {
            String status = BookDao.update(currentBook);
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

    public static String delete(Books oldBook) {
        String msg = "";

        String dberror = BookDao.delete(oldBook.getId());
        if(dberror.equals("1")) {
            msg = "Success";
        }else{
            msg = "Fail caused by :" + dberror;
        }

        return msg;
    }
}
