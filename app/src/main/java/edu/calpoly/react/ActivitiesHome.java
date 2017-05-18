package edu.calpoly.react;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Nishanth on 5/17/17.
 */

public class ActivitiesHome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_activities_home);
        RelativeLayout relative = (RelativeLayout) findViewById(R.id.scroll_view);
        MainMenu.layoutAllActivities(relative, getApplicationContext(),
                getResources().getColor(R.color.colorPrimaryDark),
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.colorGreen));
        super.onCreate(savedInstanceState);
    }

    public void createNewActivity(View view) {
        Intent intent = new Intent(this, CreateNewActivity.class);
        startActivity(intent);
    }
}
