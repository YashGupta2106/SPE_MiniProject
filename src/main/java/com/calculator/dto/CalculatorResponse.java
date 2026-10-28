package com.calculator.dto;

public class CalculatorResponse {
    private String operation;
    private double a;
    private double b;
    private double result;

    public CalculatorResponse(String operation, double a, double b, double result) {
        this.operation = operation;
        this.a = a;
        this.b = b;
        this.result = result;
    }

    public String getOperation() { return operation; }
    public double getA() { return a; }
    public double getB() { return b; }
    public double getResult() { return result; }
}