module com.uniquindio.parcial1p2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.uniquindio.parcial1p2 to javafx.fxml;
    exports com.uniquindio.parcial1p2;
}
