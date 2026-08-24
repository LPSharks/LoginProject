/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.letagost10510187.hospitalsystemmaven;
import java.util.Scanner;
/**
 *
 * @author prais
 */
/**
 * This is my Main class that runs the console-based, menu-driven application.
 * I used a while loop to keep showing the menu until the user chooses to exit.
 * I used a switch statement to handle the different menu options.
 * I also used try-catch blocks for exception handling throughout.
 */ 
public class HospitalSystemMaven {


    // I used a static Scanner so I can use it in all methods
    private static Scanner scanner = new Scanner(System.in);
    private static HospitalSystem system = new HospitalSystem();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  MEDI CARE HOSPITAL ADMISSION SYSTEM");
        System.out.println("========================================");
        System.out.println("Developed by: Letago Praise Kgwerano st10510187");
        System.out.println("Date: 08/19/2026");
        System.out.println("========================================\n");

        boolean running = true;
        while (running) {
            displayMenu();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        registerPatient();
                        break;
                    case "2":
                        searchPatient();
                        break;
                    case "3":
                        updatePatient();
                        break;
                    case "4":
                        deletePatient();
                        break;
                    case "5":
                        system.displayAllPatients();
                        break;
                    case "6":
                        allocateBed();
                        break;
                    case "7":
                        releaseBed();
                        break;
                    case "8":
                        system.displayWardLayout();
                        break;
                    case "9":
                        system.displayAvailableBeds();
                        break;
                    case "10":
                        system.displayOccupiedBeds();
                        break;
                    case "11":
                        system.generateReport();
                        break;
                    case "12":
                        system.sortPatientsBySurname();
                        System.out.println("Patients sorted by surname.");
                        system.displayAllPatients();
                        break;
                    case "13":
                        system.sortPatientsByID();
                        System.out.println("Patients sorted by Patient ID.");
                        system.displayAllPatients();
                        break;
                    case "14":
                        // Demonstrate passing array to method and using length field
                        System.out.println("\n--- Array Dimension Check ---");
                        system.printArrayDimensions(system.getWardBeds());
                        System.out.println("-----------------------------\n");
                        break;
                    case "0":
                        System.out.println("Thank you for using MediCare Hospital System. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                // I used exception handling to catch and display errors nicely
                System.out.println("\n*** ERROR: " + e.getMessage() + " ***\n");
            }
        }
        scanner.close();
    }

    /**
     * I created this method to display the main menu options.
     */
    private static void displayMenu() {
        System.out.println("========== MAIN MENU ==========");
        System.out.println("1.  Register a new patient");
        System.out.println("2.  Search for a patient");
        System.out.println("3.  Update patient details");
        System.out.println("4.  Delete a patient");
        System.out.println("5.  Display all registered patients");
        System.out.println("6.  Allocate a bed to an inpatient");
        System.out.println("7.  Release a bed");
        System.out.println("8.  Display ward layout");
        System.out.println("9.  Display available beds");
        System.out.println("10. Display occupied beds");
        System.out.println("11. Generate ward report");
        System.out.println("12. Sort patients by surname");
        System.out.println("13. Sort patients by Patient ID");
        System.out.println("14. Check array dimensions");
        System.out.println("0.  Exit");
        System.out.println("===============================");
    }

    /**
     * I created this method to handle patient registration.
     * It asks the user for all required details and creates the appropriate
     * patient type based on the category selected.
     */
    private static void registerPatient() throws Exception {
        System.out.println("\n--- Register New Patient ---");

        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter Age: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter Medical Condition: ");
        String condition = scanner.nextLine();

        // I used the enum values to display options
        System.out.println("Select Patient Category:");
        PatientCategory[] categories = PatientCategory.values(); // Using enum method
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        System.out.print("Choice: ");
        int catChoice = Integer.parseInt(scanner.nextLine());
        PatientCategory category = categories[catChoice - 1];

        Patient patient;
        // I check if the patient is an inpatient to create the correct object type
        if (category == PatientCategory.INPATIENT) {
            patient = new Inpatient(id, firstName, lastName, age, gender, condition);
        } else {
            patient = new Patient(id, firstName, lastName, age, gender, condition, category);
        }

        system.registerPatient(patient);
        System.out.println("Patient registered successfully!\n");
    }

    /**
     * I created this method to search for a patient by ID.
     */
    private static void searchPatient() {
        System.out.print("\nEnter Patient ID to search: ");
        String id = scanner.nextLine();
        Patient p = system.searchPatient(id);
        if (p != null) {
            System.out.println("\n--- Patient Found ---");
            p.displayDetails();
            System.out.println("---------------------\n");
        } else {
            System.out.println("Patient not found.\n");
        }
    }

    /**
     * I created this method to update an existing patient's details.
     */
    private static void updatePatient() {
        System.out.print("\nEnter Patient ID to update: ");
        String id = scanner.nextLine();
        Patient p = system.searchPatient(id);
        if (p == null) {
            System.out.println("Patient not found.\n");
            return;
        }

        System.out.println("Enter new details (press Enter to keep current value):");

        System.out.print("First Name [" + p.getFirstName() + "]: ");
        String firstName = scanner.nextLine();
        if (firstName.isEmpty()) firstName = p.getFirstName();

        System.out.print("Last Name [" + p.getLastName() + "]: ");
        String lastName = scanner.nextLine();
        if (lastName.isEmpty()) lastName = p.getLastName();

        System.out.print("Age [" + p.getAge() + "]: ");
        String ageStr = scanner.nextLine();
        int age = ageStr.isEmpty() ? p.getAge() : Integer.parseInt(ageStr);

        System.out.print("Gender [" + p.getGender() + "]: ");
        String gender = scanner.nextLine();
        if (gender.isEmpty()) gender = p.getGender();

        System.out.print("Medical Condition [" + p.getMedicalCondition() + "]: ");
        String condition = scanner.nextLine();
        if (condition.isEmpty()) condition = p.getMedicalCondition();

        if (system.updatePatient(id, firstName, lastName, age, gender, condition)) {
            System.out.println("Patient updated successfully!\n");
        } else {
            System.out.println("Update failed.\n");
        }
    }

    /**
     * I created this method to delete a patient from the system.
     */
    private static void deletePatient() {
        System.out.print("\nEnter Patient ID to delete: ");
        String id = scanner.nextLine();
        if (system.deletePatient(id)) {
            System.out.println("Patient deleted successfully!\n");
        } else {
            System.out.println("Patient not found.\n");
        }
    }

    /**
     * I created this method to allocate a bed to an inpatient.
     */
    private static void allocateBed() throws Exception {
        System.out.print("\nEnter Patient ID to allocate bed: ");
        String id = scanner.nextLine();
        String bedNum = system.allocateBed(id);
        System.out.println("Bed " + bedNum + " allocated successfully!\n");
    }

    /**
     * I created this method to release a bed.
     */
    private static void releaseBed() {
        System.out.print("\nEnter Bed Number to release (e.g., B01): ");
        String bedNum = scanner.nextLine();
        if (system.releaseBed(bedNum)) {
            System.out.println("Bed " + bedNum + " released successfully!\n");
        } else {
            System.out.println("Bed not found or already available.\n");
        }
    }
} 

