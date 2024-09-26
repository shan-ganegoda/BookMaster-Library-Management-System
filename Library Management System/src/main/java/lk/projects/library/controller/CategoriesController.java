package lk.projects.library.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.projects.library.dao.CategoryDao;
import lk.projects.library.entity.Category;
import lk.projects.library.service.CategoryService;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class CategoriesController implements Initializable {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtSearchName;

    @FXML
    private TextField txtSearchCode;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnSearchClear;

    @FXML
    private TableView<Category> tblCategory;

    @FXML
    private TableColumn<Category, String> idCol;

    @FXML
    private TableColumn<Category, String> nameCol;

    @FXML
    private TableColumn<Category, String> codeCol;

    ObservableList<Category> categoryList;

    Category oldCategory;
    Category currentCategory;

    Alert alert;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        tblCategory.setOnMouseClicked(e -> {
            tblMC(e);
        });

        loadTable();
        enableButtons(true,false,false);
    }


    private void loadTable() {
        categoryList = FXCollections.observableList(CategoryDao.getAll());
        fillTable();
    }

    private void fillTable(){
        tblCategory.setItems(categoryList);
    }

    public void enableButtons(boolean add,boolean update,boolean delete){
        btnAdd.setDisable(!add);
        btnUpdate.setDisable(!update);
        btnDelete.setDisable(!delete);
    }

    private void tblMC(javafx.scene.input.MouseEvent e) {
        int row = tblCategory.getSelectionModel().getSelectedIndex();
        if (row > -1) {
            Category category = categoryList.get(row);
            fillForm(category);
        }
    }

    public void fillForm(Category category) {
        oldCategory = category;

        currentCategory = Category.builder()
                .id(category.getId())
                .name(category.getName())
                .code(category.getCode())
                .build();

        txtName.setText(category.getName());
        txtCode.setText(category.getCode());

        enableButtons(false,true,true);

    }

    public String getErrors(){
        String errors = "";

        if(currentCategory.getName().isEmpty()){
            errors += "\nInvalid Name";
        }
        if(currentCategory.getCode().isEmpty()){
            errors += "\nInvalid Code";
        }

        return errors;
    }

    public String getUpdates(){
        String updates = "";

        if(!currentCategory.getName().equals(oldCategory.getName())){
            updates += "\nName Updated ";
        }
        if(!currentCategory.getCode().equals(oldCategory.getCode())){
            updates += "\nName Updated ";
        }
        return updates;
    }

    public void loadFormData(){
        String name = txtName.getText();
        String code = txtCode.getText();

        currentCategory = Category.builder()
                .name(name)
                .code(code)
                .build();
    }

    public void add(){
        loadFormData();

        String errors = getErrors();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("BookMaster");
        alert.setHeaderText("Category Module");

        if(errors.isEmpty()){
            String confmsg = "Are you sure to Proceed?\n";
            alert.setContentText(confmsg);

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK){
                String status = CategoryService.post(currentCategory);
                if(status.equals("Success")){
                    loadTable();
                    clearForm();

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("BookMaster");
                    alert1.setHeaderText("Category Module");
                    alert1.setContentText("Successfully Saved");
                    alert1.show();
                }else{
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("BookMaster");
                    alert2.setHeaderText("Category Module");
                    alert2.setContentText("Failed to save as \n\n" + status);
                    alert2.show();
                }
            }
        }else{
            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
            alert3.setTitle("BookMaster");
            alert3.setHeaderText("Category Module");
            alert3.setContentText("You have Errors:" + errors);
            alert3.show();
        }
    }

    public void update(){
        loadFormData();
        currentCategory.setId(oldCategory.getId());

        String errors = getErrors();

        if(errors.isEmpty()){
            String updates = getUpdates();

            if(!updates.isEmpty()){
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("BookMaster");
                a.setHeaderText("Category Module - Update");
                a.setContentText("You have following Updates \n\n" + updates);

                Optional<ButtonType> result = a.showAndWait();
                if(result.get() == ButtonType.OK){
                    String status = CategoryService.put(currentCategory);
                    if(status.equals("Success")){
                        loadTable();
                        clearForm();

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("BookMaster");
                        alert.setHeaderText("Category Module - Update");
                        alert.setContentText("Successfully Updated");
                        alert.show();
                    }else{
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("BookMaster");
                        alert.setHeaderText("Category Module - Update");
                        alert.setContentText("Failed to Update as \n\n" + status);
                        alert.show();
                    }
                }
            }else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Category Module - Update");
                alert.setContentText("Nothing To Update");
                alert.show();
            }
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("BookMaster");
            alert.setHeaderText("Category Module - Update");
            alert.setContentText("You Have Following Errors:\n\n" + errors);
            alert.show();
        }
    }

    public void delete(){
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("BookMaster");
        alert.setHeaderText("Category Module - Delete");
        alert.setContentText("Are you sure to Delete ?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK){
            String status = CategoryService.delete(oldCategory);
            if(status.equals("Success")){
                loadTable();
                clearForm();
                enableButtons(true,false,false);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Category Module");
                alert.setContentText("Category Successfully Deleted");
                alert.show();

            }else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Category Module - Delete");
                alert.setContentText("Failed Due to :\n\n" + status);
                alert.show();
            }

        }
    }

    public void handleSearch(){
        String ssname = txtSearchName.getText();
        String sscode = txtSearchCode.getText();

        HashMap<String,String> params = new HashMap<>();
        params.put("ssname",ssname);
        params.put("sscode",sscode);

        categoryList = FXCollections.observableList(CategoryService.get(params));
        fillTable();
    }

    public void searchClear(){
        txtSearchName.clear();
        txtSearchCode.clear();
        loadTable();
    }

    public void clearForm(){
        txtName.setText("");
        txtCode.setText("");

        enableButtons(true,false,false);
    }


}
