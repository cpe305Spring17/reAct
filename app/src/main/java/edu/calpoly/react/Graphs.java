package edu.calpoly.react;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import edu.calpoly.react.exceptions.TimeWindowException;
import edu.calpoly.react.model.Action;
import edu.calpoly.react.model.database.DBConnection;

/**
 * Created by Nishanth on 6/1/17.
 */

public class Graphs extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter<String> adapter;
    HorizontalBarChart chart;
    BarData data;
    GraphDisplay graphDisplay;


    private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
        BarData data;
        GraphDisplay graphDisplay;
        String activityName;

        public MyOnItemSelectedListener(HorizontalBarChart ch) {
            this.graphDisplay = new GraphDisplay();
        }

        public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            this.onItemSelected(parent, view, pos, id);
            Log.d("OH", "DID IT");
        }

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            this.activityName = parent.getItemAtPosition(pos).toString();
            Log.d("activity used", this.activityName);

            try {
                this.data = graphDisplay.makeChartInternals(activityName);
            }
            catch (TimeWindowException e) {
                Log.e("Exception", "TimeWindowException", e);
                return;
            }
            chart.setData(data);
            chart.setVisibleYRangeMaximum(5, chart.getAxisLeft().getAxisDependency());

            chart.animateY(3000);
            chart.notifyDataSetChanged();
            chart.invalidate();

        }



        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // not implemented
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        spinner = (Spinner) findViewById(R.id.graph_activity_spinner);
        List<Action> actions = DBConnection.getInstance().getAllActivities(null);
        List<String> names = new ArrayList<>();
        names.add("Select Activity");
        for (Action a : actions) {
            names.add(a.getName());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        this.chart = (HorizontalBarChart) findViewById(R.id.chart1);

        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener(chart));

    }
}
