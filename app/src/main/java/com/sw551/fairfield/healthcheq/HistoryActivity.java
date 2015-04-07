package com.sw551.fairfield.healthcheq;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.PointLabeler;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.Arrays;


public class HistoryActivity extends ActionBarActivity {

    private XYPlot plot;
    SqlDbHelper db = new SqlDbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        plot=(XYPlot)findViewById(R.id.mySimpleXYPlot);
        Number[] series1={10,8,6,4,2};
        Number[] series2={4,6,3,8,2};
        XYSeries xys1=new SimpleXYSeries(Arrays.asList(series1),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"goal");
        XYSeries xys2=new SimpleXYSeries(Arrays.asList(series2),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"me");
        LineAndPointFormatter sf1=new LineAndPointFormatter();
        sf1.setPointLabelFormatter(new PointLabelFormatter());
        sf1.configure(getApplicationContext(),R.xml.line_plf1);
        plot.addSeries(xys1,sf1);
        LineAndPointFormatter sf2=new LineAndPointFormatter();
        sf2.setPointLabelFormatter(new PointLabelFormatter());
        sf2.configure(getApplicationContext(),R.xml.line_plf2);
        plot.addSeries(xys2,sf2);
        plot.setTicksPerRangeLabel(3);
        plot.getGraphWidget().setDomainLabelOrientation(-45);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
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
