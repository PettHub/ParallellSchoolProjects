package test.java;

import main.java.model.Admin;
import main.java.model.CertificateHandler;
import org.junit.Test;
import org.junit.Before;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * @author Moa Berglund, Victor Cousin
 */
public class testCertificateHandler {
     @Before
    public void before(){  
        Admin admin = Admin.getInstance();
        admin.createNewEmployee("moa", "123456789231", "moa@gmail.com", "03185552266");
        admin.createNewEmployee("moa", "123456789232", "moa2@gmail.com", "03185552267");
        admin.createNewEmployee("crilllle", "312123456789", "llllllll@gmail.com", "03185552268");
        CertificateHandler ch = admin.getCertificatehandler();
        ch.createNewCertificate("Kassa");
        ch.createNewCertificate("Frukt");
        admin.createEmployeeCertificate(ch.getCertificate("Kassa"), admin.getEmployeeByID("123456789231"), new Date());
        admin.createEmployeeCertificate(ch.getCertificate("Kassa"), admin.getEmployeeByID("123456789232"), new Date());
        admin.createEmployeeCertificate(ch.getCertificate("Frukt"), admin.getEmployeeByID("123456789232"), new Date());
    }

       @Test
    public void testWhoHasCertificate() {
        Admin admin = Admin.getInstance();
        CertificateHandler ch = admin.getCertificatehandler();
        assertEquals(2, ch.getEmployeeWithCertificateSize(ch.getCertificate("Kassa")));
        assertEquals(1, ch.getEmployeeWithCertificateSize(ch.getCertificate("Frukt")));
        assertTrue(!ch.checkEmployeeWithCertificate(ch.getCertificate("Kassa"), (admin.getEmployeeByID("312123456789"))));
    }


    @Test
    public void testRemoveGlobalCertificate() {
        Admin admin = Admin.getInstance();
        CertificateHandler ch = admin.getCertificatehandler();
        ch.deleteCertificate("Kassa");
        assertEquals(1, admin.getEmployeeByID("123456789232").getCertificatesSize());
    }
}
