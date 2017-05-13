package edu.calpoly.react.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.calpoly.react.exceptions.TimeWindowException;

import static junit.framework.Assert.*;

/**
 * Created by Nishanth on 5/12/17.
 */

public class TestTimeWindow {
    @Test
    public void TestTimeWindowEmptyConstructor() {
        TimeWindow t = new TimeWindow();
        assertNotNull(t);
    }

    @Test
    public void TestTimeWindowGeneralConstructor() {
        Date start = new Date();
        Date end = new Date();
        try {
            TimeWindow t = new TimeWindow(start, end);
            assertNotNull(t);
        } catch (TimeWindowException twe) {
            assertTrue(false);
        }
    }

    @Test
    public void TestTimeWindowGetSetStartTime() {
        TimeWindow t = new TimeWindow();
        Date d = new Date();
        try {
            t.setStartTime(d);
        } catch (TimeWindowException twe) {
            assertTrue(false);
        }
        assertEquals(d, t.getStartTime());
    }

    @Test
    public void TestTimeWindowSetStartTimeException1() {
        Date start = new Date(5);
        Date end = new Date(10);
        try {
            TimeWindow t = new TimeWindow(start, end);
            try {
                Date newStart = new Date(222);
                t.setStartTime(newStart);
                assertTrue(false);
            } catch (TimeWindowException twe2) {
                assertTrue(true);
            }
        } catch (TimeWindowException twe) {
            assertTrue(false);
        }
    }

    @Test
    public void TestTimeWindowSetStartTimeException2() {
        TimeWindow t = new TimeWindow();
        try {
            t.setStartTime(null);
            assertTrue(false);
        } catch (TimeWindowException twe) {
            assertTrue(true);
        }
    }

    @Test
    public void TestTimeWindowGetSetEndTime() {
        TimeWindow t = new TimeWindow();
        Date d = new Date();
        try {
            t.setEndTime(d);
        } catch (TimeWindowException twe) {
            assertTrue(false);
        }
        assertEquals(d, t.getEndTime());
    }

    @Test
    public void TestTimeWindowSetEndTimeException1() {
        Date start = new Date(5);
        Date end = new Date(10);
        try {
            TimeWindow t = new TimeWindow(start, end);
            try {
                Date newStart = new Date(2);
                t.setEndTime(newStart);
                assertTrue(false);
            } catch (TimeWindowException twe2) {
                assertTrue(true);
            }
        } catch (TimeWindowException twe) {
            assertTrue(false);
        }
    }

    @Test
    public void TestTimeWindowTimeSpan() {
        Date start = new Date(5);
        Date end = new Date(10);
        try {
            TimeWindow t = new TimeWindow(start, end);
            long span = 5;
            assertEquals(span, t.timeSpan());
        } catch(TimeWindowException twe) {
            assertTrue(false);
        }
    }

    @Test
    public void TestTimeWindowEncompassesTrue() {
        Date start1 = new Date(1);
        Date end1 = new Date(100);
        Date start2 = new Date(10);
        Date end2 = new Date(20);
        try {
            TimeWindow t1 = new TimeWindow(start1, end1);
            TimeWindow t2 = new TimeWindow(start2, end2);
            assertTrue(t1.encompasses(t2));
        } catch (TimeWindowException twe) {
            assertTrue(false);
        }
    }

    @Test
    public void TestTimeWindowEncompassesFalse() {
        Date start1 = new Date(1);
        Date end1 = new Date(100);
        Date start2 = new Date(10);
        Date end2 = new Date(20);
        try {
            TimeWindow t2 = new TimeWindow(start1, end1);
            TimeWindow t1 = new TimeWindow(start2, end2);
            assertFalse(t1.encompasses(t2));
        } catch (TimeWindowException twe) {
            assertTrue(false);
        }
    }

    @Test
    public void TestTimeWindowEncompassesAllTrue() {
        Date start1 = new Date(1);
        Date end1 = new Date(100);
        Date start2 = new Date(10);
        Date end2 = new Date(20);
        Date start3 = new Date(14);
        Date end3 = new Date(24);
        List<TimeWindow> tws = new ArrayList<>();
        try {
            TimeWindow t1 = new TimeWindow(start1, end1);
            TimeWindow t2 = new TimeWindow(start2, end2);
            TimeWindow t3 = new TimeWindow(start3, end3);

            tws.add(t2);
            tws.add(t3);

            assertTrue(t1.encompassesAll(tws));
        } catch (TimeWindowException twe) {
            assertTrue(false);
        }
    }

    @Test
    public void TestTimeWindowEncompassesAllFalse() {
        Date start1 = new Date(1);
        Date end1 = new Date(100);
        Date start2 = new Date(10);
        Date end2 = new Date(20);
        Date start3 = new Date(14);
        Date end3 = new Date(24);
        List<TimeWindow> tws = new ArrayList<>();
        try {
            TimeWindow t1 = new TimeWindow(start1, end1);
            TimeWindow t2 = new TimeWindow(start2, end2);
            TimeWindow t3 = new TimeWindow(start3, end3);

            tws.add(t1);
            tws.add(t3);

            assertFalse(t2.encompassesAll(tws));
        } catch (TimeWindowException twe) {
            assertTrue(false);
        }
    }

    @Test
    public void TestTimeWindowEncompassTrue() {
        Date start1 = new Date(12);
        Date end1 = new Date(100);
        Date start2 = new Date(10);
        Date end2 = new Date(20);
        Date start3 = new Date(14);
        Date end3 = new Date(240);
        List<TimeWindow> tws = new ArrayList<>();
        try {
            TimeWindow t1 = new TimeWindow(start1, end1);
            TimeWindow t2 = new TimeWindow(start2, end2);
            TimeWindow t3 = new TimeWindow(start3, end3);

            tws.add(t1);
            tws.add(t2);
            tws.add(t3);

            TimeWindow timeRange = new TimeWindow();
            timeRange.encompass(tws);
            Date low = new Date(10);
            Date high = new Date(240);

            assertTrue(timeRange.getStartTime().equals(low) &&
                     timeRange.getEndTime().equals(high));
        } catch (TimeWindowException twe) {
            assertTrue(false);
        }
    }

}
