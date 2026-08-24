/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.letagost10510187.hospitalsystemmaven;

/**
 *
 * @author prais
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This is my main HospitalSystem class that manages all patients and beds.
 * I used an ArrayList to store patients because it is dynamic and easy to manage.
 * I used a two-dimensional array (4x5) for the beds to match the ward layout.
 */
public class HospitalSystem {
    // I used an ArrayList to store all registered patients
    private ArrayList<Patient> patients;

    // I used a 2D array for the 20 beds arranged in a 4x5 layout
    private Bed[][] wardBeds;

    // Constructor - initialises the patient list and the bed array
    public HospitalSystem() {
        patients = new ArrayList<>();
        wardBeds = new Bed[4][5]; // 4 rows, 5 columns = 20 beds

        int bedCounter = 1;
        // I used nested loops to initialise all 20 beds (B01 to B20)
        for (int row = 0; row < wardBeds.length; row++) {
            for (int col = 0; col < wardBeds[row].length; col++) {
                String bedNum = String.format("B%02d", bedCounter++);
                wardBeds[row][col] = new Bed(bedNum);
            }
        }
    }

    // ==================== PATIENT MANAGEMENT ====================

    /**
     * I created this method to register a new patient.
     * It checks for duplicate Patient IDs and throws an exception
     * if the ID already exists, demonstrating exception handling.
     */
    public void registerPatient(Patient patient) throws Exception {
        // Check for duplicate Patient ID
        if (searchPatient(patient.getPatientID()) != null) {
            throw new Exception("Error: Patient ID \"" + patient.getPatientID() + "\" already exists.");
        }
        patients.add(patient);
    }

    /**
     * I created this method to search for a patient by their ID.
     * It returns the Patient object if found, or null if not found.
     */
    public Patient searchPatient(String patientID) {
        for (Patient p : patients) {
            if (p.getPatientID().equalsIgnoreCase(patientID)) {
                return p;
            }
        }
        return null;
    }

    /**
     * I created this method to update an existing patient's details.
     * It searches for the patient first, then updates the fields.
     */
    public boolean updatePatient(String patientID, String firstName,
                                  String lastName, int age, String gender,
                                  String medicalCondition) {
        Patient p = searchPatient(patientID);
        if (p != null) {
            p.setFirstName(firstName);
            p.setLastName(lastName);
            p.setAge(age);
            p.setGender(gender);
            p.setMedicalCondition(medicalCondition);
            return true;
        }
        return false;
    }

    /**
     * I created this method to delete a patient from the system.
     * If the patient is an inpatient, their bed is released first.
     */
    public boolean deletePatient(String patientID) {
        Patient p = searchPatient(patientID);
        if (p != null) {
            // If inpatient, release their bed first
            if (p instanceof Inpatient) {
                Inpatient ip = (Inpatient) p;
                if (!ip.getBedNumber().equals("None")) {
                    releaseBed(ip.getBedNumber());
                }
            }
            patients.remove(p);
            return true;
        }
        return false;
    }

