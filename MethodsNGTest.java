package chatx;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author RC_Student_lab
 */
public class MethodsNGTest {

    Login login = new Login();

   
    @Test
    public void testValidUsername() {
        Methods user = new Methods("Amu_5", "Amu@1234", "+27726125067", "Amukelani", "Nhlengethwa");
        login.registerUser(user);
        Assert.assertTrue(login.checkUserName(), "Username should be valid");
    }

    @Test
    public void testInvalidUsername_NoUnderscore() {
        Methods user = new Methods("Amu5", "Amu@1234", "+27726125067", "Amukelani", "Nhlengethwa");
        login.registerUser(user);
        Assert.assertFalse(login.checkUserName(), "Username without underscore should be invalid");
    }

    // 🔹 Test password validation
    @Test
    public void testValidPassword() {
        Methods user = new Methods("Amu_5", "Amu@1234", "+27726125067", "Amukelani", "Nhlengethwa");
        login.registerUser(user);
        Assert.assertTrue(login.checkPasswordComplexity(), "Password should meet complexity requirements");
    }

    @Test
    public void testInvalidPassword_NoSpecialChar() {
        Methods user = new Methods("Amu_5", "Amu12345", "+27726125067", "Amukelani", "Nhlengethwa");
        login.registerUser(user);
        Assert.assertFalse(login.checkPasswordComplexity(), "Password missing special character should be invalid");
    }

    // 🔹 Test cellphone validation
    @Test
    public void testValidCellPhone() {
        Methods user = new Methods("Amu_5", "Amu@1234", "+27726125067", "Amukelani", "Nhlengethwa");
        login.registerUser(user);
        Assert.assertTrue(login.checkCellPhoneNumber(), "Cellphone should be valid");
    }

    @Test
    public void testInvalidCellPhone() {
        Methods user = new Methods("Amu_5", "Amu@1234", "0726125067", "Amukelani", "Nhlengethwa"); // Missing +27
        login.registerUser(user);
        Assert.assertFalse(login.checkCellPhoneNumber(), "Cellphone without +27 should be invalid");
    }
}