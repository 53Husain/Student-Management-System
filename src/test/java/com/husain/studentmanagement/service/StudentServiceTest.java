package com.husain.studentmanagement.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentServiceTest {
    @Test
    void calculatesAPlusForNinetyOrAbove() {
        assertEquals("A+", StudentService.calculateGrade(new BigDecimal("90")));
        assertEquals("A+", StudentService.calculateGrade(new BigDecimal("100")));
    }

    @Test
    void calculatesAForEightyToBelowNinety() {
        assertEquals("A", StudentService.calculateGrade(new BigDecimal("80")));
        assertEquals("A", StudentService.calculateGrade(new BigDecimal("89.99")));
    }

    @Test
    void calculatesBForSeventyToBelowEighty() {
        assertEquals("B", StudentService.calculateGrade(new BigDecimal("70")));
    }

    @Test
    void calculatesCForSixtyToBelowSeventy() {
        assertEquals("C", StudentService.calculateGrade(new BigDecimal("60")));
    }

    @Test
    void calculatesDForFortyToBelowSixty() {
        assertEquals("D", StudentService.calculateGrade(new BigDecimal("40")));
    }

    @Test
    void calculatesFBelowForty() {
        assertEquals("F", StudentService.calculateGrade(new BigDecimal("39.99")));
        assertEquals("F", StudentService.calculateGrade(BigDecimal.ZERO));
    }

    @Test
    void rejectsMarksOutsideValidRange() {
        assertThrows(IllegalArgumentException.class,
                () -> StudentService.calculateGrade(new BigDecimal("-1")));
        assertThrows(IllegalArgumentException.class,
                () -> StudentService.calculateGrade(new BigDecimal("100.01")));
    }

    @Test
    void rejectsNullMarks() {
        assertThrows(IllegalArgumentException.class, () -> StudentService.calculateGrade(null));
    }
}
