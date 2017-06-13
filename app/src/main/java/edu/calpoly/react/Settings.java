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
                    Log.i(Settings.class.getName(), "night mode is on");
                } else {
                    Log.i(Settings.class.getName(), "night mode off");
                }
            }
        });

        ToggleButton soundEffects = (ToggleButton) findViewById(R.id.sound_effects);
        soundEffects.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Log.i(Settings.class.getName(), "sound effects on");
                } else {
                    Log.i(Settings.class.getName(), "sound effects off");
                }
            }
        });

        ToggleButton allNotifications = (ToggleButton) findViewById(R.id.notifications);
        allNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Log.i(Settings.class.getName(), "Notifications are on");
                } else {
                    Log.i(Settings.class.getName(), "Notifications are off");
                }
            }
        });

        ToggleButton cloudSync = (ToggleButton) findViewById(R.id.cloud_sync);
        cloudSync.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Log.i(Settings.class.getName(), "cloudSync is on");
                } else {
                    Log.i(Settings.class.getName(), "cloudSync is off");
                }
            }
        });

    }
}
