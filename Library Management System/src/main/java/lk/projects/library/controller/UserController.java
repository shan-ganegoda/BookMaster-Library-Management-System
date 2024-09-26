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
import lk.projects.library.service.LanguageService;
import lk.projects.library.service.UserService;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
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
            fillForm(user);
        }
    }

    public void fillForm(User user) {

        oldUser = user;

        currentUser = User.builder()
                .id(user.getId())
                .fullname(user.getFullname())
                .username(user.getUsername())
                .password(user.getPassword())
                .userstatus(user.getUserstatus())
                .role(user.getRole())
                .doregistered(user.getDoregistered())
                .build();


        txtFullName.setText(user.getFullname());;
        txtUsername.setText(user.getUsername());
        txtPassword.setText(user.getPassword());
        txtConfPassword.setText(user.getPassword());
        txtDoRegistered.setValue(user.getDoregistered());

        for (Role role : roles) {
            if (Objects.equals(role.getId(), user.getRole().getId())) {
                cmbRole.setValue(role);
                break;
            }
        }

        for (UserStatus us : userStatuses) {
            if (Objects.equals(us.getId(), user.getUserstatus().getId())) {
                cmbUserStatus.setValue(us);
                break;
            }
        }

        enableButtons(false,true,true);

    }

    public String getErrors(){
        String errors = "";

        if(currentUser.getFullname() == null || currentUser.getFullname().isEmpty()){
            errors += "\nInvalid Full Name";
        }
        if(currentUser.getUsername() == null || currentUser.getUsername().isEmpty()){
            errors += "\nInvalid Username";
        }
        if(currentUser.getPassword() == null || currentUser.getPassword().isEmpty()){
            errors += "\nInvalid Password";
        }
        if(currentUser.getRole() == null){
            errors += "\nInvalid Role";
        }
        if(currentUser.getDoregistered() == null){
            errors += "\nInvalid Date of Registered";
        }
        if(currentUser.getUserstatus() == null){
            errors += "\nInvalid User Status";
        }
        if(!Objects.equals(txtPassword.getText(), txtConfPassword.getText())){
            errors += "\nPasswords don't match";
        }

        return errors;
    }

    public String getUpdates(){
        String updates = "";

        if(!currentUser.getFullname().equals(oldUser.getFullname())){
            updates += "\nFull Name Updated ";
        }
        if(!currentUser.getUsername().equals(oldUser.getUsername())){
            updates += "\nUsername Updated ";
        }
        if(!currentUser.getPassword().equals(oldUser.getPassword())){
            updates += "\nPassword Updated ";
        }
        if(!currentUser.getDoregistered().equals(oldUser.getDoregistered())){
            updates += "\nDate of Registered Updated ";
        }
        if(!currentUser.getUserstatus().getId().equals(oldUser.getUserstatus().getId())){
            updates += "\nUser Status Updated ";
        }
        if(!currentUser.getRole().getId().equals(oldUser.getRole().getId())){
            updates += "\nRole Updated ";
        }

        return updates;
    }

    public void loadFormData(){
        String username = txtUsername.getText();;
        String fullname = txtFullName.getText();
        String password = txtPassword.getText();
        LocalDate doregistered = txtDoRegistered.getValue();
        UserStatus selectedUserStatus = cmbUserStatus.getSelectionModel().getSelectedItem();
        Role selectedRole = cmbRole.getSelectionModel().getSelectedItem();

        currentUser = User.builder()
                .fullname(fullname)
                .username(username)
                .password(password)
                .doregistered(doregistered)
                .role(selectedRole)
                .userstatus(selectedUserStatus)
                .build();
    }

    public void add(){
        loadFormData();

        String errors = getErrors();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("BookMaster");
        alert.setHeaderText("Users Module");

        if(errors.isEmpty()){
            String confmsg = "Are you sure to Proceed?\n";
            alert.setContentText(confmsg);

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK){
                String status = UserService.post(currentUser);
                if(status.equals("Success")){
                    loadView();
                    clearForm();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("BookMaster");
                    alert.setHeaderText("Users Module");
                    alert.setContentText("Successfully Saved");
                    alert.show();
                }else{
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("BookMaster");
                    alert.setHeaderText("Users Module");
                    alert.setContentText("Failed to save as \n\n" + status);
                    alert.show();
                }
            }
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("BookMaster");
            alert.setHeaderText("Users Module");
            alert.setContentText("You have Errors:" + errors);
            alert.show();
        }
    }

    public void update(){
        loadFormData();
        currentUser.setId(oldUser.getId());

        String errors = getErrors();

        if(errors.isEmpty()){
            String updates = getUpdates();

            if(!updates.isEmpty()){
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("BookMaster");
                a.setHeaderText("Users Module - Update");
                a.setContentText("You have following Updates \n\n" + updates);

                Optional<ButtonType> result = a.showAndWait();
                if(result.get() == ButtonType.OK){
                    String status = UserService.put(currentUser);
                    if(status.equals("Success")){
                        loadView();
                        clearForm();

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("BookMaster");
                        alert.setHeaderText("Users Module - Update");
                        alert.setContentText("Successfully Updated");
                        alert.show();
                    }else{
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("BookMaster");
                        alert.setHeaderText("Users Module - Update");
                        alert.setContentText("Failed to Update as \n\n" + status);
                        alert.show();
                    }
                }
            }else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Users Module - Update");
                alert.setContentText("Nothing To Update");
                alert.show();
            }
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("BookMaster");
            alert.setHeaderText("Users Module - Update");
            alert.setContentText("You Have Following Errors:\n\n" + errors);
            alert.show();
        }
    }

    public void delete(){
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("BookMaster");
        alert.setHeaderText("Users Module - Delete");
        alert.setContentText("Are you sure to Delete ?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK){
            String status = UserService.delete(oldUser);
            if(status.equals("Success")){
                loadView();
                clearForm();
                enableButtons(true,false,false);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Users Module");
                alert.setContentText("User Successfully Deleted");
                alert.show();

            }else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Users Module - Delete");
                alert.setContentText("Failed Due to :\n\n" + status);
                alert.show();
            }

        }
    }

    public void handleSearch(){
        String ssfullname = txtSearchFullName.getText();
        String ssusername = txtSearchUsername.getText();
        String ssrole = "";

        try{
            Role role = cmbSearchRole.getSelectionModel().getSelectedItem();
            ssrole = role.getId().toString();
        }catch(NullPointerException e){
            ssrole = "";
        }

        HashMap<String,String> params = new HashMap<>();
        params.put("ssfullname",ssfullname);
        params.put("ssusername",ssusername);
        params.put("ssrole",ssrole);


        users = FXCollections.observableList(UserService.get(params));
        fillTable();
    }

    public void searchClear(){
        txtSearchFullName.clear();
        txtSearchUsername.clear();
        cmbSearchRole.setValue(null);
        loadView();
    }

    public void clearForm(){
        txtUsername.clear();
        txtFullName.clear();
        txtPassword.clear();
        txtConfPassword.clear();
        txtDoRegistered.setValue(null);
        cmbRole.setValue(null);
        cmbUserStatus.setValue(null);

        enableButtons(true,false,false);
    }
}
