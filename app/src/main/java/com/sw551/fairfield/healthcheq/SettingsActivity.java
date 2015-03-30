package com.sw551.fairfield.healthcheq;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sw551.fairfield.healthcheq.withings.WithingsData;

import java.util.ArrayList;


public class SettingsActivity extends ActionBarActivity {

    static TextView tvTest;
    SqlDbHelper db = new SqlDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        tvTest = (TextView)findViewById(R.id.textViewTest);
        //tvTest = (TextView)findViewById(R.id.textViewTest);

        //User userTest;
        //userTest = db.viewUser(1);

        //tvTest.setText(userTest.toString());


        //TODO put the code below after user info was added
        SharedPreferences settings = getSharedPreferences("AppPrefsFile", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isUserCreated", true);
        editor.commit();


    }

    public void addScale(View v)
    {
        //ArrayList<Record> records = new ArrayList<>();
        //WithingsData data = new WithingsData(this);


        //data.recordAndAlignTask(records);
        //data.recordAndAlignTask(tvTest);
        //data.execute();





        // TODO add scale config
    }


    public void selectWeightUnit(View v)
    {
        AlertDialog levelDialog;

        // Strings to Show In Dialog with Radio Buttons
        final CharSequence[] items = { getResources().getString(R.string.weight_pounds_abbr),
                getResources().getString(R.string.weight_kilograms_abbr) };

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.settings_weight_title));
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                SharedPreferences settings = getSharedPreferences("AppPrefsFile", 0);
                SharedPreferences.Editor editor = settings.edit();

                switch(item)
                {
                    case 0:
                        editor.putBoolean("isWeightInPounds", true);
                        break;

                    case 1:
                        editor.putBoolean("isWeightInPounds", false);
                        break;

                }
                editor.commit();
                dialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    public void viewProfile(View v)
    {
        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(intent);
    }



    static public void testDisplayWeight(ArrayList<Record> result)
    {
        String test_result = "";


        for(Record item : result)
        {
            //test_result = test_result.concat(item.getWeight() + ", ");
            test_result = test_result.concat(item.getDate() + ", ");
        }

        tvTest.setText(test_result);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_settings, menu);
//        return true;
//    }
//
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
