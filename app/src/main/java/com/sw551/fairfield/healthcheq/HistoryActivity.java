package com.sw551.fairfield.healthcheq;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.PointLabeler;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class HistoryActivity extends ActionBarActivity {
private Button button;
    private XYPlot plot1;
    private TextView tvNoData;
    int minWeight = 999;
    int maxWeight = 0;
    SqlDbHelper db = new SqlDbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        plot1=(XYPlot)findViewById(R.id.mySimpleXYPlot);
        button=(Button)findViewById(R.id.button_clear);
        tvNoData=(TextView)findViewById(R.id.txtNoData);
        ArrayList<Record> recordList=db.getAllRecord(1);
        if(!recordList.isEmpty())
        {
            tvNoData.setVisibility(View.INVISIBLE);
            Number[] years=new Integer[recordList.size()];
            Number[] numSightings=new Integer[recordList.size()];
            for (int i = 0; i < recordList.size(); i++) {
                years[i]= Integer.parseInt(recordList.get(i).getDate());
                numSightings[i]=(int)Bmi.convertWeight(getApplicationContext(),recordList.get(i).getWeight());
                testMinMax((int)numSightings[i]);
            }
            XYSeries series2 = new SimpleXYSeries(Arrays.asList(years), Arrays.asList(numSightings),"User");
            LineAndPointFormatter formatter  =
                    new LineAndPointFormatter(Color.rgb(0, 0,0), Color.BLUE, null, null);
            //formatter.setFillPaint(lineFill);
            plot1.addSeries(series2, formatter);
            // draw a domain tick for each year:
            plot1.setDomainStep(XYStepMode.SUBDIVIDE, (years.length/2));
        }
        else
        {
            plot1.setVisibility(View.INVISIBLE);
            button.setVisibility(View.INVISIBLE);
            tvNoData.setVisibility(View.VISIBLE);
        }
        Goal userGoal = db.viewGoal(1);
        if(userGoal.getTarget_date() != null)
        {
            Number[] goalYears = new Integer[2];
            Number[] goalWeight = new Integer[2];
            goalYears[0] = Integer.parseInt(userGoal.getStart_date());
            goalYears[1] = Integer.parseInt(userGoal.getTarget_date());
            goalWeight[0] = (int)Bmi.convertWeight(getApplicationContext(),userGoal.getStart_weight());
            goalWeight[1] = (int)Bmi.convertWeight(getApplicationContext(),userGoal.getTarget_weight());
            testMinMax((int)goalWeight[1]);

            XYSeries goalSeries = new SimpleXYSeries(Arrays.asList(goalYears), Arrays.asList(goalWeight),"Goal");
            LineAndPointFormatter formatter2  =
                    new LineAndPointFormatter(Color.rgb(255, 0,0), Color.RED, null, null);
            plot1.addSeries(goalSeries, formatter2);
        }

        plot1.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
        plot1.getGraphWidget().getDomainGridLinePaint().setColor(Color.BLACK);
        plot1.getGraphWidget().getDomainGridLinePaint().
                setPathEffect(new DashPathEffect(new float[]{1, 1}, 1));
        plot1.getGraphWidget().getRangeGridLinePaint().setColor(Color.BLACK);
        plot1.getGraphWidget().getRangeGridLinePaint().
                setPathEffect(new DashPathEffect(new float[]{1, 1}, 1));
        plot1.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
        plot1.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);
        // Create a formatter to use for drawing a series using LineAndPointRenderer:
        LineAndPointFormatter series1Format = new LineAndPointFormatter(
                Color.rgb(0, 100, 0),                   // line color
                Color.rgb(0, 100, 0),                   // point color
                Color.rgb(100, 200, 0), null);                // fill color
        // setup our line fill paint to be a slightly transparent gradient:
        Paint lineFill = new Paint();
        lineFill.setAlpha(200);
        // ugly usage of LinearGradient. unfortunately there's no way to determine the actual size of
        // a View from within onCreate.  one alternative is to specify a dimension in resources
        // and use that accordingly.  at least then the values can be customized for the device type and orientation.
        lineFill.setShader(new LinearGradient(0, 0, 200, 200, Color.WHITE, Color.WHITE, Shader.TileMode.CLAMP));
        plot1.getGraphWidget().setPaddingRight(2);
        //set boundaries using minWeight and maxWeight
        plot1.setRangeBoundaries(minWeight-3, maxWeight+3, BoundaryMode.FIXED);

        // customize our domain/range labels
        plot1.setDomainLabel("Date");
        plot1.setRangeLabel("Weight");
        // get rid of decimal points in our range labels:
        plot1.setRangeValueFormat(new DecimalFormat("1"));
        plot1.setDomainValueFormat(new Format() {
            private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-yyyy");

            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {

                // because our timestamps are in seconds and SimpleDateFormat expects milliseconds
                // we multiply our timestamp by 1000:
                long timestamp = ((Number) obj).longValue()*1000;
                Date date = new Date(timestamp);
                return dateFormat.format(date, toAppendTo, pos);
            }

            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;

            }
        });
                /*final XYSeries xys1=new SimpleXYSeries(Arrays.asList(series1),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"goal");
        final XYSeries xys2=new SimpleXYSeries(Arrays.asList(series2),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"me");
        LineAndPointFormatter sf1=new LineAndPointFormatter();
        sf1.setPointLabelFormatter(new PointLabelFormatter());
        sf1.configure(getApplicationContext(),R.xml.line_plf1);
        plot.addSeries(xys1,sf1);
        LineAndPointFormatter sf2=new LineAndPointFormatter();
        sf2.setPointLabelFormatter(new PointLabelFormatter());
        sf2.configure(getApplicationContext(),R.xml.line_plf2);
        plot.addSeries(xys2,sf2);
        plot.setTicksPerRangeLabel(1);
        plot.getGraphWidget().setDomainLabelOrientation(3);*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(v.getContext())
                        .setTitle("Clear History")
                        .setMessage("It will delete all stored information. Continue?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                plot1.clear();
                                plot1.redraw();
                                db.deleteAllRecord();
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
        });


        // by default, AndroidPlot displays developer guides to aid in laying out your plot.
        // To get rid of them call disableAllMarkup():
        //plot1.disableAllMarkup();
    }


    private void testMinMax(int value)
    {
        if(value > maxWeight)
        {
            maxWeight = value;
        }
        if(value < minWeight)
        {
            minWeight = value;
        }


    }
}
