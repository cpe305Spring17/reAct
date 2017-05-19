package edu.calpoly.react.model.database;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.calpoly.react.exceptions.TimeWindowException;
import edu.calpoly.react.model.Action;
import edu.calpoly.react.model.Category;
import edu.calpoly.react.model.Event;
import edu.calpoly.react.model.Goal;
import edu.calpoly.react.model.SubGoal;

/**
 * Created by Nishanth on 4/27/17.
 */

public class DBSeed {

    private static final String C_EXERCISE = "Exercise";
    private static final String C_SCHOOL = "School";
    private static final String C_VIDEO_GAMES = "Video Games";
    private static final String C_LIFESTYLE = "Lifestyle";
    private static final String C_ENTERTAINMENT = "Entertainment";

    private static final String A_RUNNING = "Running";
    private static final String A_GYM = "Getting swole";

    private static final String A_WATCHING_TV = "Watching Silicon Valley";
    private static final String A_WATCHING_MOVIES = "Watching Movies";
    private static final String A_PLAYING_GUITAR = "Playing Guitar";

    private static final String A_COOKING = "Cooking";
    private static final String A_CLEANING = "Cleaning";
    private static final String A_DOING_LAUNDRY = "Doing Laundry";
    private static final String A_CRYING = "Crying";

    private static final String A_STUDYING_357 = "Studying for 357";
    private static final String A_STUDYING_305 = "Debugging my 305 project";

    private static final String A_CORN_HUSKING = "Corn Husking";
    private static final String A_UNDERWATER_BASKET_WEAVING = "Underwater Basket Weaving";
    private static final String A_FEEDING_MY_PET = "Feeding my pet ostrich";

    private DBSeed() {}

    public static void seed(Long randSeed) {
        DBConnection db = DBConnection.getInstance();
        db.deleteAllRows();

        Random rand = new Random(randSeed == null ? new Date().getTime() : randSeed);

        Map<String, List<String>> cToA = new HashMap<>();
        cToA.put(C_EXERCISE, Arrays.asList(A_RUNNING, A_GYM));
        cToA.put(C_LIFESTYLE, Arrays.asList(A_WATCHING_TV, A_WATCHING_MOVIES, A_PLAYING_GUITAR));
        cToA.put(C_VIDEO_GAMES, Arrays.asList(A_COOKING, A_CLEANING, A_DOING_LAUNDRY, A_CRYING));
        cToA.put(C_SCHOOL, Arrays.asList(A_STUDYING_357, A_STUDYING_305, A_FEEDING_MY_PET));
        cToA.put(C_ENTERTAINMENT, Arrays.asList(A_CORN_HUSKING, A_UNDERWATER_BASKET_WEAVING));

        for (Map.Entry<String, List<String>> entry : cToA.entrySet()) {
            Category category = new Category(entry.getKey());
            db.addCategory(category);
            for (String a_name : cToA.get(entry.getKey())) {
                Action action = new Action(a_name, category);
                db.addActivity(action);

                for (int i = 0; i < rand.nextInt(10) + 1; ++i) {
                    Calendar start = new GregorianCalendar();
                    start.set(
                            rand.nextInt(17) + 2000,
                            rand.nextInt(12),
                            rand.nextInt(27) + 1,
                            rand.nextInt(24),
                            rand.nextInt(60),
                            rand.nextInt(60)
                    );
                    Calendar end = new GregorianCalendar();
                    end.setTimeInMillis(start.getTimeInMillis() + (long)rand.nextInt(86400000) + 300000L);

                    addEvent(db, String.format("%s %d", a_name, i), action, start.getTime(), end.getTime());
                }
            }
        }

        List<SubGoal> subGoals = new ArrayList<>();
        subGoals.add(new SubGoal(db.getActivity(A_CRYING), 3600000L, 1));
        subGoals.add(new SubGoal(db.getActivity(A_CORN_HUSKING), 3600000L, 1));
        Calendar start = new GregorianCalendar();
        start.set(2017, 0, 1);
        Calendar end = new GregorianCalendar();
        end.set(2018, 0, 1);
        try {
            db.addGoal(new Goal("2017", subGoals, start.getTime(), end.getTime()));
        }
        catch (TimeWindowException twe) {
            Log.e(DBSeed.class.getName(), "Could not seed goal.", twe);
        }
    }

    private static void addEvent(DBConnection db, String name, Action action, Date start, Date end) {
        try {
            Event event = new Event(name, action, start, end);
            db.addEvent(event);
        } catch (TimeWindowException twe) {
            Log.e(DBSeed.class.getName(), "Could not add event", twe);
        }
    }

}
