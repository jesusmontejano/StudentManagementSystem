package com.jesus.studentmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {
    private final String TABLE_NAME = "Students";
    //*******************
    // Insert new student
    //*******************
    public static void insertStudent(String firstName, String lastName, String address, String phone) throws SQLException {
        // Execute statement
        try {
            DBUtil.dbExecuteUpdate("INSERT INTO Students(firstName, lastName, address, phone)" +
                    "VALUES('" +firstName+ "', '" +lastName+ "', '" +address+ "', '" +phone+ "')");
        } catch (SQLException e) {
            System.out.println("Problem with insertStudent: " + e);
            throw e;
        }
    }

    //****************************
    // SELECT a student (search for a student)
    //***********************
    public static Student searchStudent(int stuId) throws SQLException {
        // Create sql statement
        String selectStmt = "SELECT * FROM Students WHERE id=" + stuId;

        // Execute statement
        try{
            ResultSet rsStudent = DBUtil.dbExecuteQuery(selectStmt);
            Student student = getStudentFromResultSet(rsStudent);
            return student;
        } catch (SQLException e) {
            System.out.println("Wrong searchStudent " + e);
            throw e;
        }
    }

    // Method to convert resultset to student
    private static Student getStudentFromResultSet(ResultSet rs) throws SQLException {
        Student stu = null;
        if(rs.next()) {
            stu = new Student();
            stu.setId(rs.getInt("ID"));
            stu.setFirstName(rs.getString("firstName"));
            stu.setLastName(rs.getString("lastName"));
            stu.setAddress(rs.getString("address"));
            stu.setPhone(rs.getString("phone"));

        }
        return stu;
    }
    //****************************
    // Delete student using id
    //***************************
    public static void deleteStuWithId(int id) throws SQLException, ClassNotFoundException {
        String stmt = "DELETE FROM Students WHERE id=" + id + ";";

        try {
            DBUtil.dbExecuteUpdate(stmt);
        } catch (SQLException e) {
            System.out.println("Problem with deletStuWithId: " + e);
            throw e;
        }
    }
    //*********************
    // Search all students
    //********************
    public static ObservableList<Student> searchStudents() throws SQLException, ClassNotFoundException {
        String stmt = "SELECT * FROM Students";
        try {
            ResultSet rsStu = DBUtil.dbExecuteQuery(stmt);
            ObservableList<Student> stuList = getStudentsList(rsStu);
            return stuList;
        } catch (SQLException e ) {
            System.out.println("Problem with searchStudents" + e);
            throw e;
        }
    }
    // Turn resultSet into observable list
    private static ObservableList<Student> getStudentsList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Student> stuList = FXCollections.observableArrayList();
        // Go through resultSet and create Students to add to arraylist
        while(rs.next()) {
            Student stu = new Student();
            stu.setId(rs.getInt("id"));
            stu.setFirstName(rs.getString("firstName"));
            stu.setLastName(rs.getString("lastName"));
            stu.setAddress(rs.getString("address"));
            stu.setPhone(rs.getString("phone"));
            stuList.add(stu);
        }
        return stuList;
    }

}
