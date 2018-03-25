package com.company;

import com.company.lesson3.Student;
import com.company.lesson3.University;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        University university = new University("My university");
        university.setStudents(Arrays.asList(
                new Student("Andy", "", "Mar", 150000L, "Vinnitsa", "5555-94", "Not builder", "1", "M"),
                new Student("Fandy", "", "Var", 190000L, "Kyiv", "55-94444", "Builder", "1", "N"),
                new Student("Gandy", "", "Dar", 150000L, "Vinnitsa", "5555-94444", "Not builder", "2", "N"),
                new Student("Dandy", "", "Gar", 150000L, "Vinnitsa", "5555-94", "Almost builder", "2", "M"),
                new Student("Bandy", "", "Mar", 150000L, "Kyiv", "555-94444", "Builder", "1", "M"),
                new Student("Sandy", "", "Sar", 160000L, "Kyiv", "5555-94444", "Not builder", "1", "N"),
                new Student("Landy", "", "Bar", 170000L, "Vinnitsa", "5555-94", "Builder", "3", "M"),
                new Student("Wandy", "", "Far", 150000L, "Kyiv", "555-94444", "Builder", "4", "M"),
                new Student("Tandy", "", "Tar", 150000L, "Vinnitsa", "5555-9444", "Builder", "5", "N"),
                new Student("Qandy", "", "Jar", 180000L, "Vinnitsa", "55-944", "Almost builder", "1", "M"),
                new Student("Pandy", "", "Lar", 150000L, "Kyiv", "5-9444", "Not builder", "1", "N")
        ));
        university.printUniversity(university.getStudents(),"All students:");
        university.printUniversity(university.studentsByBirthDay(160000L),"Birthday:");
        university.printUniversity(university.studentsByCourse("2"),"Course:");
        university.printUniversity(university.studentsByFaculty("Almost builder"),"Faculty:");
        university.printUniversity(university.studentsByGroup("N"),"Group:");
    }
}
