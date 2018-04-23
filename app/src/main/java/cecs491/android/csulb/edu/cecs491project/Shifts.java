package cecs491.android.csulb.edu.cecs491project;

/**
 * Created by Michael on 3/7/2018.
 */

public class Shifts {
    /**
     * the assigned start time of the shift
     */
    private String startTime;

    /**
     * the assigned end time of the shift
     */
    private String endTime;

    /**
     * the time the user starts the shift
     */
    private String realStartTime;

    /**
     * the time the user ends the shift
     */
    private String realEndTime;

    /**
     * the time the user started their break
     */
    private String breakStart;

    /**
     * the time the user ended their break
     */
    private String breakEnd;

    /**
     * the date of the shift
     */
    private String date;

    /**
     * the user id of the person who created the shift
     */
    private String createdBy;

    /**
     * the first name of the person whose shift this is
     */
    private String firstName;

    /**
     * the last name of the person whose shift this is
     */
    private String lastName;


    /**
     * constructor for all parameters
     * @param startTime the start time
     * @param endTime the end time
     * @param realStartTime the real start time
     * @param realEndTime the real end time
     * @param breakStart the time of break start
     * @param breakEnd the time of break end
     * @param date the date of the shift
     * @param createdBy id of person who created the shift
     * @param firstName the first name of the person whose shift this is
     * @param lastName the last name of the person whose shift this is
     */
    public Shifts(String startTime, String endTime, String realStartTime, String realEndTime, String breakStart, String breakEnd, String date, String createdBy, String firstName, String lastName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.realStartTime = realStartTime;
        this.realEndTime = realEndTime;
        this.breakStart = breakStart;
        this.breakEnd = breakEnd;
        this.date = date;
        this.createdBy = createdBy;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Shifts(String startTime, String endTime, String firstName, String lastName, String date){
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;

        this.realStartTime = " ";
        this.realEndTime = " ";
        this.breakStart = " ";
        this.breakEnd =  " ";

    }
    /**
     * gets the start time
     * @return the start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * sets the start time
     * @param startTime the start time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * gets the end time
     * @return the end time
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * sets the end time
     * @param endTime the end time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * gets the real start time
     * @return the real start time
     */
    public String getRealStartTime() {
        return realStartTime;
    }

    /**
     * sets the real start time
     * @param realStartTime the real start time
     */
    public void setRealStartTime(String realStartTime) {
        this.realStartTime = realStartTime;
    }

    /**
     * gets the real end time
     * @return the real end time
     */
    public String getRealEndTime() {
        return realEndTime;
    }

    /**
     * sets the real end time
     * @param realEndTime the real end time
     */
    public void setRealEndTime(String realEndTime) {
        this.realEndTime = realEndTime;
    }

    /**
     * gets the break start time
     * @return the break start time
     */
    public String getBreakStart() {
        return breakStart;
    }

    /**
     * sets the break start time
     * @param breakStart the break start time
     */
    public void setBreakStart(String breakStart) {
        this.breakStart = breakStart;
    }

    /**
     * gets the break end time
     * @return the break end time
     */
    public String getBreakEnd() {
        return breakEnd;
    }

    /**
     * sets the break end time
     * @param breakEnd the break end time
     */
    public void setBreakEnd(String breakEnd) {
        this.breakEnd = breakEnd;
    }

    /**
     * gets the date of the shift
     * @return the date of the shift
     */
    public String getDate() {
        return date;
    }

    /**
     * sets the date of the shift
     * @param date the date of the shift
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * gets the uid of the person who created the shift
     * @return the uid of the person who created the shift
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * sets the uid of the person who created the shift
     * @param createdBy the uid of the person who created the shift
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * gets the first name of the person whose shift this is
     * @return the first name of the person whose shift this is
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * sets the first name of the person whose shift this is
     * @param firstName the first name of the person whose shift this is
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * gets the last name of the person whose shift this is
     * @return the last name of the person whose shift this is
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * sets the last name of the person whose shift this is
     * @param lastName the last name of the person whose shift this is
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
