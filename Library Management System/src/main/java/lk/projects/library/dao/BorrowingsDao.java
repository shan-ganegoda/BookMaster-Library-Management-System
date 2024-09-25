package lk.projects.library.dao;


import lk.projects.library.entity.Borrowings;

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

}
