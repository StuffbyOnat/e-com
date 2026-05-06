/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

/**
 *
 * @author onatu
 */
public class User {


    public static int getUserID() {
        return userID;
    }

    public static String getFullName() {
        return fullName;
    }

    public static String getEmail() {
        return email;
    }

    public static String getPassword() {
        return password;
    }

    public static String getAddress() {
        return address;
    }

    public static String getRole() {
        return role;
    }

    static int userID;
    static String fullName;
    static String email;
    static String password;
    static String address;
    static String role;
    
    public static void initializeUser(int UserID,String FullName,String Email,String Password,String Address,String Role){
    userID=UserID;
    fullName=FullName;
    email=Email;
    password=Password;
    address=Address;
    role=Role;
    }
}
