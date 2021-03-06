package test.java;

import main.java.model.Admin;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * @author Christian Lind
 */
public class testLogin {

    @Test
    public void testCreateUser() {
        Admin admin = Admin.getInstance();
        admin.createNewUser("Markus", "Hemligt!");
        assertTrue(admin.isLoginInformationCorrect("Markus", "Hemligt!"));
        assertFalse(admin.isLoginInformationCorrect("Markus", "Hemligt"));
        admin.removeUser("Markus", "Hemligt!");
        assertFalse(admin.isLoginInformationCorrect("Markus", "Hemligt!"));
    }

}
