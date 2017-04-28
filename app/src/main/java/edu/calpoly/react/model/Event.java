package edu.calpoly.react.model;

import android.util.Log;

import java.util.Date;

import edu.calpoly.react.exceptions.TimeWindowException;

/**
 * Created by Nishanth on 4/27/17.
 */

public class Event extends TimeWindow {

    private Long id;
    private String name;
    private Activity activity;

    /* CONSTRUCTORS */

    public Event() {
        // empty constructor for serialization
    }

    public Event(String name, Activity activity, Date startTime) {
        setName(name);
        setActivity(activity);
        start(startTime);
    }

    public Event(String name, Activity activity, Date startTime, Date endTime) throws TimeWindowException {
        super(startTime, endTime);
        setName(name);
        setActivity(activity);
    }

    /* GETTERS/SETTERS */

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null && name.isEmpty() ? null : name;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        if (activity == null)
            throw new NullPointerException("Activity can not be null for an event");

        this.activity = activity;
    }

    /* METHODS */

    public void start(Date startTime) {
        try {
            setEndTime(null);
            setStartTime(startTime);
        } catch (TimeWindowException twe) {
            Log.e(Event.class.getName(), "Could not start event", twe);
        }
    }

    public void stop(Date endTime) {
        if (startTime == null || endTime.before(startTime))
            throw new IllegalStateException("Can not stop an event that has not started");

        try {
            setEndTime(endTime);
        } catch (TimeWindowException twe) {
            Log.e(Event.class.getName(), "Could not stop event", twe);
        }
    }

    @Override
    public long timeSpan() {
        if (startTime == null)
            throw new IllegalStateException("Event has not started");

        long duration = 0L;
        if (endTime == null) {
            duration = new Date().getTime() - startTime.getTime();
        } else {
            try {
                duration = super.timeSpan();
            } catch (IllegalStateException ex) {
                Log.e(Event.class.getName(),
                        "Could not obtain event duration. The event has invalid start/stop times.", ex);
            }
        }

        return duration;
    }
}
