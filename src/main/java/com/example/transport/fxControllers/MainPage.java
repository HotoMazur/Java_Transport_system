package com.example.transport.fxControllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import com.example.transport.utils.DbUtils;
import com.example.transport.utils.fxUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import static com.example.transport.utils.fxUtils.updateItem;


public class MainPage implements Initializable {
    private final String UPDATE_DRIVER = "UPDATE user SET name=?, surname=?, med_num=? ,driver_license=?, phone_number = ? WHERE login = ?";
    private final String UPDATE_MANAGER = "UPDATE user SET name=?, surname=?, phone_number = ?, email=? WHERE login = ?";
    private final String INSERT_CARGO = "INSERT INTO fullstack.cargo(title, date_create, weight, cargo_type, description, customer) VALUES (?,?,?,?,?,?)";
    private final String UPDATE_CARGO = "UPDATE fullstack.cargo SET title=?, weight=?, cargo_type=? ,description=?, customer = ?  WHERE id = ?";
    private final String UPDATE_DESTINATION = "UPDATE fullstack.destination SET start_city=?, start_ln=?, start_lat=?, end_city=?, end_ln=?, end_lat=?, date_updated=?, id_truck=?, id_cargo=?, id_responsible_manager=? WHERE id=?";
    private final String INSERT_CHECKPOINT = "INSERT INTO Checkpoint(title, longStop, dateArrived, destination_id) VALUE (?,?,?,?)";
    private static final String UPDATE_CARGO_ID = "UPDATE fullstack.cargo SET id_destination=? WHERE id=?";
    private static final String INSERT_DESTINATION = "INSERT INTO fullstack.destination(start_city, start_ln, start_lat, end_city, end_ln, end_lat, date_created, id_truck, id_cargo, id_responsible_manager) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_TRUCK_ID = "UPDATE fullstack.truck SET id_destination=? WHERE id=?";
    private static final String INSERT_TRUCK = "INSERT INTO fullstack.truck(make, model, year, odometer, fuel_tank_capacity, tyre_type) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_TRUCK = "UPDATE fullstack.truck SET make=?, model=?, year=? ,odometer=?, fuel_tank_capacity = ?  WHERE id = ?";

    @FXML
    public TextField makeField;
    @FXML
    public TextField modelField;
    @FXML
    public TextField yearField;
    @FXML
    public TextField odometerField;
    @FXML
    public TextField tankCapacityField;
    @FXML
    public ComboBox<TyreType> tyreTypeField;
    @FXML
    public ListView<Truck> truckListField;
    @FXML
    public ListView<Cargo> cargoListField;
    @FXML
    public TextField cargoField;
    @FXML
    public TextField cargoWeightField;
    @FXML
    public TextField cargoDescription;
    @FXML
    public TextField cargoCustomerField;
    @FXML
    public ComboBox<CargoType> CargoTypeField;
    @FXML
    public TextField destinationStartCity;
    @FXML
    public TextField destinationStartLN;
    @FXML
    public TextField destinationStartLat;
    @FXML
    public TextField destinationEndCity;
    @FXML
    public TextField destinationEndLn;
    @FXML
    public TextField checkpointTitle;
    @FXML
    public RadioButton radioShortStop;
    @FXML
    public TextField destinationEndLat;
    @FXML
    public RadioButton radioLongStop;
    @FXML
    public DatePicker destinationDateArrive;
    @FXML
    public ComboBox<Manager> responsibilityManager;
    @FXML
    public ListView<Destination> destinationList;
    @FXML
    public TableColumn<Driver, Integer> d_id;
    @FXML
    public TableColumn<Driver, String> d_login;
    @FXML
    public TableColumn<Driver, String> d_name;
    @FXML
    public TableColumn<Driver, String> d_surname;
    @FXML
    public TableColumn<Driver, Date> d_birthdate;
    @FXML
    public TableColumn<Driver, String> d_phoneNumber;
    @FXML
    public TableColumn<Driver, LocalDate> d_medCertificateDate;
    @FXML
    public TableColumn<Driver, String> d_medCertificateNumber;
    @FXML
    public TableColumn<Driver, String> d_driverLicence;
    @FXML
    public TableColumn<Manager, Integer> m_id;
    @FXML
    public TableColumn<Manager, String> m_login;
    @FXML
    public TableColumn<Manager, String> m_name;
    @FXML
    public TableColumn<Manager, String> m_surname;
    @FXML
    public TableColumn<Manager, String> m_phoneNumber;
    @FXML
    public TableColumn<Manager, String> m_email;
    @FXML
    public TableColumn<Manager, LocalDate> m_employmentDate;
    @FXML
    public TableView<Driver> driver_view;
    public TableColumn<Manager, LocalDate> m_birthdate;
    public TableView<Manager> manager_view;
    public ListView<CheckPoint> CheckpointList;
    public ComboBox<Truck> selectedTruckField;
    public ComboBox<Cargo> selectedCargoField;
    private User loggedUser;


