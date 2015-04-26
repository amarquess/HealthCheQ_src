package com.sw551.fairfield.healthcheq;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;


public class SetGoalsActivity extends ActionBarActivity {

    Spinner goalWeight, goalTime, goalMotive ;
    Goal goal;
    SqlDbHelper db = new SqlDbHelper(this);
    TextView tvGoalTitle, tvGoalMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);
        tvGoalTitle = (TextView)findViewById(R.id.txtGoal);
        tvGoalMessage = (TextView)findViewById(R.id.txtGoalMessage);
        goalWeight = (Spinner)findViewById(R.id.spinnerWeight);
        Integer[] itemsWeight = new Integer[]{1,2,3,4,5,10,15,20,25};
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

        goal = db.viewGoal(1);

        if(goal.getTarget_date() != null)
        {
            double targetWeight = goal.getStart_weight()-goal.getTarget_weight();

            tvGoalTitle.setText("Edit Your Goal");
            tvGoalMessage.setText("Goal Started on " + formatDate(goal.getStart_date()) + ":");

            goalWeight.setSelection(getIndex(goalWeight,Integer.toString((int)targetWeight)));
            goalTime.setSelection(getIndex(goalTime, Integer.toString(calculateTargetMonth(goal.getStart_date(), goal.getTarget_date()))));
            goalMotive.setSelection(goal.getPhrase_number());
        }
    }

    public void submitGoal(View v)
    {
        if(goal.getTarget_date() != null)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Set New Goal")
                    .setMessage("It will overwrite the previous Goal. Continue?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            setGoal();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else
        {
            setGoal();
        }


    }

    public void goHome(View v)
    {
        finish();
    }

    private void setGoal()
    {
        Record lastRecord = db.getLastRecord(1);
        goal = new Goal();

        String lastDate = lastRecord.getDate();

        goal.setStart_weight(lastRecord.getWeight());
        goal.setStart_date(String.valueOf(System.currentTimeMillis()/1000L));

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


    private String calculateTargetDate(String startDate, String month)
    {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(startDate)*1000L);
        c.set(Calendar.MONTH,c.get(Calendar.MONTH) + Integer.parseInt(month));

        return String.valueOf(c.getTimeInMillis()/1000L);

    }

    private int calculateTargetMonth(String startDate, String targetDate)
    {

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTimeInMillis(Long.parseLong(startDate) * 1000L);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeInMillis(Long.parseLong(targetDate) * 1000L);

        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

        return diffMonth;

    }



    private String formatDate(String date) {

        String formattedDate;
        int year, month, day;

        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(date)*1000L);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        formattedDate = new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).toString();

        return formattedDate;
    }

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

}
