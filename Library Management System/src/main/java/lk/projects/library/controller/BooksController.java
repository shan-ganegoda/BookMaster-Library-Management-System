package lk.projects.library.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import lk.projects.library.dao.BookDao;
import lk.projects.library.dao.CategoryDao;
import lk.projects.library.dao.UserDao;
import lk.projects.library.entity.Books;
import lk.projects.library.entity.Category;
import lk.projects.library.entity.Language;
import lk.projects.library.entity.User;
import lk.projects.library.service.BookService;
import lk.projects.library.service.CategoryService;
import lk.projects.library.service.LanguageService;

import java.net.URL;
import java.util.Objects;
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
        yearCol.setCellValueFactory(new PropertyValueFactory<>("yopublication"));
        tblBooks.setOnMouseClicked(e -> {
            tblMC(e);
        });

        categories = FXCollections.observableArrayList(CategoryDao.getAll());
        languages = FXCollections.observableArrayList(LanguageService.get());
        users = FXCollections.observableArrayList(UserDao.getAll());

        // Set converter to display only the name in the ComboBox
        cmbCategory.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category category) {
                return category != null ? category.getName() : "";
            }

            @Override
            public Category fromString(String string) {
                return cmbCategory.getItems().stream()
                        .filter(category -> category.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        // Set converter to display only the name in the ComboBox in cmbLanguage
        cmbLanguage.setConverter(new StringConverter<Language>() {
            @Override
            public String toString(Language language) {
                return language != null ? language.getName() : "";
            }

            @Override
            public Language fromString(String string) {
                return cmbLanguage.getItems().stream()
                        .filter(language -> language.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        // Set converter to display only the name in the ComboBox in cmbLanguage
        cmbUser.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user != null ? user.getFullname() : "";
            }

            @Override
            public User fromString(String string) {
                return cmbUser.getItems().stream()
                        .filter(language -> language.getFullname().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        loadView();
    }

    private void loadView() {
        books = FXCollections.observableList(BookDao.getAll());
        fillTable();

        cmbCategory.setItems(categories);
        cmbLanguage.setItems(languages);
        cmbUser.setItems(users);
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
            fillForm(book);
        }
    }

    public void fillForm(Books books) {

        oldBook = books;

        currentBook = Books.builder()
                .id(books.getId())
                .title(books.getTitle())
                .code(books.getCode())
                .author(books.getAuthor())
                .publisher(books.getPublisher())
                .yopublication(books.getYopublication())
                .isbn(books.getIsbn())
                .pages(books.getPages())
                .doadded(books.getDoadded())
                .category(books.getCategory())
                .language(books.getLanguage())
                .user(books.getUser())
                .build();


        txtTitle.setText(books.getTitle());;
        txtCode.setText(books.getCode());
        txtAuthor.setText(books.getAuthor());
        txtPublisher.setText(books.getPublisher());
        txtYear.setText(books.getYopublication().toString());
        txtIsbn.setText(books.getIsbn());
        txtPages.setText(books.getPages().toString());
        txtAdded.setValue(books.getDoadded());

        for (Category cat : categories) {
            if (Objects.equals(cat.getId(), books.getCategory().getId())) {
                cmbCategory.setValue(cat);
                break;
            }
        }

        for (Language lang : languages) {
            if (Objects.equals(lang.getId(), books.getLanguage().getId())) {
                cmbLanguage.setValue(lang);
                break;
            }
        }

        for (User usr : users) {
            if (Objects.equals(usr.getId(), books.getUser().getId())) {
                cmbUser.setValue(usr);
                break;
            }
        }

        enableButtons(false,true,true);

    }
}
