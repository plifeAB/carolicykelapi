module com.caroli.carolicykel {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.json;
    requires json.simple;
    requires com.google.gson;


    opens com.caroli.cykel to javafx.fxml;
    exports com.caroli.cykel;
}