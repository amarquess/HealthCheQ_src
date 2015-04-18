package com.sw551.fairfield.healthcheq;

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
    SqlDbHelper db = new SqlDbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        plot1=(XYPlot)findViewById(R.id.plot1);
        button=(Button)findViewById(R.id.button_clear);
        ArrayList<Record> recordList=db.getAllRecord(1);
        Number[] years=new Integer[recordList.size()];
        Number[] numSightings=new Integer[recordList.size()];
        for (int i = 0; i < recordList.size(); i++) {
            years[i]= Integer.parseInt(recordList.get(i).getDate());
            numSightings[i]=(int)recordList.get(i).getWeight();
        }
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(years), Arrays.asList(numSightings),"Weight vs Date");
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
        LineAndPointFormatter formatter  =
                new LineAndPointFormatter(Color.rgb(0, 0,0), Color.BLUE, Color.RED, null);
        formatter.setFillPaint(lineFill);
        plot1.getGraphWidget().setPaddingRight(2);
        plot1.addSeries(series2, formatter);
        // draw a domain tick for each year:
        plot1.setDomainStep(XYStepMode.SUBDIVIDE, (years.length/2));
        // customize our domain/range labels
        plot1.setDomainLabel("Year");
        plot1.setRangeLabel("weight");
        // get rid of decimal points in our range labels:
        plot1.setRangeValueFormat(new DecimalFormat("0"));
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
                plot1.clear();
                plot1.redraw();
                db.deleteAllRecord();


            }
        });


        // by default, AndroidPlot displays developer guides to aid in laying out your plot.
        // To get rid of them call disableAllMarkup():
        //plot1.disableAllMarkup();
    }
}
