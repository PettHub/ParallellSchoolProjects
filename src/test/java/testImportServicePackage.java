package test.java;

import main.java.model.ImportServicePackage;
import org.junit.Test;
/**
 * @author Markus Grahn
 */
public class testImportServicePackage {

    @Test
    public void testImportServicePackage(){
        ImportServicePackage.loadPackage();
    }

}
