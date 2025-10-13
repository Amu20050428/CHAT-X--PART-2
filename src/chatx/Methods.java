/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatx;

/**
 *
 * @author RC_Student_lab
 */
public class Methods {
    private final String username;
    private final String password;
    private final String cellphone;
    private final String firstName;
    private final String lastName;
    
    // contructor (used to create a ser object)
    public Methods ( String username,String password,String cellphone,String firstName,String lastName ){
        this.username= username;
        this.password= password;
        this.cellphone= cellphone;
        this.firstName= firstName;
        this.lastName=lastName;
        
    }
    
    // 🔹 Getters (to safely access private variables)
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCellPhone() {
        return cellphone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

    
