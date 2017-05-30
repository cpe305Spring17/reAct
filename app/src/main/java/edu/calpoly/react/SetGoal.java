package edu.calpoly.react;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import edu.calpoly.react.exceptions.TimeWindowException;
import edu.calpoly.react.model.Action;
import edu.calpoly.react.model.Goal;
import edu.calpoly.react.model.SubGoal;
import edu.calpoly.react.model.database.DBConnection;

/**
 * Created by Nishanth on 5/29/17.
 */

public class SetGoal extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goal);

        spinner = (Spinner) findViewById(R.id.activity_spinner);
        List<Action> activities = DBConnection.getInstance().getAllActivities(null);
        List<String> names = new ArrayList<>();
        for (Action a : activities) {
            names.add(a.getName());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_set_goal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cancel_new_goal) {
            Intent intent = new Intent(this, GoalsHome.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setNewGoal(View view) {
        Intent intent = new Intent(this, GoalsHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        String goalName = ((EditText)findViewById(R.id.activity_name)).getText().toString();
        Log.i(SetGoal.class.getName(), "Making goal: " + goalName);
        Date start = new Date();
        Date end = new Date(start.getTime() + 100);

        String activityName = ((Spinner)findViewById(R.id.activity_spinner)).getSelectedItem().toString();
        Action action = DBConnection.getInstance().getActivity(activityName);
        assert action != null;
        Integer totalEvents = 1;
        Long totalTime = 3600000L;
        SubGoal subGoal = new SubGoal(action, totalTime, totalEvents);

        Goal goal = null;
        try {
            goal = new Goal(goalName, Arrays.asList(new SubGoal[] {subGoal}), start, end);
            Log.i(SetGoal.class.getName(), "Saving goal: " + goalName);
            DBConnection.getInstance().addGoal(goal);
        } catch (TimeWindowException twe) {
            Log.e(SetGoal.class.getName(), "Invalid time window while setting goal: " + goalName, twe);
        }

        finish();
    }
}
