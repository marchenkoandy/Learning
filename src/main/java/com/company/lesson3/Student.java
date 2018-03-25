package com.company.lesson3;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class Student extends Person {
    private Long id;
    private String faculty;
    private String course;
    private String group;

    public Student(String firstName, String middleName, String lastName, Long birthDay, String address, String phoneNumber, String faculty, String course, String group) {
        super(firstName, middleName, lastName, birthDay, address, phoneNumber);
        this.id = Long.valueOf(RandomStringUtils.randomNumeric(5));
        this.faculty = faculty;
        this.course = course;
        this.group = group;
    }

    public static Student generateStudent() {
        String firstName = RandomStringUtils.randomAlphabetic(5, 10);
        String middleName = RandomStringUtils.randomAlphabetic(5, 10);
        String lastName = RandomStringUtils.randomAlphabetic(5, 10);
        Long birthDay = Long.valueOf(RandomStringUtils.randomNumeric(15));
        String address = RandomStringUtils.randomAlphabetic(5, 10);
        String phoneNumber = RandomStringUtils.randomNumeric(10);
        String faculty = RandomStringUtils.randomAlphabetic(5, 10);
        String course = RandomStringUtils.randomAlphabetic(5, 10);
        String group = RandomStringUtils.randomAlphabetic(5, 10);
        return new Student(firstName, middleName, lastName, birthDay, address, phoneNumber, faculty, course, group);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", faculty='" + faculty + '\'' +
                ", course='" + course + '\'' +
                ", group='" + group + '\'' +
                "} " + super.toString();
    }
}
