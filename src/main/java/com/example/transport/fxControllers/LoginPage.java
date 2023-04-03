package com.example.transport.fxControllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Driver;
import model.User;

import com.example.transport.utils.DbUtils;

import java.io.IOException;
import java.sql.SQLException;

import static com.example.transport.utils.fxUtils.alertDialogWarning;

public class LoginPage {
    @FXML
    public TextField loginField;
    @FXML
    public TextField passwordField;

    public void validate() throws IOException, SQLException {

        User user = DbUtils.validateUser(loginField.getText(), passwordField.getText());
        if (user != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/transport/main-page.fxml"));
            Parent content = (Parent) loader.load();
//            FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("main-page.fxml"));
//            Parent parent = fxmlLoader.load();

            MainPage mainPage = loader.getController();
            if (user instanceof Driver) {
                mainPage.manager_view.setVisible(false);
            }
            mainPage.setInfo(user);
            Scene scene = new Scene(content);
            Stage stage = (Stage) loginField.getScene().getWindow();
            stage.setTitle("FreightSys");
            stage.setScene(scene);
            stage.show();
        } else {
            alertDialogWarning("No such user", "User error");
        }
    }

    public void register() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/transport/registration-page.fxml"));
        Parent content = (Parent) loader.load();

        Scene scene = new Scene(content);
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setTitle("FreightSys");
        stage.setScene(scene);
        stage.show();
    }


}
