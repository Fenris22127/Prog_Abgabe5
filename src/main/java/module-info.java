module de.medieninformatik.prog_abgabe5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.medieninformatik.prog_abgabe5 to javafx.fxml;
    exports de.medieninformatik.prog_abgabe5;
}