package com.wiftwift.task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class CosCalculatorTest {
    
    private CosCalculator cosCalculator;
    private static final double DELTA = 1e-10;
    
    @BeforeEach
    void setUp() {
        cosCalculator = new CosCalculator();
    }
    
    @ParameterizedTest(name = "cos({0}) должен быть близок к Math.cos({0})")
    @ValueSource(doubles = {0.0, Math.PI/6, Math.PI/4, Math.PI/3, Math.PI/2, Math.PI, 
                           3*Math.PI/2, 2*Math.PI, -Math.PI/4, -Math.PI})
    void testCosAtSpecialAngles(double angle) {
        assertEquals(Math.cos(angle), cosCalculator.calculateCos(angle, 15), DELTA);
    }
    
    @ParameterizedTest(name = "cos({0}) с {1} членами ряда должен иметь погрешность не более {2}")
    @CsvSource({
        "1.5, 5, 1e-4",
        "1.5, 10, 1e-9",
        "2.5, 15, 1e-14"
    })
    void testCosAccuracyWithDifferentTerms(double angle, int terms, double accuracy) {
        double expected = Math.cos(angle);
        double actual = cosCalculator.calculateCos(angle, terms);
        assertEquals(expected, actual, accuracy);
    }
    
    @Test
    void testCosForLargeInputs() {
        double angle = 100 * Math.PI;
        assertEquals(Math.cos(angle), cosCalculator.calculateCos(angle, 20), DELTA);
    }
    
    @Test
    void testCosForNegativeInputs() {
        double angle = -5.5;
        assertEquals(Math.cos(angle), cosCalculator.calculateCos(angle, 15), DELTA);
    }
    
    @Test
    void testInvalidTermsCount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cosCalculator.calculateCos(1.0, 0);
        });
        
        String expectedMessage = "Количество членов ряда должно быть положительным";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
