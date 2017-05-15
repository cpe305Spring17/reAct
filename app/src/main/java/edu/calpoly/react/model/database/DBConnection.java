package edu.calpoly.react.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.calpoly.react.model.Action;
import edu.calpoly.react.model.Category;
import edu.calpoly.react.model.Event;
import edu.calpoly.react.model.Goal;
import edu.calpoly.react.model.SubGoal;
import edu.calpoly.react.exceptions.TimeWindowException;


/**
 * Created by Nishanth on 4/27/17.
 */

public class DBConnection extends SQLiteOpenHelper {
    /* DATABASE STRINGS FOR TABLES, COLUMNS */

    /* Database Specs */
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "react";

    /* Database Relations */
    private static final String TABLE_CATEGORY = "Categories";
    private static final String TABLE_ACTIVITY = "Activities";
    private static final String TABLE_EVENT = "Events";
    private static final String TABLE_GOAL = "Goals";
    private static final String TABLE_SUBGOAL = "SubGoals";

    /* Columns - TABLE_CATEGORY */
    private static final String CATEGORY_ID = "id";
    private static final String CATEGORY_NAME = "name";

    /* Columns - TABLE_ACTIVITY */
    private static final String ACTIVITY_ID = "id";
    private static final String ACTIVITY_NAME = "name";
    private static final String ACTIVITY_CATEGORY = "category";

    /* Columns - TABLE_EVENT */
    private static final String EVENT_ID = "id";
    private static final String EVENT_NAME = "name";
    private static final String EVENT_ACTIVITY = "activity";
    private static final String EVENT_START = "start";
    private static final String EVENT_END = "end";

    /* Columns - TABLE_GOAL */
    private static final String GOAL_ID = "id";
    private static final String GOAL_NAME = "name";
    private static final String GOAL_START = "start";
    private static final String GOAL_END = "end";

    /* Columns - TABLE_SUBGOAL */
    private static final String SUBGOAL_ID = "id";
    private static final String SUBGOAL_ACTIVITY = "activity";
    private static final String SUBGOAL_TOTALTIME = "totalTime";
    private static final String SUBGOAL_NUMEVENTS = "numEvents";
    private static final String SUBGOAL_GOAL = "goal";

    /* SQLite syntax */
    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String INT_PK_AI = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
    private static final String TEXT = " Text, ";
    private static final String INT_REF = " INTEGER REFERENCES ";
    private static final String DROP_TABLE_IF_EXITS = "DROP TABLE IF EXISTS ";

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /* DATABASE METHODS */

    /* Constructor */
    private static DBConnection instance = null;
    private SQLiteDatabase rdb = this.getReadableDatabase();
    private SQLiteDatabase wdb = this.getWritableDatabase();

    private DBConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBConnection getInstance() {
        return instance;
    }

    public static synchronized DBConnection getInstance(Context context) {
        if (instance == null) {
            instance = new DBConnection(context);
        }
            return instance;
    }

    /* Create Database Tables */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL Statement - Create TABLE_CATEGORY
        String createTableCategory = CREATE_TABLE + TABLE_CATEGORY + "("
                + CATEGORY_ID + INT_PK_AI
                + CATEGORY_NAME + " TEXT"
                + ");";

        //SQL Statement - CREATE TABLE_ACTIVITY
        String createTableActivity = CREATE_TABLE + TABLE_ACTIVITY + "("
                + ACTIVITY_ID + INT_PK_AI
                + ACTIVITY_NAME + TEXT
                + ACTIVITY_CATEGORY + INT_REF + TABLE_CATEGORY + "(" + CATEGORY_ID + ") ON DELETE SET NULL"
                + ");";

        //SQL Statement - CREATE TABLE_EVENT
        String createTableEvent = CREATE_TABLE + TABLE_EVENT + "("
                + EVENT_ID + INT_PK_AI
                + EVENT_NAME + TEXT
                + EVENT_ACTIVITY + INT_REF + TABLE_ACTIVITY + "(" + ACTIVITY_ID + ") ON DELETE CASCADE, "
                + EVENT_START + " DATETIME, "
                + EVENT_END + " DATETIME"
                + ")";

        //SQL Statement - CREATE TABLE_GOAL
        String createTableGoal = CREATE_TABLE + TABLE_GOAL + "("
                + GOAL_ID + INT_PK_AI
                + GOAL_NAME + TEXT
                + GOAL_START + " DATETIME, "
                + GOAL_END + " DATETIME"
                + ");";

