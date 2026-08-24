/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.letagost10510187.hospitalsystemmaven;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author prais
 */


/**
 * This is my JUnit test class to verify the functionality of the HospitalSystem.
 * I created tests for all the required features as specified in the assignment.
 * I used JUnit 4 annotations (@Test, @Before) for the test methods.
 */
public class HospitalSystemTest {

    private HospitalSystem hospitalSystem;

    /**
     * I used the @Before annotation to set up a fresh HospitalSystem
     * before each test, so tests don't interfere with each other.
     */
    @Before
    public void setUp() {
        hospitalSystem = new HospitalSystem();
    }

    /**
     * Test 1: Register a patient.
     * I verify that a patient is successfully added to the system.
     */
    @Test
    public void testRegisterPatient() throws Exception {
        Patient p = new Patient("P001", "John", "Doe", 30, "Male", "Flu", PatientCategory.OUTPATIENT);
        hospitalSystem.registerPatient(p);
        assertEquals(1, hospitalSystem.getTotalPatients());
    }

    /**
     * Test 2: Search for a patient.
     * I verify that searching by Patient ID returns the correct patient.
     */
    @Test
    public void testSearchPatient() throws Exception {
        Patient p = new Patient("P002", "Jane", "Smith", 25, "Female", "Cold", PatientCategory.EMERGENCY);
        hospitalSystem.registerPatient(p);
        Patient found = hospitalSystem.searchPatient("P002");
        assertNotNull(found);
        assertEquals("Jane", found.getFirstName());
    }

    /**
     * Test 3: Update patient details.
     * I verify that updating a patient's details works correctly.
     */
    @Test
    public void testUpdatePatient() throws Exception {
        Patient p = new Patient("P003", "Mike", "Brown", 40, "Male", "Asthma", PatientCategory.INPATIENT);
        hospitalSystem.registerPatient(p);
        boolean updated = hospitalSystem.updatePatient("P003", "Michael", "Brown", 41, "Male", "Asthma");
        assertTrue(updated);
        Patient updatedPatient = hospitalSystem.searchPatient("P003");
        assertEquals("Michael", updatedPatient.getFirstName());
        assertEquals(41, updatedPatient.getAge());
    }

    /**
     * Test 4: Delete a patient.
     * I verify that deleting a patient removes them from the system.
     */
    @Test
    public void testDeletePatient() throws Exception {
        Patient p = new Patient("P004", "Sarah", "Wilson", 35, "Female", "Fever", PatientCategory.OUTPATIENT);
        hospitalSystem.registerPatient(p);
        assertTrue(hospitalSystem.deletePatient("P004"));
        assertNull(hospitalSystem.searchPatient("P004"));
        assertEquals(0, hospitalSystem.getTotalPatients());
    }

    /**
     * Test 5: Allocate a bed.
     * I verify that a bed can be allocated to an inpatient.
     */
    @Test
    public void testAllocateBed() throws Exception {
        Inpatient ip = new Inpatient("P005", "Tom", "Clark", 50, "Male", "Pneumonia");
        hospitalSystem.registerPatient(ip);
        String bedNum = hospitalSystem.allocateBed("P005");
        assertNotNull(bedNum);
        assertTrue(bedNum.startsWith("B"));
        assertEquals(1, hospitalSystem.getTotalOccupiedBeds());
    }

    /**
     * Test 6: Release a bed.
     * I verify that releasing a bed makes it available again.
     */
    @Test
    public void testReleaseBed() throws Exception {
        Inpatient ip = new Inpatient("P006", "Amy", "Davis", 28, "Female", "Fracture");
        hospitalSystem.registerPatient(ip);
        String bedNum = hospitalSystem.allocateBed("P006");
        assertEquals(1, hospitalSystem.getTotalOccupiedBeds());
        assertTrue(hospitalSystem.releaseBed(bedNum));
        assertEquals(0, hospitalSystem.getTotalOccupiedBeds());
    }

    /**
     * Test 7: Prevent duplicate Patient IDs.
     * I verify that registering a patient with an existing ID throws an exception.
     */
    @Test(expected = Exception.class)
    public void testPreventDuplicatePatientID() throws Exception {
        Patient p1 = new Patient("P007", "Chris", "Evans", 45, "Male", "Diabetes", PatientCategory.OUTPATIENT);
        Patient p2 = new Patient("P007", "Lisa", "Ray", 32, "Female", "Allergy", PatientCategory.EMERGENCY);
        hospitalSystem.registerPatient(p1);
        hospitalSystem.registerPatient(p2); // Should throw exception
    }

