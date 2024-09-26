package lk.projects.library.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import lk.projects.library.dao.BookDao;
import lk.projects.library.dao.CategoryDao;
import lk.projects.library.dao.HomeDao;
import lk.projects.library.dao.MemberDao;
import lk.projects.library.entity.Books;
import lk.projects.library.entity.Category;
import lk.projects.library.entity.ChartData;
import lk.projects.library.entity.Member;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private BarChart<String,Number> chrMembers;
    @FXML
    private Label lblMemberCount;
    @FXML
    private Label lblBookCount;
    @FXML
    private Label lblCategoryCount;

    ObservableList<Books> books;
    ObservableList<Category> categories;
    ObservableList<Member> members;
    ObservableList<ChartData> chartDatas;

    Integer memberCount =0;
    Integer bookCount=0;
    Integer categoryCount=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        categories = FXCollections.observableList(CategoryDao.getAll());
        books = FXCollections.observableList(BookDao.getAll());
        members = FXCollections.observableList(MemberDao.getAll());
        chartDatas = FXCollections.observableList(HomeDao.getAll());

        if(!members.isEmpty()){
            memberCount = members.size();
        }

        if(!books.isEmpty()){
            bookCount = books.size();
        }

        if(!categories.isEmpty()){
            categoryCount = categories.size();
        }

        lblMemberCount.setText(String.valueOf(memberCount));
        lblBookCount.setText(String.valueOf(bookCount));
        lblCategoryCount.setText(String.valueOf(categoryCount));

        loadChart();

    }

    public void loadChart(){

        chrMembers.setTitle("Members Registration By Month");
        chrMembers.setLegendVisible(false);

        XYChart.Series<String,Number> series = new XYChart.Series<>();
        series.setName("Members Count");

        for (ChartData data : chartDatas) {
            if (data.getMonth() != null && data.getCount() != 0) {
                series.getData().add(new XYChart.Data<>(data.getMonth(), data.getCount()));
            }
        }

        chrMembers.getData().add(series);
    }
}
