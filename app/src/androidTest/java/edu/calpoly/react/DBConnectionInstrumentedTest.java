package edu.calpoly.react;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import edu.calpoly.react.model.*;

import static junit.framework.Assert.*;

/**
 * Created by Nishanth on 5/14/17.
 */

@RunWith(AndroidJUnit4.class)

@LargeTest
public class DBConnectionInstrumentedTest extends IntegratedInstrumentedTest {

    private static final String TAG = "DBConnection";
    private static final String ERROR_MESSAGE = "Error in DBConnectionInstrumentedTest";

    @Override
    protected void printErrorMessage(Exception e) {
        Log.d(TAG, ERROR_MESSAGE, e);
    }

    @Test
    public void testDBSetUp() {
        SQLiteDatabase sq = db.getWritableDatabase();
        assertTrue(sq.isOpen());
    }

    /* ######## CATEGORY ######## */

    // NOTE the following tests are identical. Can't get something out before putting something in.
    @Test
    public void testDBAddCategory() {
        Category c = new Category("Test");
        db.addCategory(c);

        Category dbGet = db.getCategory(c.getName());
        assertEquals(c.getName(), dbGet.getName());
    }

    @Test
    public void testDBGetAllCategories() {
        Category c1 = new Category("Test");
        Category c2 = new Category("test");

        db.addCategory(c1);
        db.addCategory(c2);

        List<Category> cs = db.getAllCategories();

        assertEquals(2, cs.size());
    }

    @Test
    public void testDBDeleteCategory() {
        Category c = new Category("Test");
        db.addCategory(c);

        db.deleteCategory(c);
        assertNull(db.getCategory(c.getName()));
    }

    @Test
    public void testDBDeleteAllCategories() {
        Category c1 = new Category("A");
        Category c2 = new Category("B");
        db.addCategory(c1);
        db.addCategory(c2);

        db.deleteCategory(null); // Should delete all categories if passed null
        assertEquals(0, db.getAllCategories().size());
    }

    @Test
    public void testDBGetCategoryID() {
        Category c = new Category("Test");

        db.addCategory(c);
        long id = c.getId();

        long dbId = db.getCategoryId(c.getName());

        assertEquals(id, dbId);
    }

    /* ######## ACTIVITY ######## */

    @Test
    public void testDBAddActivity() {
        Action a = new Action("Test", null);
        db.addActivity(a);

        Action dbGet = db.getActivity("Test");
        assertEquals(a.getName(), dbGet.getName());
    }

    @Test
    public void testDBGetActivity() {
        Category c = new Category("Test");
        db.addCategory(c);

        Action a = new Action("Test", c);
        db.addActivity(a);

        Action dbGet = db.getActivity(a.getName());
        assertEquals(a.getName(), dbGet.getName());
    }

    @Test
    public void testDBUpdateActivity() {
        Category c1 = new Category("Test");
        db.addCategory(c1);

        Category c2 = new Category("test");
        db.addCategory(c2);

        Action a = new Action("Test", c1);
        db.addActivity(a);

        a.setCategory(c2);
        assertEquals(1, db.updateActivity(a)); // Number of rows affected should be 1
    }


    @Test
    public void testDBDeleteActivity() {
        Category c = new Category("Test");
        db.addCategory(c);

        Action a = new Action("Test", c);
        db.addActivity(a);
        db.deleteActivity(a);
        assertNull(db.getActivity(a.getName()));
    }

    @Test
    public void testDBAddEvent() {
        Action a = new Action("Test", null);
        db.addActivity(a);

        Event e = new Event("Test", a, s);

        long id = db.addEvent(e);

        Event dbGet = db.getEventFromId(id);


        assertEquals(e.getName(), dbGet.getName());
    }

    @Test
    public void testDBGetAllEvents() {
        Action a1 = new Action("Test", null);

        db.addActivity(a1);

        Event e1 = new Event("Test", a1, s);
        Event e2 = new Event ("test", a1, s);

        db.addEvent(e1);
        db.addEvent(e2);


        List<Event> es = db.getAllEvents(a1);

        assertEquals(2, es.size());
    }

    @Test
    public void testDBAddGoal() {

        boolean passed = false;

        try{
            List<SubGoal> subgoals = new ArrayList<>();
            Goal g = new Goal("Test", subgoals, s, f);
            db.addGoal(g);

            List<Goal> gs = db.getAllGoals();

            passed = 1 == gs.size(); // Check if the size we got out was 1

        }
        catch (Exception e) {
            printErrorMessage(e);
        }

        assertEquals(true, passed);
    }

    @Test
    public void testDBGetAllGoals() {
        db.deleteAllRows();

        try{
            List<SubGoal> sublist = new ArrayList<>();

            Goal g1 = new Goal("Test", sublist, s, f);
            Goal g2 = new Goal("test", sublist, s, f);
            db.addGoal(g1);
            db.addGoal(g2);

            List<Goal> gs = db.getAllGoals();
            assertEquals(2, gs.size());
        }
        catch (Exception e) {
            printErrorMessage(e);
            assertTrue(false);
        }
    }

    @Test
    public void testDBGetAllActivities() {
        db.deleteAllRows();
        Category c = new Category("Test");

        db.addCategory(c);

        Action a1 = new Action("Test", c);
        Action a2 = new Action("test", c);

        db.addActivity(a1);
        db.addActivity(a2);

        List<Action> as = db.getAllActivities(c);

        assertEquals(2, as.size());
    }
}
