package main.java.model;

import java.util.*;

/**
 * @author Markus Grahnand, Christian Lind, Oliver Andersson
 * Uses Department, WorkShift, Employee, OccupiedTime, Used By Admin, EmployeeSorter, OurCalendar, ImportServicePackage.
 * Represents a work day with a specified date, a hash map(with departments, work shifts and employees),and a list of departments
 * @since 2020-09-12
 */
public class WorkDay implements Observer {
    public final long DATE;
    private static final List<Department> DEPARTMENTS = new ArrayList<>();
    private final HashMap<Department, List<WorkShift>> DEPARTMENTLINKS;


    /**
     * Constructs a work day with a specified date and with hash map
     *
     * @param date Date of the work day
     */
    protected WorkDay(long date) {
        this.DATE = date;
        this.DEPARTMENTLINKS = new HashMap<>();
        for (Department d : DEPARTMENTS) {
            d.addObserver(this);
        }
    }

    public int getDepartmentSize() {
        return DEPARTMENTS.size();
    }

    public Department getDepartment(int index) {
        return DEPARTMENTS.get(index);
    }


    /**
     * Registers an Employee for a Workshift and ensures they get their free time
     *
     * @param workShift A WorkShift
     * @param employee  An Employee
     */
    public void occupiesEmployee(WorkShift workShift, Employee employee) {
        ArrayList<Certificate> certificates = new ArrayList<>();
        for (int i = 0; i < workShift.getCertificatesSize(); i++) {
            certificates.add(workShift.getCertificate(i));
        }
        if (!employee.isOccupied(workShift.START, workShift.END) && employee.hasCertifices(certificates) && !workShift.isOccupied()) {
            long endOccupiedTime = (workShift.END) + Admin.getInstance().getGuaranteedFreeTime();
            OccupiedTime ot = new OccupiedTime(workShift.START, endOccupiedTime);
            employee.registerOccupation(ot);
            workShift.registerOccupation(employee, ot);
        } else {
            throw new IllegalArgumentException("The employee cannot occupy the shift");
        }
    }

    /**
     * Swaps out an Employee for another one on that WorkShift
     *
     * @param workShift A WorkShift
     * @param employee  An Employee
     */
    public void reOccupieEmployee(WorkShift workShift, Employee employee) {
        ArrayList<Certificate> certificates = new ArrayList<>();
        for (int i = 0; i < workShift.getCertificatesSize(); i++) {
            certificates.add(workShift.getCertificate(i));
        }
        if (!employee.isOccupied(workShift.START, workShift.END) && employee.hasCertifices(certificates)) {
            workShift.clearWorkShiftOccupation();
            long endOccupiedTime = (workShift.END) + Admin.getInstance().getGuaranteedFreeTime();
            OccupiedTime ot = new OccupiedTime(workShift.START, endOccupiedTime);
            employee.registerOccupation(ot);
            workShift.registerOccupation(employee, ot);
        } else {
            throw new IllegalArgumentException("The employees cannot be reoccupied");
        }
    }

    /**
     * Swaps the Employee of ws1 and ws2 if both are qualified
     *
     * @param ws1 Workshift to swap Employee
     * @param ws2 Workshift to swap Employee
     */
    public void swapOccupation(WorkShift ws1, WorkShift ws2) {
        ArrayList<Certificate> certificates = new ArrayList<>();
        for (int i = 0; i < ws1.getCertificatesSize(); i++) {
            certificates.add(ws1.getCertificate(i));
        }
        ArrayList<Certificate> certificates2 = new ArrayList<>();
        for (int i = 0; i < ws2.getCertificatesSize(); i++) {
            certificates2.add(ws2.getCertificate(i));
        }
        if (ws1.isOccupied() && ws2.isOccupied() && ws1.getEmployee().hasCertifices(certificates2) && ws2.getEmployee().hasCertifices(certificates)) {
            Employee e1 = ws1.getEmployee();
            Employee e2 = ws2.getEmployee();
            ws1.clearWorkShiftOccupation();
            ws2.clearWorkShiftOccupation();
            if (e1.isOccupied(ws2.START, ws2.END) || e2.isOccupied(ws1.START, ws1.END)) {
                occupiesEmployee(ws1, e1);
                occupiesEmployee(ws2, e2);
            } else {
                occupiesEmployee(ws2, e1);
                occupiesEmployee(ws1, e2);
            }
        }
    }


