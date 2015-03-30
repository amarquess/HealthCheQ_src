package com.sw551.fairfield.healthcheq;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Bmi {


    static public enum BmiInfo {
        UNDERWEIGHT,
        HEALTHYWEIGHT,
        OVERWEIGHT,
        OBESE
    }

    static public BmiInfo checkBmiRange(double value)
    {
        if(value < 18.5)
        {
            return BmiInfo.UNDERWEIGHT;
        }
        else if(value >= 18.5 && value < 25)
        {
            return BmiInfo.HEALTHYWEIGHT;
        }
        else if(value >= 25 && value < 30)
        {
            return BmiInfo.OVERWEIGHT;
        }
        else
        {
            return BmiInfo.OBESE;
        }
    }

    static public double convertWeight(Context context, double weight)
    {
        double result;
        SharedPreferences settings = context.getSharedPreferences("AppPrefsFile", 0);
        boolean isWeightInPounds = settings.getBoolean("isWeightInPounds", true);

        if(isWeightInPounds)
        {
            result = weight*2.2046;
        }
        else
        {
            result = weight;
        }


        return result;

    }


}


