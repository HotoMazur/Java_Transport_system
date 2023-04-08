package com.example.transport.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Driver;
import model.*;

import java.sql.*;
import java.time.LocalDate;


public class DbUtils {
    public static Connection connectToDb() {
        Connection connection = null;
        String DB_URL = "jdbc:mysql://localhost:3306/fullstack";
        String USER = "root";
        String PASS = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
        return connection;
    }

    public static void disconnection(Connection connection, Statement statement) {
        try {
            if (connection != null && statement != null) {
                connection.close();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User validateUser(String login, String password) throws SQLException {
        Connection connection = connectToDb();
        String sql = "SELECT * FROM user WHERE login=? AND password=?";
        PreparedStatement preparedStatementUser = connection.prepareStatement(sql);
        preparedStatementUser.setString(1, login);
        preparedStatementUser.setString(2, password);
        ResultSet rsU = preparedStatementUser.executeQuery();
        User user = null;
        while (rsU.next()) {
            if (rsU.getString("usertype").equals("driver")) {
                user = new Driver(rsU.getString("login"), rsU.getString("password"), rsU.getString("name"), rsU.getString("surname"), rsU.getDate("birth_date").toLocalDate(), rsU.getString("phone_number"), rsU.getDate("med_date").toLocalDate(), rsU.getString("med_num"), rsU.getString("driver_license"));
            } else {
                user = new Manager(rsU.getString("login"), rsU.getString("password"), rsU.getString("name"), rsU.getString("surname"), rsU.getDate("birth_date").toLocalDate(), rsU.getString("phone_number"), rsU.getString("email"), rsU.getDate("employment_date").toLocalDate(), rsU.getBoolean("is_admin"));
            }
        }
        disconnection(connection, preparedStatementUser);
        return user;
    }

    public static ObservableList<Driver> getDataDrivers() throws ClassNotFoundException {
        Connection connection = connectToDb();
        ObservableList<Driver> list = FXCollections.observableArrayList();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("select * from user WHERE usertype = 'driver'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Driver(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("name"), rs.getString("surname"), rs.getDate("birth_date").toLocalDate(), rs.getString("phone_number"), Date.valueOf(rs.getString("med_date")).toLocalDate(), rs.getString("med_num"), rs.getString("driver_license")));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        disconnection(connection, ps);
        return list;
    }

    public static ObservableList<Manager> getDataManagers() throws ClassNotFoundException {
        Connection connection = connectToDb();
        ObservableList<Manager> list = FXCollections.observableArrayList();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("select * from user WHERE usertype = 'manager'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Manager(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("name"), rs.getString("surname"), rs.getDate("birth_date").toLocalDate(), rs.getString("phone_number"), rs.getString("email"), Date.valueOf(rs.getString("employment_date")).toLocalDate(), rs.getBoolean("is_admin")));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        disconnection(connection, ps);
        return list;
    }


    public static ObservableList<Cargo> getDataCargo() throws SQLException {
        Connection conn = connectToDb();
        ObservableList<Cargo> list = FXCollections.observableArrayList();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM fullstack.cargo WHERE cargo.id_destination IS NULL");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Cargo(rs.getInt("id"), rs.getString("title"),
                    rs.getDouble("weight"), getCargoTypeFromString(rs.getString("cargo_type")),
                    rs.getString("description"), rs.getString("customer")));
        }
        disconnection(conn, ps);
        return list;
    }

    public static CargoType getCargoTypeFromString(String cargoTypeString) {
        return switch (cargoTypeString) {
            case "PAPER" -> CargoType.PAPER;
            case "ALCOHOL" -> CargoType.ALCOHOL;
            case "MIX" -> CargoType.MIX;
            case "ELECTRONICS" -> CargoType.ELECTRONICS;
            case "CONSUMER_ELECTRONICS" -> CargoType.CONSUMER_ELECTRONICS;
            case "FOOD" -> CargoType.FOOD;
            default -> throw new IllegalArgumentException("Invalid TyreType: " + cargoTypeString);
        };
    }


    public static ObservableList<CheckPoint> getDataCheckpoint(Destination selectedDestination) throws SQLException {
        Connection conn = connectToDb();
        ObservableList<CheckPoint> list = FXCollections.observableArrayList();
        PreparedStatement psCheckpoint = conn.prepareStatement("SELECT id, title, long_stop, date_arrived FROM fullstack.checkpoint WHERE id_destination=?");
        psCheckpoint.setInt(1, selectedDestination.getId());
        ResultSet rs = psCheckpoint.executeQuery();
        while (rs.next()) {
            list.add(new CheckPoint(rs.getInt("id"), rs.getString("title"), rs.getBoolean("long_stop"), LocalDate.parse(rs.getString("date_arrived"))));
        }
        disconnection(conn, psCheckpoint);
        return list;
    }

    public static ObservableList<Destination> getDataDestination() throws SQLException {
        Connection conn = connectToDb();
        ObservableList<Destination> list = FXCollections.observableArrayList();
        PreparedStatement psDestination = conn.prepareStatement("SELECT * FROM fullstack.destination");
        ResultSet rsDestination = psDestination.executeQuery();
        while (rsDestination.next()) {
            list.add(new Destination(rsDestination.getInt("id"), rsDestination.getString("start_city"), rsDestination.getLong("start_ln"), rsDestination.getLong("start_lat"), rsDestination.getString("end_city"), rsDestination.getLong("end_ln"), rsDestination.getLong("end_lat")));
        }
        rsDestination.close();
        DbUtils.disconnection(conn, psDestination);
        return list;
    }

    public static ObservableList<Truck> getDataTruck() throws ClassNotFoundException, SQLException {
        Connection conn = DbUtils.connectToDb();
        ObservableList<Truck> list = FXCollections.observableArrayList();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM fullstack.truck WHERE truck.id_destination IS NULL");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Truck(rs.getInt("id"), rs.getString("make"), rs.getString("model"), rs.getInt("year"), rs.getDouble("odometer"), rs.getDouble("fuel_tank_capacity"), getTyreTypeFromString(rs.getString("tyre_type"))));
        }
        DbUtils.disconnection(conn, ps);
        return list;
    }

    public static TyreType getTyreTypeFromString(String tyreTypeString) {
        return switch (tyreTypeString) {
            case "TYPE_1" -> TyreType.TYPE_1;
            case "TYPE_2" -> TyreType.TYPE_2;
            default -> throw new IllegalArgumentException("Invalid TyreType: " + tyreTypeString);
        };
    }
}
