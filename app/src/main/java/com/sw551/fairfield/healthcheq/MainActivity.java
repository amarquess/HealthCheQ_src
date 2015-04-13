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

import java.lang.reflect.Field;
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
    TextView tv_weight_range;
    TextView tv_date;
    TextView tv_weight_type;

    SqlDbHelper db = new SqlDbHelper(this);
    Record last_record = new Record();


    DecimalFormat df = new DecimalFormat("#.#");
    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!userDataCreated())
        {
            finish();
            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);

        tv_bmi_info = (TextView)findViewById(R.id.tv_bmi_info);
        tv_bmi_info.setPaintFlags(tv_bmi_info.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tv_weight_info = (TextView)findViewById(R.id.tv_weight_info);
        tv_weight_info.setPaintFlags(tv_weight_info.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tv_bmi_value = (TextView)findViewById(R.id.tv_bmi_value);
        tv_weight_value = (TextView)findViewById(R.id.tv_weight_value);
        tv_date = (TextView)findViewById(R.id.tv_date);
        tv_weight_range = (TextView)findViewById(R.id.tv_weight_range);
        tv_weight_type = (TextView)findViewById(R.id.tv_weight_type);

        checkLastRecord();
        updateUserInfo(last_record);

        //testDB();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        updateUserInfo(last_record);
    }

    public void startGoals(View v)
    {
        Intent intent = new Intent(getApplicationContext(),SetGoalsActivity.class);
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
        Intent intent = new Intent(getApplicationContext(),know_the_facts_child1.class);
        startActivity(intent);
    }

    public void viewWeightInfo(View v)
    {
        Intent intent = new Intent(getApplicationContext(),know_the_facts_child2.class);
        startActivity(intent);
    }

    public void viewFacebookGroup(View v) {
        Uri facebook_url = Uri.parse("https://www.facebook.com/HealthcheQ.community");
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
        String weight_name;
        String weight_type;
        Bmi.BmiInfo range;
        User user = db.viewUser(1);
        double minWeight, maxWeight;

        SharedPreferences settings = getSharedPreferences("AppPrefsFile", 0);
        boolean isWeightInPounds = settings.getBoolean("isWeightInPounds", true);

        //Calendar time = new GregorianCalendar(last_record_db.getDate());
        Date date = new Date(Long.parseLong(last_record_db.getDate())*1000L);

        height = (double)user.getHeight()/100; //Height in meters

        bmi = last_record_db.getWeight() / Math.pow(height,2);

        minWeight = 18.5 * Math.pow(height,2);
        maxWeight = 25 * Math.pow(height,2);

        if(isWeightInPounds)
        {
            weight_type = getResources().getString(R.string.weight_pounds_abbr);
            weight_name = getResources().getString(R.string.weight_pounds);
        }
        else
        {
            weight_type = getResources().getString(R.string.weight_kilograms_abbr);
            weight_name = getResources().getString(R.string.weight_kilograms);
        }

        tv_weight_type.setText(weight_type);
        tv_weight_value.setText(String.valueOf(df.format(Bmi.convertWeight(getApplicationContext(),last_record_db.getWeight()))));
        tv_bmi_value.setText(String.valueOf(df.format(bmi)));
        tv_date.setText(String.valueOf(sdf.format(date)));
        tv_weight_range.setText("Your normal weight range would be from " +
                df.format(Bmi.convertWeight(getApplicationContext(),minWeight)) +
                " to "+ df.format(Bmi.convertWeight(getApplicationContext(),maxWeight)) + " " + weight_name);

        range = Bmi.checkBmiRange(bmi);
        if(range == Bmi.BmiInfo.UNDERWEIGHT)
        {
            tv_weight_info.setText(getResources().getString(R.string.bmi_underweight));
            tv_weight_info.setTextColor(getResources().getColor(R.color.yellow));
        }
        else if(range == Bmi.BmiInfo.HEALTHYWEIGHT)
        {
            tv_weight_info.setText(getResources().getString(R.string.bmi_healthyweight));
            tv_weight_info.setTextColor(getResources().getColor(R.color.green));
        }
        else if(range == Bmi.BmiInfo.OVERWEIGHT)
        {
            tv_weight_info.setText(getResources().getString(R.string.bmi_overweight));
            tv_weight_info.setTextColor(getResources().getColor(R.color.orange));
        }
        else
        {
            tv_weight_info.setText(getResources().getString(R.string.bmi_obese));
            tv_weight_info.setTextColor(getResources().getColor(R.color.red));
        }

        //tv_weight_info.setText(getResourceId("bmi_"+range.toString().toLowerCase(), R.string.class));







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


    @SuppressWarnings("rawtypes")
    public static int getResourceId(String name,  Class resType){

        try {
            Class res = null;
            if(resType == R.drawable.class)
                res = R.drawable.class;
            if(resType == R.id.class)
                res = R.id.class;
            if(resType == R.string.class)
                res = R.string.class;
            Field field = res.getField(name);
            int retId = field.getInt(null);
            return retId;
        }
        catch (Exception e) {
            // Log.d(TAG, "Failure to get drawable id.", e);
        }
        return 0;
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