    /**
     * I created this method to display all registered patients.
     * It loops through the ArrayList and prints each patient.
     */
    public void displayAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        System.out.println("\n========== ALL REGISTERED PATIENTS ==========");
        System.out.printf("%-10s %-12s %-12s %-4s %-10s %-20s %-12s\n",
                "ID", "First Name", "Last Name", "Age", "Gender", "Condition", "Category");
        System.out.println("--------------------------------------------------------------------------------");
        for (Patient p : patients) {
            System.out.println(p.toString());
        }
        System.out.println("==============================================\n");
    }

    // ==================== BED MANAGEMENT ====================

    /**
     * I created this method to allocate an available bed to an inpatient.
     * It finds the first available bed and assigns it.
     * It throws an exception if no beds are available.
     */
    public String allocateBed(String patientID) throws Exception {
        Patient p = searchPatient(patientID);
        if (p == null) {
            throw new Exception("Error: Patient not found.");
        }
        if (!(p instanceof Inpatient)) {
            throw new Exception("Error: Only Inpatients can be allocated a bed.");
        }

        // Find first available bed using nested loops
        for (int row = 0; row < wardBeds.length; row++) {
            for (int col = 0; col < wardBeds[row].length; col++) {
                if (!wardBeds[row][col].isOccupied()) {
                    wardBeds[row][col].setOccupied(true);
                    wardBeds[row][col].setPatientID(patientID);
                    ((Inpatient) p).setBedInfo(1, wardBeds[row][col].getBedNumber());
                    return wardBeds[row][col].getBedNumber();
                }
            }
        }
        throw new Exception("Error: No beds available. All 20 beds are occupied.");
    }

    /**
     * I created this overloaded method to allocate a specific bed.
     * This is useful for testing and prevents allocating an occupied bed.
     */
    public void allocateSpecificBed(String patientID, String bedNumber) throws Exception {
        Patient p = searchPatient(patientID);
        if (p == null) {
            throw new Exception("Error: Patient not found.");
        }
        if (!(p instanceof Inpatient)) {
            throw new Exception("Error: Only Inpatients can be allocated a bed.");
        }

        // Search for the specific bed
        for (int row = 0; row < wardBeds.length; row++) {
            for (int col = 0; col < wardBeds[row].length; col++) {
                if (wardBeds[row][col].getBedNumber().equalsIgnoreCase(bedNumber)) {
                    if (wardBeds[row][col].isOccupied()) {
                        throw new Exception("Error: Bed " + bedNumber + " is already occupied.");
                    }
                    wardBeds[row][col].setOccupied(true);
                    wardBeds[row][col].setPatientID(patientID);
                    ((Inpatient) p).setBedInfo(1, bedNumber);
                    return;
                }
            }
        }
        throw new Exception("Error: Bed " + bedNumber + " not found.");
    }

    /**
     * I created this method to release a bed when a patient is discharged.
     * It finds the bed by number and marks it as available.
     */
    public boolean releaseBed(String bedNumber) {
        for (int row = 0; row < wardBeds.length; row++) {
            for (int col = 0; col < wardBeds[row].length; col++) {
                if (wardBeds[row][col].getBedNumber().equalsIgnoreCase(bedNumber)) {
                    if (wardBeds[row][col].isOccupied()) {
                        String pid = wardBeds[row][col].getPatientID();
                        Patient p = searchPatient(pid);
                        if (p instanceof Inpatient) {
                            ((Inpatient) p).clearBedInfo();
                        }
                        wardBeds[row][col].setOccupied(false);
                        wardBeds[row][col].setPatientID(null);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * I created this method to display the complete ward layout.
     * I used nested loops to print the 4x5 grid of beds.
     */
    public void displayWardLayout() {
        System.out.println("\n========== WARD LAYOUT (4 x 5) ==========");
        for (int row = 0; row < wardBeds.length; row++) {
            for (int col = 0; col < wardBeds[row].length; col++) {
                Bed b = wardBeds[row][col];
                String status = b.isOccupied() ? "[X]" : "[ ]";
                System.out.print(b.getBedNumber() + status + "  ");
            }
            System.out.println(); // New line after each row
        }
        System.out.println("=========================================\n");
    }

    /**
     * I created this method to display all available beds.
     */
    public void displayAvailableBeds() {
        System.out.println("\n========== AVAILABLE BEDS ==========");
        boolean found = false;
        for (int row = 0; row < wardBeds.length; row++) {
            for (int col = 0; col < wardBeds[row].length; col++) {
                if (!wardBeds[row][col].isOccupied()) {
                    System.out.print(wardBeds[row][col].getBedNumber() + " ");
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.print("None");
        }
        System.out.println("\n====================================\n");
    }

    /**
     * I created this method to display all occupied beds.
     */
    public void displayOccupiedBeds() {
        System.out.println("\n========== OCCUPIED BEDS ==========");
        boolean found = false;
        for (int row = 0; row < wardBeds.length; row++) {
            for (int col = 0; col < wardBeds[row].length; col++) {
                if (wardBeds[row][col].isOccupied()) {
                    System.out.println(wardBeds[row][col].getBedNumber() +
                            " -> Patient: " + wardBeds[row][col].getPatientID());
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No beds are currently occupied.");
        }
        System.out.println("====================================\n");
    }

    // ==================== REPORTS ====================

    /**
     * I created this method to return the total number of registered patients.
     * I used the size() method of the ArrayList.
     */
    public int getTotalPatients() {
        return patients.size();
    }

    /**
     * I created this method to count the total number of occupied beds.
     * I used nested loops to iterate through the 2D bed array.
     */
    public int getTotalOccupiedBeds() {
        int count = 0;
        for (int row = 0; row < wardBeds.length; row++) {
            for (int col = 0; col < wardBeds[row].length; col++) {
                if (wardBeds[row][col].isOccupied()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * I created this method to calculate the ward occupancy percentage.
     * There are exactly 20 beds, so I divide occupied by 20 and multiply by 100.
     */
    public double getOccupancyPercentage() {
        return (getTotalOccupiedBeds() / 20.0) * 100.0;
    }

    /**
     * I created this method to generate a basic ward report.
     * It displays all the statistics required by the assignment.
     */
    public void generateReport() {
        System.out.println("\n========== WARD REPORT ==========");
        System.out.println("Total Registered Patients: " + getTotalPatients());
        System.out.println("Total Occupied Beds: " + getTotalOccupiedBeds());
        System.out.println("Total Available Beds: " + (20 - getTotalOccupiedBeds()));
        System.out.printf("Ward Occupancy Percentage: %.2f%%\n", getOccupancyPercentage());
        System.out.println("=================================\n");
    }

    // ==================== SORTING ====================

    /**
     * I created this method to sort patients by surname (last name).
     * I used the Collections.sort() method with a custom Comparator.
     */
    public void sortPatientsBySurname() {
        Collections.sort(patients, new Comparator<Patient>() {
            @Override
            public int compare(Patient p1, Patient p2) {
                return p1.getLastName().compareToIgnoreCase(p2.getLastName());
            }
        });
    }

    /**
     * I created this method to sort patients by Patient ID.
     * I used the Collections.sort() method with a custom Comparator.
     */
    public void sortPatientsByID() {
        Collections.sort(patients, new Comparator<Patient>() {
            @Override
            public int compare(Patient p1, Patient p2) {
                return p1.getPatientID().compareToIgnoreCase(p2.getPatientID());
            }
        });
    }

    // ==================== ARRAY METHODS (DEMONSTRATION) ====================

    /**
     * I created this method to demonstrate passing an array to a method
     * and using the length field, as required by the assignment.
     * It receives a 2D Bed array and prints its dimensions.
     */
    public void printArrayDimensions(Bed[][] beds) {
        System.out.println("Array rows: " + beds.length); // Using length field
        if (beds.length > 0) {
            System.out.println("Array columns: " + beds[0].length); // Using length field
        }
    }

    // ==================== GETTERS ====================

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public Bed[][] getWardBeds() {
        return wardBeds;
    }
}