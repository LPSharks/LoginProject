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
 * I created this Bed class to represent each individual hospital bed.
 * Each bed has a unique number and tracks whether it is occupied.
 * I also store the patient ID so I know which patient is in the bed.
 */
public class Bed {
    private String bedNumber;
    private boolean occupied;
    private String patientID; // null if bed is available

    // Constructor
    public Bed(String bedNumber) {
        this.bedNumber = bedNumber;
        this.occupied = false;
        this.patientID = null;
    }

    // Getters
    public String getBedNumber() { return bedNumber; }
    public boolean isOccupied() { return occupied; }
    public String getPatientID() { return patientID; }

    // Setters
    public void setOccupied(boolean occupied) { this.occupied = occupied; }
    public void setPatientID(String patientID) { this.patientID = patientID; }

    @Override
    public String toString() {
        return bedNumber + (occupied ? " [OCCUPIED]" : " [AVAILABLE]");
    }
}