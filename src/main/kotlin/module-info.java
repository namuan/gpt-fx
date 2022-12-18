module com.github.namuan.gptfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires com.google.gson;
    requires java.net.http;


    opens com.github.namuan.gptfx to javafx.fxml, com.google.gson;
    exports com.github.namuan.gptfx;
}