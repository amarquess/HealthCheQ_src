package com.sw551.fairfield.healthcheq;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(userDataCreated())
        {
            setContentView(R.layout.activity_main);
        }
        else
        {
            setContentView(R.layout.activity_profile);
        }
    }

    public void startGoals(View v)
    {
        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(intent);
    }

    public void startHistory(View v)
    {
        Intent intent = new Intent(getApplicationContext(),HistoryActivity.class);
        startActivity(intent);
    }

    public void startNearbyPlaces(View v)
    {
        Intent intent = new Intent(getApplicationContext(),NearbyPlacesActivity.class);
        startActivity(intent);
    }

    public void startKnowTheFacts(View v)
    {
        Intent intent = new Intent(getApplicationContext(),KnowTheFactsActivity.class);
        startActivity(intent);
    }

    public void startSettings(View v)
    {
        Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
        startActivity(intent);
    }


    public boolean userDataCreated()
    {
        //TO DO: verify user data in database

        return true;
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
