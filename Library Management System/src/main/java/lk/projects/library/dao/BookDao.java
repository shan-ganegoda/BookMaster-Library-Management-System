package lk.projects.library.dao;

import lk.projects.library.entity.Books;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    public static List<Books> get(String qry) {
        List<Books> bookList = new ArrayList<Books>();

        try{
            ResultSet result = CommonDao.get(qry);

            while (result.next()) {
                Books books = new Books();
                books.setId(result.getInt("id"));
                books.setTitle(result.getString("title"));
                books.setAuthor(result.getString("author"));
                books.setCode(result.getString("code"));
                books.setPublisher(result.getString("publisher"));
                books.setYopublication(result.getInt("yopublication"));
                books.setIsbn(result.getString("isbn"));
                books.setPages(result.getInt("pages"));
                books.setDoadded(LocalDate.parse( result.getObject("doadded").toString() )  );
                books.setCategory( CategoryDao.getById( result.getInt("category_id")  )  );
                books.setLanguage( LanguageDao.getById( result.getInt("language_id")  )  );
                books.setUser( UserDao.getById( result.getInt("user_id")  )  );
                bookList.add(books);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return bookList;
    }

    public static Books getBy(String qry) {
        Books book = new Books();

        try{
            ResultSet result = CommonDao.get(qry);

            result.next();

            book.setId(result.getInt("id"));
            book.setTitle(result.getString("title"));
            book.setAuthor(result.getString("author"));
            book.setCode(result.getString("code"));
            book.setPublisher(result.getString("publisher"));
            book.setYopublication(result.getInt("yopublication"));
            book.setIsbn(result.getString("isbn"));
            book.setPages(result.getInt("pages"));
            book.setDoadded(LocalDate.parse( result.getObject("doadded").toString() )  );
            book.setCategory( CategoryDao.getById( result.getInt("category_id")  )  );
            book.setLanguage( LanguageDao.getById( result.getInt("language_id")  )  );
            book.setUser( UserDao.getById( result.getInt("user_id")  )  );
            return book;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public static Books getById(int id) {
        String qry = "select * from books where id=" + id;
        return getBy(qry);
    }

    public static List<Books> getAll() {
        String qry = "SELECT * FROM books";
        return get(qry);
    }
}
