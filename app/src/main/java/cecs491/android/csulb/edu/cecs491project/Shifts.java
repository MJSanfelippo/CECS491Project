package cecs491.android.csulb.edu.cecs491project;

/**
 * Created by Michael on 3/7/2018.
 */

public class Shifts {
    private String startTime;
    private String endTime;
    private String realStartTime;
    private String realEndTime;
    private String breakStart;
    private String breakEnd;
    private String date;
    private String createdBy;
    private String firstName;
    private String lastName;

    public Shifts(){

    }

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(String realStartTime) {
        this.realStartTime = realStartTime;
    }

    public String getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(String realEndTime) {
        this.realEndTime = realEndTime;
    }

    public String getBreakStart() {
        return breakStart;
    }

    public void setBreakStart(String breakStart) {
        this.breakStart = breakStart;
    }

    public String getBreakEnd() {
        return breakEnd;
    }

    public void setBreakEnd(String breakEnd) {
        this.breakEnd = breakEnd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
}
