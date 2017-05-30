package edu.calpoly.react;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.List;

import edu.calpoly.react.model.Goal;
import edu.calpoly.react.model.database.DBConnection;

/**
 * Created by Nishanth on 5/29/17.
 */

public class GoalsHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_home);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.goal_scroll_view);

        List<Goal> goals = DBConnection.getInstance().getAllGoals();
        Integer top = 200;
        for (int i = 0; i < goals.size(); ++i) {
            final Goal goal = goals.get(i);
            Button button = new Button(getApplicationContext());
            button.setId(goal.getId().intValue());
            button.setText(goal.getName());
            button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            button.setTextColor(getResources().getColor(R.color.white));
            AddButtonLayout(button, RelativeLayout.ALIGN_PARENT_LEFT, 0, top, 0, 0);

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.i("Goal Button", "Clicked goal: " + goal.getName());
                }
            });

            layout.addView(button);
            top += 200;
        }
    }

    protected void AddButtonLayout(Button button, int centerInParent, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        // Defining the layout parameters of the Button
        RelativeLayout.LayoutParams buttonLayoutParameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        // Add Margin to the LayoutParameters
        buttonLayoutParameters.setMargins(marginLeft, marginTop, marginRight, marginBottom);

        // Add Rule to Layout
        buttonLayoutParameters.addRule(centerInParent);

        // Setting the parameters on the Button
        button.setLayoutParams(buttonLayoutParameters);
    }

    public void setNewGoal(View view) {
        Intent intent = new Intent(this, SetGoal.class);
        startActivity(intent);
    }
}
