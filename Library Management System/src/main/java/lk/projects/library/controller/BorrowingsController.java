package lk.projects.library.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.projects.library.entity.Books;
import lk.projects.library.entity.BorrowStatus;
import lk.projects.library.entity.Borrowings;
import lk.projects.library.entity.Member;

public class BorrowingsController {

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
}
