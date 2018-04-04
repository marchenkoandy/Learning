package com.company.lesson6;

public class Point1D {
    private int x;

    public int getX() {
        return x;
    }

    public Point1D(int x) {
        this.x = x;
    }
    public double length() {
        return Math.abs(x);
    }
    @Override
    public String toString() {
        return " x=" + x;
    }
}
