package lk.projects.library.dao;

import lk.projects.library.entity.Books;
import lk.projects.library.entity.Category;

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

    public static Books getByCode(String code) {
        String qry = "SELECT * FROM books where code='" + code +"'";
        return getBy(qry);
    }

    public static Books getByIsbn(String isbn) {
        String qry = "SELECT * FROM books where isbn='" + isbn +"'";
        return getBy(qry);
    }

    public static String save(Books books) {
        String qry = "INSERT INTO books (title,code,author,publisher,yopublication,isbn,pages,doadded,category_id,language_id,user_id) VALUES('" +
                books.getTitle() +"','" +
                books.getCode()+"','" +
                books.getAuthor()+"','" +
                books.getPublisher()+"','" +
                books.getYopublication()+"','" +
                books.getIsbn()+"','" +
                books.getPages()+"','" +
                books.getDoadded()+"','" +
                books.getCategory().getId()+"','" +
                books.getLanguage().getId()+"','" +
                books.getUser().getId() +"')";

        return CommonDao.modify(qry);
    }


    public static String update(Books currentBook) {
        String qry = "UPDATE books set title='" + currentBook.getTitle() +
                "',code='" + currentBook.getCode() +
                "',author='" + currentBook.getAuthor() +
                "',publisher='" + currentBook.getPublisher() +
                "',yopublication='" + currentBook.getYopublication() +
                "',isbn='" + currentBook.getIsbn() +
                "',pages='" + currentBook.getPages() +
                "',doadded='" + currentBook.getDoadded() +
                "',category_id='" + currentBook.getCategory().getId() +
                "',language_id='" + currentBook.getLanguage().getId() +
                "',user_id='" + currentBook.getUser().getId() +
                "' WHERE id=" + currentBook.getId();
        return CommonDao.modify(qry);
    }

    public static String delete(Integer id) {
        String qry = "DELETE FROM books WHERE id=" + id;
        return CommonDao.modify(qry);
    }

    public static List<Books> getAllByTitle(String title) {
        String qry = "SELECT * FROM books where title like '" + title + "%'";
        return get(qry);
    }

    public static List<Books> getAllByCode(String code) {
        String qry = "SELECT * FROM books where code ='" + code + "'";
        return get(qry);
    }

    public static List<Books> getAllByAuthor(String author) {
        String qry = "SELECT * FROM books where author Like '" + author + "%'";
        return get(qry);
    }

    public static List<Books> getByTitleAndCode(String sstitle, String sscode) {
        String qry = "SELECT * FROM books where title Like '" + sstitle + "%' AND code ='" + sscode + "'";
        return get(qry);
    }

    public static List<Books> getByTitleAndAuthor(String sstitle, String ssauthor) {
        String qry = "SELECT * FROM books where title Like '" + sstitle + "%' AND author Like '" + ssauthor + "%'";
        return get(qry);
    }

    public static List<Books> getByCodeAndAuthor(String sscode, String ssauthor) {
        String qry = "SELECT * FROM books where code = '" + sscode + "' AND author Like '" + ssauthor + "%'";
        return get(qry);
    }

    public static List<Books> getAllByTitleAndCodeAndAuthor(String sstitle, String sscode, String ssauthor) {
        String qry = "SELECT * FROM books where code = '" + sscode + "' AND author Like '" + ssauthor + "%' AND title Like '" + sstitle + "%'";
        return get(qry);
    }
}
