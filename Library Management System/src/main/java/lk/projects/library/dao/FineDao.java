package lk.projects.library.dao;

import lk.projects.library.entity.Fine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FineDao {

    public static List<Fine> get(String qry) {
        List<Fine> fineList = new ArrayList<Fine>();

        try{
            ResultSet result = CommonDao.get(qry);

            while (result.next()) {
                Fine fine = new Fine();
                fine.setId(result.getInt("id"));
                fine.setFine(result.getDouble("fine"));
                fine.setLatedays(result.getInt("latedays"));
                fine.setFineStatus( FineStatusDao.getById( result.getInt("finestatus_id")  )  );
                fine.setBorrowings( BorrowingsDao.getById( result.getInt("borrowings_id")  )  );
                fineList.add(fine);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return fineList;
    }

    public static Fine getBy(String qry) {
        Fine fine = new Fine();

        try{
            ResultSet result = CommonDao.get(qry);

            result.next();

            fine.setId(result.getInt("id"));
            fine.setFine(result.getDouble("fine"));
            fine.setLatedays(result.getInt("latedays"));
            fine.setFineStatus( FineStatusDao.getById( result.getInt("finestatus_id")  )  );
            fine.setBorrowings( BorrowingsDao.getById( result.getInt("borrowings_id")  )  );
            return fine;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public static Fine getById(int id) {
        String qry = "select * from fine where id=" + id;
        return getBy(qry);
    }

    public static List<Fine> getAll() {
        String qry = "SELECT * FROM fine";
        return get(qry);
    }

    public static Fine getByBorrowing(Integer borrowingid) {
        String qry = "SELECT * FROM fine where borrowings_id=" + borrowingid;
        return getBy(qry);
    }

    public static String save(Fine fine) {
        String qry = "INSERT INTO fine (fine,latedays,finestatus_id,borrowings_id) VALUES('" +
                fine.getFine() +"','" +
                fine.getLatedays()+"','" +
                fine.getFineStatus().getId()+"','" +
                fine.getBorrowings().getId() +"')";

        return CommonDao.modify(qry);
    }


    public static String update(Fine fine) {
        String qry = "UPDATE fine set fine='" + fine.getFine() +
                "',latedays=" + fine.getLatedays() +
                ",borrowings_id=" + fine.getBorrowings().getId() +
                ",finestatus_id=" + fine.getFineStatus().getId() +
                " WHERE id=" + fine.getId();
        return CommonDao.modify(qry);
    }

    public static String delete(Integer id) {
        String qry = "DELETE FROM fine WHERE id=" + id;
        return CommonDao.modify(qry);
    }

    public static List<Fine> getAllByBorrowing(Integer borrowingid) {
        String qry = "SELECT * FROM fine where borrowings_id =" + borrowingid;
        return get(qry);
    }
}
