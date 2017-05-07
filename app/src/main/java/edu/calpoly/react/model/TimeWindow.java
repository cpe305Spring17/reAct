package edu.calpoly.react.model;

import java.util.Date;
import java.util.List;

import edu.calpoly.react.exceptions.TimeWindowException;

/**
 * Created by Nishanth on 4/27/17.
 */

public class TimeWindow {

    protected Date startTime;
    protected Date endTime;

    /* CONSTRUCTORS */

    public TimeWindow() {
        // empty constructor for serialization
    }

    public TimeWindow(Date startTime, Date endTime) throws TimeWindowException {
        setStartTime(startTime);
        setEndTime(endTime);
    }

    /* GETTERS/SETTERS */

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) throws TimeWindowException {
        if (endTime != null && startTime != null && startTime.after(endTime))
            throw new TimeWindowException("StartTime is before EndTime");

        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) throws TimeWindowException {
        if (startTime != null && endTime != null && endTime.before(startTime))
            throw new TimeWindowException("EndTime must be after StartTime");

        this.endTime = endTime;
    }

    public long timeSpan() {
        if (startTime == null || endTime == null)
            throw new IllegalStateException("StartTime and EndTime required for calculating time span");

        return endTime.getTime() - startTime.getTime();
    }

    public void encompass(List<? extends TimeWindow> others) throws TimeWindowException {
        Date start = null;
        Date end = null;

        for (TimeWindow tw : others) {
            if (start == null || tw.getStartTime().before(start))
                start = tw.getStartTime();
            if (end == null || tw.getEndTime().after(end))
                end = tw.getEndTime();
        }

        setStartTime(start);
        setEndTime(end);
    }

    public Boolean encompasses(TimeWindow other) {
        Date otherStart = other.getStartTime();
        boolean startBefore = startTime != null && otherStart != null
                && (startTime.before(otherStart) || startTime.equals(otherStart));

        Date otherEnd = other.getEndTime();
        boolean endAfter = endTime != null && otherEnd != null
                && (endTime.after(otherEnd) || endTime.equals(otherEnd));

        return startBefore && endAfter;
    }

    public Boolean encompassesAll(List<? extends TimeWindow> others) {
        for (TimeWindow tw : others) {
            if (!encompasses(tw))
                return false;
        }
        return true;
    }
}