    /**
     * Test 8: Prevent allocating an occupied bed.
     * I verify that trying to allocate an already occupied bed throws an exception.
     */
    @Test(expected = Exception.class)
    public void testPreventAllocateOccupiedBed() throws Exception {
        Inpatient ip1 = new Inpatient("P008", "Mark", "Taylor", 55, "Male", "Heart Disease");
        Inpatient ip2 = new Inpatient("P009", "Emma", "Watson", 22, "Female", "Appendicitis");
        hospitalSystem.registerPatient(ip1);
        hospitalSystem.registerPatient(ip2);
        hospitalSystem.allocateSpecificBed("P008", "B01");
        hospitalSystem.allocateSpecificBed("P009", "B01"); // Should throw exception - bed occupied
    }

    /**
     * Test 9: Prevent bed allocation when all beds are occupied.
     * I create 20 inpatients and allocate all beds, then try to allocate one more.
     */
    @Test(expected = Exception.class)
    public void testPreventAllocateWhenAllBedsOccupied() throws Exception {
        // Register and allocate beds for 20 inpatients
        for (int i = 1; i <= 20; i++) {
            String id = String.format("P%03d", i + 10);
            Inpatient ip = new Inpatient(id, "Patient", String.valueOf(i), 30, "Male", "Condition");
            hospitalSystem.registerPatient(ip);
            hospitalSystem.allocateBed(id);
        }
        assertEquals(20, hospitalSystem.getTotalOccupiedBeds());

        // Try to allocate a 21st patient - should fail
        Inpatient extra = new Inpatient("P999", "Extra", "Patient", 25, "Female", "Flu");
        hospitalSystem.registerPatient(extra);
        hospitalSystem.allocateBed("P999"); // Should throw exception
    }

    /**
     * Test 10: Sort patients by surname.
     * I verify that patients are sorted alphabetically by last name.
     */
    @Test
    public void testSortPatientsBySurname() throws Exception {
        hospitalSystem.registerPatient(new Patient("P100", "Zack", "Anderson", 20, "Male", "A", PatientCategory.OUTPATIENT));
        hospitalSystem.registerPatient(new Patient("P101", "Mike", "Brown", 30, "Male", "B", PatientCategory.OUTPATIENT));
        hospitalSystem.registerPatient(new Patient("P102", "John", "Clark", 40, "Male", "C", PatientCategory.OUTPATIENT));

        hospitalSystem.sortPatientsBySurname();
        assertEquals("Anderson", hospitalSystem.getPatients().get(0).getLastName());
        assertEquals("Brown", hospitalSystem.getPatients().get(1).getLastName());
        assertEquals("Clark", hospitalSystem.getPatients().get(2).getLastName());
    }

    /**
     * Test 11: Sort patients by Patient ID.
     * I verify that patients are sorted by their ID.
     */
    @Test
    public void testSortPatientsByID() throws Exception {
        hospitalSystem.registerPatient(new Patient("P300", "John", "Doe", 20, "Male", "A", PatientCategory.OUTPATIENT));
        hospitalSystem.registerPatient(new Patient("P100", "Jane", "Doe", 25, "Female", "B", PatientCategory.OUTPATIENT));
        hospitalSystem.registerPatient(new Patient("P200", "Jim", "Doe", 30, "Male", "C", PatientCategory.OUTPATIENT));

        hospitalSystem.sortPatientsByID();
        assertEquals("P100", hospitalSystem.getPatients().get(0).getPatientID());
        assertEquals("P200", hospitalSystem.getPatients().get(1).getPatientID());
        assertEquals("P300", hospitalSystem.getPatients().get(2).getPatientID());
    }

    /**
     * Test 12: Verify occupancy percentage calculation.
     * I check that the percentage is calculated correctly.
     */
    @Test
    public void testOccupancyPercentage() throws Exception {
        Inpatient ip = new Inpatient("P400", "Test", "User", 30, "Male", "Test");
        hospitalSystem.registerPatient(ip);
        hospitalSystem.allocateBed("P400");
        assertEquals(5.0, hospitalSystem.getOccupancyPercentage(), 0.01);
    }

    /**
     * Test 13: Verify only inpatients can be allocated beds.
     * I verify that trying to allocate a bed to an outpatient throws an exception.
     */
    @Test(expected = Exception.class)
    public void testOnlyInpatientCanGetBed() throws Exception {
        Patient p = new Patient("P500", "Out", "Patient", 25, "Male", "Cold", PatientCategory.OUTPATIENT);
        hospitalSystem.registerPatient(p);
        hospitalSystem.allocateBed("P500"); // Should throw exception
    }
}
