package lk.projects.library.dao;

import lk.projects.library.entity.Category;
import lk.projects.library.entity.ChartData;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HomeDao {

    public static List<ChartData> getAll(){

        List<ChartData> chartDataList = new ArrayList<ChartData>();

        String qry = "SELECT \n" +
                "    DATE_FORMAT(STR_TO_DATE(CONCAT(YEAR(CURDATE()), '-', LPAD(m.m, 2, '0'), '-01'), '%Y-%m-%d'), '%M %Y') AS month_name,\n" +
                "    COUNT(member.id) AS member_count\n" +
                "FROM \n" +
                "    (SELECT MONTH(CURDATE()) AS m \n" +
                "     UNION SELECT MONTH(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))\n" +
                "     UNION SELECT MONTH(DATE_SUB(CURDATE(), INTERVAL 2 MONTH))\n" +
                "     UNION SELECT MONTH(DATE_SUB(CURDATE(), INTERVAL 3 MONTH))\n" +
                "     UNION SELECT MONTH(DATE_SUB(CURDATE(), INTERVAL 4 MONTH))\n" +
                "     UNION SELECT MONTH(DATE_SUB(CURDATE(), INTERVAL 5 MONTH))) AS m\n" +
                "LEFT JOIN \n" +
                "    member ON MONTH(member.doregistered) = m.m \n" +
                "    AND member.doregistered >= DATE_SUB(CURDATE(), INTERVAL 5 MONTH)\n" +
                "    AND member.doregistered <= LAST_DAY(CURDATE())\n" +
                "GROUP BY \n" +
                "    m.m\n" +
                "ORDER BY \n" +
                "    FIELD(m.m, MONTH(DATE_SUB(CURDATE(), INTERVAL 5 MONTH)), \n" +
                "               MONTH(DATE_SUB(CURDATE(), INTERVAL 4 MONTH)), \n" +
                "               MONTH(DATE_SUB(CURDATE(), INTERVAL 3 MONTH)),\n" +
                "               MONTH(DATE_SUB(CURDATE(), INTERVAL 2 MONTH)),\n" +
                "               MONTH(DATE_SUB(CURDATE(), INTERVAL 1 MONTH)),\n" +
                "               MONTH(CURDATE()))";

        try{

            ResultSet result = CommonDao.get(qry);

            while (result.next()) {
                ChartData data = new ChartData();
                data.setMonth(result.getString("month_name"));
                data.setCount(result.getLong("member_count"));
                chartDataList.add(data);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return chartDataList;
    }
}
