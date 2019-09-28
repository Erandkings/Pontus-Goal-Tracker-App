package com.project.pontusgoaltracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import com.project.pontusgoaltracker.models.Goal;


public class GoalDetailsActivity extends AppCompatActivity {
    Intent intent;
    Goal goal ;
    TextView goalTitle ,goalDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_details);
        intent = getIntent();
        goal= GoalListActivity.clickedGoal;

        goalTitle= findViewById(R.id.goaltitle);
        goalDescription = findViewById(R.id.goaldescription);
        TextView task1 = findViewById(R.id.task_1);
        CheckBox checkbox = findViewById(R.id.checkbox_1);
        TextView task2    =  findViewById(R.id.task_2);
        CheckBox checkbox2 = findViewById(R.id.checkbox_2);
        TextView task3 = findViewById(R.id.task_3);
        CheckBox checkbox3 = findViewById(R.id.checkbox_3);
        fillGoalDetails();
    }

    void fillGoalDetails(){

        goalTitle.setText(goal.getTitle());
        goalDescription.setText((goal.getDescription()));
    }
}

