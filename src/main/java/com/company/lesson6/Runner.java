package com.company.lesson6;

public class Runner {
    public static void main1(String[] args) {
        PointReport d = new PointReport();
        Point1D p1 = new Point1D(-7);
        d.printReport(p1);
        Point2D p2 = new Point2D(3, 4);
        d.printReport(p2);
        Point3D p3 = new Point3D(3, 4, 5);
        d.printReport(p3);
    }

    public static void main2(String[] args) {
        Point2DCreator br = new Point3DCreator();
//        Point3D p = br.createPoint(); // ошибка компиляции
        Point2D p = br.createPoint(); // "раннее связывание"
        System.out.println(br.createPoint().getX());
        System.out.println(br.createPoint().getY());
//        System.out.println(br.createPoint().getZ());
    }
}
