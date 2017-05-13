package edu.calpoly.react.model;

import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.calpoly.react.exceptions.TimeWindowException;

import static junit.framework.Assert.*;

/**
 * Created by Nishanth on 5/13/17.
 */

public class TestGoal {
    @Test
    public void TestGoalEmptyConstructor() {
        Goal g = new Goal();
        assertNotNull(g);
    }

    @Test
    public void TestGoalGeneralConstructor() {
        SubGoal s1 = new SubGoal();
        SubGoal s2 = new SubGoal();
        List<SubGoal> sgs = new ArrayList<>();

        sgs.add(s1);
        sgs.add(s2);

        Date start = new Date(5);
        Date end = new Date(10);

        try {
            Goal g = new Goal("goal", sgs, start, end);
            assertNotNull(g);
        } catch (TimeWindowException twe) {
            assertTrue(false);
        }
    }

    @Test
    public void TestGoalGetSetId() {
        Goal g = new Goal();
        g.setId((long) 1);
        Long id = g.getId();
        assertEquals(Long.valueOf(1), id);
    }

    @Test
    public void TestGoalGetSetName() {
        Goal g = new Goal();
        g.setName("goal");
        assertEquals("goal", g.getName());
    }

    @Test
    public void TestGoalSetNameException1() {
        Goal g = new Goal();
        try {
            g.setName(null);
            assertTrue(false);
        } catch (InvalidParameterException ipe) {
            assertTrue(true);
        }
    }

    @Test
    public void TestGoalSetNameException2() {
        Goal g = new Goal();
        try {
            g.setName("");
            assertTrue(false);
        } catch (InvalidParameterException ipe) {
            assertTrue(true);
        }
    }

    @Test
    public void TestGoalGetSetSubGoal() {
        SubGoal s1 = new SubGoal();
        SubGoal s2 = new SubGoal();
        List<SubGoal> sgs = new ArrayList<>();

        sgs.add(s1);
        sgs.add(s2);

        Goal g = new Goal();
        g.setSubGoals(sgs);
        assertEquals(sgs, g.getSubGoals());
    }

    @Test
    public void TestGoalValidateTrue() {
        SubGoal s1 = new SubGoal();
        SubGoal s2 = new SubGoal();
        s1.setTotalTime((long) 10);
        s2.setTotalTime((long) 10);

        List<SubGoal> sgs = new ArrayList<>();
        sgs.add(s1);
        sgs.add(s2);

        Date start = new Date(2);
        Date end = new Date(200);
        try {
            Goal g = new Goal("goal", sgs, start, end);
            assertTrue(g.validate());
        } catch(TimeWindowException twe) {
            assertTrue(false);
        }
    }

    @Test
    public void TestGoalValidateFalse() {
        SubGoal s1 = new SubGoal();
        SubGoal s2 = new SubGoal();
        s1.setTotalTime((long) 10);
        s2.setTotalTime((long) 10);

        List<SubGoal> sgs = new ArrayList<>();
        sgs.add(s1);
        sgs.add(s2);

        Date start = new Date(2);
        Date end = new Date(20);
        try {
            Goal g = new Goal("goal", sgs, start, end);
            assertFalse(g.validate());
        } catch(TimeWindowException twe) {
            assertTrue(false);
        }
    }
}
