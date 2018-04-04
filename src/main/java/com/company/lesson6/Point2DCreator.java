package com.company.lesson6;

public class Point2DCreator extends Point1DCreator {
    @Override
    public Point2D createPoint() { // метод - подставка
        System.out.println("Point2D");
        return new Point2D(2, 5);
    }
}