    public void setInfo(User user) {
        this.loggedUser = user;
    }

    public void createTruck() throws SQLException, ClassNotFoundException {
        if (makeField.getText().isEmpty() || modelField.getText().isEmpty() || yearField.getText().isEmpty() ||
                odometerField.getText().isEmpty() || tankCapacityField.getText().isEmpty()) {
            fxUtils.throwAlert("error", "big error");
            return;
        }
        Truck truck = new Truck(makeField.getText(), modelField.getText(), Integer.parseInt(yearField.getText()),
                Double.parseDouble(odometerField.getText()), Double.parseDouble(tankCapacityField.getText()),
                tyreTypeField.getValue());

        Connection conn = DbUtils.connectToDb();
        PreparedStatement ps = conn.prepareStatement(INSERT_TRUCK);
        ps.setString(1, truck.getMake());
        ps.setString(2, truck.getModel());
        ps.setInt(3, truck.getYear());
        ps.setDouble(4, truck.getOdometer());
        ps.setDouble(5, truck.getFuelTankCapacity());
        ps.setString(6, String.valueOf(truck.getTyreType()));
        ps.execute();
        DbUtils.disconnection(conn, ps);
        truckListField.setItems(DbUtils.getDataTruck());
        selectedTruckField.setItems(DbUtils.getDataTruck());
        selectedTruckField.getSelectionModel().select(0);
    }

