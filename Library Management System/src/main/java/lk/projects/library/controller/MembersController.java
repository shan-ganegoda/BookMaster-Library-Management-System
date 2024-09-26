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
import lk.projects.library.service.BookService;
import lk.projects.library.service.GenderService;
import lk.projects.library.service.MemberService;
import lk.projects.library.service.MemberStatusService;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
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
            fillForm(member);
        }
    }

    public void fillForm(Member member) {

        oldMember = member;

        currentMember = Member.builder()
                .id(member.getId())
                .fullname(member.getFullname())
                .code(member.getCode())
                .dob(member.getDob())
                .nic(member.getNic())
                .address(member.getAddress())
                .doregistered(member.getDoregistered())
                .gender(member.getGender())
                .memberstatus(member.getMemberstatus())
                .user(member.getUser())
                .build();


        txtFullName.setText(member.getFullname());;
        txtCode.setText(member.getCode());
        txtDob.setValue(member.getDob());
        txtNic.setText(member.getNic());
        txtAddress.setText(member.getAddress());
        txtDoRegistered.setValue(member.getDoregistered());

        for (Gender gen : genders) {
            if (Objects.equals(gen.getId(), member.getGender().getId())) {
                cmbGender.setValue(gen);
                break;
            }
        }

        for (MemberStatus ms : memberStatuses) {
            if (Objects.equals(ms.getId(), member.getMemberstatus().getId())) {
                cmbMemberStatus.setValue(ms);
                break;
            }
        }

        for (User usr : users) {
            if (Objects.equals(usr.getId(), member.getUser().getId())) {
                cmbUser.setValue(usr);
                break;
            }
        }

        enableButtons(false,true,true);

    }

    public String getErrors(){
        String errors = "";

        if(currentMember.getFullname() == null || currentMember.getFullname().isEmpty()){
            errors += "\nInvalid Full Name";
        }
        if(currentMember.getCode() == null || currentMember.getCode().isEmpty()){
            errors += "\nInvalid Code";
        }
        if(currentMember.getDob() == null){
            errors += "\nInvalid Date of Birth";
        }
        if(currentMember.getNic() == null || currentMember.getNic().isEmpty()){
            errors += "\nInvalid Nic";
        }
        if(currentMember.getAddress() == null || currentMember.getAddress().isEmpty()){
            errors += "\nInvalid Address";
        }
        if(currentMember.getDoregistered() == null){
            errors += "\nInvalid Date of Registered";
        }
        if(currentMember.getGender() == null){
            errors += "\nInvalid Gender";
        }
        if(currentMember.getMemberstatus() == null){
            errors += "\nInvalid MemberStatus";
        }
        if(currentMember.getUser() == null){
            errors += "\nInvalid User";
        }

        return errors;
    }

    public String getUpdates(){
        String updates = "";

        if(!currentMember.getFullname().equals(oldMember.getFullname())){
            updates += "\nFullName Updated ";
        }
        if(!currentMember.getCode().equals(oldMember.getCode())){
            updates += "\nCode Updated ";
        }
        if(!currentMember.getDob().equals(oldMember.getDob())){
            updates += "\nDate of Birth Updated ";
        }
        if(!currentMember.getNic().equals(oldMember.getNic())){
            updates += "\nNic Updated ";
        }
        if(!currentMember.getAddress().equals(oldMember.getAddress())){
            updates += "\nAddress Updated ";
        }
        if(!currentMember.getDoregistered().equals(oldMember.getDoregistered())){
            updates += "\nDate of Registered Updated ";
        }
        if(!currentMember.getGender().getId().equals(oldMember.getGender().getId())){
            updates += "\nCategory Updated ";
        }
        if(!currentMember.getMemberstatus().getId().equals(oldMember.getMemberstatus().getId())){
            updates += "\nLanguage Updated ";
        }
        if(!currentMember.getUser().getId().equals(oldMember.getUser().getId())){
            updates += "\nUser Updated ";
        }

        return updates;
    }

    public void loadFormData(){
        String fullname = txtFullName.getText();;
        String code = txtCode.getText();
        LocalDate dob = txtDob.getValue();
        String nic = txtNic.getText();
        String address = txtAddress.getText();
        LocalDate doregistered = txtDoRegistered.getValue();
        Gender selectedGender = cmbGender.getSelectionModel().getSelectedItem();
        MemberStatus selectedMemberStatus = cmbMemberStatus.getSelectionModel().getSelectedItem();
        User selectedUser = cmbUser.getSelectionModel().getSelectedItem();

        currentMember = Member.builder()
                .fullname(fullname)
                .code(code)
                .dob(dob)
                .nic(nic)
                .address(address)
                .doregistered(doregistered)
                .gender(selectedGender)
                .memberstatus(selectedMemberStatus)
                .user(selectedUser)
                .build();
    }

    public void add(){
        loadFormData();

        String errors = getErrors();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("BookMaster");
        alert.setHeaderText("Member Module");

        if(errors.isEmpty()){
            String confmsg = "Are you sure to Proceed?\n";
            alert.setContentText(confmsg);

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK){
                String status = MemberService.post(currentMember);
                if(status.equals("Success")){
                    loadView();
                    clearForm();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("BookMaster");
                    alert.setHeaderText("Member Module");
                    alert.setContentText("Successfully Saved");
                    alert.show();
                }else{
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("BookMaster");
                    alert.setHeaderText("Member Module");
                    alert.setContentText("Failed to save as \n\n" + status);
                    alert.show();
                }
            }
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("BookMaster");
            alert.setHeaderText("Member Module");
            alert.setContentText("You have Errors:" + errors);
            alert.show();
        }
    }

    public void update(){
        loadFormData();
        currentMember.setId(oldMember.getId());

        String errors = getErrors();

        if(errors.isEmpty()){
            String updates = getUpdates();

            if(!updates.isEmpty()){
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("BookMaster");
                a.setHeaderText("Member Module - Update");
                a.setContentText("You have following Updates \n\n" + updates);

                Optional<ButtonType> result = a.showAndWait();
                if(result.get() == ButtonType.OK){
                    String status = MemberService.put(currentMember);
                    if(status.equals("Success")){
                        loadView();
                        clearForm();

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("BookMaster");
                        alert.setHeaderText("Member Module - Update");
                        alert.setContentText("Successfully Updated");
                        alert.show();
                    }else{
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("BookMaster");
                        alert.setHeaderText("Member Module - Update");
                        alert.setContentText("Failed to Update as \n\n" + status);
                        alert.show();
                    }
                }
            }else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Member Module - Update");
                alert.setContentText("Nothing To Update");
                alert.show();
            }
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("BookMaster");
            alert.setHeaderText("Member Module - Update");
            alert.setContentText("You Have Following Errors:\n\n" + errors);
            alert.show();
        }
    }

    public void delete(){
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("BookMaster");
        alert.setHeaderText("Member Module - Delete");
        alert.setContentText("Are you sure to Delete ?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK){
            String status = MemberService.delete(oldMember);
            if(status.equals("Success")){
                loadView();
                clearForm();
                enableButtons(true,false,false);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Member Module");
                alert.setContentText("Successfully Deleted");
                alert.show();

            }else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Member Module - Delete");
                alert.setContentText("Failed Due to :\n\n" + status);
                alert.show();
            }

        }
    }

    public void handleSearch(){
        String ssfullname = txtSearchFullName.getText();
        String sscode = txtSearchCode.getText();
        String ssnic = txtSearchNic.getText();

        HashMap<String,String> params = new HashMap<>();
        params.put("ssfullname",ssfullname);
        params.put("sscode",sscode);
        params.put("ssnic",ssnic);

        members = FXCollections.observableList(MemberService.get(params));
        fillTable();
    }

    public void searchClear(){
        txtSearchNic.clear();
        txtSearchCode.clear();
        txtSearchFullName.clear();
        loadView();
    }

    public void clearForm(){
        txtFullName.clear();
        txtCode.clear();
        txtDob.setValue(null);
        txtDoRegistered.setValue(null);
        txtNic.clear();
        txtAddress.clear();
        cmbGender.setValue(null);
        cmbMemberStatus.setValue(null);
        cmbUser.setValue(null);

        enableButtons(true,false,false);
    }
}
