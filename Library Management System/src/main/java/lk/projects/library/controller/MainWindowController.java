package lk.projects.library.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.projects.library.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindowController implements Initializable {


    @FXML
    private Label exit;

    @FXML
    private StackPane contentArea;

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnMinimize;

    @FXML
    private Label lblUsername;

    Stage stage;

    Alert alert;

    private String username;

    public void setUsername(String username) {
        this.username = username;
        lblUsername.setText(username);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/projects/library/view/home.fxml"));
            Parent fxml = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(fxml);
        }catch (IOException e){
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE,null,e);
        }


    }

    public void home(ActionEvent actionEvent) throws IOException{
        loadFXML("/lk/projects/library/view/home.fxml");
    }

    public void books(ActionEvent actionEvent) throws IOException{
        loadFXML("/lk/projects/library/view/books.fxml");
    }

    public void categories(ActionEvent actionEvent) throws IOException{
        loadFXML("/lk/projects/library/view/categories.fxml");
    }

    public void members(ActionEvent actionEvent) throws IOException{
        loadFXML("/lk/projects/library/view/members.fxml");
    }

    public void borrowings(ActionEvent actionEvent) throws IOException{
        loadFXML("/lk/projects/library/view/borrowings.fxml");
    }

    public void fine(ActionEvent actionEvent) throws IOException{
        loadFXML("/lk/projects/library/view/fine.fxml");
    }

    public void users(ActionEvent actionEvent) throws IOException{
        loadFXML("/lk/projects/library/view/user.fxml");
    }

    private void loadFXML(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent fxml = loader.load();
        contentArea.getChildren().clear();
        contentArea.getChildren().add(fxml);
    }

    @FXML
    public void closeWindow(){
        stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void minimizeWindow(){
        stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void logout() throws IOException {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Confirmation Required");
        alert.setContentText("Are you sure you want to Logout?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            this.closeWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/lk/projects/library/view/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        }
    }
}
