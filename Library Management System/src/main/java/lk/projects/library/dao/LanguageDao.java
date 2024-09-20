package lk.projects.library.dao;

import lk.projects.library.entity.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LanguageDao {

    public static List<Language> getAll() {
        List<Language> languageList = new ArrayList<Language>();

        try{
            String qry = "SELECT * FROM language";
            ResultSet result = CommonDao.get(qry);

            while (result.next()) {
                Language language = new Language();
                language.setId(result.getInt("id"));
                language.setName(result.getString("name"));
                languageList.add(language);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return languageList;
    }

    public static Language getById(int id){

        Language language = new Language();

        try {

            String qry = "Select * from language where id ="+id;
            ResultSet result = CommonDao.get(qry);

            result.next();
            language.setId(result.getInt("id"));
            language.setName( result.getObject("name").toString() );
        }

        catch(SQLException e){
            System.out.println("Can't Connect as : "+ e.getMessage());

        }
        return language;

    }

}
