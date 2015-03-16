package com.sw551.fairfield.healthcheq;

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
