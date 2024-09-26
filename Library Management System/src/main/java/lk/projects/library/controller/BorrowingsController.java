package lk.projects.library.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import lk.projects.library.dao.*;
import lk.projects.library.entity.*;
import lk.projects.library.service.BookService;
import lk.projects.library.service.BorrowingsService;
import lk.projects.library.service.LanguageService;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class BorrowingsController implements Initializable {

    @FXML
    private TextField txtSearchCode;
    @FXML
    private ComboBox<Member> cmbSearchMember;
    @FXML
    private ComboBox<Books> cmbSearchBook;
    @FXML
    private TableView<Borrowings> tblBorrowings;
    @FXML
    private TableColumn<Borrowings,String> idCol;
    @FXML
    private TableColumn<Borrowings,String> codeCol;
    @FXML
    private TableColumn<Borrowings,String> doborrowedCol;
    @FXML
    private TableColumn<Borrowings,String> handoverCol;
    @FXML
    private TableColumn<Borrowings,String> statusCol;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtCode;
    @FXML
    private DatePicker txtDoBorrowed;
    @FXML
    private DatePicker txtDoHandover;
    @FXML
    private ComboBox<BorrowStatus> cmbBorrowingStatus;
    @FXML
    private ComboBox<Books> cmbBook;
    @FXML
    private ComboBox<Member> cmbMember;

    ObservableList<Member> members;
    ObservableList<Borrowings> borrowings;
    ObservableList<Books> books;
    ObservableList<BorrowStatus> borrowStatuses;

    Alert alert;

    Borrowings currentBorrowing;
    Borrowings oldBorrowing;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        doborrowedCol.setCellValueFactory(new PropertyValueFactory<>("doborrowed"));
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        handoverCol.setCellValueFactory(new PropertyValueFactory<>("dohandedover"));
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBorrowStatus().getName()));
        tblBorrowings.setOnMouseClicked(e -> {
            tblMC(e);
        });

        members = FXCollections.observableArrayList(MemberDao.getAll());
        books = FXCollections.observableArrayList(BookDao.getAll());
        borrowStatuses = FXCollections.observableArrayList(BorrowStatusDao.getAll());

        // Set converter to display only the name in the ComboBox
        cmbBook.setConverter(new StringConverter<Books>() {
            @Override
            public String toString(Books book) {
                return book != null ? book.getTitle() : "";
            }

            @Override
            public Books fromString(String string) {
                return cmbBook.getItems().stream()
                        .filter(book -> book.getTitle().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        // Set converter to display only the name in the ComboBox
        cmbSearchBook.setConverter(new StringConverter<Books>() {
            @Override
            public String toString(Books book) {
                return book != null ? book.getTitle() : "";
            }

            @Override
            public Books fromString(String string) {
                return cmbSearchBook.getItems().stream()
                        .filter(book -> book.getTitle().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        // Set converter to display only the name in the ComboBox in cmbLanguage
        cmbMember.setConverter(new StringConverter<Member>() {
            @Override
            public String toString(Member member) {
                return member != null ? member.getFullname() : "";
            }

            @Override
            public Member fromString(String string) {
                return cmbMember.getItems().stream()
                        .filter(member -> member.getFullname().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        // Set converter to display only the name in the ComboBox in cmbLanguage
        cmbSearchMember.setConverter(new StringConverter<Member>() {
            @Override
            public String toString(Member member) {
                return member != null ? member.getFullname() : "";
            }

            @Override
            public Member fromString(String string) {
                return cmbSearchMember.getItems().stream()
                        .filter(member -> member.getFullname().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        // Set converter to display only the name in the ComboBox in cmbLanguage
        cmbBorrowingStatus.setConverter(new StringConverter<BorrowStatus>() {
            @Override
            public String toString(BorrowStatus borrowStatus) {
                return borrowStatus != null ? borrowStatus.getName() : "";
            }

            @Override
            public BorrowStatus fromString(String string) {
                return cmbBorrowingStatus.getItems().stream()
                        .filter(borrowStatus -> borrowStatus.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        loadView();
        enableButtons(true,false,false);

    }

    private void loadView() {
        borrowings = FXCollections.observableList(BorrowingsDao.getAll());
        fillTable();

        cmbBorrowingStatus.setItems(borrowStatuses);
        cmbMember.setItems(members);
        cmbSearchMember.setItems(members);
        cmbBook.setItems(books);
        cmbSearchBook.setItems(books);
    }

    private void fillTable(){
        tblBorrowings.setItems(borrowings);
    }

    public void enableButtons(boolean add,boolean update,boolean delete){
        btnAdd.setDisable(!add);
        btnUpdate.setDisable(!update);
        btnDelete.setDisable(!delete);
    }

    private void tblMC(javafx.scene.input.MouseEvent e) {
        int row = tblBorrowings.getSelectionModel().getSelectedIndex();
        if (row > -1) {
            Borrowings borrowing = borrowings.get(row);
            fillForm(borrowing);
        }
    }

    public void fillForm(Borrowings borrowing) {

        oldBorrowing = borrowing;

        currentBorrowing = Borrowings.builder()
                .id(borrowing.getId())
                .code(borrowing.getCode())
                .doborrowed(borrowing.getDoborrowed())
                .dohandedover(borrowing.getDohandedover())
                .borrowStatus(borrowing.getBorrowStatus())
                .member(borrowing.getMember())
                .books(borrowing.getBooks())
                .build();

        txtCode.setText(borrowing.getCode());
        txtDoBorrowed.setValue(borrowing.getDoborrowed());
        txtDoHandover.setValue(borrowing.getDohandedover());

        for (BorrowStatus bs : borrowStatuses) {
            if (Objects.equals(bs.getId(), borrowing.getBorrowStatus().getId())) {
                cmbBorrowingStatus.setValue(bs);
                break;
            }
        }

        for (Member member : members) {
            if (Objects.equals(member.getId(), borrowing.getMember().getId())) {
                cmbMember.setValue(member);
                break;
            }
        }

        for (Books book : books) {
            if (Objects.equals(book.getId(), borrowing.getBooks().getId())) {
                cmbBook.setValue(book);
                break;
            }
        }

        enableButtons(false,true,true);

    }

    public String getErrors(){
        String errors = "";

        if(currentBorrowing.getCode() == null || currentBorrowing.getCode().isEmpty()){
            errors += "\nInvalid Code";
        }
        if(currentBorrowing.getDoborrowed() == null){
            errors += "\nInvalid Date of Borrowed";
        }
        if(currentBorrowing.getDohandedover() == null){
            errors += "\nInvalid Date of Handover";
        }
        if(currentBorrowing.getMember() == null){
            errors += "\nInvalid Member";
        }
        if(currentBorrowing.getBorrowStatus() == null){
            errors += "\nInvalid Borrow Status";
        }
        if(currentBorrowing.getBooks() == null){
            errors += "\nInvalid Book";
        }
        if(currentBorrowing.getDohandedover() != null && currentBorrowing.getDohandedover().isBefore(currentBorrowing.getDoborrowed())){
            errors += "\nCheck Dates Again";
        }

        return errors;
    }

    public String getUpdates(){
        String updates = "";

        if(!currentBorrowing.getCode().equals(oldBorrowing.getCode())){
            updates += "\nCode Updated ";
        }
        if(!currentBorrowing.getDohandedover().equals(oldBorrowing.getDohandedover())){
            updates += "\nDate of HandedOver Updated ";
        }
        if(!currentBorrowing.getDoborrowed().equals(oldBorrowing.getDoborrowed())){
            updates += "\nDate of Borrowed Updated ";
        }
        if(!currentBorrowing.getMember().getId().equals(oldBorrowing.getMember().getId())){
            updates += "\nMember Updated ";
        }
        if(!currentBorrowing.getBorrowStatus().getId().equals(oldBorrowing.getBorrowStatus().getId())){
            updates += "\nBorrow Status Updated ";
        }
        if(!currentBorrowing.getBooks().getId().equals(oldBorrowing.getBooks().getId())){
            updates += "\nBook Updated ";
        }

        return updates;
    }

    public void loadFormData(){
        String code = txtCode.getText();
        LocalDate doborrowed = txtDoBorrowed.getValue();
        LocalDate dohandedover = txtDoHandover.getValue();
        Member selectedMember = cmbMember.getSelectionModel().getSelectedItem();
        Books selectedBooks = cmbBook.getSelectionModel().getSelectedItem();
        BorrowStatus selectedBorrowStatus = cmbBorrowingStatus.getSelectionModel().getSelectedItem();

        currentBorrowing = Borrowings.builder()
                .code(code)
                .dohandedover(dohandedover)
                .doborrowed(doborrowed)
                .member(selectedMember)
                .borrowStatus(selectedBorrowStatus)
                .books(selectedBooks)
                .build();
    }

    public void add(){
        loadFormData();

        String errors = getErrors();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("BookMaster");
        alert.setHeaderText("Borrowings Module");

        if(errors.isEmpty()){
            String confmsg = "Are you sure to Proceed?\n";
            alert.setContentText(confmsg);

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK){
                String status = BorrowingsService.post(currentBorrowing);
                if(status.equals("Success")){
                    loadView();
                    clearForm();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("BookMaster");
                    alert.setHeaderText("Borrowings Module");
                    alert.setContentText("Successfully Saved");
                    alert.show();
                }else{
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("BookMaster");
                    alert.setHeaderText("Borrowings Module");
                    alert.setContentText("Failed to save as \n\n" + status);
                    alert.show();
                }
            }
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("BookMaster");
            alert.setHeaderText("Borrowings Module");
            alert.setContentText("You have Errors:" + errors);
            alert.show();
        }
    }

    public void update(){
        loadFormData();
        currentBorrowing.setId(oldBorrowing.getId());

        String errors = getErrors();

        if(errors.isEmpty()){
            String updates = getUpdates();

            if(!updates.isEmpty()){
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("BookMaster");
                a.setHeaderText("Borrowings Module - Update");
                a.setContentText("You have following Updates \n\n" + updates);

                Optional<ButtonType> result = a.showAndWait();
                if(result.get() == ButtonType.OK){
                    String status = BorrowingsService.put(currentBorrowing);
                    if(status.equals("Success")){
                        loadView();
                        clearForm();

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("BookMaster");
                        alert.setHeaderText("Borrowings Module - Update");
                        alert.setContentText("Successfully Updated");
                        alert.show();
                    }else{
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("BookMaster");
                        alert.setHeaderText("Borrowings Module - Update");
                        alert.setContentText("Failed to Update as \n\n" + status);
                        alert.show();
                    }
                }
            }else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Borrowings Module - Update");
                alert.setContentText("Nothing To Update");
                alert.show();
            }
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("BookMaster");
            alert.setHeaderText("Borrowings Module - Update");
            alert.setContentText("You Have Following Errors:\n\n" + errors);
            alert.show();
        }
    }

    public void delete(){
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("BookMaster");
        alert.setHeaderText("Borrowings Module - Delete");
        alert.setContentText("Are you sure to Delete ?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK){
            String status = BorrowingsService.delete(oldBorrowing);
            if(status.equals("Success")){
                loadView();
                clearForm();
                enableButtons(true,false,false);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Borrowings Module");
                alert.setContentText("Borrowing Successfully Deleted");
                alert.show();

            }else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Borrowings Module - Delete");
                alert.setContentText("Failed Due to :\n\n" + status);
                alert.show();
            }

        }
    }

    public void handleSearch(){

        String sscode = txtSearchCode.getText();
        String ssbook = "";
        String ssmember = "";

        try{
            Books book = cmbSearchBook.getSelectionModel().getSelectedItem();
            ssbook = book.getId().toString();
        }catch(NullPointerException e){
            ssbook = "";
        }

        try{
            Member member = cmbSearchMember.getSelectionModel().getSelectedItem();
            ssmember = member.getId().toString();
        }catch(NullPointerException e){
            ssmember = "";
        }

        HashMap<String,String> params = new HashMap<>();
        params.put("ssbook",ssbook);
        params.put("sscode",sscode);
        params.put("ssmember",ssmember);

        borrowings = FXCollections.observableList(BorrowingsService.get(params));
        fillTable();
    }

    public void searchClear(){
        cmbSearchMember.setValue(null);
        txtSearchCode.clear();
        cmbSearchBook.setValue(null);
        loadView();
    }

    public void clearForm(){
        txtCode.clear();
        txtDoHandover.setValue(null);
        txtDoBorrowed.setValue(null);
        cmbBook.setValue(null);
        cmbBorrowingStatus.setValue(null);
        cmbMember.setValue(null);

        enableButtons(true,false,false);
    }
}
