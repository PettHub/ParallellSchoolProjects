package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee with a specified name, email, personal ID, certificates and time span where the employee is not available for work
 */
public class Employee {
    private List<OccupiedTime> occupiedTimes; //TODO should vacation be seperate?
    private String name;
    private String email;
    public final String PERSONAL_ID;
    private List<EmployeeCertificate> certificates;

    /**
     * constructs an employee with a list for time span where the employee is not available for work, a specified name, specified personal ID and a list for provided certificates
     *
     * @param name       the employee's name
     * @param personalId the employee's personal ID
     */
    protected Employee(String name, String personalId, String email) {
        this.occupiedTimes = new ArrayList<>();
        this.name = name;
        this.PERSONAL_ID = personalId;
        this.certificates = new ArrayList<>();
        this.email = email;
    }

    public EmployeeCertificate getEmployeeCertificate(Certificate certificate) {
        for (EmployeeCertificate c : certificates) {
            if (c.getCertificate() == certificate) {
                return c;
            }
        }
        return null;
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
     * Assigns several specified certificates to the employee
     *
     * @param certificates list with all the certificates that should be assigned
     */
    protected void assignCertificate(List<EmployeeCertificate> certificates) {
        this.certificates.addAll(certificates);
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
        for (OccupiedTime occupiedTime : occupiedTimes) {
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

    public void unRegisterOccupation(OccupiedTime ot) {
        occupiedTimes.remove(ot);
    }

    public List<OccupiedTime> getOccupiedTimes() {
        return occupiedTimes;
    }

    public void registerOccupation(long start, long end) {
        occupiedTimes.add(new OccupiedTime(start, end));
    }

    public void registerOccupation(OccupiedTime ot) {
        occupiedTimes.add(ot);
    }


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
        for (Certificate c : certificates) {
            for (EmployeeCertificate ec : this.certificates) {
                if (c != ec.getCertificate()) {
                    return false;
                }
            }
        }
        return true;
    }
}
