package com.sw551.fairfield.healthcheq;

/**
 * Created by marques on 2/14/2015.
 */
public class User {

    private int user_id;
    private String first_name;
    private String last_name;
    private String date_of_birth;
    private int age;
    private Gender gender;
    private double height;
    private String zip_code;

    public void createTestUser()
    {
        this.first_name = "John";
        this.last_name = "Doe";
        this.date_of_birth = "1992-05-19";
        this.gender = Gender.MALE;
        this.height = 176;
        this.zip_code = "06230";

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public int getAge() {
        return age;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getZipcode() {
        return zip_code;
    }

    public void setZipcode(String zip_code) {
        this.zip_code = zip_code;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}


