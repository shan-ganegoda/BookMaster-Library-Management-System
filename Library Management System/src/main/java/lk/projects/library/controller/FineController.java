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


        loadView();
        enableButtons(true,false,false);
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
            if (Objects.equals(fs.getId(), fine.getBorrowings().getId())) {
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
}
