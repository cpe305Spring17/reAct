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
    private Action action;

    /* CONSTRUCTORS */

    public Event() {
        // empty constructor for serialization
    }

    public Event(String name, Action action, Date startTime) {
        setName(name);
        setAction(action);
        start(startTime);
    }

    public Event(String name, Action action, Date startTime, Date endTime) throws TimeWindowException {
        super(startTime, endTime);
        setName(name);
        setAction(action);
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

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        if (action == null) {
            throw new NullPointerException("Action can not be null for an event");
        }
        this.action = action;
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
        if (endTime.before(startTime))
            throw new IllegalStateException("Can not stop an event before it's started");

        try {
            setEndTime(endTime);
        } catch (TimeWindowException twe) {
            Log.e(Event.class.getName(), "Could not stop event", twe);
        }
    }

    @Override
    public long timeSpan() {
        long duration = 0L;
        if (endTime == null) {
            duration = new Date().getTime() - startTime.getTime();
        } else {
            try {
                duration = super.timeSpan();
            } catch (IllegalStateException ex) {
                Log.e(Event.class.getName(),
                        "Could not obtain event duration. The event has invalid start/stop times.",
                        ex);
            }
        }
        return duration;
    }
}
