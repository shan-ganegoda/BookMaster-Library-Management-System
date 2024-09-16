package lk.projects.library.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.projects.library.HelloApplication;
import lk.projects.library.dao.CommonDao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    public void login(ActionEvent event) throws SQLException, IOException {

        Alert alert;
        String username = txtUsername.getText();
        String password = txtPassword.getText();

//        System.out.println(password + " " + username);

        ResultSet result = CommonDao.get("select * from user where username = '" + username + "' and password = '" + password + "'");

        if (!result.isBeforeFirst()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Username or Passsword Invalid!");
            alert.showAndWait();
        }else{
            this.closeWindow();



            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/lk/projects/library/view/mainwindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);

            MainWindowController controller = fxmlLoader.getController();
            controller.setUsername(username);

            stage.show();


        }
    }

    @FXML
    public void closeWindow(){
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }
}