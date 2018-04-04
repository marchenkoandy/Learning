package com.company.lesson6;

public class Point3DCreator extends Point2DCreator {
    @Override
    public Point3D createPoint() { // метод - подставка
        System.out.println("Point3D");
        return new Point3D(3, 7, 8);
    }
}
