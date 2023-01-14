module com.caroli.carolicykel {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.json;
    requires json.simple;
    requires com.google.gson;

    requires httpclient;  // <-- added this line
    requires httpcore;    // <-- added this lin

    opens com.caroli.cykel to javafx.fxml;
    exports com.caroli.cykel;
}