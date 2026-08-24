/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.letagost10510187.hospitalsystemmaven;

/**
 *
 * @author prais
 */
/**
 * This is my base Patient class. I applied the concept of information hiding
 * by making all fields private and providing public getters and setters.
 * This protects the data from being modified directly from outside the class.
 */
public class Patient {
    // I made all fields private to apply information hiding
    private String patientID;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String medicalCondition;
    private PatientCategory category;

    // Constructor to initialise all patient attributes
    public Patient(String patientID, String firstName, String lastName,
                   int age, String gender, String medicalCondition,
                   PatientCategory category) {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.category = category;
    }

    // Getters - I created these to allow controlled access to private fields
    public String getPatientID() { return patientID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getMedicalCondition() { return medicalCondition; }
    public PatientCategory getCategory() { return category; }

    // Setters - I created these to allow updating patient details
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setMedicalCondition(String medicalCondition) { this.medicalCondition = medicalCondition; }
    public void setCategory(PatientCategory category) { this.category = category; }

    /**
     * I created this method to display all patient details.
     * It will be overridden by the Inpatient subclass to include
     * additional ward and bed information.
     */
    public void displayDetails() {
        System.out.println("Patient ID: \"" + patientID + "\"");
        System.out.println("First Name: \"" + firstName + "\"");
        System.out.println("Last Name: \"" + lastName + "\"");
        System.out.println("Age: " + age);
        System.out.println("Gender: \"" + gender + "\"");
        System.out.println("Medical Condition: \"" + medicalCondition + "\"");
        System.out.println("Category: " + category);
    }

    /**
     * I overrode the toString() method to make it easier to display
     * patient information in lists and reports.
     */
    @Override
    public String toString() {
        return String.format("%-10s %-12s %-12s %-4d %-10s %-20s %-12s",
                patientID, firstName, lastName, age, gender, medicalCondition, category);
    }
}