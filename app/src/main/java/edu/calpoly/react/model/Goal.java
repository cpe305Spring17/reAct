package edu.calpoly.react.model;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.calpoly.react.exceptions.TimeWindowException;
import edu.calpoly.react.model.database.DBConnection;

/**
 * Created by Nishanth on 4/27/17.
 */

public class Goal extends TimeWindow {

    private Long id;
    private String name;
    private List<SubGoal> subGoals;
    private NotificationCompat.Builder notifBuilder;
    private NotificationManager notifManager;

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

    public void buildNotification(Context ctx, int iconId) {
        notifBuilder = new NotificationCompat.Builder(ctx);
        notifBuilder.setSmallIcon(iconId);
        notifManager =  (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
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

    public void setNotifTitle(String title) {
        if (title == null) {
            notifBuilder.setContentTitle("");
        }
        else {
            notifBuilder.setContentTitle(title);
        }
    }

    public void setNotifText(String text) {
        if (text == null) {
            notifBuilder.setContentText("");
        }
        else {
            notifBuilder.setContentText(text);
        }
    }

    public Boolean checkComplete() {
        for (int i = 0; i < subGoals.size(); i++) {
            SubGoal sub = subGoals.get(i);
            if (sub.isComplete())
                continue;

            if (notifBuilder != null && notifManager != null) {
                //need to add notification i
                if (sub.getAction() != null) {
                    setNotifTitle(sub.getAction().getName() + ": SubGoal has failed");
                    setNotifText(sub.getAction().getName() + " SubGoal has not completed in time!");
                }
                else {
                    setNotifTitle(name + "-Goal has failed");
                    setNotifText(name + "-Goal has not completed in time!");
                }
                notifManager.notify(12345, notifBuilder.build());
            }
            return false;
        }

        if (notifBuilder != null && notifManager != null) {
            //need to add notification id
            setNotifTitle(name + "-Goal has succeed!");
            setNotifText(name + "-Goal has been completed in time!");
            notifManager.notify(12345, notifBuilder.build());
        }
        return true;
    }

    public boolean isValid() {
        Set<Action> activities = new HashSet<>();
        for (SubGoal sg : this.subGoals) {
            if (activities.contains(sg.getAction()))
                return false;
            activities.add(sg.getAction());
        }

        return DBConnection.getInstance().getGoal(name) == null;
    }
}
