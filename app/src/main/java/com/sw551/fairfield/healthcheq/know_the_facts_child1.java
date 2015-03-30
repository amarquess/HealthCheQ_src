package com.sw551.fairfield.healthcheq;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class know_the_facts_child1 extends ActionBarActivity {
    TextView textView;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_the_facts_child1);
        textView=(TextView) findViewById(R.id.textView3);
        textView.setText(Html.fromHtml("<p>Health consequences of overweight and obesity in adults. The BMI ranges are based on the relationship between body weight and disease and death. <br><br> Overweight and obese individuals are at an increased risk for many diseases and health conditions, including the following:  <br><b> - </b> Hypertension Dyslipidemia (for example, high LDL cholesterol, low HDL cholesterol, or high levels of triglycerides) <br><br><b> - </b> Type 2 diabetes Coronary heart disease Stroke Gallbladder disease Osteoarthritis Sleep apnea and respiratory problems At least 10 cancers (including endometrial, breast, and colon). </p>"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_know_the_facts_child1, menu);
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