    public void updateTruck() {

        Truck selectedTruck = truckListField.getSelectionModel().getSelectedItem();
        if (selectedTruck == null) {
            fxUtils.throwAlert("Error", "Please select a truck to update.");
            return;
        }
        TextField idField = new TextField(String.valueOf(selectedTruck.getId()));
        TextField makeField = new TextField(selectedTruck.getMake());
        TextField modelField = new TextField(selectedTruck.getModel());
        TextField yearField = new TextField(String.valueOf(selectedTruck.getYear()));
        TextField odometerField = new TextField(String.valueOf(selectedTruck.getOdometer()));
        TextField tankCapacityField = new TextField(String.valueOf(selectedTruck.getFuelTankCapacity()));
        ChoiceBox<TyreType> tyreTypeChoiceBox = new ChoiceBox<>();
        tyreTypeChoiceBox.getItems().addAll(TyreType.values());
        tyreTypeChoiceBox.setValue(selectedTruck.getTyreType());

        VBox vbox = new VBox(new Label("Make:"), makeField, new Label("Model:"), modelField, new Label("Year:"), yearField, new Label("Odometer:"), odometerField, new Label("Fuel Tank Capacity:"), tankCapacityField, new Label("Tyre Type:"), tyreTypeChoiceBox);
        updateItem(truckListField, selectedTruck, "Update Truck Information", vbox, () -> {
            Connection conn = DbUtils.connectToDb();
            PreparedStatement ps = null;
            if (makeField.getText().isEmpty() || modelField.getText().isEmpty() || yearField.getText().isEmpty() ||
                    odometerField.getText().isEmpty() || tankCapacityField.getText().isEmpty()) {
                fxUtils.throwAlert("error", "Write text in field");
                return;
            }
            try {
                ps = conn.prepareStatement(UPDATE_TRUCK);
                ps.setString(1, makeField.getText());
                ps.setString(2, modelField.getText());
                ps.setInt(3, Integer.parseInt(yearField.getText()));
                ps.setDouble(4, Double.parseDouble(odometerField.getText()));
                ps.setDouble(5, Double.parseDouble(tankCapacityField.getText()));
                ps.setInt(6, selectedTruck.getId());
                System.out.println(idField.getText());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DbUtils.disconnection(conn, ps);
            }
            fxUtils.throwAlert("Updating truck info", "Updated");
        });
        try {
            truckListField.setItems(DbUtils.getDataTruck());
            selectedTruckField.setItems(DbUtils.getDataTruck());
            selectedTruckField.getSelectionModel().select(0);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void truckDelete() throws SQLException, ClassNotFoundException {
        Truck selectedTruck = truckListField.getSelectionModel().getSelectedItem();
        Connection connection = DbUtils.connectToDb();
        if (selectedTruck != null) {
            int idDriver = selectedTruck.getId();
            PreparedStatement truckStmt = connection.prepareStatement("DELETE FROM fullstack.truck WHERE id=?");
            truckStmt.setInt(1, idDriver);
            truckStmt.executeUpdate();
            DbUtils.disconnection(connection, truckStmt);
        }

        truckListField.setItems(DbUtils.getDataTruck());
        selectedTruckField.setItems(DbUtils.getDataTruck());
        selectedTruckField.getSelectionModel().select(0);
    }

    public void createCargo() throws SQLException {
        if (cargoField.getText().isEmpty() || cargoWeightField.getText().isEmpty() ||
                cargoDescription.getText().isEmpty() || cargoCustomerField.getText().isEmpty()) {
            fxUtils.throwAlert("error", "Write text in field");
            return;
        }
        Cargo cargo = new Cargo(cargoField.getText(), Double.parseDouble(cargoWeightField.getText()), CargoTypeField.getValue(), cargoDescription.getText(), cargoCustomerField.getText());

        Connection conn = DbUtils.connectToDb();
        PreparedStatement ps = conn.prepareStatement(INSERT_CARGO);
        ps.setString(1, cargo.getTitle());
        ps.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
        ps.setString(3, String.valueOf(cargo.getWeight()));
        ps.setString(4, String.valueOf(cargo.getCargoType()));
        ps.setString(5, cargo.getDescription());
        ps.setString(6, cargo.getCustomer());
        ps.execute();
        DbUtils.disconnection(conn, ps);

        cargoListField.setItems(DbUtils.getDataCargo());
        selectedCargoField.setItems(DbUtils.getDataCargo());
        selectedCargoField.getSelectionModel().select(0);
    }

    public void updateCargo() {
        Cargo selectedCargo = cargoListField.getSelectionModel().getSelectedItem();
        if (selectedCargo == null) {
            fxUtils.throwAlert("Error", "Please select a cargo to update.");
            return;
        }

        TextField idField = new TextField(String.valueOf(selectedCargo.getId()));
        TextField titleField = new TextField(selectedCargo.getTitle());
        TextField weightField = new TextField(Double.toString(selectedCargo.getWeight()));
        ChoiceBox<CargoType> typeChoiceBox = new ChoiceBox<>();
        typeChoiceBox.getItems().addAll(CargoType.values());
        typeChoiceBox.setValue(selectedCargo.getCargoType());
        TextArea descriptionArea = new TextArea(selectedCargo.getDescription());
        TextField customerField = new TextField(selectedCargo.getCustomer());


        VBox vbox = new VBox(
                new Label("Title:"), titleField,
                new Label("Weight:"), weightField,
                new Label("Type:"), typeChoiceBox,
                new Label("Description:"), descriptionArea,
                new Label("Customer:"), customerField
        );

        fxUtils.updateItem(cargoListField, selectedCargo, "Update Cargo Information", vbox, () -> {
                    Connection conn = DbUtils.connectToDb();
                    PreparedStatement ps = null;
                    if (cargoField.getText().isEmpty() || cargoWeightField.getText().isEmpty() ||
                            cargoDescription.getText().isEmpty() || cargoCustomerField.getText().isEmpty()) {
                        fxUtils.throwAlert("error", "Write text in field");
                    }
                    try {
                        ps = conn.prepareStatement(UPDATE_CARGO);
                        ps.setString(1, titleField.getText());
                        ps.setDouble(2, Double.parseDouble(weightField.getText()));
                        ps.setString(3, String.valueOf(typeChoiceBox.getValue()));
                        ps.setString(4, descriptionArea.getText());
                        ps.setString(5, customerField.getText());
                        ps.setInt(6, Integer.parseInt(idField.getText()));
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } finally {
                        DbUtils.disconnection(conn, ps);
                    }
                    fxUtils.throwAlert("Updating cargo info", "Updated");
                    try {
                        cargoListField.setItems(DbUtils.getDataCargo());
                        selectedCargoField.setItems(DbUtils.getDataCargo());
                        selectedCargoField.getSelectionModel().select(0);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
        );
    }

    public void cargoDelete() throws SQLException {
        Connection conn = DbUtils.connectToDb();
        Cargo selectedCargo = cargoListField.getSelectionModel().getSelectedItem();
        if (selectedCargo != null) {
            int idCargo = selectedCargo.getId();
            System.out.println(idCargo);
            PreparedStatement cargoStmt = conn.prepareStatement("DELETE FROM fullstack.cargo WHERE id=?");
            cargoStmt.setInt(1, idCargo);
            cargoStmt.executeUpdate();
            DbUtils.disconnection(conn, cargoStmt);
            cargoListField.setItems(DbUtils.getDataCargo());
            selectedCargoField.setItems(DbUtils.getDataCargo());
            selectedCargoField.getSelectionModel().select(0);
        }
    }

    public void DestinationAdd() throws SQLException, ClassNotFoundException, IOException {
        if (destinationStartCity.getText().isEmpty() || destinationStartLN.getText().isEmpty() ||
                destinationStartLat.getText().isEmpty() || destinationEndCity.getText().isEmpty() ||
                destinationEndLn.getText().isEmpty() || destinationEndLat.getText().isEmpty()) {
            fxUtils.throwAlert("error", "Write text in field");
            return;
        }
        Destination destination = new Destination(destinationStartCity.getText(),
                Long.parseLong(destinationStartLN.getText()),
                Long.parseLong(destinationStartLat.getText()),
                destinationEndCity.getText(), Long.parseLong(destinationEndLn.getText()),
                Long.parseLong(destinationEndLat.getText()),
                selectedTruckField.getValue(),
                responsibilityManager.getValue(),
                selectedCargoField.getValue());
        Connection conn = DbUtils.connectToDb();

        PreparedStatement psCargo = conn.prepareStatement("SELECT id FROM fullstack.cargo WHERE id=?");
        psCargo.setInt(1, destination.getCargo().getId());
        ResultSet rsCargo = psCargo.executeQuery();
        int idCargo = rsCargo.next() ? rsCargo.getInt("id") : 0;
        rsCargo.close();
        psCargo.close();

        PreparedStatement psTruck = conn.prepareStatement("SELECT id FROM fullstack.truck WHERE id=?");
        psTruck.setInt(1, destination.getTruck().getId());
        ResultSet rsTruck = psTruck.executeQuery();
        int idTruck = rsTruck.next() ? rsTruck.getInt("id") : 0;
        rsTruck.close();
        psTruck.close();

        PreparedStatement psManager = conn.prepareStatement("SELECT id FROM user WHERE id=? AND usertype='manager'");
        psManager.setInt(1, destination.getResponsibleManager().getId());
        ResultSet rsManager = psManager.executeQuery();
        int idManager = rsManager.next() ? rsManager.getInt("id") : 0;
        rsManager.close();
        psManager.close();

        PreparedStatement psDestination = conn.prepareStatement(INSERT_DESTINATION);
        psDestination.setString(1, destination.getStartCity());
        psDestination.setLong(2, destination.getStartLn());
        psDestination.setLong(3, destination.getStartLat());
        psDestination.setString(4, destination.getEndCity());
        psDestination.setLong(5, destination.getEndLn());
        psDestination.setLong(6, destination.getEndLat());
        psDestination.setDate(7, java.sql.Date.valueOf(LocalDate.now()));
        psDestination.setInt(8, idTruck);
        psDestination.setInt(9, idCargo);
        psDestination.setInt(10, idManager);
        psDestination.execute();
        psDestination.close();

        PreparedStatement psDestinationGet = conn.prepareStatement("SELECT id FROM fullstack.destination WHERE id_truck=? AND id_cargo=?");
        psDestinationGet.setInt(1, idTruck);
        psDestinationGet.setInt(2, idCargo);
        ResultSet rsDestinationGet = psDestinationGet.executeQuery();
        int idDestination = rsDestinationGet.next() ? rsDestinationGet.getInt("id") : 0;
        rsDestinationGet.close();
        psDestinationGet.close();

        PreparedStatement psCargoUpdate = conn.prepareStatement(UPDATE_CARGO_ID);
        psCargoUpdate.setInt(1, idDestination);
        psCargoUpdate.setInt(2, idCargo);
        psCargoUpdate.executeUpdate();
        psCargoUpdate.close();

        PreparedStatement psTruckUpdate = conn.prepareStatement(UPDATE_TRUCK_ID);
        psTruckUpdate.setInt(1, idDestination);
        psTruckUpdate.setInt(2, idTruck);
        psTruckUpdate.executeUpdate();
        psTruckUpdate.close();

        destinationList.setItems(DbUtils.getDataDestination());
        destinationDisable();
    }

    public void updateDestination() throws SQLException, ClassNotFoundException {
        Destination selectedDestination = destinationList.getSelectionModel().getSelectedItem();
        if (selectedDestination == null) {
            fxUtils.throwAlert("Error", "Please select a destination to update.");
            return;
        }

        TextField startCityField = new TextField(selectedDestination.getStartCity());
        TextField startLNField = new TextField(String.valueOf(selectedDestination.getStartLn()));
        TextField startLatField = new TextField(String.valueOf(selectedDestination.getStartLat()));
        TextField endCityField = new TextField(selectedDestination.getEndCity());
        TextField endLNField = new TextField(String.valueOf(selectedDestination.getEndLn()));
        TextField endLatField = new TextField(String.valueOf(selectedDestination.getEndLat()));
        ChoiceBox<Truck> truckChoiceBox = new ChoiceBox<>(DbUtils.getDataTruck());
        truckChoiceBox.getSelectionModel().select(0);
        ChoiceBox<Cargo> cargoChoiceBox = new ChoiceBox<>(DbUtils.getDataCargo());
        cargoChoiceBox.getSelectionModel().select(0);
        ChoiceBox<Manager> managerChoiceBox = new ChoiceBox<>(DbUtils.getDataManagers());
        managerChoiceBox.getSelectionModel().select(0);
        VBox vbox = new VBox(new Label("Start City:"), startCityField, new Label("Start Longitude:"), startLNField, new Label("Start Latitude:"), startLatField, new Label("End City:"), endCityField, new Label("End Longitude:"), endLNField, new Label("End Latitude:"), endLatField, new Label("Truck"), truckChoiceBox, new Label("Cargo"), cargoChoiceBox, new Label("Manager"), managerChoiceBox);
        fxUtils.updateItem(destinationList, selectedDestination, "Update Destination Information", vbox, () -> {

            Connection conn = DbUtils.connectToDb();
            PreparedStatement psGetCargoTruckId = null;

            if (destinationStartCity.getText().isEmpty() || destinationStartLN.getText().isEmpty() ||
                    destinationStartLat.getText().isEmpty() || destinationEndCity.getText().isEmpty() ||
                    destinationEndLn.getText().isEmpty() || destinationEndLat.getText().isEmpty()) {
                fxUtils.throwAlert("error", "Write text in field");
                return;
            }

            try {
                psGetCargoTruckId = conn.prepareStatement("SELECT truck_id, cargo_id FROM Destination WHERE id=?");

                psGetCargoTruckId.setInt(1, selectedDestination.getId());
                ResultSet rsGetTruckCargoId = psGetCargoTruckId.executeQuery();
                int truckId = 0;
                int cargoId = 0;
                while (rsGetTruckCargoId.next()) {
                    truckId = rsGetTruckCargoId.getInt("truck_id");
                    cargoId = rsGetTruckCargoId.getInt("cargo_id");
                }
                System.out.println(truckId);
                System.out.println(cargoId);
                rsGetTruckCargoId.close();
                psGetCargoTruckId.close();

                PreparedStatement psDestinationUpdate = conn.prepareStatement(UPDATE_DESTINATION);
                psDestinationUpdate.setString(1, startCityField.getText());
                psDestinationUpdate.setLong(2, Long.parseLong(startLNField.getText()));
                psDestinationUpdate.setLong(3, Long.parseLong(startLatField.getText()));
                psDestinationUpdate.setString(4, endCityField.getText());
                psDestinationUpdate.setLong(5, Long.parseLong(endLNField.getText()));
                psDestinationUpdate.setLong(6, Long.parseLong(endLatField.getText()));
                psDestinationUpdate.setDate(7, java.sql.Date.valueOf(LocalDate.now()));
                psDestinationUpdate.setInt(8, truckChoiceBox.getValue().getId());
                psDestinationUpdate.setInt(9, cargoChoiceBox.getValue().getId());
                psDestinationUpdate.setInt(10, managerChoiceBox.getValue().getId());
                psDestinationUpdate.setInt(11, selectedDestination.getId());
                psDestinationUpdate.executeUpdate();
                psDestinationUpdate.close();

                if (truckId != truckChoiceBox.getValue().getId()) {
                    PreparedStatement psIdDestinationDeleteFromTruck = conn.prepareStatement("UPDATE Truck SET destination_id=NULL WHERE id=?");
                    psIdDestinationDeleteFromTruck.setInt(1, truckId);
                    psIdDestinationDeleteFromTruck.executeUpdate();
                    psIdDestinationDeleteFromTruck.close();

                    PreparedStatement psIdDestinationAddToTruck = conn.prepareStatement("UPDATE Truck SET destination_id=? WHERE id=?");
                    psIdDestinationAddToTruck.setInt(1, selectedDestination.getId());
                    psIdDestinationAddToTruck.setInt(2, truckChoiceBox.getValue().getId());
                    psIdDestinationAddToTruck.executeUpdate();
                    psIdDestinationAddToTruck.close();
                }

                if (cargoId != cargoChoiceBox.getValue().getId()) {
                    PreparedStatement psIdDestinationDeleteFromCargo = conn.prepareStatement("UPDATE Cargo SET destination_id=NULL WHERE id=?");
                    psIdDestinationDeleteFromCargo.setInt(1, cargoId);
                    psIdDestinationDeleteFromCargo.executeUpdate();
                    psIdDestinationDeleteFromCargo.close();

                    PreparedStatement psDestinationIdAddToCargo = conn.prepareStatement("UPDATE Cargo SET destination_id=? WHERE id=?");
                    psDestinationIdAddToCargo.setInt(1, selectedDestination.getId());
                    psDestinationIdAddToCargo.setInt(2, cargoChoiceBox.getValue().getId());
                    psDestinationIdAddToCargo.executeUpdate();
                    psDestinationIdAddToCargo.close();
                }
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            fxUtils.throwAlert("Updating destination info", "Updated");
        });
        destinationList.setItems(DbUtils.getDataDestination());
        destinationDisable();
    }

    public void destinationDelete() throws SQLException, ClassNotFoundException {
        Connection conn = DbUtils.connectToDb();
        Destination selectedDestination = destinationList.getSelectionModel().getSelectedItem();
        System.out.println(selectedDestination);
        if (selectedDestination != null) {
            int idDestination = selectedDestination.getId();

            PreparedStatement psTruck = conn.prepareStatement("UPDATE Truck SET Truck.destination_id=NULL WHERE destination_id=?");
            psTruck.setInt(1, idDestination);
            psTruck.executeUpdate();

            PreparedStatement psCargo = conn.prepareStatement("UPDATE Cargo SET Cargo.destination_id=NULL WHERE destination_id=?");
            psCargo.setInt(1, idDestination);
            psCargo.executeUpdate();

            System.out.println(idDestination);
            PreparedStatement psDestination = conn.prepareStatement("DELETE FROM Destination WHERE id=?");
            psDestination.setInt(1, idDestination);
            psDestination.executeUpdate();
            DbUtils.disconnection(conn, psDestination);

            destinationList.setItems(DbUtils.getDataDestination());
            destinationDisable();
        }
    }

    public void destinationDisable() throws SQLException, ClassNotFoundException {
        selectedTruckField.setItems(DbUtils.getDataTruck());
        selectedTruckField.getSelectionModel().select(0);
        selectedCargoField.setItems(DbUtils.getDataCargo());
        selectedCargoField.getSelectionModel().select(0);
        cargoListField.setItems(DbUtils.getDataCargo());
        truckListField.setItems(DbUtils.getDataTruck());
    }

    public void addCheckpoint() throws SQLException {
        if (checkpointTitle.getText().isEmpty()) {
            fxUtils.throwAlert("error", "Write text in field");
            return;
        }
        Destination selectDestination = destinationList.getSelectionModel().getSelectedItem();
        if (selectDestination != null) {
            Connection conn = DbUtils.connectToDb();
            PreparedStatement psCheckpointAdd = conn.prepareStatement(INSERT_CHECKPOINT);
            psCheckpointAdd.setString(1, checkpointTitle.getText());
            psCheckpointAdd.setBoolean(2, radioLongStop.isSelected());
            psCheckpointAdd.setDate(3, java.sql.Date.valueOf(destinationDateArrive.getValue()));
            psCheckpointAdd.setInt(4, selectDestination.getId());
            psCheckpointAdd.execute();
            DbUtils.disconnection(conn, psCheckpointAdd);
            CheckpointList.setItems(DbUtils.getDataCheckpoint(selectDestination));
        } else {
            fxUtils.throwAlert("Error", "Select destination");
        }
    }

    public void updateCheckpoint() {
        CheckPoint selectedCheckpoint = CheckpointList.getSelectionModel().getSelectedItem();
        if (selectedCheckpoint == null) {
            fxUtils.throwAlert("Error", "Please select a destination to update.");
            return;
        }

        TextField titleField = new TextField(selectedCheckpoint.getTitle());
        DatePicker dateArriveField = new DatePicker(selectedCheckpoint.getDateArrived());
        VBox vbox = new VBox(new Label("Title:"), titleField, new Label("Date Arrive:"), dateArriveField);
        if (checkpointTitle.getText().isEmpty()) {
            fxUtils.throwAlert("error", "Write text in field");
            return;
        }
        fxUtils.updateItem(CheckpointList, selectedCheckpoint, "Update Checkpoint Information", vbox, () -> {
            selectedCheckpoint.setTitle(titleField.getText());
            selectedCheckpoint.setDateArrived(dateArriveField.getValue());
            fxUtils.throwAlert("Updating checkpoint info", "Updated");
        });
        CheckpointList.refresh();
    }

    public void deleteCheckpoint() throws SQLException {
        CheckPoint selectedCheckpoint = CheckpointList.getSelectionModel().getSelectedItem();
        System.out.println(selectedCheckpoint.getId());
        Connection conn = DbUtils.connectToDb();
        PreparedStatement psCheckpointDelete = conn.prepareStatement("DELETE FROM Checkpoint WHERE id=?");
        psCheckpointDelete.setInt(1, selectedCheckpoint.getId());
        psCheckpointDelete.executeUpdate();
        DbUtils.disconnection(conn, psCheckpointDelete);

    }

    public void updateUser() throws ClassNotFoundException {
        Connection conn = DbUtils.connectToDb();
        Driver selectedDriver = driver_view.getSelectionModel().getSelectedItem();
        if (selectedDriver != null) {
            TextField loginField = new TextField(selectedDriver.getLogin());
            TextField nameField = new TextField(selectedDriver.getName());
            TextField surnameField = new TextField(selectedDriver.getSurname());
            TextField med_numField = new TextField(selectedDriver.getMedCertificateNumber());
            TextField driver_licenceField = new TextField(selectedDriver.getDriverLicense());
            TextField phone_numberField = new TextField(selectedDriver.getPhoneNumber());

            VBox vbox = new VBox(
                    new Label("Name:"), nameField,
                    new Label("Surname:"), surnameField,
                    new Label("Phone number"), phone_numberField,
                    new Label("Medical licence number"), med_numField,
                    new Label("Driver Licence"), driver_licenceField
            );
            fxUtils.updateItem(driver_view,
                    selectedDriver,
                    "Update Driver Information",
                    vbox,
                    () -> {
                        PreparedStatement ps = null;
                        try {
                            ps = conn.prepareStatement(UPDATE_DRIVER);
                            ps.setString(1, nameField.getText());
                            ps.setString(2, surnameField.getText());
                            ps.setString(3, med_numField.getText());
                            ps.setString(4, driver_licenceField.getText());
                            ps.setString(5, phone_numberField.getText());
                            ps.setString(6, loginField.getText());
                            ps.executeUpdate();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } finally {
                            DbUtils.disconnection(conn, ps);
                        }
                        fxUtils.throwAlert("Updating driver info", "Updated");
                    });
            driver_view.setItems(DbUtils.getDataDrivers());
        } else {
            Manager selectedManager = manager_view.getSelectionModel().getSelectedItem();
            TextField loginField = new TextField(selectedManager.getLogin());
            TextField nameField = new TextField(selectedManager.getName());
            TextField surnameField = new TextField(selectedManager.getSurname());
            TextField emailField = new TextField(selectedManager.getEmail());
            TextField phone_numberField = new TextField(selectedManager.getPhoneNumber());

            VBox vbox = new VBox(
                    new Label("Name:"), nameField,
                    new Label("Surname:"), surnameField,
                    new Label("Phone number"), phone_numberField,
                    new Label("Email"), emailField
            );

            fxUtils.updateItem(manager_view,
                    selectedManager,
                    "Update manager information",
                    vbox,
                    () -> {
                        PreparedStatement ps = null;
                        try {
                            ps = conn.prepareStatement(UPDATE_MANAGER);
                            ps.setString(1, nameField.getText());
                            ps.setString(2, surnameField.getText());
                            ps.setString(3, phone_numberField.getText());
                            ps.setString(4, emailField.getText());
                            ps.setString(5, loginField.getText());
                            ps.executeUpdate();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } finally {
                            DbUtils.disconnection(conn, ps);
                        }
                        fxUtils.throwAlert("Updating driver info", "Updated");
                    });
            manager_view.setItems(DbUtils.getDataManagers());
        }
    }

    public void deleteUser() throws SQLException {
        Connection connection = DbUtils.connectToDb();
        Driver selectedDriver = driver_view.getSelectionModel().getSelectedItem();
        Manager selectedManager = manager_view.getSelectionModel().getSelectedItem();
        if (selectedDriver != null) {
            String loginDriver = selectedDriver.getLogin();
            PreparedStatement driverStmt = connection.prepareStatement("DELETE FROM user WHERE login=?");
            driverStmt.setString(1, loginDriver);
            try {
                connection.setAutoCommit(false);
                driverStmt.executeUpdate();
                connection.commit();
                ObservableList<Driver> driverList = driver_view.getItems();
                driverList.remove(selectedDriver);
                driver_view.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                connection.setAutoCommit(true);
                connection.close();
            }
        } else {
            String loginManager = selectedManager.getLogin();
            PreparedStatement managerStmt = connection.prepareStatement("DELETE FROM user WHERE login=?");
            managerStmt.setString(1, loginManager);
            connection.setAutoCommit(false);
            try {
                connection.setAutoCommit(false);
                managerStmt.executeUpdate();
                ObservableList<Manager> managerList = manager_view.getItems();
                managerList.remove(selectedManager);
                manager_view.refresh();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Initializing Truck type
        tyreTypeField.setItems(FXCollections.observableArrayList(TyreType.values()));
        tyreTypeField.getSelectionModel().select(0);
        //Initializing Cargo type
        CargoTypeField.setItems(FXCollections.observableArrayList(CargoType.values()));
        CargoTypeField.getSelectionModel().select(0);
        //Initialization responsibility manager
        try {
            responsibilityManager.setItems(DbUtils.getDataManagers());
            responsibilityManager.getSelectionModel().select(0);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //initialization Cargo in Destination
        try {
            selectedCargoField.setItems(DbUtils.getDataCargo());
            selectedCargoField.getSelectionModel().select(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //initialization Truck in Destination
        try {
            selectedTruckField.setItems(DbUtils.getDataTruck());
            selectedTruckField.getSelectionModel().select(0);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        //initialization destination
        try {
            destinationList.setItems(DbUtils.getDataDestination());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //initialization Long stop radio button
        radioLongStop.setSelected(true);


        //initialization driver
        d_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        d_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        d_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        d_surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        d_birthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        d_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        d_medCertificateDate.setCellValueFactory(new PropertyValueFactory<>("medCertificateDate"));
        d_medCertificateNumber.setCellValueFactory(new PropertyValueFactory<>("medCertificateNumber"));
        d_driverLicence.setCellValueFactory(new PropertyValueFactory<>("driverLicense"));
        try {
            driver_view.setItems(DbUtils.getDataDrivers());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        //initialization manager
        m_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        m_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        m_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        m_surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        m_birthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        m_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        m_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        m_employmentDate.setCellValueFactory(new PropertyValueFactory<>("employmentDate"));
        try {
            manager_view.setItems(DbUtils.getDataManagers());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //initialization truck
        try {
            truckListField.setItems(DbUtils.getDataTruck());
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        //initialization cargo

        try {
            cargoListField.setItems(DbUtils.getDataCargo());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void openForum() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/transport/forum-page.fxml"));
        Parent content = (Parent) loader.load();

        Scene scene = new Scene(content);
        Stage stage = new Stage();
        stage.setTitle("Forum");
        stage.setScene(scene);
        stage.show();
    }

    public void goToCheckpoint() throws SQLException {
        Destination selectedDestination = destinationList.getSelectionModel().getSelectedItem();
        if (selectedDestination != null) {
            CheckpointList.setItems(DbUtils.getDataCheckpoint(selectedDestination));
        }
    }
}
