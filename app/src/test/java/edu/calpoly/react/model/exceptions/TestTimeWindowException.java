package edu.calpoly.react.model.exceptions;

import org.junit.Test;

import edu.calpoly.react.exceptions.TimeWindowException;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Nishanth on 5/14/17.
 */

public class TestTimeWindowException {
    @Test
    public void TestTimeWindowEmptyConstructor() {
        TimeWindowException twe = new TimeWindowException();
        assertNotNull(twe);
    }

    @Test
    public void TestTimeWindowGeneralConstructor() {
        TimeWindowException twe = new TimeWindowException("exception");
        assertNotNull(twe);
    }
}
