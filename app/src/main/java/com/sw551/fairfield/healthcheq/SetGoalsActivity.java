package com.sw551.fairfield.healthcheq;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Calendar;


public class SetGoalsActivity extends ActionBarActivity {

    Spinner goalWeight, goalTime, goalMotive ;
    Goal goal;
    SqlDbHelper db = new SqlDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);
        goalWeight = (Spinner)findViewById(R.id.spinnerWeight);
        Integer[] itemsWeight = new Integer[]{5,10,15,20,25};
        ArrayAdapter<Integer> adapterWeight = new ArrayAdapter<Integer>(this, R.layout.spinner_item, itemsWeight);
        goalWeight.setAdapter(adapterWeight);
        goalTime = (Spinner)findViewById(R.id.spinnerTime);
        Integer[] itemsTime = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
        ArrayAdapter<Integer> adapterTime = new ArrayAdapter<Integer>(this, R.layout.spinner_item, itemsTime);
        goalTime.setAdapter(adapterTime);
        goalMotive = (Spinner)findViewById(R.id.spinnerMotive);
        String[] itemsMotive = new String[]{"look the best I can","improve my health","improve my self esteem","live longer","increase my energy levels"};
        ArrayAdapter<String> adapterMotive = new ArrayAdapter<String>(this, R.layout.spinner_item, itemsMotive);
        goalMotive.setAdapter(adapterMotive);




    }

    public void submitGoal(View v)
    {
        Record lastRecord = db.getLastRecord(1);
        goal = new Goal();

        String lastDate = lastRecord.getDate();

        goal.setStart_weight(lastRecord.getWeight());
        goal.setStart_date(String.valueOf(System.currentTimeMillis()));

        goal.setTarget_weight(lastRecord.getWeight() - Double.parseDouble(goalWeight.getSelectedItem().toString()));
        goal.setTarget_date(calculateTargetDate(goal.getStart_date(), goalTime.getSelectedItem().toString()));

        goal.setPhrase_number((int)goalMotive.getSelectedItemId());
        goal.setUser_id(1);

        Goal existingGoal = db.viewGoal(goal.getUser_id());
        if(existingGoal.getTarget_date() == null)
        {
            db.setNewGoal(goal);
        }
        else
        {
            db.updateGoal(goal);
        }

        finish();
    }

    public void goHome(View v)
    {
        finish();
    }


    public String calculateTargetDate(String startDate, String month)
    {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(startDate));
        c.set(Calendar.MONTH,c.get(Calendar.MONTH) + Integer.parseInt(month));

        return String.valueOf(c.getTimeInMillis());

    }

}
