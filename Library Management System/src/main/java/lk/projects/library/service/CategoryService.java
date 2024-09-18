package lk.projects.library.service;

import lk.projects.library.dao.CategoryDao;
import lk.projects.library.entity.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryService {

    public static String post(Category category) {
        String msg = "";
        String err = "";

        Category res = CategoryDao.getByCode(category.getCode());

        if(res != null) err = err + "\nCode Exists";

        if(err.isEmpty()) {
           String status = CategoryDao.save(category);
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

    public static String put(Category category) {
        String msg = "";
        String err = "";

        Category res = CategoryDao.getByCode(category.getCode());

        if( res != null && !res.getCode().equals(category.getCode()) ) err = err + "\nCode Exists";

        if(err.isEmpty()) {
            String status = CategoryDao.update(category);
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

    public static String delete(Category category) {
        String msg = "";

        String dberror = CategoryDao.delete(category.getId());
        if(dberror.equals("1")) {
            msg = "Success";
        }else{
            msg = "Fail caused by :" + dberror;
        }

        return msg;
    }

    public static List<Category> get(HashMap<String, String> params) {

        List<Category> categories = new ArrayList<Category>();

        if(params.isEmpty()){
            categories = CategoryDao.getAll();
        }

        String ssname = params.get("ssname");
        String sscode = params.get("sscode");

        if(ssname.equals("") && sscode.equals("")){
            categories = CategoryDao.getAll();
        }

        if(!ssname.equals("") && sscode.equals("")){
            categories = CategoryDao.getAllByName(ssname);
        }

        if(ssname.equals("") && !sscode.equals("")){
            categories = CategoryDao.getAllByCode(sscode);
        }

        if(!ssname.equals("") && !sscode.equals("")){
            categories = CategoryDao.getAllByNameAndCode(ssname,sscode);
        }

        return categories;
    }
}
