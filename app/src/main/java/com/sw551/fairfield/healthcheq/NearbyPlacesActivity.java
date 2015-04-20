package com.sw551.fairfield.healthcheq;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class NearbyPlacesActivity extends ActionBarActivity {

    public Button btnFitness, btnHealth, btnFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places);

        btnFitness=(Button)findViewById(R.id.buttonFitness);
        btnHealth=(Button)findViewById(R.id.buttonHealth);
        btnFood=(Button)findViewById(R.id.buttonFood);

        btnFitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent23=new Intent(NearbyPlacesActivity.this,know_the_facts_child1.class);
                startActivity(intent23);*/

            }
        });

        btnHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent23=new Intent(NearbyPlacesActivity.this,know_the_facts_child2.class);
                startActivity(intent23);*/
            }
        });

        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent23=new Intent(NearbyPlacesActivity.this,know_the_facts_child2.class);
                startActivity(intent23);*/
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nearby_places, menu);
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
