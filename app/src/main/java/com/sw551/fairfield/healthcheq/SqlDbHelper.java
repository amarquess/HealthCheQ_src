package com.sw551.fairfield.healthcheq;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlDbHelper extends SQLiteOpenHelper{
	
	public static final String DATABASE_NAME = "HealthCheQ";
	public static final String DATABASE_TABLE_1 = "USER_TABLE";
    public static final String DATABASE_TABLE_2 = "HISTORY_TABLE";
    public static final String DATABASE_TABLE_3 = "BMI_TABLE";
    public static final String DATABASE_TABLE_4 = "GOAL_TABLE";


	public static final int DATABASE_VERSION = 1;
	
	public static final String USER_COLUMN_1 = "user_id";
	public static final String USER_COLUMN_2 = "first_name";
    public static final String USER_COLUMN_3 = "last_name";
    public static final String USER_COLUMN_4 = "date_of_birth";
    public static final String USER_COLUMN_5 = "gender";
    public static final String USER_COLUMN_6 = "height";
    public static final String USER_COLUMN_7 = "zipcode";

    public static final String HISTORY_COLUMN_1 = "measure_id";
    public static final String HISTORY_COLUMN_2 = "weight";
    public static final String HISTORY_COLUMN_3 = "bmi";
    public static final String HISTORY_COLUMN_4 = "date";
    public static final String HISTORY_COLUMN_5 = "user_id";

    public static final String BMI_COLUMN_1 = "measure_id";
    public static final String BMI_COLUMN_2 = "weight";
    public static final String BMI_COLUMN_3 = "bmi";
    public static final String BMI_COLUMN_4 = "date";
    public static final String BMI_COLUMN_5 = "user_id";

    public static final String GOAL_COLUMN_1 = "goal_id";
    public static final String GOAL_COLUMN_2 = "current_weight";
    public static final String GOAL_COLUMN_3 = "target_weight";
    public static final String GOAL_COLUMN_4 = "target_date";
    public static final String GOAL_COLUMN_5 = "user_id";

	String SCRIPT_CREATE_DATABASE_1 = "CREATE TABLE " + DATABASE_TABLE_1 + "("
			+ USER_COLUMN_1 + " INTEGER PRIMARY KEY," + USER_COLUMN_2 + " TEXT,"
            + USER_COLUMN_3 + " TEXT," + USER_COLUMN_4 + " TEXT,"
            + USER_COLUMN_5 + " INTEGER," + USER_COLUMN_6 + " INTEGER,"
            + USER_COLUMN_7 + " TEXT" + ")";

    String SCRIPT_CREATE_DATABASE_2 = "CREATE TABLE " + DATABASE_TABLE_2 + "("
            + HISTORY_COLUMN_1 + " INTEGER PRIMARY KEY," + HISTORY_COLUMN_2 + " INTEGER,"
            + HISTORY_COLUMN_3 + " REAL," + HISTORY_COLUMN_4 + " TEXT,"
            + HISTORY_COLUMN_5 + " INTEGER" + ")";

    String SCRIPT_CREATE_DATABASE_3 = "CREATE TABLE " + DATABASE_TABLE_3 + "("
            + BMI_COLUMN_1 + " INTEGER PRIMARY KEY," + BMI_COLUMN_2 + " INTEGER,"
            + BMI_COLUMN_3 + " REAL," + BMI_COLUMN_4 + " TEXT,"
            + BMI_COLUMN_5 + " INTEGER" + ")";

    String SCRIPT_CREATE_DATABASE_4 = "CREATE TABLE " + DATABASE_TABLE_4 + "("
            + GOAL_COLUMN_1 + " INTEGER PRIMARY KEY," + GOAL_COLUMN_2 + " INTEGER,"
            + GOAL_COLUMN_3 + " INTEGER," + GOAL_COLUMN_4 + " TEXT,"
            + GOAL_COLUMN_5 + " INTEGER" + ")";


	
	public SqlDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(SCRIPT_CREATE_DATABASE_1);
        db.execSQL(SCRIPT_CREATE_DATABASE_2);
        db.execSQL(SCRIPT_CREATE_DATABASE_3);
        db.execSQL(SCRIPT_CREATE_DATABASE_4);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE_1);
        db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE_2);
        db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE_3);
        db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE_4);
		
		onCreate(db);
		// TODO Auto-generated method stub
		
	}

    public void addUser(User new_user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_COLUMN_2, new_user.getFirst_name());
        values.put(USER_COLUMN_3, new_user.getLast_name());
        values.put(USER_COLUMN_4, new_user.getDate_of_birth());
        values.put(USER_COLUMN_5, new_user.getGender().ordinal());
        values.put(USER_COLUMN_6, new_user.getHeight());
        values.put(USER_COLUMN_7, new_user.getZipcode());
        db.insert(DATABASE_TABLE_1, null, values);
        db.close();
    }

    public void updateUser(User new_user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_COLUMN_2, new_user.getFirst_name());
        values.put(USER_COLUMN_3, new_user.getLast_name());
        values.put(USER_COLUMN_4, new_user.getDate_of_birth());
        values.put(USER_COLUMN_5, new_user.getGender().ordinal());
        values.put(USER_COLUMN_6, new_user.getHeight());
        values.put(USER_COLUMN_7, new_user.getZipcode());
        //db.update(DATABASE_TABLE_1, null, values); TODO
        db.close();
    }

    public User viewUser()
    {
        User user = new User();

        // Select All Query
		String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_1;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {


                user.setUser_id(Integer.parseInt(cursor.getString(0)));
                user.setFirst_name(cursor.getString(1));
                user.setLast_name(cursor.getString(2));
                user.setDate_of_birth(cursor.getString(3));
                //user.setGender(cursor.getString(4));
                user.setHeight(Integer.parseInt(cursor.getString(5)));
                user.setZipcode(cursor.getString(6));
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		// return contact list

        return user;
    }
	
	
//	public List<CourseInfo> getAllItems() {
//		List<CourseInfo> itemList = new ArrayList<CourseInfo>();
//
//		// Select All Query
//		String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				CourseInfo course = new CourseInfo();
//
//				course.setId(Integer.parseInt(cursor.getString(0)));
//				course.setCourseName(cursor.getString(1));
//
//				// Adding contact to list
//				itemList.add(course);
//			} while (cursor.moveToNext());
//		}
//		cursor.close();
//		db.close();
//		// return contact list
//		return itemList;
//	}
	
	
	public void deleteTable()
	{
		String deleteQuery = "DELETE FROM " + DATABASE_TABLE_1;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(deleteQuery, null);
		
		cursor.close();		
		db.close();

	}
	

}
