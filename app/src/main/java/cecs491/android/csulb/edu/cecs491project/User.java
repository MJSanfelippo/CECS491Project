package cecs491.android.csulb.edu.cecs491project;

/**
 * Created by Michael on 3/3/2018.
 */

public class User {

    /**
     * the email of the user
     */
    private String email;

    /**
     * the first name of the user
     */
    private String firstName;

    /**
     * the last name of the user
     */
    private String lastName;

    /**
     * the phone number of the user
     */
    private String phoneNumber;

    /**
     * the type of user
     */
    private String userType;

    /**
     * the uid of the user
     */
    private String uid;

    /**
     * constructor for user not including uid
     * @param email the email of the user
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param phoneNumber the phone number of the user
     * @param userType the type of user (employee or employer)
     */
    public User(String email, String firstName, String lastName, String phoneNumber, String userType){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
    }

    /**
     * constructor for user including uid
     * @param email the email of the user
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param phoneNumber the phone number of the user
     * @param userType the type of user (employee or employer)
     * @param uid the uid of the user
     */
    public User(String email, String firstName, String lastName, String phoneNumber, String userType, String uid) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.uid = uid;
    }

    /**
     * gets the uid of the user
     * @return the uid of the user
     */
    public String getUid() {
        return uid;
    }

    /**
     * sets the uid of the user
     * @param uid the uid of the user
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * gets the email of the user
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets the email of the user
     * @param email the email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * gets the first name of the user
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * sets the first name of the user
     * @param firstName the first name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * gets the last name of the user
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * sets the last name of the user
     * @param lastName the last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * gets the phone number of the user
     * @return the phone number of the user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * sets the phone number of the user
     * @param phoneNumber the phone number of the user
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * gets the type of user
     * @return the type of user
     */
    public String getUserType() {
        return userType;
    }

    /**
     * sets the type of user
     * @param userType the type of user
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }
}
