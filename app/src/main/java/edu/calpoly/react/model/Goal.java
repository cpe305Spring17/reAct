package edu.calpoly.react.model;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

import edu.calpoly.react.exceptions.TimeWindowException;

/**
 * Created by Nishanth on 4/27/17.
 */

public class Goal extends TimeWindow {

    private Long id;
    private String name;
    private List<SubGoal> subGoals;

    /* CONSTRUCTORS */

    public Goal() {
        // empty constructor for serialization
    }

    public Goal(String name, List<SubGoal> subGoals, Date startTime, Date endTime)
            throws TimeWindowException
    {
        super(startTime, endTime);
        setName(name);
        setSubGoals(subGoals);
    }

    /* GETTERS/SETTERS */

    public void setId(Long id) { this.id = id; }

    public Long getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidParameterException("Goals must have names");
        }
        this.name = name;
    }

    public List<SubGoal> getSubGoals() {
        return subGoals;
    }

    public void setSubGoals(List<SubGoal> subGoals) {
        this.subGoals = subGoals;
    }

    public Boolean validate() {
        if (startTime != null && endTime != null) {
            long totalTime = this.timeSpan();

            for (SubGoal sg : this.subGoals) {
                totalTime -= sg.getTotalTime();
                if (totalTime < 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
