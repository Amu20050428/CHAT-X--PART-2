/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatx;
import java.util.regex.*; 

public class Login {
    private Methods user;  

    // Register new user
    public String registerUser(Methods user) {
        this.user = user; // save the user

        if (!checkUserName()) {
            return "Username is not correctly formatted. Please ensure that your username contains an underscore and is no more than 5 characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted. Please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an international code.";
        }

        return "User successfully registered!";
    }

    // Login user
    public String loginUser(String username, String password) {
        if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
            return "Welcome " + user.getFirstName() + " " + user.getLastName() + ", it is great to see you again!";
        } else {
            return "Username or password incorrect. Please try again.";
        }
    }

    // ✅ Check username validity
    public boolean checkUserName() {
        return user.getUsername().contains("_") && user.getUsername().length() <= 5;
    }

    // ✅ Check password complexity
    public boolean checkPasswordComplexity() {
        String password = user.getPassword();
        boolean length = password.length() >= 8;
        boolean capital = password.matches(".*[A-Z].*");
        boolean number = password.matches(".*[0-9].*");
        boolean special = password.matches(".*[@#$%^&+=!].*");

        return length && capital && number && special;
    }

    // ✅ Check cellphone format using regex
    public boolean checkCellPhoneNumber() {
        // Regex: must start with +27 and be followed by exactly 9 digits (South African format)
        Pattern pattern = Pattern.compile("^\\+27[0-9]{9}$");
        Matcher matcher = pattern.matcher(user.getCellPhone());
        return matcher.matches();
    }
}