module com.uniquindio.parcial1p2 {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens com.uniquindio.parcial1p2 to javafx.fxml;
    opens com.uniquindio.parcial1p2.controlador to javafx.fxml;
    opens com.uniquindio.parcial1p2.modelo to javafx.base;
    opens com.uniquindio.parcial1p2.modelo.programa to javafx.base;

    exports com.uniquindio.parcial1p2;
    exports com.uniquindio.parcial1p2.controlador;
}