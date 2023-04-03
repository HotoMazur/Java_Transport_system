module com.example.transport {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires lombok;
    requires mysql.connector.java;
    requires java.persistence;
    requires com.fasterxml.jackson.databind;
    requires org.hibernate.orm.core;

    opens entity;
    opens com.example.transport to javafx.fxml;
    opens com.example.transport.fxControllers to javafx.fxml;
    opens model to javafx.base;
    exports com.example.transport;
    exports com.example.transport.fxControllers to javafx.fxml;
}
