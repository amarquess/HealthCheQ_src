package com.sw551.fairfield.healthcheq;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class SetGoalsActivity extends ActionBarActivity {

    Spinner goalWeight, goalTime, goalMotive ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);
        goalWeight = (Spinner)findViewById(R.id.spinnerWeight);
        Integer[] itemsWeight = new Integer[]{5,10,15,20,25};
        ArrayAdapter<Integer> adapterWeight = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, itemsWeight);
        goalWeight.setAdapter(adapterWeight);
        goalTime = (Spinner)findViewById(R.id.spinnerTime);
        Integer[] itemsTime = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
        ArrayAdapter<Integer> adapterTime = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, itemsTime);
        goalTime.setAdapter(adapterTime);
        goalMotive = (Spinner)findViewById(R.id.spinnerMotive);
        String[] itemsMotive = new String[]{"look the best I can","improve my health","improve my self esteem","live longer","increase my energy levels"};
        ArrayAdapter<String> adapterMotive = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemsMotive);
        goalMotive.setAdapter(adapterMotive);
    }

    public void submitGoal(View v)
    {
        /*TBD*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_goals, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
