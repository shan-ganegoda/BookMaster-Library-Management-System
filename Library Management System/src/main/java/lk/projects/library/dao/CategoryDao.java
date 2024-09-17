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

    public static String update(Category category) {
        String qry = "UPDATE category set name='" + category.getName() +"',code='" + category.getCode() +"' WHERE id=" + category.getId();
        return CommonDao.modify(qry);
    }

    public static String delete(int id) {
        String qry = "DELETE FROM category WHERE id=" + id;
        return CommonDao.modify(qry);
    }
}
