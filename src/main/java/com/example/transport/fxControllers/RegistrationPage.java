package com.example.transport.fxControllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Manager;
import com.example.transport.utils.DbUtils;
import com.example.transport.utils.fxUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegistrationPage implements Initializable {
    private final String EXISTING_USER_QUERY = "(Select * FROM user WHERE login=?)";
    String insertDriver = "INSERT INTO drivers(login, password, name, surname, birth_date, med_date, med_num, driver_license, phone_num) VALUES (?,?,?,?,?,?,?,?,?)";
    String INSERT_DRIVER = "INSERT INTO user(login, password, name, surname, birth_date, phone_number, med_date, med_num, driver_license, usertype) VALUES (?,?,?,?,?,?,?,?,?,?)";
    String INSERT_MANAGER = "INSERT INTO user(login, password, name, surname, birth_date, phone_number, email, employment_date, is_admin, usertype) VALUES (?,?,?,?,?,?,?,?,?,?)";
    String insertManager = "INSERT INTO managers(login, password, name, surname, birth_date, phone_number, email, employment_date, is_admin) VALUES (?,?,?,?,?,?,?,?,?)";
    public TextField loginField;
    public TextField nameField;
    public TextField surnameField;
    public PasswordField pswField;
    public PasswordField repPswField;
    public DatePicker bDateField;
    public TextField managerEmailField;
    public TextField phoneNumberField;
    public RadioButton radioD;
    public RadioButton radioM;
    public DatePicker medCerDateField;
    public TextField medCerNumField;
    public TextField driveLicField;
    public CheckBox isAdminChk;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        radioM.setSelected(true);
        isAdminChk.setVisible(false);
        disableFields();
    }

    public void createNewUser() throws IOException, SQLException, ClassNotFoundException {
        Connection connection = DbUtils.connectToDb();
        PreparedStatement preparedStatement;
        if (pswField.getText().equals(repPswField.getText())) {
            if (checkUserInDb(connection)) {
                if (radioD.isSelected()) {
                    preparedStatement = connection.prepareStatement(INSERT_DRIVER);
                    model.Driver driver = new model.Driver(loginField.getText(), pswField.getText(), nameField.getText(), surnameField.getText(), LocalDate.parse(bDateField.getValue().toString()), phoneNumberField.getText(), LocalDate.parse(medCerDateField.getValue().toString()), medCerNumField.getText(), driveLicField.getText());
                    preparedStatement.setString(1, driver.getLogin());
                    preparedStatement.setString(2, driver.getPassword());
                    preparedStatement.setString(3, driver.getName());
                    preparedStatement.setString(4, driver.getSurname());
                    preparedStatement.setDate(5, Date.valueOf(driver.getBirthdate()));
                    preparedStatement.setString(6, driver.getPhoneNumber());
                    preparedStatement.setDate(7, Date.valueOf(driver.getMedCertificateDate()));
                    preparedStatement.setString(8, driver.getMedCertificateNumber());
                    preparedStatement.setString(9, driver.getDriverLicense());
                    preparedStatement.setString(10, "driver");

                    preparedStatement.execute();

                } else {
                    preparedStatement = connection.prepareStatement(INSERT_MANAGER);
                    Manager manager = new Manager(loginField.getText(), pswField.getText(), nameField.getText(), surnameField.getText(), LocalDate.parse(bDateField.getValue().toString()), phoneNumberField.getText(), managerEmailField.getText());

                    preparedStatement.setString(1, manager.getLogin());
                    preparedStatement.setString(2, manager.getPassword());
                    preparedStatement.setString(3, manager.getName());
                    preparedStatement.setString(4, manager.getSurname());
                    preparedStatement.setDate(5, Date.valueOf(manager.getBirthdate()));
                    preparedStatement.setString(6, manager.getPhoneNumber());
                    preparedStatement.setString(7, manager.getEmail());
                    preparedStatement.setDate(8, Date.valueOf(manager.getEmploymentDate()));
                    preparedStatement.setBoolean(9, manager.isAdmin());
                    preparedStatement.setString(10, "manager");


                    preparedStatement.execute();

                }

                DbUtils.disconnection(connection, preparedStatement);
                returnToPrevious();
            }
        }
    }

    public void returnToPrevious() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/transport/login-page.fxml"));
        Parent content = (Parent) loader.load();

        Scene scene = new Scene(content);
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setTitle("FreightSys");
        stage.setScene(scene);
        stage.show();
    }

    public void disableFields() {
        if (radioD.isSelected()) {
            medCerDateField.setDisable(false);
            medCerNumField.setDisable(false);
            driveLicField.setDisable(false);
            managerEmailField.setDisable(true);
        } else {
            medCerDateField.setDisable(true);
            medCerNumField.setDisable(true);
            driveLicField.setDisable(true);
            managerEmailField.setDisable(false);
        }
    }

    public boolean checkUserInDb(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(EXISTING_USER_QUERY);
        preparedStatement.setString(1, loginField.getText());
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            fxUtils.alertDialogWarning("Login used", "User error");
            return false;
        } else {
            return true;
        }
    }
}
