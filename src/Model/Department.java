package Model;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private List<WorkShift> allShifts;
    private String name;
    private List<Certificate> certificates;

    public Department(String name, List<Certificate> certificates) {
        this.allShifts = new ArrayList<>();
        this.name = name;
        this.certificates = certificates;
    }

    public Department(String name) {
        this.allShifts = new ArrayList<>();
        this.name = name;
        this.certificates = new ArrayList<>();
    }

    public void createShift(long start, long stop, int nEmployees) {
        allShifts.add(new WorkShift(start, stop, nEmployees));
    }

    public void removeShift(WorkShift ws){
        allShifts.remove(ws);
    }

    public List<WorkShift> getAllShifts() {
        return allShifts;
    }

    public void addCertificate(Certificate c) {
        certificates.add(c);
    }

    public List<Certificate> getAllCertificate() {
        return certificates;
    }

    public void removeCertificate(Certificate c) {
        certificates.remove(c);
    }
}