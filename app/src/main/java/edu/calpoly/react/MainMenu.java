package edu.calpoly.react;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import edu.calpoly.react.model.Action;
import edu.calpoly.react.model.Event;
import edu.calpoly.react.model.database.DBConnection;
import edu.calpoly.react.model.database.DBSeed;

/**
 * Created by Nishanth on 5/17/17.
 */

public class MainMenu extends AppCompatActivity {

    public static void addButtonLayout(Button button, int centerInParent, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        // Defining the layout parameters of the Button
        RelativeLayout.LayoutParams buttonLayoutParameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        // Add Margin to the LayoutParameters
        buttonLayoutParameters.setMargins(marginLeft, marginTop, marginRight, marginBottom);

        // Add Rule to Layout
        buttonLayoutParameters.addRule(centerInParent);

        // Setting the parameters on the Button
        button.setLayoutParams(buttonLayoutParameters);
    }

    public static void layoutAllActivities(RelativeLayout relative, Context context, final int backgroundColor, final int textColor, final int activeColor) {
        int i = 0;
        int j = 0;
        for (Action a : DBConnection.getInstance().getAllActivities(null)) {
            final Button btn = new Button(context);
            btn.setId(a.getId().intValue());
            final int btnId = btn.getId();
            btn.setText(a.getName());
            btn.setBackgroundColor(backgroundColor);
            btn.setTextColor(textColor);

            if (i % 3 == 0) {
                addButtonLayout(btn, RelativeLayout.ALIGN_PARENT_LEFT, 0, 200 + 600 * j, 0, 0);
            }
            else if (i % 3 == 1) {
                addButtonLayout(btn, RelativeLayout.ALIGN_PARENT_LEFT, 0, 400 + 600 * j, 0, 0);
            }
            else {
                addButtonLayout(btn, RelativeLayout.ALIGN_PARENT_LEFT, 0, 600 + 600 * j, 0, 0);
                j++;
            }

            btn.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    ColorDrawable colorD = (ColorDrawable) v.findViewById(btnId).getBackground();
                    int colorID = colorD.getColor();
                    if (colorID == backgroundColor) {
                        v.findViewById(btnId).setBackgroundColor(activeColor);
                        String activityName = ((Button)v.findViewById(btnId)).getText().toString();
                        Event event = new Event(null, DBConnection.getInstance().getActivity(activityName), null);
                        event.start(new Date());
                        DBConnection.getInstance().addEvent(event);
                    } else {
                        v.findViewById(btnId).setBackgroundColor(backgroundColor);
                        String activityName = ((Button)v.findViewById(btnId)).getText().toString();
                        Action a1 = DBConnection.getInstance().getActivity(activityName);
                        Event event = DBConnection.getInstance().getActiveEventFromActvity(a1).get(0);
                        event.stop(new Date());
                        DBConnection.getInstance().updateEvent(event);
                    }
                }
            });

            relative.addView(btn);
            i++;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBConnection.getInstance(getApplicationContext());
        DBSeed.seed(null);
        setContentView(R.layout.activity_main);
        RelativeLayout relative = (RelativeLayout) findViewById(R.id.scroll_view);
        layoutAllActivities(relative, getApplicationContext(),
                getResources().getColor(R.color.colorPrimaryDark),
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.colorGreen));
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);

        MenuItem item = menu.findItem(R.id.action_menu_selector);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.menu_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        final List<Intent> intents = Arrays.asList(
                null,
                new Intent(MainMenu.this, ActivitiesHome.class)
                /*
                new Intent(MainMenu.this, Notifications.class),
                new Intent(MainMenu.this, Graphs.class),
                new Intent(MainMenu.this, GoalsHome.class),
                new Intent(MainMenu.this, Categories.class),
                new Intent(MainMenu.this, Settings.class)
                */

        );

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null)
                    selectedText.setTextColor(Color.parseColor("#3F51B5"));
                Intent intent = intents.get(position);
                if (intent != null)
                    MainMenu.this.startActivity(intent);
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {
                // nothing to do
            }
        });
        return true;
    }

}
