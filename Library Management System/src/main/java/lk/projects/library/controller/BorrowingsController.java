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
import lk.projects.library.service.LanguageService;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class BorrowingsController implements Initializable {

    @FXML
    private TextField txtSearchCode;
    @FXML
    private TextField txtSearchMemberCode;
    @FXML
    private TextField txtSearchBookCode;
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
        cmbBook.setItems(books);
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
}
