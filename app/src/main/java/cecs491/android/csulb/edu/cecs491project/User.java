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
    private String uid;

    public User(){
        // it wants a default constructor for firebase for some reason
    }

    public User(String email, String firstName, String lastName, String phoneNumber, String userType, String uid){
        this.email = email;
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


}
