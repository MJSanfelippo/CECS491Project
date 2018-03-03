package cecs491.android.csulb.edu.cecs491project;

/**
 * Created by Michael on 3/3/2018.
 */

public class User {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String userType;

    public User(){
        // it wants a default constructor for firebase for some reason
    }
    public User(String email, String firstName, String lastName, String phoneNumber, String userType){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
    }

    // add getters and setters later
}
