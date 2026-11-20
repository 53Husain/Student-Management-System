package com.husain.studentmanagement;

import com.husain.studentmanagement.database.DBConnection;
import com.husain.studentmanagement.model.Student;
import com.husain.studentmanagement.service.StudentService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService service = new StudentService();

    public static void main(String[] args) {
        try (var connection = DBConnection.getConnection()) {
            System.out.println("Connected to Student Management database.");
        } catch (SQLException e) {
            System.err.println("Could not connect to MySQL: " + e.getMessage());
            System.err.println("Check MySQL Server and DB_URL, DB_USER and DB_PASSWORD environment variables.");
            return;
        }

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> addStudent();
                    case "2" -> showStudents(service.getAllStudents());
                    case "3" -> findStudent();
                    case "4" -> searchStudents();
                    case "5" -> updateStudent();
                    case "6" -> deleteStudent();
                    case "0" -> running = false;
                    default -> System.out.println("Invalid option. Please choose again.");
                }
            } catch (SQLException e) {
                System.out.println("Database operation failed: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Input error: " + e.getMessage());
            }
        }
        System.out.println("Thank you for using Student Management System.");
    }

    private static void printMenu() {
        System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
        System.out.println("1. Add student");
        System.out.println("2. View all students");
        System.out.println("3. Search student by ID");
        System.out.println("4. Search students by name");
        System.out.println("5. Update student");
        System.out.println("6. Delete student");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addStudent() throws SQLException {
        System.out.println("\n--- Add Student ---");
        service.addStudent(readText("Name: "), readText("Email: "),
                readText("Course: "), readMarks());
        System.out.println("Student added successfully.");
    }

    private static void findStudent() throws SQLException {
        int id = readId();
        service.getStudentById(id).ifPresentOrElse(
                student -> printStudent(student),
                () -> System.out.println("Student not found."));
    }

    private static void searchStudents() throws SQLException {
        showStudents(service.searchByName(readText("Enter name to search: ")));
    }

    private static void updateStudent() throws SQLException {
        int id = readId();
        if (service.getStudentById(id).isEmpty()) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Enter the updated details:");
        boolean updated = service.updateStudent(id, readText("Name: "), readText("Email: "),
                readText("Course: "), readMarks());
        System.out.println(updated ? "Student updated successfully." : "No changes made.");
    }

    private static void deleteStudent() throws SQLException {
        boolean deleted = service.deleteStudent(readId());
        System.out.println(deleted ? "Student deleted successfully." : "Student not found.");
    }

    private static void showStudents(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        students.forEach(Main::printStudent);
    }

    private static void printStudent(Student student) {
        System.out.println(student + " | Grade: " + StudentService.calculateGrade(student.getMarks()));
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readId() {
        try {
            return Integer.parseInt(readText("Student ID: "));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Student ID must be a whole number.");
        }
    }

    private static BigDecimal readMarks() {
        try {
            return new BigDecimal(readText("Marks (0-100): "));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Marks must be a valid number.");
        }
    }
}