    /**
     * Checks if all departments have 0 shifts
     *
     * @return true if all departments have 0 shifts
     */
    public boolean isEmpty() {
        updateDepartments();
        for (Department d : DEPARTMENTS) {
            if (DEPARTMENTLINKS.get(d).size() != 0)
                return false;
        }
        return true;
    }

    /**
     * Checks if all shifts are filled
     *
     * @return true if all shifts are occupied
     */
    public boolean isFilled() {
        updateDepartments();
        for (Department d : DEPARTMENTS) {
            for (WorkShift w : DEPARTMENTLINKS.get(d)) {
                if (!w.isOccupied())
                    return false;
            }
        }
        return true;
    }

    /**
     * Returns all workshifts in the specified department
     *
     * @param department The department to get the workshift from
     * @return a list of workshift in the department
     */
    public List<WorkShift> getWorkShifts(Department department) {
        return DEPARTMENTLINKS.get(department);
    }

    /**
     * Makes sure all departments are properly linked in departmentLinks
     */
    public void updateDepartments() {
        for (Department d : DEPARTMENTS) {
            DEPARTMENTLINKS.computeIfAbsent(d, k -> new ArrayList<>());
        }
    }

    /**
     * Adds a department
     *
     * @param department department to add
     */
    protected static void addDepartment(Department department) {
        DEPARTMENTS.add(department);
    }

    public void removeWorkshift(WorkShift ws) {
        for (Department d : DEPARTMENTS) {
            if (DEPARTMENTLINKS.get(d).contains(ws)) {
                if (ws.isOccupied()) {
                    ws.clearWorkShiftOccupation();
                }

                DEPARTMENTLINKS.get(d).remove(ws);
            }
        }
    }

    /**
     * removes a department
     *
     * @param department department to remove
     */
    protected static void removeDepartment(Department department) {
        DEPARTMENTS.remove(department);
    }

    /**
     * Unregister an employee from a specified time
     *
     * @param employee the employee
     * @param start    time to remove from
     * @param end      time to remove to
     */
    public void unRegisterOccupations(Employee employee, long start, long end) {
        for (Department d : DEPARTMENTS) {
            if (!(DEPARTMENTLINKS.isEmpty())) {
                for (WorkShift ws : DEPARTMENTLINKS.get(d)) {
                    if (ws.isOccupied()) {
                        if (ws.getOccupation().inBetween(start, end) && ws.getEmployee() == employee) {
                            ws.clearWorkShiftOccupation();
                        }
                    }
                }
            }
        }
    }

    /**
     * Updates the workDay with shifts to be added
     */
    @Override
    public void update() {
        updateDepartments();
        for (Department d : DEPARTMENTS) {
            if (d.getAddWorkShiftSize() > 0) {
                ArrayList<WorkShift> addShifts = new ArrayList<>();
                for (int i = 0; i < d.getAddWorkShiftSize(); i++) {
                    addShifts.add(d.getAddWorkShift(i));
                }
                for (WorkShift addWorkShift : addShifts) {
                    Date wsDate = new Date(addWorkShift.START);
                    Date thisDate = new Date(this.DATE);
                    if ((addWorkShift.REPEAT && (wsDate.getDay() == thisDate.getDay())) || (!addWorkShift.REPEAT && (wsDate.getDay() == thisDate.getDay()) && (wsDate.getDate() == thisDate.getDate()))) {
                        this.DEPARTMENTLINKS.get(d).add(new WorkShift(addWorkShift, this.DATE));
                    }
                }
            }
            if (d.getRemoveWorkShiftSize() > 0) {
                ArrayList<WorkShift> removeShifts = new ArrayList<>();
                for (int i = 0; i < d.getRemoveWorkShiftSize(); i++) {
                    removeShifts.add(d.getRemoveWorkShift(i));
                }
                for (WorkShift removeShift : removeShifts) {
                    DEPARTMENTLINKS.get(d).removeIf(checkShift -> removeShift.ID == checkShift.ID);
                }
            }
        }
    }
}
