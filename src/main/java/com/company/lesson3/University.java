package com.company.lesson3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class University {
    private String title;
    private List<Student> students;

    public University() {
    }

    public University(String title) {
        this.title = title;
    }

    public University(String title, List<Student> students) {
        this.title = title;
        this.students = students;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Student> generateListOfStudents(int number) {
        List<Student> lout = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            lout.add(Student.generateStudent());
        }
        return lout;
    }

    public List<Student> studentsByFaculty(String faculty) {
        return students.stream().filter(s -> s.getFaculty().equals(faculty)).collect(Collectors.toList());
    }

    public List<Student> studentsByCourse(String course) {
        return students.stream().filter(s -> s.getCourse().equals(course)).collect(Collectors.toList());
    }

    public List<Student> studentsByBirthDay(Long birthDayDate) {
        return students.stream().filter(s -> s.getBirthDay() >= birthDayDate).collect(Collectors.toList());
    }

    public List<Student> studentsByGroup(String group) {
        return students.stream().filter(s -> s.getGroup().equals(group)).collect(Collectors.toList());
    }

    public void printUniversity(List<Student> students, String message) {
        System.out.println(String.format("=== University name: '%s' filtered by: '%s' ===", title, message));
        students.forEach(student -> System.out.println(student.toString()));
        System.out.println("==============================================================");
    }
}
