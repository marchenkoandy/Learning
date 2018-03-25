package com.company;

import com.company.lesson3.University;

public class Main {

    public static void main(String[] args) {
        University university = new University("My university");
        university.setStudents(university.generateListOfStudents(20));
        university.printUniversity();
    }
}
