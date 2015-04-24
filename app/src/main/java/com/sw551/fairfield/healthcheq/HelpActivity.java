package com.sw551.fairfield.healthcheq;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class HelpActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }


    public void viewProfileSetup(View v)
    {
        Intent intent = new Intent(getApplicationContext(),ProfileSetupActivity.class);
        startActivity(intent);
    }

    public void viewGoalSetup(View v)
    {
        Intent intent = new Intent(getApplicationContext(),GoalSetupActivity.class);
        startActivity(intent);
    }

    public void viewAbout(View v)
    {
        Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
        startActivity(intent);
    }

}
