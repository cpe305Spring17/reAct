package edu.calpoly.react.model;

import org.junit.Test;

import java.util.Date;

import edu.calpoly.react.exceptions.TimeWindowException;

import static junit.framework.Assert.*;

/**
 * Created by Nishanth on 5/12/17.
 */

public class TestEvent {
    @Test
    public void TestEventEmptyConstructor() {
        Event e = new Event();
        assertNotNull(e);
    }

    @Test
    public void TestEventStartTimeConstructor() {
        Date start = new Date();
        Action a = new Action("action");

        Event e = new Event("event", a, start);
        assertNotNull(e);
    }

    @Test
    public void TestEventFullConstructor() {
        Date start = new Date();
        Date end = new Date();
        Action a = new Action("action");

        try {
            Event e = new Event("event", a, start, end);
            assertNotNull(e);
        } catch (TimeWindowException twe) {
            assertTrue(false);
        }
    }

    @Test
    public void TestEventGetSetId() {
        Event e = new Event();
        e.setId((long) 1);
        long id = e.getId();
        assertEquals((long) 1, id);
    }

    @Test
    public void TestEventGetSetName() {
        Event e = new Event();
        e.setName("event");
        String name = e.getName();
        assertEquals("event", name);
    }

    @Test
    public void TestEventGetSetActivity() {
        Event e = new Event();
        Action a = new Action("action");
        e.setAction(a);
        assertEquals(a, e.getAction());
    }

    @Test
    public void TestEventSetActivityException() {
        Event e = new Event();
        try {
            e.setAction(null);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertTrue(true);
        }
    }

    @Test
    public void TestEventStart() {
        Event e = new Event();
        Date now = new Date();
        e.start(now);
        assertEquals(now, e.getStartTime());
    }

    @Test
    public void TestEventStop() {
        Action a = new Action("action");
        Date start = new Date(2);
        Date end = new Date(5);
        Event e = new Event("event", a, start);
        e.stop(end);
        assertEquals(end, e.getEndTime());
    }

    @Test
    public void TestEventStopException() {
        Action a = new Action("action");
        Date start = new Date(5);
        Event e = new Event("event", a, start);
        Date end = new Date(2);
        try {
            e.stop(end);
            assertTrue(false);
        } catch (IllegalStateException ise) {
            assertTrue(true);
        }
    }

    @Test
    public void TestEventTimeSpan() {
        Date start = new Date(5);
        Date end = new Date(10);
        Action a = new Action("action");
        try {
            Event e = new Event("event", a, start, end);
            assertEquals(5, e.timeSpan());
        } catch (TimeWindowException twe) {
            assertTrue(false);
        }
    }
}

