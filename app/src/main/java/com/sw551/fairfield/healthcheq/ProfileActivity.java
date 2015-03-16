package com.sw551.fairfield.healthcheq;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IntegerRes;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.Calendar;


public class ProfileActivity extends ActionBarActivity {

    EditText  firstName, lastName, zipCode, mEdit;
    DatePicker birthDate;
    Spinner dropdownFeet, dropdownInches;
    RadioGroup radioSexGroup;
    RadioButton male;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firstName = (EditText)this.findViewById(R.id.firstName);
        lastName = (EditText)this.findViewById(R.id.lastName);

        radioSexGroup = (RadioGroup)findViewById(R.id.radioSex);
        male = (RadioButton)findViewById(R.id.male);
        dropdownFeet = (Spinner)findViewById(R.id.spinnerFeet);
        Integer[] itemsFeet = new Integer[]{4, 5, 6, 7};
        ArrayAdapter<Integer> adapterFeet = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, itemsFeet);
        dropdownFeet.setAdapter(adapterFeet);
        dropdownInches = (Spinner)findViewById(R.id.spinnerInches);
        Integer[] itemsInches = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
        ArrayAdapter<Integer> adapterInches = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, itemsInches);
        dropdownInches.setAdapter(adapterInches);
        zipCode = (EditText)this.findViewById(R.id.zipCode);
    }

   /* @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        populateSetDate(yy, mm+1, dd);
    }

    public void populateSetDate(int year, int month, int day) {
        mEdit = (EditText)findViewById(R.id.editText1);
        mEdit.setText(month+"/"+day+"/"+year);
    }*/

    public void submitProfile(View v)
    {
        User user = new User();
        user.setFirst_name(firstName.getText().toString());
        user.setLast_name(lastName.getText().toString());
        int selectedSex = radioSexGroup.getCheckedRadioButtonId();
        if(selectedSex == male.getId())
            user.setGender(Gender.MALE);
        else
            user.setGender(Gender.FEMALE);

       // DialogFragment newFragment = new SelectDateFragment();
       // newFragment.show(getSupportFragmentManager(), "DatePicker");
       /* int day = birthDate.getDayOfMonth();
        int month = birthDate.getMonth();
        int year = birthDate.getYear();
        Calendar cal = Calendar.getInstance();
        int age = cal.get(Calendar.YEAR) - year;
        user.setAge(age);
*/
        dropdownFeet = (Spinner)findViewById(R.id.spinnerFeet);
        Integer feet = (Integer)dropdownFeet.getSelectedItem();
        dropdownInches = (Spinner)findViewById(R.id.spinnerInches);
        Integer inches = (Integer)dropdownInches.getSelectedItem();
        double totalInches = (feet*12)+ inches;
        double height = totalInches * 2.54;
        int heightCM = (int)height;
        user.setHeight(heightCM);
        user.setZipcode(zipCode.getText().toString());
        SqlDbHelper db = new SqlDbHelper(v.getContext());
        db.addUser(user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
