package main.java.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oliver Andersson, Makrus Grahn, Chistian Lind.
 * Uses OccupiedTime, EmployeeCertificate. Used By EmployeeSorter, WorkShift, Admin, CertificateHandler, WorkDay.
 * Represents an employee with a specified name, email, personal ID, certificates and time span where the employee is not available for work
 * @since 2020-09-21
 */
public class Employee {
    private final List<OccupiedTime> OCCUPIEDTIMES; //TODO should vacation be seperate?
    private String name;
    private String email;
    private String phoneNumber;
    public final String PERSONAL_ID;
    private List<EmployeeCertificate> certificates;

    /**
     * Constructs an employee with a list for time span where the employee is not available for work, a specified name, specified personal ID and a list for provided certificates
     *
     * @param name        the employee's name
     * @param personalId  the employee's personal ID
     * @param email       the employee's personal email
     * @param phoneNumber the employee's personal phonenumber
     */
    protected Employee(String name, String personalId, String email, String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.OCCUPIEDTIMES = new ArrayList<>();
        this.name = name;
        this.PERSONAL_ID = personalId;
        this.certificates = new ArrayList<>();
        this.email = email;
    }


    /**
     * Returns the specified EmployeeCertificate if the list of EmployeeCertificates has the specified certificate
     *
     * @param certificate The certificate to see if the employee has
     * @return The EmployeeCertificate which holds the certificate
     */
    public EmployeeCertificate getEmployeeCertificate(Certificate certificate) {
        for (EmployeeCertificate c : certificates) {
            if (c.getCertificate() == certificate) {
                return c;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Assigns a specified certificate to the employee
     *
     * @param certificate the certificate that should be assigned
     */
    protected void assignCertificate(EmployeeCertificate certificate) {
        this.certificates.add(certificate);

    }

    /**
     * Removes a specified certificate from the employee
     *
     * @param certificate the certificate that should be taken from the employee
     */
    protected void unAssignCertificate(EmployeeCertificate certificate) {
        certificates.remove(certificate);
    }

    /**
     * checks if the employee is not available for work at an chosen time span
     *
     * @param start when the time span starts
     * @param end   when the time span stops
     * @return true if the employee is not available and false if the employee is available
     */
    public boolean isOccupied(long start, long end) {
        for (OccupiedTime occupiedTime : OCCUPIEDTIMES) {
            if (occupiedTime.inBetween(start, end))
                return true;
        }
        return false;
    }

    /**
     * checks if the employee is qualified, has the required certificates, for a chosen workshift or not
     *
     * @param workShift the chosen workshift
     * @return true if the employee has the required certificates for the workshift and false if not
     */
    public boolean isQualified(WorkShift workShift) {
        int count = 0;
        Certificate certificate;
        for (int i = 0; i < workShift.getCertificatesSize(); i++) {
            certificate = workShift.getCertificate(i);
            for (EmployeeCertificate certificate1 : certificates) {
                if (certificate1.getCertificate() == certificate) {
                    count++;
                }
            }
        }
        return count == workShift.getCertificatesSize();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Returns the personal id
     *
     * @return The employees personal id
     */
    public String getPersonalId() {
        return PERSONAL_ID;
    }

    /**
     * Returns the name of the employee
     *
     * @return The name of the employee
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the employees name
     *
     * @param name The new name the employee changed to
     */
    public void newName(String name) {
        this.name = name;
    }

    /**
     * Unregister the Employee at the time the Employee is occupied
     *
     * @param occupiedTime The OccupiedTime in which the employee should no longer have
     */
    public void unRegisterOccupation(OccupiedTime occupiedTime) {
        OCCUPIEDTIMES.remove(occupiedTime);
    }

    /**
     * Get the size of
     *
     * @return The size of OccupiedTime
     */
    public int getOccupiedTimesSize() {
        return OCCUPIEDTIMES.size();
    }

    /**
     * Get an occupiedTime at the index in the ArrayList of occupiedTimes
     *
     * @param index Index of the EmployeeCertificate to return
     * @return The OccupiedTime at the specified index
     */
    public OccupiedTime getOccupiedTime(int index) {//TODO immutable
        return OCCUPIEDTIMES.get(index);
    }

    /**
     * Registers the employee to the time OccupiedTime of Start and end times
     *
     * @param start The start time of the shift
     * @param end   The end time of the shift
     */
    protected void registerOccupation(long start, long end) {
        OCCUPIEDTIMES.add(new OccupiedTime(start, end));
    }

    /**
     * Registers the employee to the time OccupiedTime
     *
     * @param occupiedTime The occupiedTime the Employee is to be non avalible
     */
    protected void registerOccupation(OccupiedTime occupiedTime) {
        OCCUPIEDTIMES.add(occupiedTime);
    }

    /**
     * Get an EmployeeCertificate at the index in the ArrayList of EmployeeCertificates
     *
     * @param index Index of the EmployeeCertificate to return
     * @return The EmployeeCertificate at the specified index
     */
    public EmployeeCertificate getCertificate(int index) {
        return certificates.get(index);
    }

    /**
     * Returns how many certificates the employee has
     *
     * @return the number of certificates the employee is holding
     */
    public int getCertificatesSize() {
        return this.certificates.size();
    }

    /**
     * Checks if the employee has the required certificates
     *
     * @param certificates The certificates to check if the employee has
     * @return true if the employee has all the certificates, otherwise false
     */
    public boolean hasCertifices(List<Certificate> certificates) {
        ArrayList<Certificate> certificates1 = new ArrayList<>();
        for (EmployeeCertificate ec : this.certificates) {
            certificates1.add(ec.getCertificate());

        }
        return certificates1.containsAll(certificates);
    }
}
