package edu.calpoly.react;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

/**
 * Created by Nishanth on 5/29/17.
 */

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ToggleButton nightMode = (ToggleButton) findViewById(R.id.night_mode);
        nightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //Toggled is enabled
                    //keep night mode on
                    Log.i(Settings.class.getName(), "night mode is on");
                }
                else {
                    //Toggled is not enabled
                    //keep night mode off
                    Log.i(Settings.class.getName(), "night mode off");
                }
            }
        });

        ToggleButton soundEffects = (ToggleButton) findViewById(R.id.sound_effects);
        soundEffects.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //Toggled is enabled
                    //keep sound effects on
                    Log.i(Settings.class.getName(), "sound effects on");
                }
                else {
                    //Toggled is not enabled
                    //keep sound effects off
                    Log.i(Settings.class.getName(), "sound effects off");
                }
            }
        });

        ToggleButton allNotifications = (ToggleButton) findViewById(R.id.notifications);
        allNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //Toggled is enabled
                    //All Notifications are on
                    Log.i(Settings.class.getName(), "Notifications are on");
                }
                else {
                    //Toggled is not enabled
                    //All Notifications are off
                    Log.i(Settings.class.getName(), "Notifications are off");
                }
            }
        });

        ToggleButton cloudSync = (ToggleButton) findViewById(R.id.cloud_sync);
        cloudSync.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //Toggled is enabled
                    //cloudSync is on
                    Log.i(Settings.class.getName(), "cloudSync is on");
                }
                else {
                    //Toggled is not enabled
                    //cloud sync is off
                    Log.i(Settings.class.getName(), "cloudSync is off");
                }
            }
        });

    }
}
