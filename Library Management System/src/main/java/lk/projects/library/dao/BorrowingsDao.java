package lk.projects.library.dao;


import lk.projects.library.entity.Borrowings;
import lk.projects.library.entity.Fine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowingsDao {

    public static List<Borrowings> get(String qry) {
        List<Borrowings> borrowingsList = new ArrayList<Borrowings>();

        try{
            ResultSet result = CommonDao.get(qry);

            while (result.next()) {
                Borrowings borrowings = new Borrowings();
                borrowings.setId(result.getInt("id"));
                borrowings.setCode(result.getString("code"));
                borrowings.setDoborrowed(LocalDate.parse( result.getObject("doborrowed").toString() )  );
                borrowings.setDohandedover(LocalDate.parse( result.getObject("dohandedover").toString() )  );
                borrowings.setMember( MemberDao.getById( result.getInt("member_id")  )  );
                borrowings.setBorrowStatus( BorrowStatusDao.getById( result.getInt("borrowstatus_id")  )  );
                borrowings.setBooks( BookDao.getById( result.getInt("books_id")  )  );
                borrowingsList.add(borrowings);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return borrowingsList;
    }

    public static Borrowings getBy(String qry) {
        Borrowings borrowings = new Borrowings();

        try{
            ResultSet result = CommonDao.get(qry);

            result.next();

            borrowings.setId(result.getInt("id"));
            borrowings.setCode(result.getString("code"));
            borrowings.setDoborrowed(LocalDate.parse( result.getObject("doborrowed").toString() )  );
            borrowings.setDohandedover(LocalDate.parse( result.getObject("dohandedover").toString() )  );
            borrowings.setMember( MemberDao.getById( result.getInt("member_id")  )  );
            borrowings.setBorrowStatus( BorrowStatusDao.getById( result.getInt("borrowstatus_id")  )  );
            borrowings.setBooks( BookDao.getById( result.getInt("books_id")  )  );
            return borrowings;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public static Borrowings getById(int id) {
        String qry = "select * from borrowings where id=" + id;
        return getBy(qry);
    }

    public static List<Borrowings> getAll() {
        String qry = "SELECT * FROM borrowings";
        return get(qry);
    }

    public static List<Borrowings> getAllCon() {
        String qry = "SELECT * FROM borrowings where borrowstatus_id = 2";
        return get(qry);
    }

    public static Borrowings getByCode(String code) {
        String qry = "select * from borrowings where code='" + code +"'";
        return getBy(qry);
    }

    public static String save(Borrowings borrowings) {
        String qry = "INSERT INTO borrowings (code,doborrowed,dohandedover,books_id,member_id,borrowstatus_id) VALUES('" +
                borrowings.getCode()+"','" +
                borrowings.getDoborrowed()+"','" +
                borrowings.getDohandedover()+"','" +
                borrowings.getBooks().getId()+"','" +
                borrowings.getMember().getId()+"','" +
                borrowings.getBorrowStatus().getId() +"')";

        return CommonDao.modify(qry);
    }

    public static String update(Borrowings borrowings) {
        String qry = "UPDATE borrowings set doborrowed='" + borrowings.getDoborrowed() +
                "',code='" + borrowings.getCode() +
                "',dohandedover='" + borrowings.getDohandedover() +
                "',member_id='" + borrowings.getMember().getId() +
                "',books_id='" + borrowings.getBooks().getId() +
                "',borrowstatus_id='" + borrowings.getBorrowStatus().getId() +
                "' WHERE id=" + borrowings.getId();
        return CommonDao.modify(qry);
    }

    public static String delete(Integer id) {
        String qry = "DELETE FROM borrowings WHERE id=" + id;
        return CommonDao.modify(qry);
    }

    public static List<Borrowings> getAllByMember(Integer ssmember) {
        String qry = "SELECT * FROM borrowings where member_id =" + ssmember;
        return get(qry);
    }

    public static List<Borrowings> getAllByCode(String sscode) {
        String qry = "SELECT * FROM borrowings where code = '" + sscode + "'";
        return get(qry);
    }

    public static List<Borrowings> getAllByBook(Integer ssbook) {
        String qry = "SELECT * FROM borrowings where books_id =" + ssbook;
        return get(qry);
    }

    public static List<Borrowings> getByMemberAndCode(Integer ssmember, String sscode) {
        String qry = "SELECT * FROM borrowings where code = '" + sscode + "' and member_id =" + ssmember;
        return get(qry);
    }

    public static List<Borrowings> getByMemberAndBook(Integer ssmember, Integer ssbook) {
        String qry = "SELECT * FROM borrowings where member_id = " + ssmember + " and books_id =" + ssbook;
        return get(qry);
    }

    public static List<Borrowings> getByCodeAndBook(String sscode, Integer ssbook) {
        String qry = "SELECT * FROM borrowings where code = '" + sscode + "' and books_id =" + ssbook;
        return get(qry);
    }

    public static List<Borrowings> getAllByMemberAndCodeAndBook(Integer ssmember, String sscode, Integer ssbook) {
        String qry = "SELECT * FROM borrowings where code = '" + sscode + "' and member_id = " + ssmember + " and books_id =" + ssbook;
        return get(qry);
    }
}
