package org.example.ecommercewebsite.business;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Shift implements Serializable {
    @Id
    private String shiftID;
    private String shiftName;
    @Temporal(TemporalType.DATE)
    private Date shiftDate;
    private String startTime;
    private String endTime;

    public Shift(){}

    public Shift(String shiftName, String startTime, String endTime, Date shiftDate) {
        this.shiftID = shiftName+shiftDate.toString();
        this.shiftName = shiftName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shiftDate = shiftDate;
    }

    public String getShiftID() {
        return shiftID;
    }

    public void setShiftID(String shiftID) {
        this.shiftID = shiftID;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public Date getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(Date shiftDate) {
        this.shiftDate = shiftDate;
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

    @Override
    public String toString() {
        return shiftName+'-'+shiftDate;
    }
}
