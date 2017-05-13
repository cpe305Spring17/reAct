package edu.calpoly.react.model;

import org.junit.Test;

import java.security.InvalidParameterException;

import static junit.framework.Assert.*;

/**
 * Created by Nishanth on 5/12/17.
 */

public class TestAction {
    @Test
    public void TestActionEmptyConstructor() {
        Action a = new Action();
        assertNotNull(a);
    }

    @Test
    public void TestActionStringConstructor() {
        Action a = new Action("action");
        assertNotNull(a);
    }

    @Test
    public void TestActionGeneralConstructor() {
        Category c = new Category("school");
        Action a = new Action("class", c);
        assertNotNull(a);
    }

    @Test
    public void TestActionGetSetId() {
        Action a = new Action();
        a.setId((long) 1);
        long id = a.getId();
        assertEquals((long) 1, id);
    }

    @Test
    public void TestActionGetSetName() {
        Action a = new Action();
        a.setName("basketball");
        String name = a.getName();
        assertEquals("basketball", name);
    }

    @Test
    public void TestActionSetNameException() {
        Action a = new Action();
        try {
            a.setName(null);
            assertTrue(false);
        } catch (InvalidParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void TestActionGetSetCategory() {
        Category c = new Category("school");
        Action a = new Action();
        a.setCategory(c);
        assertEquals(c, a.getCategory());
    }
}
