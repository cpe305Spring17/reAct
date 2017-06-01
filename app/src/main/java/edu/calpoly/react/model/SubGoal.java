package edu.calpoly.react.model;

import java.security.InvalidParameterException;

/**
 * Created by Nishanth on 4/27/17.
 */

public class SubGoal {

    private Long id;
    private Action action;
    private Long totalTime;
    private Integer totalEvents;

    /* CONSTRUCTORS */

    public SubGoal() {
        // empty constructor for serialization
    }

    public SubGoal(Action action, Long totalTime, Integer totalEvents) {
        setAction(action);
        setTotalTime(totalTime);
        setTotalEvents(totalEvents);
    }

    /* GETTERS/SETTERS */

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        if (action == null) {
            throw new NullPointerException("Action cannot be null for SubGoal");
        }
        this.action = action;
    }


    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        if (totalTime == null || totalTime <= 0) {
            throw new InvalidParameterException("SubGoal total time must be at least 0");
        }
        this.totalTime = totalTime;
    }

    public Integer getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(Integer totalEvents) {
        if (totalEvents == null || totalEvents <= 0) {
            throw new InvalidParameterException("SubGoal must have at least 1 event");
        }
        this.totalEvents = totalEvents;
    }

    //Need to have this actually implemented
    public Boolean isComplete() {
        return false;
    }
}
