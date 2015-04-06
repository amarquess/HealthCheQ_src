package com.sw551.fairfield.healthcheq;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;


public class ProfileActivity extends ActionBarActivity {

    EditText  firstName, lastName, zipCode, mEdit;

    TextView dateOfBirth;
    Button btnDateOfBirth;
    int year;
    int month;
    int day;

    Spinner dropdownFeet, dropdownInches;
    RadioGroup radioSexGroup;
    RadioButton male, female;
    static final int DATE_DIALOG_ID = 0;

    SqlDbHelper db = new SqlDbHelper(this);
    User user;
    boolean isUserCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firstName = (EditText)this.findViewById(R.id.firstName);
        lastName = (EditText)this.findViewById(R.id.lastName);

        radioSexGroup = (RadioGroup)findViewById(R.id.radioSex);
        male = (RadioButton)findViewById(R.id.male);
        female = (RadioButton)findViewById(R.id.female);

        dateOfBirth = (TextView) findViewById(R.id.DateOfBirth);
        dropdownFeet = (Spinner)findViewById(R.id.spinnerFeet);
        Integer[] itemsFeet = new Integer[]{4, 5, 6, 7};
        ArrayAdapter<Integer> adapterFeet = new ArrayAdapter<Integer>(this, R.layout.spinner_item, itemsFeet);
        dropdownFeet.setAdapter(adapterFeet);
        dropdownInches = (Spinner)findViewById(R.id.spinnerInches);
        Integer[] itemsInches = new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11};
        ArrayAdapter<Integer> adapterInches = new ArrayAdapter<Integer>(this, R.layout.spinner_item, itemsInches);
        dropdownInches.setAdapter(adapterInches);
        zipCode = (EditText)this.findViewById(R.id.zipCode);


        addListenerOnButton();

        SharedPreferences settings = getSharedPreferences("AppPrefsFile", 0);
        isUserCreated = settings.getBoolean("isUserCreated", false);

        if(isUserCreated)
        {
            user = db.viewUser(1);
            firstName.setText(user.getFirst_name());
            lastName.setText(user.getLast_name());

            if(user.getGender().equals(Gender.MALE))
            {
                male.setChecked(true);
            }
            else
            {
                female.setChecked(true);
            }

            String dob = user.getDate_of_birth();
            String[] list = dob.split("-|\\s+");
            month = Integer.parseInt(list[0])-1;
            day = Integer.parseInt(list[1]);
            year = Integer.parseInt(list[2]);
            dateOfBirth.setText(dob);

            int height = user.getHeight();
            int totalFeet = (int) Math.floor(((double)height / 2.54) / 12);
            int totalInches = (int) Math.round(((double) height / 2.54) - (totalFeet * 12));
            dropdownFeet.setSelection((int)totalFeet-4);
            dropdownInches.setSelection(totalInches);

            zipCode.setText(user.getZipcode());
        }
        else
        {
            setCurrentDateOnView();
        }



    }

    public void submitProfile(View v)
    {
        user = new User();
        user.setFirst_name(firstName.getText().toString());
        user.setLast_name(lastName.getText().toString());
        int selectedSex = radioSexGroup.getCheckedRadioButtonId();
        if(selectedSex == male.getId())
            user.setGender(Gender.MALE);
        else
            user.setGender(Gender.FEMALE);

        user.setDate_of_birth(dateOfBirth.getText().toString());

        Integer feet = Integer.parseInt(dropdownFeet.getSelectedItem().toString());
        Integer inches = Integer.parseInt(dropdownInches.getSelectedItem().toString());
        double totalInches = (feet*12)+ inches;
        double height = totalInches * 2.54;
        int heightCM = (int)Math.round(height);
        user.setHeight(heightCM);
        user.setZipcode(zipCode.getText().toString());

        if(isUserCreated)
        {
            db.updateUser(user,1);
        }
        else
        {
            db.addUser(user);

            SharedPreferences settings = getSharedPreferences("AppPrefsFile", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("isUserCreated", true);
            editor.commit();

            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        finish();
    }

    // display current date
    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,1985);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        dateOfBirth.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));

    }

    public void addListenerOnButton() {

        btnDateOfBirth = (Button) findViewById(R.id.btnDateOfBirth);

        btnDateOfBirth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            dateOfBirth.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

            // set selected date into datepicker also
            //dpResult.init(year, month, day, null);

        }
    };



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
