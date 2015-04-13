package com.sw551.fairfield.healthcheq;

/**
 * Created by marques on 4/6/2015.
 */
public class Goal {

    private int id;
    private double start_weight;
    private String start_date;
    private double target_weight;
    private String target_date;
    private int phrase_number;
    private int user_id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getStart_weight() {
        return start_weight;
    }

    public void setStart_weight(double start_weight) {
        this.start_weight = start_weight;
    }

    public double getTarget_weight() {
        return target_weight;
    }

    public void setTarget_weight(double target_weight) {
        this.target_weight = target_weight;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getTarget_date() {
        return target_date;
    }

    public void setTarget_date(String target_date) {
        this.target_date = target_date;
    }

    public int getPhrase_number() {
        return phrase_number;
    }

    public void setPhrase_number(int phrase_number) {
        this.phrase_number = phrase_number;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
