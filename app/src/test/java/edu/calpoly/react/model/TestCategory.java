package edu.calpoly.react.model;

import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Created by Nishanth on 5/7/17.
 */

public class TestCategory {
    @Test
    public void TestCategoryEmptyConstructor() {
        Category c = new Category();
        assertNotNull(c);
    }

    @Test
    public void TestCategoryGeneralConstructor() {
        Category c = new Category("category");
        assertNotNull(c);
    }

    @Test
    public void TestCategoryGetSetId() {
        Category c = new Category("category");
        c.setId((long) 1);
        long id = c.getId();
        assertEquals((long) 1, id);
    }

    @Test
    public void TestCategoryGetSetName() {
        Category c = new Category();
        c.setName("category");
        String name = c.getName();
        assertEquals("category", name);
    }

    @Test
    public void TestCategorySetNameException() {
        Category c = new Category();
        try {
            c.setName(null);
            assertTrue(false);
        } catch (NullPointerException n){
            assertTrue(true);
        }
    }

    @Test
    public void TestCategoryEqualsFalse() {
        Category c = new Category("category");
        Category b = new Category("other");
        assertFalse(c.equals(b));
    }

    @Test
    public void TestCategoryCompareToTrue() {
        Category c = new Category("category");
        Category b = new Category("category");
        assertTrue(c.compareTo(b) == 0);
    }

    @Test
    public void TestCategoryCompareToFalse() {
        Category c = new Category("category");
        Category b = new Category("categoryB");
        assertTrue(c.compareTo(b) != 0);
    }
}
