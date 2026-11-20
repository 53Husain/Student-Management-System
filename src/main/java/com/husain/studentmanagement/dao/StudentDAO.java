package com.husain.studentmanagement.dao;

import com.husain.studentmanagement.database.DBConnection;
import com.husain.studentmanagement.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDAO {
    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (name, email, course, marks) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getCourse());
            statement.setBigDecimal(4, student.getMarks());
            statement.executeUpdate();
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        String sql = "SELECT id, name, email, course, marks FROM students ORDER BY id";
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) students.add(mapStudent(result));
        }
        return students;
    }

    public Optional<Student> getStudentById(int id) throws SQLException {
        String sql = "SELECT id, name, email, course, marks FROM students WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) return Optional.of(mapStudent(result));
            }
        }
        return Optional.empty();
    }

    public List<Student> searchByName(String name) throws SQLException {
        String sql = "SELECT id, name, email, course, marks FROM students WHERE name LIKE ? ORDER BY name";
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + name + "%");
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) students.add(mapStudent(result));
            }
        }
        return students;
    }

    public boolean updateStudent(Student student) throws SQLException {
        String sql = "UPDATE students SET name = ?, email = ?, course = ?, marks = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getCourse());
            statement.setBigDecimal(4, student.getMarks());
            statement.setInt(5, student.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    private Student mapStudent(ResultSet result) throws SQLException {
        return new Student(result.getInt("id"), result.getString("name"),
                result.getString("email"), result.getString("course"),
                result.getBigDecimal("marks"));
    }
}
