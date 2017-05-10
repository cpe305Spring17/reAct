package edu.calpoly.react;

import org.junit.Test;

import edu.calpoly.react.model.Activity;
import edu.calpoly.react.model.Category;

import static junit.framework.Assert.*;

/**
 * Created by Nishanth on 5/7/17.
 */

public class TestCategory {
    @Test
    public void TestActivityEmptyConstructor() {
        Category c = new Category();
        assertNotNull(c);
    }

    @Test
    public void TestActivityGeneralConstructor() {
        Category c = new Category();
        assertNotNull(c);
    }
}
