package cecs491.android.csulb.edu.cecs491project;

/**
 * Created by phannachrin on 4/24/18.
 */

public class Announcement {
    /**
     * Field that holds the announcement message.
     */
    private String message;
    /**
     * Field that holds the author of the message.
     */
    private String postedBy;
    /**
     * Field that holds the timestamp for the message.
     */
    private String timestamp;

    /**
     * Default constructor.
     */
    public Announcement(){}
    /**
     * Constructor that sets the message, postedBy, and timestamp fields.
     * @param message
     * @param postedBy
     * @param timestamp
     */
    public Announcement(String message, String postedBy, String timestamp) {
        this.message = message;
        this.postedBy = postedBy;
        this.timestamp = timestamp;
    }
    /**
     * Method that sets the message field.
     * @param m
     */
    public void setMessage(String m) {
        message = m;
    }

    /**
     * Method that sets the postedBy field.
     * @param name
     */
    public void setPostedBy(String name) {
        postedBy = name;
    }

    /**
     * Method that sets the timestamp field.
     * @param t
     */
    public void setTimestamp(String t) {
        timestamp = t;
    }
    /**
     * Method that return the message field.
     * @return
     */
    public String getMessage() {return message;}

    /**
     * Method that return the postedBy field.
     * @return
     */
    public String getPostedBy() {
        return postedBy;
    }

    /**
     * Method that return the timestamp field.
     * @return
     */
    public String getTimestamp() {
        return timestamp;
    }
}
