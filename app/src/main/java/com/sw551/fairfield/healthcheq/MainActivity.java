package com.sw551.fairfield.healthcheq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sw551.fairfield.healthcheq.withings.WithingsData;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class MainActivity extends ActionBarActivity {

    //public static final String PREFS_NAME = "AppPrefsFile";
    TextView tv_bmi_info;
    TextView tv_bmi_value;
    TextView tv_weight_info;
    TextView tv_weight_value;
    TextView tv_date;
    SqlDbHelper db = new SqlDbHelper(this);
    Record last_record = new Record();
    DecimalFormat df = new DecimalFormat("#.##");
    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if(userDataCreated())
//        {
            setContentView(R.layout.activity_main);

            tv_bmi_info = (TextView)findViewById(R.id.tv_bmi_info);
            tv_bmi_info.setPaintFlags(tv_bmi_info.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            tv_weight_info = (TextView)findViewById(R.id.tv_weight_info);
            tv_weight_info.setPaintFlags(tv_weight_info.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            tv_bmi_value = (TextView)findViewById(R.id.tv_bmi_value);
            tv_weight_value = (TextView)findViewById(R.id.tv_weight_value);
            tv_date = (TextView)findViewById(R.id.tv_date);

            checkLastRecord();

//        }
//        else
//        {
//            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
//            startActivity(intent);
//
//        }

        //testDB();
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

    public void viewBmiInfo(View v)
    {
        Intent intent = new Intent(getApplicationContext(),KnowTheFactsActivity.class);
        startActivity(intent);
    }

    public void viewWeightInfo(View v)
    {
        Intent intent = new Intent(getApplicationContext(),KnowTheFactsActivity.class);
        startActivity(intent);
    }

    public void viewFacebookGroup(View v) {
        Uri facebook_url = Uri.parse("http://www.facebook.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, facebook_url);
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }

    public void getUserData(View v)
    {

        ArrayList<Record> records = new ArrayList<>();
        WithingsData data = new WithingsData(this){
            @Override
            protected void onPostExecute(ArrayList<Record> result)
            {
                //Now you can update the UI here
                cleanInvalidRecords(result);
                addRecordsOnDB(result);
                checkLastRecord();
                updateUserInfo(last_record);
            }
        };
        //tvTest = (TextView)findViewById(R.id.textViewTest);
        //data.recordAndAlignTask(tvTest);
        data.execute();
        // TODO Refresh Data
    }

    public void updateUserInfo(Record last_record_db)
    {
        double bmi;
        double height;
        User user = new User();
        user = db.viewUser(1);

        //Calendar time = new GregorianCalendar(last_record_db.getDate());
        Date date = new Date(Long.parseLong(last_record_db.getDate())*1000L);

        height = (double)user.getHeight()/100; //Height in meters

        bmi = last_record_db.getWeight() / Math.pow(height,2);

        tv_weight_value.setText(String.valueOf(df.format(last_record_db.getWeight())));
        tv_bmi_value.setText(String.valueOf(df.format(bmi)));
        tv_date.setText(String.valueOf(sdf.format(date)));
    }

    public boolean userDataCreated()
    {

        SharedPreferences settings = getSharedPreferences("AppPrefsFile", 0);
        boolean isUserCreated = settings.getBoolean("isUserCreated", false);

        return isUserCreated;
    }

    private void cleanInvalidRecords(ArrayList<Record> records)
    {
        int compare_number = 0;
        for(Record item : records)
        {
            compare_number = Double.compare(item.getWeight(), 0);
            if(compare_number == 0)
            {
                records.remove(item);
                cleanInvalidRecords(records);
                break;
            }


        }

    }

    private void addRecordsOnDB(ArrayList<Record> records)
    {
        //SqlDbHelper db = SqlDbHelper.getInstance(this);

        //last_record = db.getLastRecord(1);


        for(Record item : records)
        {
            if(Integer.parseInt(last_record.getDate()) < Integer.parseInt(item.getDate()))
            {
                db.addRecord(item, 1);
            }
        }
    }

    public boolean testDB()
    {

        SqlDbHelper db = new SqlDbHelper(this);
        User new_user = new User();
        new_user.createTestUser();

        db.addUser(new_user);
        //db.updateUser(new_user, 1);

        //TO DO: verify user data in database

        return true;
    }

    private void checkLastRecord()
    {
        last_record = db.getLastRecord(1);
        if(last_record == null)
        {
            last_record = new Record();
        }

    }

    public static Calendar unixToCalendar(long unixTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(unixTime);
        return calendar;
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
