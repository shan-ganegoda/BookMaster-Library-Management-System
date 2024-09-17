package lk.projects.library.dao;

import lk.projects.library.entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {

    public static Category getByCode(String code) {
        Category category = new Category();

        try{
            String qry = "SELECT * FROM category where code='" + code +"'";
            System.out.println(qry);
            ResultSet result = CommonDao.get(qry);

            result.next();
                category.setId(result.getInt("id"));
                category.setName(result.getString("name"));
                category.setCode(result.getString("code"));

            return category;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public static List<Category> getAll() {
        List<Category> categoryList = new ArrayList<Category>();

        try{
            String qry = "SELECT * FROM category";
            ResultSet result = CommonDao.get(qry);

            while (result.next()) {
                Category category = new Category();
                category.setId(result.getInt("id"));
                category.setName(result.getString("name"));
                category.setCode(result.getString("code"));
                categoryList.add(category);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return categoryList;
    }

    public static String save(Category category) {
        String qry = "INSERT INTO category(name,code) VALUES('" +category.getName() +"','" +category.getCode()+"')";
        return CommonDao.modify(qry);
    }
}
