package com.wiftwift.task1;

public class CosCalculator {
    public double calculateCos(double x, int terms) {
        if (terms <= 0) {
            throw new IllegalArgumentException("Количество членов ряда должно быть положительным");
        }
        
        x = x % (2 * Math.PI);
        if (x < 0) {
            x += 2 * Math.PI;
        }
        
        double result = 0;
        double term = 1.0;
        int sign = 1;
        
        for (int i = 0; i < terms; i++) {
            result += sign * term;
            
            term = term * x * x / ((2.0 * i + 1) * (2.0 * i + 2));
            sign = -sign;
        }
        
        return result;
    }
}
