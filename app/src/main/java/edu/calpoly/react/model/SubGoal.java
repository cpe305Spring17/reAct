package edu.calpoly.react.model;

import java.security.InvalidParameterException;

/**
 * Created by Nishanth on 4/27/17.
 */

public class SubGoal {

    private Long id;
    private Activity activity;
    private Long totalTime;
    private Integer totalEvents;

    /* CONSTRUCTORS */

    public SubGoal() {
        // empty constructor for serialization
    }

    public SubGoal(Activity activity, Long totalTime, Integer totalEvents) {
        setActivity(activity);
        setTotalTime(totalTime);
        setTotalEvents(totalEvents);
    }

    /* GETTERS/SETTERS */

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        if (activity == null)
            throw new NullPointerException("Activity cannot be null for SubGoal");
        this.activity = activity;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        if (activity != null && totalTime != null)
            throw new IllegalStateException("Instant activities must have a total time for SubGoal");
        else if (totalTime == null && totalTime <= 0)
            throw new InvalidParameterException("SubGoal total time must be at least 0");

        this.totalTime = totalTime;
    }

    public Integer getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(Integer totalEvents) {
        if (totalEvents == null && totalEvents <= 0)
            throw new InvalidParameterException("SubGoal must have at least 1 event");

        this.totalEvents = totalEvents;
    }

    /* METHODS */

    public Boolean validate() {
        return activity == null || totalTime == null;
    }
}
