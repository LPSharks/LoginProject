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
 * This is my Inpatient class that inherits from the Patient class.
 * I used the extends keyword to apply the concept of inheritance.
 * Inpatients require a hospital bed, so I added ward and bed fields.
 */
public class Inpatient extends Patient {
    // Additional fields specific to Inpatient
    private int wardNumber;
    private String bedNumber;

    /**
     * I used super() to initialise the inherited attributes from
     * the Patient class, as required by the assignment.
     * I set the category to INPATIENT automatically since this
     * class is specifically for inpatients.
     */
    public Inpatient(String patientID, String firstName, String lastName,
                     int age, String gender, String medicalCondition) {
        super(patientID, firstName, lastName, age, gender,
              medicalCondition, PatientCategory.INPATIENT);
        this.wardNumber = 0;         // 0 means not assigned yet
        this.bedNumber = "None";     // None means no bed allocated yet
    }

    // Getters for the additional fields
    public int getWardNumber() { return wardNumber; }
    public String getBedNumber() { return bedNumber; }

    /**
     * I created this method to set the ward and bed information
     * when a bed is allocated to this inpatient.
     */
    public void setBedInfo(int wardNumber, String bedNumber) {
        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }

    /**
     * I created this method to clear bed information when the
     * patient is discharged and the bed is released.
     */
    public void clearBedInfo() {
        this.wardNumber = 0;
        this.bedNumber = "None";
    }

    /**
     * I overrode the displayDetails() method to include
     * ward and bed information for inpatients, as required.
     * I called super.displayDetails() first to reuse the parent code.
     */
    @Override
    public void displayDetails() {
        super.displayDetails(); // Reuse the parent class display method
        System.out.println("Ward Number: " + wardNumber);
        System.out.println("Bed Number: \"" + bedNumber + "\"");
    }

    /**
     * I also overrode toString() to include bed info in list views.
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" Ward:%d Bed:%s", wardNumber, bedNumber);
    }
}