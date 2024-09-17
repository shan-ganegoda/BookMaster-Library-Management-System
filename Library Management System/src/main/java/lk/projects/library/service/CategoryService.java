package lk.projects.library.service;

import lk.projects.library.dao.CategoryDao;
import lk.projects.library.entity.Category;

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
}
