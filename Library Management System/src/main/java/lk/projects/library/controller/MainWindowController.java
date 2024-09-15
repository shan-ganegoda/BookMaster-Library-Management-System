package lk.projects.library.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindowController implements Initializable {

    @FXML
    private Label exit;

    @FXML
    private StackPane contentArea;

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

    private void loadFXML(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent fxml = loader.load();
        contentArea.getChildren().clear();
        contentArea.getChildren().add(fxml);
    }
}