        //SQL Statement - CREATE TABLE_SUBGOAL
        String createTableSubGoal = CREATE_TABLE + TABLE_SUBGOAL + "("
                + SUBGOAL_ID + INT_PK_AI
                + SUBGOAL_ACTIVITY + INT_REF + TABLE_ACTIVITY + "(" + ACTIVITY_ID + ") ON DELETE SET NULL,"
                + SUBGOAL_TOTALTIME + " BIGINT, "
                + SUBGOAL_NUMEVENTS + " INTEGER, "
                + SUBGOAL_GOAL + INT_REF + TABLE_GOAL + "(" + GOAL_ID + ")"
                + ");";

        //Execute SQL Statements to create tables
        db.execSQL(createTableCategory);
        db.execSQL(createTableActivity);
        db.execSQL(createTableEvent);
        db.execSQL(createTableGoal);
        db.execSQL(createTableSubGoal);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_IF_EXITS + TABLE_SUBGOAL + ";");
        db.execSQL(DROP_TABLE_IF_EXITS + TABLE_GOAL + ";");
        db.execSQL(DROP_TABLE_IF_EXITS + TABLE_EVENT + ";");
        db.execSQL(DROP_TABLE_IF_EXITS + TABLE_ACTIVITY + ";");
        db.execSQL(DROP_TABLE_IF_EXITS + TABLE_CATEGORY + ";");

        onCreate(db);
    }

    @Override
    public void close() {
        super.close();
        rdb.close();
        wdb.close();
    }

    /* Delete all rows in all tables */
    public void deleteAllRows() {
        deleteGoal(null);
        deleteEvent(null);
        deleteActivity(null);
        deleteCategory(null);
    }


    /* CATEGORY TABLE METHODS */

    /* Add category to TABLE_CATEGORY */
    /* Returns id for new category, returns -1 on error */
    public long addCategory(Category category) {
        ContentValues values = new ContentValues();

        values.put(CATEGORY_NAME, category.getName());

        long categoryId = wdb.insert(TABLE_CATEGORY, null, values);
        category.setId(categoryId);
        return categoryId;
    }

    /* Update a given category in TABLE_CATEGORY */
    /* Returns number of rows affected by method, expected return should be 1 */
    public int updateCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, category.getName());

        String categoryId = Long.toString(category.getId());
        String whereClause = CATEGORY_ID + " = ?";
        String[] args = {categoryId};
        return wdb.update(TABLE_CATEGORY, values, whereClause, args);
    }

    /* Delete a given category in TABLE_CATEGORY */
    /* Pass null to delete all categories in TABLE_CATEGORY */
    public void deleteCategory(Category category) {
        if (category != null) {
            String categoryId = Long.toString(category.getId());
            String whereClause = CATEGORY_ID + " = ?";
            String[] args = {categoryId};
            wdb.delete(TABLE_CATEGORY, whereClause, args);
        } else {
            wdb.delete(TABLE_CATEGORY, null, null);
        }
    }

    /* Return id of category given category name */
    /* Returns -1 to indicate an error */
    public int getCategoryId(String categoryName) {
        int categoryId = -1;

        String[] columns = {CATEGORY_ID};
        String whereClause = CATEGORY_NAME + " = ?";
        String[] args = {categoryName};
        Cursor cursor = rdb.query(TABLE_CATEGORY, columns, whereClause, args, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            categoryId = cursor.getInt(0);
            cursor.close();
        }
        return categoryId;
    }

    /* Get all categories in TABLE_CATEGORY */
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();

        String[] columns = {CATEGORY_ID, CATEGORY_NAME};
        Cursor cursor = rdb.query(TABLE_CATEGORY, columns, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Category category = new Category(cursor.getString(1));
                category.setId(cursor.getLong(0));
                categories.add(category);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return categories;
    }

    /* Return Category with given id from TABLE_CATEGORY */
    /* Returns null on error */
    public Category getCategoryFromId(long categoryId) {
        Category category = null;
        String[] columns = {CATEGORY_ID, CATEGORY_NAME};
        String whereClause = CATEGORY_ID + " = ?";
        String[] args = {Long.toString(categoryId)};
        Cursor cursor = rdb.query(TABLE_CATEGORY, columns, whereClause, args, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            category = new Category(cursor.getString(1));
            category.setId(cursor.getLong(0));
            cursor.close();
        }
        return category;
    }

    /* Return Category given the category name */
    /* Returns null if category doesn't exist */
    /* Useful to check if category already exists in database */
    public Category getCategory(String categoryName) {
        Category category = null;
        String[] columns = {CATEGORY_ID, CATEGORY_NAME};
        String whereClause = CATEGORY_NAME + " = ?";
        String[] args = {categoryName};
        Cursor cursor = rdb.query(TABLE_CATEGORY, columns, whereClause, args, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            category = new Category(categoryName);
            category.setId(cursor.getLong(0));
            cursor.close();
        }
        return category;
    }


    /* ACTIVITY TABLE METHODS */

    /* Add action to TABLE_ACTIVITY */
    /* Returns id of new action, -1 on error */
    public long addActivity(Action action) {
        ContentValues values = new ContentValues();

        values.put(ACTIVITY_NAME, action.getName());
        if (action.getCategory() != null) {
            values.put(ACTIVITY_CATEGORY, action.getCategory().getId());
        }

        long activityId = wdb.insert(TABLE_ACTIVITY, null, values);
        action.setId(activityId);
        return activityId;
    }

    /* Update a given action in TABLE_ACTIVITY */
    /* Returns number of rows affected by method, expected return should be 1 */
    public int updateActivity(Action action) {
        ContentValues values = new ContentValues();

        values.put(ACTIVITY_NAME, action.getName());
        values.put(ACTIVITY_CATEGORY, action.getCategory().getId());

        String activityId = Long.toString(action.getId());
        String whereClause = ACTIVITY_ID + " = ?";
        String[] args = {activityId};
        return wdb.update(TABLE_ACTIVITY, values, whereClause, args);
    }

    /* Delete a given action in TABLE_ACTIVITY */
    public void deleteActivity(Action action) {
        if (action != null) {
            String activityId = Long.toString(action.getId());
            String whereClause = ACTIVITY_ID + " = ?";
            String[] args = {activityId};
            wdb.delete(TABLE_ACTIVITY, whereClause, args);
        } else {
            wdb.delete(TABLE_ACTIVITY, null, null);
        }
    }

    /* Return id of activity given activity name */
    /* Returns -1 to indicate an error */
    public int getActivityId(String activityName) {
        int activityId = -1;

        String[] columns = {ACTIVITY_ID};
        String whereClause = ACTIVITY_NAME + " = ?";
        String[] args = {activityName};
        Cursor cursor = rdb.query(TABLE_ACTIVITY, columns, whereClause, args, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            activityId = cursor.getInt(0);
            cursor.close();
        }
        return activityId;
    }

    public boolean getBooleanFromInt(int x) {
        return x != 0;
    }

    /* Get all activities in TABLE_ACTIVITY */
    /* Pass null for ALL activities for all categories */
    /* Returns ArrayList of events */
    public List<Action> getAllActivities(Category category) {
        List<Action> activities = new ArrayList<>();

        Cursor cursor;
        String[] columns = {ACTIVITY_ID, ACTIVITY_NAME, ACTIVITY_CATEGORY};
        if (category != null) {
            String whereClause = ACTIVITY_CATEGORY + " = ?";
            String[] args = {Long.toString(getCategoryId(category.getName()))};
            cursor = rdb.query(TABLE_ACTIVITY, columns, whereClause, args, null, null, null, null);
        } else {
            cursor = rdb.query(TABLE_ACTIVITY, columns, null, null, null, null, null, null);
        }

        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Long activityId = cursor.getLong(0);
                String activityName = cursor.getString(1);
                Category activityCategory = cursor.isNull(2) ? null : getCategoryFromId(cursor.getLong(2));
                Action action = new Action(activityName, activityCategory);
                action.setId(activityId);

                activities.add(action);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return activities;
    }

    /* Return Category with given id from TABLE_CATEGORY */
    /* Returns null on error */
    public Action getActivityFromId(long activityId) {
        Action action = null;
        String[] columns = {ACTIVITY_ID, ACTIVITY_NAME, ACTIVITY_CATEGORY};
        String whereClause = ACTIVITY_ID + " = ?";
        String[] args = {Long.toString(activityId)};
        Cursor cursor = rdb.query(TABLE_ACTIVITY, columns, whereClause, args, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String activityName = cursor.getString(1);
            Category category = getCategoryFromId(cursor.getLong(2));
            action = new Action(activityName, category);
            action.setId(activityId);
            cursor.close();
        }
        return action;
    }

    /* Return Action given the activity name */
    /* Returns null if activity doesn't exist */
    /* Useful to check if activity already exists in database */
    public Action getActivity(String activityName) {
        Action action = null;
        String[] columns = {ACTIVITY_ID, ACTIVITY_CATEGORY};
        String whereClause = ACTIVITY_NAME + " = ?";
        String[] args = {activityName};
        Cursor cursor = rdb.query(TABLE_ACTIVITY, columns, whereClause, args, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Long activityId = cursor.getLong(0);
            Category activityCategory = cursor.isNull(1) ? null : getCategoryFromId(cursor.getLong(1));
            action = new Action(activityName, activityCategory);
            action.setId(activityId);
            cursor.close();
        }
        return action;
    }


    /* EVENT TABLE METHODS */

    /* Add event to TABLE_EVENT */
    /* Returns id of new event, -1 or error */
    public long addEvent(Event event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        ContentValues values = new ContentValues();

        values.put(EVENT_NAME, event.getName());
        values.put(EVENT_ACTIVITY, event.getAction().getId());
        values.put(EVENT_START, dateFormat.format(event.getStartTime()));
        if (event.getEndTime() != null) {
            values.put(EVENT_END, dateFormat.format(event.getEndTime()));
        }

        long eventId = wdb.insert(TABLE_EVENT, null, values);
        event.setId(eventId);
        return eventId;
    }

    /* Update a given event in TABLE_EVENT */
    /* Returns number of rows affected by method, expected return should be 1 */
    public int updateEvent(Event event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        ContentValues values = new ContentValues();

        values.put(EVENT_NAME, event.getName());
        values.put(EVENT_ACTIVITY, event.getAction().getId());
        values.put(EVENT_START, dateFormat.format(event.getStartTime()));
        values.put(EVENT_END, dateFormat.format(event.getEndTime()));

        String eventId = Long.toString(event.getId());
        String whereClause = EVENT_ID + " = ?";
        String[] args = {eventId};
        return wdb.update(TABLE_EVENT, values, whereClause, args);
    }

    /* Delete a given event in TABLE_EVENT */
    /* Pass null to delete all events in TABLE_EVENT */
    public void deleteEvent(Event event) {
        if (event != null) {
            String eventId = Long.toString(event.getId());
            String whereClause = EVENT_ID + " = ?";
            String[] args = {eventId};
            wdb.delete(TABLE_EVENT, whereClause, args);
        } else {
            wdb.delete(TABLE_EVENT, null, null);
        }
    }


    /* Return Event with given id from TABLE_EVENT */
    /* Returns null on error */
    public Event getEventFromId(long eventId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);

        Event event = null;
        String[] columns = {EVENT_ID, EVENT_NAME, EVENT_ACTIVITY, EVENT_START, EVENT_END};
        String whereClause = EVENT_ID + " = ?";
        String[] args = {Long.toString(eventId)};
        Cursor cursor = rdb.query(TABLE_EVENT, columns, whereClause, args, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String eventName = cursor.getString(1);
            Action eventAction = getActivityFromId(cursor.getLong(2));

            Date startTime = null;
            Date endTime = null;

            try {
                startTime = dateFormat.parse(cursor.getString(3));
                endTime = cursor.isNull(4) ? null : dateFormat.parse(cursor.getString(4));
            } catch (ParseException e) {
                Log.e(DBConnection.class.getName(),
                        "Problem with parsing dates from database when getting event from id.", e);
            }
            try {
                event = new Event(eventName, eventAction, startTime, endTime);
                event.setId(eventId);
            } catch (TimeWindowException e) {
                Log.e(DBConnection.class.getName(),
                        "Bad start/end time for event when getting event from id.", e);
            }
            cursor.close();
        }
        return event;
    }

    private List<Event> collectEvents(Cursor cursor) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);

        List<Event> events = new ArrayList<>();

        if(cursor != null && cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                Long eventId = cursor.getLong(0);
                String eventName = cursor.getString(1);
                Action eventAction = getActivityFromId(cursor.getLong(2));

                Date startTime = null;
                Date endTime = null;
                Event event;

                try {
                    startTime = dateFormat.parse(cursor.getString(3));
                    endTime = cursor.isNull(4) ? null : dateFormat.parse(cursor.getString(4));
                } catch (ParseException pe) {
                    Log.e(DBConnection.class.getName(), "Problem with parsing dates from database", pe);
                }

                try {
                    event = new Event(eventName, eventAction, startTime, endTime);
                    event.setId(eventId);
                    events.add(event);
                } catch (TimeWindowException twe) {
                    Log.e(DBConnection.class.getName(), "bad start/end time for event.", twe);
                }

                cursor.moveToNext();
            }
        }
        return events;
    }

    /* Return Events that are active (start time but no end time) given action */
    /* Pass in null for all active events */
    /* Returns null on error */
    public List<Event> getActiveEventFromActvity(Action action) {
        Cursor cursor;

        String[] columns = {EVENT_ID, EVENT_NAME, EVENT_ACTIVITY, EVENT_START, EVENT_END};
        if (action != null) {
            String whereClause = EVENT_ACTIVITY + " = ? AND " + EVENT_END + " IS NULL";
            String[] args = {Long.toString(getActivityId(action.getName()))};
            cursor = rdb.query(TABLE_EVENT, columns, whereClause, args, null, null, null, null);
        } else {
            String whereClause = EVENT_END + " IS NULL";
            cursor = rdb.query(TABLE_EVENT, columns, whereClause, null, null, null, null, null);
        }
        List<Event> events = collectEvents(cursor);
        if (cursor != null)
            cursor.close();
        return events;
    }

    /* Return List of events that are inactive (start time and end time) given action */
    /* Returns null on error */
    public List<Event> getInActiveEventsFromActvity(Action action) {
        Cursor cursor;

        String[] columns = {EVENT_ID, EVENT_NAME, EVENT_ACTIVITY, EVENT_START, EVENT_END};
        if (action == null ) {
            String whereClause = EVENT_END + " IS NOT NULL";
            cursor = rdb.query(TABLE_EVENT, columns, whereClause, null, null, null, null, null);
        } else {
            String whereClause = EVENT_ACTIVITY + " = ? AND " + EVENT_END + " IS NOT NULL";
            String[] args = {Long.toString(getActivityId(action.getName()))};
            cursor = rdb.query(TABLE_EVENT, columns, whereClause, args, null, null, null, null);
        }
        List<Event> events = collectEvents(cursor);
        if(cursor != null)
            cursor.close();
        return events;
    }

    /* Get all events in TABLE_EVENT for specific action */
    /* Pass null for ALL events for all activities */
    /* Returns ArrayList of events */
    public List<Event> getAllEvents(Action action) {
        Cursor cursor;

        String[] columns = {EVENT_ID, EVENT_NAME, EVENT_ACTIVITY, EVENT_START, EVENT_END};
        if (action != null) {
            String whereClause = EVENT_ACTIVITY + " = ?";
            String[] args = {Long.toString(getActivityId(action.getName()))};
            cursor = rdb.query(TABLE_EVENT, columns, whereClause, args, null, null, null, null);
        } else {
            cursor = rdb.query(TABLE_EVENT, columns, null, null, null, null, null, null);
        }

        List<Event> events = collectEvents(cursor);
        if (cursor != null) {
            cursor.close();
        }
        return events;
    }


    /* GOAL TABLE METHODS */

    /* Add goal to TABLE_GOAL */
    /* Return id of new goal, -1 on error */
    public long addGoal(Goal goal) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        ContentValues values = new ContentValues();

        values.put(GOAL_NAME, goal.getName());
        values.put(GOAL_START, dateFormat.format(goal.getStartTime()));
        values.put(GOAL_END, dateFormat.format(goal.getEndTime()));

        long goalId = wdb.insert(TABLE_GOAL, null, values);

        for (SubGoal subGoal : goal.getSubGoals()) {
            addSubGoal(goalId, subGoal);
        }
        goal.setId(goalId);
        return goalId;
    }

    /* Update a given goal in TABLE_GOAL */
    public void updateGoal(Goal goal) {
        deleteGoal(goal);
        addGoal(goal);
    }

    /* Delete a given goal in TABLE_GOAL */
    /* Pass null to delete all goals in TABLE_GOAL */
    public void deleteGoal(Goal goal) {
        if (goal != null) {
            String goalId = Long.toString(goal.getId());
            String whereClause = GOAL_ID + " = ?";
            String[] args = {goalId};
            wdb.delete(TABLE_GOAL, whereClause, args);
            deleteSubGoals(goalId, wdb);
        } else {
            wdb.delete(TABLE_GOAL, null, null);
            deleteSubGoals(Integer.toString(-1), wdb);
        }
    }

    /* Return Goal given the goal name */
    /* Returns null if goal doesn't exist */
    /* Useful to check if goal already exists in database */
    public Goal getGoal(String goalName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);

        Goal goal = null;
        String[] columns = {GOAL_ID, GOAL_NAME, GOAL_START, GOAL_END};
        String whereClause = GOAL_NAME + " = ?";
        String[] args = {goalName};
        Cursor cursor = rdb.query(TABLE_GOAL, columns, whereClause, args, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Long goalId = cursor.getLong(0);
            List<SubGoal> subGoals = getAllSubGoals(goalId);

            try {
                Date start = dateFormat.parse(cursor.getString(2));
                Date end = dateFormat.parse(cursor.getString(3));
                goal = new Goal(goalName, subGoals, start, end);
                goal.setId(goalId);
            } catch (ParseException pe) {
                Log.e(DBConnection.class.getName(), "Could not get start/end times", pe);
            } catch (TimeWindowException twe) {
                Log.e(DBConnection.class.getName(), "Bad start/end times for goal", twe);
            }
            cursor.close();
        }
        return goal;
    }

    /* Get all goals in TABLE_GOAL */
    /* Returns ArrayList of goals */
    public List<Goal> getAllGoals() {
        List<Goal> goals = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);

        String[] columns = {GOAL_ID, GOAL_NAME, GOAL_START, GOAL_END};
        Cursor cursor = rdb.query(TABLE_GOAL, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Long goalId = cursor.getLong(0);
                List<SubGoal> subGoals =  getAllSubGoals(goalId);
                String goalName = cursor.getString(1);

                Date goalStart = null;
                Date goalEnd = null;
                Goal goal;

                try {
                    goalStart = dateFormat.parse(cursor.getString(2));
                    goalEnd = dateFormat.parse(cursor.getString(3));
                } catch (ParseException e) {
                    Log.e(DBConnection.class.getName(), "Problem with parsing dates from database.", e);
                }
                try {
                    goal = new Goal(goalName, subGoals, goalStart, goalEnd);
                    goal.setId(goalId);
                    goals.add(goal);
                } catch (TimeWindowException e) {
                    Log.e(DBConnection.class.getName(), "bad start/end time for goal." , e);
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return goals;
    }


    /* SUBGOAL TABLE METHODS */

    /* Add subGoal to TABLE_SUBGOAL */
    private long addSubGoal(Long goalId, SubGoal subGoal) {
        ContentValues values = new ContentValues();

        values.put(SUBGOAL_ACTIVITY, getActivityId(subGoal.getAction().getName()));
        values.put(SUBGOAL_TOTALTIME, subGoal.getTotalTime());
        values.put(SUBGOAL_NUMEVENTS, subGoal.getTotalEvents());
        values.put(SUBGOAL_GOAL, goalId);

        long subGoalId = wdb.insert(TABLE_SUBGOAL, null, values);
        subGoal.setId(subGoalId);
        return subGoalId;
    }

    /* Delete all subgoals derived from a goal in TABLE_SUBGOALS */
    private void deleteSubGoals(String goalId, SQLiteDatabase db) {
        if (!"-1".equals(goalId)) {
            String whereClause = SUBGOAL_GOAL + " = ?";
            String[] args = {goalId};
            db.delete(TABLE_SUBGOAL, whereClause, args);
        } else {
            db.delete(TABLE_SUBGOAL, null, null);
        }
    }

    /* Get all subgoals given a goalId in TABLE_SUBGOAL */
    private List<SubGoal> getAllSubGoals(long goalId) {
        List<SubGoal> subGoals = new ArrayList<>();

        String[] columns = {SUBGOAL_ID, SUBGOAL_ACTIVITY, SUBGOAL_TOTALTIME, SUBGOAL_NUMEVENTS};
        String whereClause = SUBGOAL_GOAL + " = ?";
        String[] args = {Long.toString(goalId)};
        Cursor cursor = rdb.query(TABLE_SUBGOAL, columns, whereClause, args, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Action action = getActivityFromId(cursor.getLong(1));
                Long totalTime = cursor.getLong(2);
                int numEvents = cursor.getInt(3);
                SubGoal subGoal = new SubGoal(action, totalTime, numEvents);
                subGoal.setId(cursor.getLong(0));

                subGoals.add(subGoal);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return subGoals;
    }
}