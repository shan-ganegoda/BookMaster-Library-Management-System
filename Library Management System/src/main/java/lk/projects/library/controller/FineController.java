package lk.projects.library.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import lk.projects.library.dao.*;
import lk.projects.library.entity.*;
import lk.projects.library.service.BorrowingsService;
import lk.projects.library.service.FineService;
import lk.projects.library.service.LanguageService;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class FineController implements Initializable {

    @FXML
    private ComboBox<Borrowings> cmbBorrowing;
    @FXML
    private TextField txtFine;
    @FXML
    private TextField txtLateDays;
    @FXML
    private ComboBox<FineStatus> cmbFineStatus;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TableColumn<Fine,String> idCol;
    @FXML
    private TableColumn<Fine,String> fineCol;
    @FXML
    private TableColumn<Fine,String> fineStatusCol;
    @FXML
    private TableColumn<Fine,String> borrowingCol;
    @FXML
    private TableView<Fine> tblFine;
    @FXML
    private ComboBox<Borrowings> cmbSearchBorrowing;

    ObservableList<Fine> fines;
    ObservableList<FineStatus> fineStatuses;
    ObservableList<Borrowings> borrowings;
    ObservableList<Borrowings> borrowingsCon;

    Alert alert;

    Fine currentFine;
    Fine oldFine;
    Borrowings br;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fineCol.setCellValueFactory(new PropertyValueFactory<>("fine"));
        fineStatusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFineStatus().getName()));
        borrowingCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBorrowings().getCode()));
        tblFine.setOnMouseClicked(e -> {
            tblMC(e);
        });

        fineStatuses = FXCollections.observableArrayList(FineStatusDao.getAll());
        borrowings = FXCollections.observableArrayList(BorrowingsDao.getAll());
        borrowingsCon = FXCollections.observableArrayList(BorrowingsDao.getAllCon());

        // Set converter to display only the name in the ComboBox
        cmbBorrowing.setConverter(new StringConverter<Borrowings>() {
            @Override
            public String toString(Borrowings borrowings) {
                return borrowings != null ? borrowings.getCode() : "";
            }

            @Override
            public Borrowings fromString(String string) {
                return cmbBorrowing.getItems().stream()
                        .filter(borrowing -> borrowing.getCode().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        // Set converter to display only the name in the ComboBox
        cmbSearchBorrowing.setConverter(new StringConverter<Borrowings>() {
            @Override
            public String toString(Borrowings borrowings) {
                return borrowings != null ? borrowings.getCode() : "";
            }

            @Override
            public Borrowings fromString(String string) {
                return cmbSearchBorrowing.getItems().stream()
                        .filter(borrowing -> borrowing.getCode().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        // Set converter to display only the name in the ComboBox in cmbLanguage
        cmbFineStatus.setConverter(new StringConverter<FineStatus>() {
            @Override
            public String toString(FineStatus fineStatus) {
                return fineStatus != null ? fineStatus.getName() : "";
            }

            @Override
            public FineStatus fromString(String string) {
                return cmbFineStatus.getItems().stream()
                        .filter(fs -> fs.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        cmbBorrowing.valueProperty().addListener(new ChangeListener<Borrowings>() {
            @Override
            public void changed(ObservableValue ov, Borrowings t, Borrowings t1) {
                cmbBorrowingAp();
            }
        });


        loadView();
        enableButtons(true,false,false);
    }

    public void cmbBorrowingAp(){
        br = cmbBorrowing.getSelectionModel().getSelectedItem();
        if (br != null) {
            long daysBetween = ChronoUnit.DAYS.between(br.getDoborrowed(), br.getDohandedover());
            if(daysBetween > 14){
                int latedays = Math.toIntExact(daysBetween - 14);
                txtLateDays.setText(Integer.toString(latedays));
                CalculateFine(latedays);
            }
        }
    }

    public void CalculateFine(int latedays){
        double dayfine = 10.50;

        double totalfine = dayfine * latedays;
        txtFine.setText(Double.toString(totalfine));
    }

    private void loadView() {
        fines = FXCollections.observableList(FineDao.getAll());
        fillTable();

        cmbFineStatus.setItems(fineStatuses);
        cmbBorrowing.setItems(borrowingsCon);
        cmbSearchBorrowing.setItems(borrowings);
    }

    private void fillTable(){
        tblFine.setItems(fines);
    }

    public void enableButtons(boolean add,boolean update,boolean delete){
        btnAdd.setDisable(!add);
        btnUpdate.setDisable(!update);
        btnDelete.setDisable(!delete);
    }

    private void tblMC(javafx.scene.input.MouseEvent e) {
        int row = tblFine.getSelectionModel().getSelectedIndex();
        if (row > -1) {
            Fine fine = fines.get(row);
            fillForm(fine);
        }
    }

    public void fillForm(Fine fine) {

        cmbBorrowing.setItems(borrowings);

        oldFine = fine;

        currentFine = Fine.builder()
                .id(fine.getId())
                .fine(fine.getFine())
                .latedays(fine.getLatedays())
                .fineStatus(fine.getFineStatus())
                .borrowings(fine.getBorrowings())
                .build();

        txtFine.setText(fine.getFine().toString());
        txtLateDays.setText(fine.getLatedays().toString());

        for (FineStatus fs : fineStatuses) {
            if (Objects.equals(fs.getId(), fine.getFineStatus().getId())) {
                cmbFineStatus.setValue(fs);
                break;
            }
        }

        for (Borrowings b : borrowings) {
            if (Objects.equals(b.getId(), fine.getBorrowings().getId())) {
                cmbBorrowing.setValue(b);
                break;
            }
        }

        txtFine.setDisable(true);
        cmbBorrowing.setDisable(true);
        txtLateDays.setDisable(true);

        enableButtons(false,true,true);

    }

    public String getErrors(){
        String errors = "";

        if(currentFine.getFine() == null || currentFine.getFine() == 0.0){
            errors += "\nInvalid Fine";
        }
        if(currentFine.getLatedays() == null || currentFine.getLatedays() == 0){
            errors += "\nInvalid Late Days";
        }
        if(currentFine.getFineStatus() == null){
            errors += "\nInvalid Fine Status";
        }
        if(currentFine.getBorrowings() == null){
            errors += "\nInvalid Borrowings";
        }

        return errors;
    }

    public String getUpdates(){
        String updates = "";

        if(!currentFine.getFine().equals(oldFine.getFine())){
            updates += "\nFine Updated ";
        }
        if(!currentFine.getLatedays().equals(oldFine.getLatedays())){
            updates += "\nLate Days Updated ";
        }
        if(!currentFine.getBorrowings().getId().equals(oldFine.getBorrowings().getId())){
            updates += "\nBorrowings Updated ";
        }
        if(!currentFine.getFineStatus().getId().equals(oldFine.getFineStatus().getId())){
            updates += "\nFine Status Updated ";
        }

        return updates;
    }

    public void loadFormData(){
        String fine = txtFine.getText();
        String latedays = txtLateDays.getText();
        FineStatus selectedFineStatus = cmbFineStatus.getSelectionModel().getSelectedItem();
        Borrowings selectedBorrowings = cmbBorrowing.getSelectionModel().getSelectedItem();

        int lastdaysnum = 0;
        double finenum = 0.0;

        if(!latedays.isEmpty()){
            lastdaysnum = Integer.parseInt(latedays);
        }

        if(!fine.isEmpty()){
            finenum = Double.parseDouble(fine);
        }

        currentFine = Fine.builder()
                .fine(finenum)
                .latedays(lastdaysnum)
                .fineStatus(selectedFineStatus)
                .borrowings(selectedBorrowings)
                .build();
    }

    public void add(){
        loadFormData();

        String errors = getErrors();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("BookMaster");
        alert.setHeaderText("Fine Module");

        if(errors.isEmpty()){
            String confmsg = "Are you sure to Proceed?\n";
            alert.setContentText(confmsg);

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK){
                String status = FineService.post(currentFine);
                if(status.equals("Success")){
                    loadView();
                    clearForm();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("BookMaster");
                    alert.setHeaderText("Fine Module");
                    alert.setContentText("Successfully Saved");
                    alert.show();
                }else{
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("BookMaster");
                    alert.setHeaderText("Fine Module");
                    alert.setContentText("Failed to save as \n\n" + status);
                    alert.show();
                }
            }
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("BookMaster");
            alert.setHeaderText("Fine Module");
            alert.setContentText("You have Errors:" + errors);
            alert.show();
        }
    }

    public void update(){
        loadFormData();
        currentFine.setId(oldFine.getId());
        String errors = getErrors();

        if(errors.isEmpty()){
            String updates = getUpdates();

            if(!updates.isEmpty()){
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("BookMaster");
                a.setHeaderText("Fine Module - Update");
                a.setContentText("You have following Updates \n\n" + updates);

                Optional<ButtonType> result = a.showAndWait();
                if(result.get() == ButtonType.OK){
                    String status = FineService.put(currentFine);
                    if(status.equals("Success")){
                        loadView();
                        clearForm();

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("BookMaster");
                        alert.setHeaderText("Fine Module - Update");
                        alert.setContentText("Successfully Updated");
                        alert.show();
                    }else{
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("BookMaster");
                        alert.setHeaderText("Fine Module - Update");
                        alert.setContentText("Failed to Update as \n\n" + status);
                        alert.show();
                    }
                }
            }else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Fine Module - Update");
                alert.setContentText("Nothing To Update");
                alert.show();
            }
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("BookMaster");
            alert.setHeaderText("Fine Module - Update");
            alert.setContentText("You Have Following Errors:\n\n" + errors);
            alert.show();
        }
    }

    public void delete(){
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("BookMaster");
        alert.setHeaderText("Fine Module - Delete");
        alert.setContentText("Are you sure to Delete ?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK){
            String status = FineService.delete(oldFine);
            if(status.equals("Success")){
                loadView();
                clearForm();
                enableButtons(true,false,false);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Fine Module");
                alert.setContentText("Fine Successfully Deleted");
                alert.show();

            }else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("BookMaster");
                alert.setHeaderText("Fine Module - Delete");
                alert.setContentText("Failed Due to :\n\n" + status);
                alert.show();
            }

        }
    }

    public void handleSearch(){

        String ssborrowing = "";

        try{
            Borrowings borrowing = cmbSearchBorrowing.getSelectionModel().getSelectedItem();
            ssborrowing = borrowing.getId().toString();
        }catch(NullPointerException e){
            ssborrowing = "";
        }

        HashMap<String,String> params = new HashMap<>();
        params.put("ssborrowing",ssborrowing);

        fines = FXCollections.observableList(FineService.get(params));
        fillTable();
    }

    public void searchClear(){
        cmbSearchBorrowing.setValue(null);
        loadView();
    }

    public void clearForm(){
        txtFine.clear();
        txtLateDays.clear();
        cmbBorrowing.setValue(null);
        cmbFineStatus.setValue(null);

        loadView();

        enableButtons(true,false,false);
    }


}
