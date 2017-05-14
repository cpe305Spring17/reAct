package edu.calpoly.react;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.Date;

import edu.calpoly.react.model.database.DBConnection;

/**
 * Created by Nishanth on 5/14/17.
 */

@RunWith(AndroidJUnit4.class)

@LargeTest
public abstract class IntegratedInstrumentedTest {
    protected static DBConnection db;

    protected Date s;
    protected Date f;

    protected void printErrorMessage(Exception e) {
        Log.d("Default", "Exception thrown in unknown location.", e);
    }

    @BeforeClass
    public static void setUp() {
        db = DBConnection.getInstance(InstrumentationRegistry.getTargetContext());
        db.deleteAllRows();
    }

    @AfterClass
    public static void tearDown() {
        db.close();
    }

    @Before
    public void clearDB() {
        db.deleteAllRows();
    }

    @Before
    public void setDates() {
        s = new Date();
        f = new Date();
    }
}
