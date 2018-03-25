package com.company.lesson2;

import java.util.List;

public class Company {
    private String companyName;
    private List<Person> employees;

    public Company(String companyName, List<Person> employees) {
        this.companyName = companyName;
        this.employees = employees;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Person> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Person> employees) {
        this.employees = employees;
    }

    public int getNumberOfEmployee() {
        return employees.size();
    }

    public boolean isPersonPresentByFirstName(String firstName) {
        for (Person person : employees) {
            if (person.getFirstName().equals(firstName)){
                return true;
            }
        }
        return false;
    }

    public boolean isPersonPresentByLastName(String lastName) {
        for (Person person : employees) {
            if (person.getLastName().equals(lastName)){
                return true;
            }
        }
        return false;
    }
}
