package edu.calpoly.react.model;

import org.junit.Test;

import java.security.InvalidParameterException;

import static junit.framework.Assert.*;

/**
 * Created by Nishanth on 5/13/17.
 */

public class TestSubGoal {
    @Test
    public void TestSubGoalEmptyConstructor() {
        SubGoal s = new SubGoal();
        assertNotNull(s);
    }

    @Test
    public void TestSubGoalGeneralConstructor() {
        Action a = new Action("action");
        long time = 5;
        Integer events = 5;
        SubGoal s = new SubGoal(a, time, events);
        assertNotNull(s);
    }

    @Test
    public void TestSubGoalGetSetId() {
        SubGoal s = new SubGoal();
        s.setId((long) 1);
        long id = s.getId();
        assertEquals(1, id);
    }

    @Test
    public void TestSubGoalGetSetAction() {
        SubGoal s = new SubGoal();
        Action a = new Action("action");
        s.setAction(a);
        assertEquals(a, s.getAction());
    }

    @Test
    public void TestSubGoalGetSetTotalTime() {
        SubGoal s = new SubGoal();
        try {
            long time = 100;
            s.setTotalTime(time);
            assertEquals(Long.valueOf(time), s.getTotalTime());
        } catch (InvalidParameterException ipe) {
            assertTrue(false);
        }
    }

    @Test
    public void TestSubGoalSetTotalTimeException1() {
        SubGoal s = new SubGoal();
        try {
            s.setTotalTime(null);
            assertTrue(false);
        } catch (InvalidParameterException ipe) {
            assertTrue(true);
        }
    }

    @Test
    public void TestSubGoalSetTotalTimeException2() {
        SubGoal s = new SubGoal();
        try {
            s.setTotalTime((long) 0);
            assertTrue(false);
        } catch (InvalidParameterException ipe) {
            assertTrue(true);
        }
    }

    @Test
    public void TestSubGoalGetSetTotalEvents() {
        SubGoal s = new SubGoal();
        try {
            Integer events = 100;
            s.setTotalEvents(events);
            assertEquals(events, s.getTotalEvents());
        } catch (InvalidParameterException ipe) {
            assertTrue(false);
        }
    }

    @Test
    public void TestSubGoalSetTotalEventsException1() {
        SubGoal s = new SubGoal();
        try {
            s.setTotalEvents(null);
            assertTrue(false);
        } catch (InvalidParameterException ipe) {
            assertTrue(true);
        }
    }

    @Test
    public void TestSubGoalSetTotalEventsException2() {
        SubGoal s = new SubGoal();
        try {
            s.setTotalEvents(0);
            assertTrue(false);
        } catch (InvalidParameterException ipe) {
            assertTrue(true);
        }
    }
}
