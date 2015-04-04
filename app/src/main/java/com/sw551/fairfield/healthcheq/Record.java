package com.sw551.fairfield.healthcheq;

/**
 * Created by marques on 3/2/2015.
 */
public class Record {

    private int id;
    private double weight;
    private double bmi;
    private String date;
    private int user_id;

    public Record() {
        this.weight = 0;
        this.date = "0";
        this.bmi = 0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String toString()
    {
        return weight + "," + date + "; ";
    }
}
