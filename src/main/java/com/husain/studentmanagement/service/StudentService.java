package com.husain.studentmanagement.service;

import com.husain.studentmanagement.dao.StudentDAO;
import com.husain.studentmanagement.model.Student;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class StudentService {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private final StudentDAO studentDAO = new StudentDAO();

    public void addStudent(String name, String email, String course, BigDecimal marks) throws SQLException {
        validate(name, email, course, marks);
        studentDAO.addStudent(new Student(name.trim(), email.trim(), course.trim(), marks));
    }

    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }

    public Optional<Student> getStudentById(int id) throws SQLException {
        requirePositiveId(id);
        return studentDAO.getStudentById(id);
    }

    public List<Student> searchByName(String name) throws SQLException {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be empty.");
        return studentDAO.searchByName(name.trim());
    }

    public boolean updateStudent(int id, String name, String email, String course, BigDecimal marks)
            throws SQLException {
        requirePositiveId(id);
        validate(name, email, course, marks);
        return studentDAO.updateStudent(new Student(id, name.trim(), email.trim(), course.trim(), marks));
    }

    public boolean deleteStudent(int id) throws SQLException {
        requirePositiveId(id);
        return studentDAO.deleteStudent(id);
    }

    public static String calculateGrade(BigDecimal marks) {
        if (marks == null || marks.compareTo(BigDecimal.ZERO) < 0
                || marks.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Marks must be between 0 and 100.");
        }
        if (marks.compareTo(new BigDecimal("90")) >= 0) return "A+";
        if (marks.compareTo(new BigDecimal("80")) >= 0) return "A";
        if (marks.compareTo(new BigDecimal("70")) >= 0) return "B";
        if (marks.compareTo(new BigDecimal("60")) >= 0) return "C";
        if (marks.compareTo(new BigDecimal("40")) >= 0) return "D";
        return "F";
    }

    private void validate(String name, String email, String course, BigDecimal marks) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be empty.");
        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches())
            throw new IllegalArgumentException("Enter a valid email address.");
        if (course == null || course.isBlank()) throw new IllegalArgumentException("Course cannot be empty.");
        if (marks == null || marks.compareTo(BigDecimal.ZERO) < 0
                || marks.compareTo(new BigDecimal("100")) > 0)
            throw new IllegalArgumentException("Marks must be between 0 and 100.");
    }

    private void requirePositiveId(int id) {
        if (id <= 0) throw new IllegalArgumentException("Student ID must be a positive number.");
    }
}
