module com.jesus.studentmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;


    opens com.jesus.studentmanagement to javafx.fxml;
    exports com.jesus.studentmanagement;
}