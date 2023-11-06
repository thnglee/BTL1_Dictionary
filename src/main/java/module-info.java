module com.example.btl1_dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;
    requires javafx.web;
    requires freetts;


    opens com.example.btl1_dictionary to javafx.fxml;
    exports com.example.btl1_dictionary;
}