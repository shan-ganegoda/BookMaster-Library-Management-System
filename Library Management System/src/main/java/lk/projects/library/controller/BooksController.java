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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
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

    @FXML
    private TextField txtSearchTitle;

    @FXML
    private TextField txtSearchCode;

    @FXML
    private TextField txtSearchAuthor;

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
        enableButtons(true,false,false);
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

    public String getErrors(){
        String errors = "";

        if(currentBook.getTitle() == null || currentBook.getTitle().isEmpty()){
            errors += "\nInvalid Title";
        }
        if(currentBook.getCode() == null || currentBook.getCode().isEmpty()){
            errors += "\nInvalid Code";
        }
        if(currentBook.getAuthor() == null || currentBook.getAuthor().isEmpty()){
            errors += "\nInvalid Author";
        }
        if(currentBook.getPublisher() == null || currentBook.getPublisher().isEmpty()){
            errors += "\nInvalid Publisher";
        }
        if(currentBook.getYopublication() == null || currentBook.getYopublication() == 0){
            errors += "\nInvalid Year Of Publication";
        }
        if(currentBook.getIsbn() == null || currentBook.getIsbn().isEmpty()){
            errors += "\nInvalid Isbn";
        }
        if(currentBook.getPages() == null || currentBook.getPages() == 0){
            errors += "\nInvalid Pages";
        }
        if(currentBook.getDoadded() == null){
            errors += "\nInvalid Date of Added";
        }
        if(currentBook.getCategory() == null){
            errors += "\nInvalid Category";
        }
        if(currentBook.getLanguage() == null){
            errors += "\nInvalid Language";
        }
        if(currentBook.getUser() == null){
            errors += "\nInvalid User";
        }

        return errors;
    }

    public String getUpdates(){
        String updates = "";

        if(!currentBook.getTitle().equals(oldBook.getTitle())){
            updates += "\nTitle Updated ";
        }
        if(!currentBook.getCode().equals(oldBook.getCode())){
            updates += "\nCode Updated ";
        }
        if(!currentBook.getAuthor().equals(oldBook.getAuthor())){
            updates += "\nAuthor Updated ";
        }
        if(!currentBook.getPublisher().equals(oldBook.getPublisher())){
            updates += "\nPublisher Updated ";
        }
        if(!currentBook.getYopublication().equals(oldBook.getYopublication())){
            updates += "\nYear of Publication Updated ";
        }
        if(!currentBook.getIsbn().equals(oldBook.getIsbn())){
            updates += "\nIsbn Updated ";
        }
        if(!currentBook.getPages().equals(oldBook.getPages())){
            updates += "\nPages Updated ";
        }
        if(!currentBook.getDoadded().equals(oldBook.getDoadded())){
            updates += "\nDate of Added Updated ";
        }
        if(!currentBook.getCategory().getId().equals(oldBook.getCategory().getId())){
            updates += "\nCategory Updated ";
        }
        if(!currentBook.getLanguage().getId().equals(oldBook.getLanguage().getId())){
            updates += "\nLanguage Updated ";
        }
        if(!currentBook.getUser().getId().equals(oldBook.getUser().getId())){
            updates += "\nUser Updated ";
        }

        return updates;
    }

    public void loadFormData(){
        String title = txtTitle.getText();;
        String code = txtCode.getText();
        String author = txtAuthor.getText();
        String publisher = txtPublisher.getText();
        String yopublish = txtYear.getText();
        String isbn = txtIsbn.getText();
        String pages = txtPages.getText();
        LocalDate doadded = txtAdded.getValue();
        Category selectedCategory = cmbCategory.getSelectionModel().getSelectedItem();
        Language selectedLanguage = cmbLanguage.getSelectionModel().getSelectedItem();
        User selectedUser = cmbUser.getSelectionModel().getSelectedItem();

        int yopublishnum = 0;
        int pagesnum = 0;

        if(!yopublish.isEmpty()){
            yopublishnum = Integer.parseInt(yopublish);
        }

        if(!pages.isEmpty()){
            pagesnum = Integer.parseInt(pages);
        }

        currentBook = Books.builder()
                .title(title)
                .code(code)
                .author(author)
                .publisher(publisher)
                .yopublication(yopublishnum)
                .isbn(isbn)
                .pages(pagesnum)
                .doadded(doadded)
                .category(selectedCategory)
                .language(selectedLanguage)
                .user(selectedUser)
                .build();
    }

    public void add(){
        loadFormData();

        String errors = getErrors();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("BookMaster");
        alert.setHeaderText("Books Module");

        if(errors.isEmpty()){
            String confmsg = "Are you sure to Proceed?\n";
            alert.setContentText(confmsg);

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK){
                String status = BookService.post(currentBook);
                if(status.equals("Success")){
                    loadView();
                    clearForm();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("BookMaster");
                    alert.setHeaderText("Books Module");
                    alert.setContentText("Successfully Saved");
                    alert.show();
                }else{
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("BookMaster");
                    alert.setHeaderText("Books Module");
                    alert.setContentText("Failed to save as \n\n" + status);
                    alert.show();
                }
            }
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("BookMaster");
            alert.setHeaderText("Books Module");
            alert.setContentText("You have Errors:" + errors);
            alert.show();
        }
    }

    public void update(){
        loadFormData();
        currentBook.setId(oldBook.getId());

        String errors = getErrors();

        if(errors.isEmpty()){
            String updates = getUpdates();

            if(!updates.isEmpty()){
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("BookMaster");
                a.setHeaderText("Books Module - Update");
                a.setContentText("You have following Updates \n\n" + updates);

                Optional<ButtonType> result = a.showAndWait();
                if(result.get() == ButtonType.OK){
                    String status = BookService.put(currentBook);
                    if(status.equals("Success")){
                        loadView();
                        clearForm();

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("BookMaster");
                        alert.setHeaderText("Books Module - Update");
                        alert.setContentText("Successfully Updated");
                        alert.show();
                    }else{
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("BookMaster");
                        alert.setHeaderText("Books Module - Update");
                        alert.setContentText("Failed to Update as \n\n" + status);
                        alert.show();
                    }
                }
            }else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Books Module - Update");
                alert.setContentText("Nothing To Update");
                alert.show();
            }
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("BookMaster");
            alert.setHeaderText("Books Module - Update");
            alert.setContentText("You Have Following Errors:\n\n" + errors);
            alert.show();
        }
    }

    public void delete(){
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("BookMaster");
        alert.setHeaderText("Books Module - Delete");
        alert.setContentText("Are you sure to Delete ?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK){
            String status = BookService.delete(oldBook);
            if(status.equals("Success")){
                loadView();
                clearForm();
                enableButtons(true,false,false);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Books Module");
                alert.setContentText("Book Successfully Deleted");
                alert.show();

            }else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Books Module - Delete");
                alert.setContentText("Failed Due to :\n\n" + status);
                alert.show();
            }

        }
    }

    public void handleSearch(){
        String sstitle = txtSearchTitle.getText();
        String sscode = txtSearchCode.getText();
        String ssauthor = txtSearchAuthor.getText();

        HashMap<String,String> params = new HashMap<>();
        params.put("sstitle",sstitle);
        params.put("sscode",sscode);
        params.put("ssauthor",ssauthor);

        books = FXCollections.observableList(BookService.get(params));
        fillTable();
    }

    public void searchClear(){
        txtSearchTitle.clear();
        txtSearchCode.clear();
        loadView();
    }

    public void clearForm(){
        txtTitle.clear();
        txtCode.clear();
        txtAuthor.clear();
        txtPublisher.clear();
        txtYear.clear();
        txtIsbn.clear();
        txtPages.clear();
        txtAdded.setValue(null);
        cmbCategory.setValue(null);
        cmbLanguage.setValue(null);
        cmbUser.setValue(null);

        enableButtons(true,false,false);
    }
}
