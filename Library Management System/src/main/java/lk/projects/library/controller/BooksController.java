package lk.projects.library.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.projects.library.dao.BookDao;
import lk.projects.library.dao.CategoryDao;
import lk.projects.library.entity.Books;
import lk.projects.library.entity.Category;
import lk.projects.library.entity.Language;
import lk.projects.library.entity.User;
import lk.projects.library.service.BookService;

import java.net.URL;
import java.util.ResourceBundle;

public class BooksController implements Initializable {

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtPublisher;

    @FXML
    private TextField txtYear;

    @FXML
    private TextField txtIsbn;

    @FXML
    private TextField txtPages;

    @FXML
    private DatePicker txtAdded;

    @FXML
    private ComboBox<Category> cmbCategory;

    @FXML
    private ComboBox<Language> cmbLanguage;

    @FXML
    private ComboBox<User> cmbUser;

    @FXML
    private TableView<Books> tblBooks;

    @FXML
    private TableColumn<Books, String> idCol;

    @FXML
    private TableColumn<Books, String> titleCol;

    @FXML
    private TableColumn<Books, String> authorCol;

    @FXML
    private TableColumn<Books, String> codeCol;

    @FXML
    private TableColumn<Books, String> yearCol;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    ObservableList<User> users;
    ObservableList<Category> categories;
    ObservableList<Language> languages;
    ObservableList<Books> books;

    Books currentBook;
    Books oldBook;

    Alert alert;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("yopublication"));
        tblBooks.setOnMouseClicked(e -> {
            tblMC(e);
        });

        loadTable();
    }

    private void loadTable() {
        books = FXCollections.observableList(BookDao.getAll());
        fillTable();
    }

    private void fillTable(){
        tblBooks.setItems(books);
    }

    public void enableButtons(boolean add,boolean update,boolean delete){
        btnAdd.setDisable(!add);
        btnUpdate.setDisable(!update);
        btnDelete.setDisable(!delete);
    }

    private void tblMC(javafx.scene.input.MouseEvent e) {
        int row = tblBooks.getSelectionModel().getSelectedIndex();
        if (row > -1) {
            Books book = books.get(row);
            //fillForm(book);
        }
    }
}
