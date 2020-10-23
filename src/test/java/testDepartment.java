package test.java;

import main.java.model.Admin;
import main.java.model.WeekHandler;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
/**
 * @author Victor Cousin, Moa Berglund, Markus Grahn
 */
public class testDepartment {
    @Test
    public void testCreateBreak() {
        Admin admin = Admin.getInstance();
        admin.createNewDepartment("Kassa", 2);
        Date date = (new Date());
        date.setTime(date.getTime() + (WeekHandler.plusDays(1)));
        boolean[] repeat = {false, false, false, false, true, false, false};
        admin.getDepartmentByName("Kassa").getBreakHandler().setMinBreakLength(30);
        admin.createWorkshift(admin.getDepartmentByName("Kassa"), date.getTime() + (WeekHandler.plusHours(1)), date.getTime() + (WeekHandler.plusHours(5)), repeat);
        admin.createWorkshift(admin.getDepartmentByName("Kassa"), date.getTime() + (WeekHandler.plusHours(1)), date.getTime() + (WeekHandler.plusHours(5)), repeat);
        admin.createWorkshift(admin.getDepartmentByName("Kassa"), date.getTime() + (WeekHandler.plusHours(1)), date.getTime() + (WeekHandler.plusHours(5)), repeat);
        admin.createWorkshift(admin.getDepartmentByName("Kassa"), date.getTime() + (WeekHandler.plusHours(1)), date.getTime() + (WeekHandler.plusHours(5)), repeat);

        assertEquals(new Date(admin.getDepartmentByName("Kassa").getShift(3).getBreakTime().START).getHours() + new Date(admin.getDepartmentByName("Kassa").getShift(3).getBreakTime().START).getMinutes(), new Date(admin.getDepartmentByName("Kassa").getShift(0).getBreakTime().START).getHours() + new Date(admin.getDepartmentByName("Kassa").getShift(0).getBreakTime().START).getMinutes());

    }
}
