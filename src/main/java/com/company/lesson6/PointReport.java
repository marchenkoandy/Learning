package com.company.lesson6;

public class PointReport {
    public void printReport(Point1D p) {
        System.out.printf("length=%.2f %s%n", p.length(), p);
// вызовы out.print(p.toString()) и out.print(p) для объекта идентичны !
    }
}
