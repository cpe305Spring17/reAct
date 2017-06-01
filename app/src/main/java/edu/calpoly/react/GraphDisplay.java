package edu.calpoly.react;


import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import edu.calpoly.react.exceptions.TimeWindowException;
import edu.calpoly.react.model.Event;
import edu.calpoly.react.model.database.DBConnection;

import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;

/**
 * Created by Nishanth on 6/1/17.
 */

public class GraphDisplay {
    LinkedList<BarEntry> barEntryList;
    LinkedList<String> barLabelList;
    List<Event> eventList;
    Integer currDex;
    java.util.Calendar currDate;
    Integer currMinutes;
    SimpleDateFormat dateFormat;

    public static final int MINUTES_IN_DAY = 1440;
    public static final float MINUTES_IN_HOUR = 60.0f;

    public List<Event> getEventsFromActivityName(String whichActivity) {
        DBConnection db = DBConnection.getInstance();
        return db.getInActiveEventsFromActvity(db.getActivity(whichActivity));
    }

    public BarData makeChartInternals(String whichActivity) throws TimeWindowException {
        this.eventList = getEventsFromActivityName(whichActivity);
        this.barEntryList = new LinkedList<>();
        this.barLabelList = new LinkedList<>();
        this.currDex = 0;
        this.dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
        BarDataSet barDataSet;
        BarData barData;

        sortEventList(eventList);
        for (Event event : eventList) {
            eventToPreBar(event);
        }

        barDataSet = new BarDataSet(this.barEntryList, "Time in Hours");
        barData = new BarData(barDataSet);
        //barData = new BarData(this.barLabelList, barDataSet);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        return barData;
    }

    public void eventToPreBar(Event event) throws TimeWindowException {
        Date start = event.getStartTime();
        Date end = event.getEndTime();
        java.util.Calendar startCal = dateToCalendar(start);
        java.util.Calendar endCal = dateToCalendar(end);

        if (isSameDay(startCal, endCal)) {
            addToPrebar(startCal, (int) TimeUnit.MILLISECONDS.toMinutes(event.timeSpan()));
        }
        else {
            addToPrebar(startCal, timeTillEnd(startCal));
            event.setStartTime(nextDay(startCal));
            eventToPreBar(event);
        }
    }

    private Date nextDay(java.util.Calendar cal) {
        cal.add(DAY_OF_YEAR, 1);
        cal.set(HOUR_OF_DAY, 0);
        cal.set(MINUTE, 0);
        cal.set(SECOND, 0);
        cal.set(MILLISECOND, 0);

        return cal.getTime();
    }

    public void addToPrebar(java.util.Calendar cal, Integer minutes) {
        if (this.currDate == null) {
            this.currDate = (java.util.Calendar) cal.clone();
            this.currMinutes = minutes;
            this.currDex = 0;
        }
        else if (isSameDay(cal, this.currDate)) {
            this.currMinutes += minutes;
        }
        else {
            push();
            this.currDate = (java.util.Calendar) cal.clone();
            this.currMinutes = minutes;
        }
    }

    public void push() {
        float hours = ((float)this.currMinutes) / MINUTES_IN_HOUR;
        BarEntry barEntry = new BarEntry(hours, this.currDex);


        barEntryList.add(barEntry);
        barLabelList.add(this.currDex, dateFormat.format(this.currDate.getTime()));
        this.currDex += 1;

    }

    public void sortEventList(List<Event> eventList) {
        Collections.sort(eventList, new Comparator<Event>() { // NOSONAR
            @Override
            public int compare(Event o1, Event o2) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        });
    }

    private java.util.Calendar dateToCalendar(Date a) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTimeInMillis(a.getTime());
        return cal;
    }

    private boolean isSameDay(java.util.Calendar a, java.util.Calendar b) {
        return (a.get(DAY_OF_YEAR) == b.get(DAY_OF_YEAR)) && (a.get(YEAR) == b.get(YEAR));
    }

    private int minuteOfDay(java.util.Calendar a) {
        return a.get(MINUTE) + (a.get(HOUR_OF_DAY) * 60);
    }

    private int timeTillEnd(java.util.Calendar a) {
        return MINUTES_IN_DAY - minuteOfDay(a);
    }
}
