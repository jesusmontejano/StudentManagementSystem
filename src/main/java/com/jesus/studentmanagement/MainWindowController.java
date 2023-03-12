package com.jesus.studentmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class MainWindowController {
    // Insert new student fields
    @FXML
    private TextField stuIdText;
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField lastNameText;
    @FXML
    private TextField addressText;
    @FXML
    private TextField phoneText;
    @FXML
    private TextArea resultArea;
    @FXML
    private TextField newPhoneText;
    @FXML
    private TextField newAddressText;
    // Tableview fields
    @FXML
    private TableView studentTable;
    @FXML
    private TableColumn<Student, Integer> stuIdColumn;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;
    @FXML
    private TableColumn<Student, String> addressColumn;
    @FXML
    private TableColumn<Student, String> phoneColumn;

    // Initialization of controller class
    @FXML
    private void initialize() {
        stuIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
    }

    // Populate student in table
    @FXML
    private void populateStudent(Student stu) throws ClassNotFoundException {
        ObservableList<Student> stuData = FXCollections.observableArrayList();
        stuData.add(stu);
        studentTable.setItems(stuData);
    }
    // Set student info in textArea
    @FXML
    private void setStuInfoTextArea(Student stu) {
        resultArea.setText("First Name: " + stu.getFirstName() + "\n" +
                "Last Name: " + stu.getLastName());
    }
    // Populate students in table and Display on textarea
    @FXML
    private void populateAndShowStudent(Student stu) throws ClassNotFoundException{
        if(stu != null) {
            populateStudent(stu);
        } else {
            resultArea.setText("This student does not exist");
        }
    }

    // Insert new student
    @FXML
    private void insertStudent(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            StudentDAO.insertStudent(firstNameText.getText(), lastNameText.getText(),
                    addressText.getText(), phoneText.getText());
            resultArea.setText("Student Added");

        } catch (SQLException e) {
            resultArea.setText("Problem has occurred: " + e);
            throw e;
        } finally {
            firstNameText.clear();
            lastNameText.clear();
            addressText.clear();
            phoneText.clear();
        }
        DBUtil.dbDisconnect();
    }

    // Search a student using id
    @FXML
    private void searchStudent(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            Student student = StudentDAO.searchStudent(Integer.parseInt(stuIdText.getText()));
            populateAndShowStudent(student);
        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error occurred getting info for student: " + e);
            throw e;
        }
        DBUtil.dbDisconnect();
    }

    // Search ALL students
    @FXML
    private void searchStudents(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            ObservableList<Student> stuData = StudentDAO.searchStudents();
            populateStudents(stuData);
        } catch (SQLException e) {
            System.out.println("Error occurred searchStudents: " + e);
            throw e;
        }
        DBUtil.dbDisconnect();
    }
    // Populate all Students
    @FXML
    private void populateStudents(ObservableList<Student> stuData) throws  ClassNotFoundException {
        studentTable.setItems(stuData);
    }

    // Delete student
    @FXML
    private void deleteStudent(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            StudentDAO.deleteStuWithId(Integer.parseInt(stuIdText.getText()));
            resultArea.setText("Student with id: " + stuIdText.getText() + "\n");
        } catch (SQLException e) {
            resultArea.setText("Problem occurred deleting student: " + e);
            throw e;
        }
        studentTable.getItems().clear();
        DBUtil.dbDisconnect();
    }

    // Update Address and Phone
    @FXML
    private void updateStudent(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(newAddressText.getText().isEmpty() || newPhoneText.getText().isEmpty() || stuIdText.getText().isEmpty()){
            resultArea.setText("Both new address and new phone as the student ID need to be entered");
        } else {
            // Get new values
            String stuId = stuIdText.getText();
            String newAddress = newAddressText.getText();
            String newPhone = newPhoneText.getText();
            String stmt = "UPDATE Students " +
                    "SET address = '" + newAddress + "', phone = '" + newPhone + "'" +
                    "WHERE id= " + stuId;
            DBUtil.dbExecuteUpdate(stmt);
            resultArea.setText("Student with id: " + stuId + " is updated.\n" +
                    "Please refresh the table to show changes.");
        }

    }

}