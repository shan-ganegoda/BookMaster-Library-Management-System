package lk.projects.library.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import lk.projects.library.dao.MemberDao;
import lk.projects.library.dao.UserDao;
import lk.projects.library.entity.*;
import lk.projects.library.service.GenderService;
import lk.projects.library.service.MemberStatusService;

import java.net.URL;
import java.util.ResourceBundle;

public class MembersController implements Initializable {

    @FXML
    private ComboBox<Gender> cmbGender;

    @FXML
    private DatePicker txtDob;

    @FXML
    private DatePicker txtDoRegistered;

    @FXML
    private ComboBox<MemberStatus> cmbMemberStatus;

    @FXML
    private ComboBox<User> cmbUser;

    @FXML
    private TextArea txtAddress;

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtNic;

    @FXML
    private TableColumn<Member,String> idCol;

    @FXML
    private TableColumn<Member,String> fullNameCol;

    @FXML
    private TableColumn<Member,String> codeCol;

    @FXML
    private TableColumn<Member,String> nicCol;

    @FXML
    private TableColumn<Member,String> addressCol;

    @FXML
    private TextField txtSearchNic;

    @FXML
    private TextField txtSearchCode;

    @FXML
    private TextField txtSearchFullName;

    @FXML
    private TableView<Member> tblMembers;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    ObservableList<User> users;
    ObservableList<MemberStatus> memberStatuses;
    ObservableList<Gender> genders;
    ObservableList<Member> members;

    Member currentMember;
    Member oldMember;

    Alert alert;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        nicCol.setCellValueFactory(new PropertyValueFactory<>("nic"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        tblMembers.setOnMouseClicked(e -> {
            tblMC(e);
        });

        memberStatuses = FXCollections.observableArrayList(MemberStatusService.get());
        genders = FXCollections.observableArrayList(GenderService.get());
        users = FXCollections.observableArrayList(UserDao.getAll());

        // Set converter to display only the name in the ComboBox
        cmbGender.setConverter(new StringConverter<Gender>() {
            @Override
            public String toString(Gender gender) {
                return gender != null ? gender.getName() : "";
            }

            @Override
            public Gender fromString(String string) {
                return cmbGender.getItems().stream()
                        .filter(gender -> gender.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        // Set converter to display only the name in the ComboBox in cmbLanguage
        cmbMemberStatus.setConverter(new StringConverter<MemberStatus>() {
            @Override
            public String toString(MemberStatus memberStatus) {
                return memberStatus != null ? memberStatus.getName() : "";
            }

            @Override
            public MemberStatus fromString(String string) {
                return cmbMemberStatus.getItems().stream()
                        .filter(memberStatus -> memberStatus.getName().equals(string))
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
        members = FXCollections.observableList(MemberDao.getAll());
        fillTable();

        cmbMemberStatus.setItems(memberStatuses);
        cmbGender.setItems(genders);
        cmbUser.setItems(users);
    }

    private void fillTable(){
        tblMembers.setItems(members);
    }

    public void enableButtons(boolean add,boolean update,boolean delete){
        btnAdd.setDisable(!add);
        btnUpdate.setDisable(!update);
        btnDelete.setDisable(!delete);
    }

    private void tblMC(javafx.scene.input.MouseEvent e) {
        int row = tblMembers.getSelectionModel().getSelectedIndex();
        if (row > -1) {
            Member member = members.get(row);
            //fillForm(book);
        }
    }
}
