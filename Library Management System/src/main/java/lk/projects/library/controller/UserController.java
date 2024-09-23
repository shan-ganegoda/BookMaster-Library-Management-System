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
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtConfPassword;
    @FXML
    private ComboBox<UserStatus> cmbUserStatus;
    @FXML
    private ComboBox<Role> cmbRole;
    @FXML
    private DatePicker txtDoRegistered;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TableColumn<User,String> idCol;
    @FXML
    private TableColumn<User,String> usernameCol;
    @FXML
    private TableColumn<User,String> fullNameCol;
    @FXML
    private TableColumn<User,String> roleCol;
    @FXML
    private TableColumn<User,String> statusCol;
    @FXML
    private TableView<User> tblUsers;
    @FXML
    private ComboBox<Role> cmbSearchRole;
    @FXML
    private TextField txtSearchUsername;
    @FXML
    private TextField txtSearchFullName;

    ObservableList<User> users;
    ObservableList<UserStatus> userStatuses;
    ObservableList<Role> roles;

    User currentUser;
    User oldUser;

    Alert alert;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole().getName()));
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserstatus().getName()));
        tblUsers.setOnMouseClicked(e -> {
            tblMC(e);
        });

        userStatuses = FXCollections.observableArrayList(UserStatusDao.getAll());
        roles = FXCollections.observableArrayList(RoleDao.getAll());

        // Set converter to display only the name in the ComboBox
        cmbRole.setConverter(new StringConverter<Role>() {
            @Override
            public String toString(Role role) {
                return role != null ? role.getName() : "";
            }

            @Override
            public Role fromString(String string) {
                return cmbRole.getItems().stream()
                        .filter(role -> role.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        // Set converter to display only the name in the ComboBox in cmbLanguage
        cmbUserStatus.setConverter(new StringConverter<UserStatus>() {
            @Override
            public String toString(UserStatus userStatus) {
                return userStatus != null ? userStatus.getName() : "";
            }

            @Override
            public UserStatus fromString(String string) {
                return cmbUserStatus.getItems().stream()
                        .filter(userStatus -> userStatus.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        // Set converter to display only the name in the ComboBox
        cmbSearchRole.setConverter(new StringConverter<Role>() {
            @Override
            public String toString(Role role) {
                return role != null ? role.getName() : "";
            }

            @Override
            public Role fromString(String string) {
                return cmbSearchRole.getItems().stream()
                        .filter(role -> role.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        loadView();
        enableButtons(true,false,false);

    }

    private void loadView() {
        users = FXCollections.observableList(UserDao.getAll());
        fillTable();

        cmbRole.setItems(roles);
        cmbSearchRole.setItems(roles);
        cmbUserStatus.setItems(userStatuses);
    }

    private void fillTable(){
        tblUsers.setItems(users);
    }

    public void enableButtons(boolean add,boolean update,boolean delete){
        btnAdd.setDisable(!add);
        btnUpdate.setDisable(!update);
        btnDelete.setDisable(!delete);
    }

    private void tblMC(javafx.scene.input.MouseEvent e) {
        int row = tblUsers.getSelectionModel().getSelectedIndex();
        if (row > -1) {
            User user = users.get(row);
            //fillForm(book);
            System.out.println(user);
        }
    }
}
