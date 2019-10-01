package com.example.supervizor.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DataBaseHelper extends SQLiteOpenHelper {
    public static int DATA_BASE_VERSION = 1;
    public static String DATABASE_NAME = "SalaryInformation";

    public static String DATABASE_TABLE_NAME = "SalaryTable";


    public static String COL_USER_ID = "UserID";
    public static String COL_NAME = "Name";
    public static String COL_EMAIL = "Email";
    public static String COL_PHONE = "Phone";
    public static String COL_COMPANIESWORKINGDAYSS = "CompaniesWorkingDays";
    public static String COL_ATTENDANCECOUNT = "AttendanceCount";
    public static String COL_BASICSALARY = "BasicSalary";
    public static String COL_TOTALSALARY = "TotalSalary";
    public static String COL_BONUSSALARY = "BonusSalary";
    public static String COL_SALARYFINE = "SalaryFine";
    public static String COL_PAYABLESALARY = "PayAbleSalary";


    public static String CREATE_TABLE = "CREATE TABLE " + DATABASE_TABLE_NAME + " ( " +
            COL_USER_ID + " INTEGER PRIMARY KEY, " +
            COL_NAME + " TEXT NOT NULL," +
            COL_EMAIL + " TEXT NOT NULL," +
            COL_PHONE + " TEXT NOT NULL," +
            COL_COMPANIESWORKINGDAYSS + " TEXT NOT NULL," +
            COL_ATTENDANCECOUNT + "TEXT NOT NULL," +
            COL_BASICSALARY + "TEXT NOT NULL," +
            COL_TOTALSALARY + "TEXT NOT NULL,"+
            COL_BONUSSALARY + "TEXT NOT NULL,"+
            COL_SALARYFINE + "TEXT NOT NULL,"+
            COL_PAYABLESALARY +"TEXT NOT NULL);";

    /*
    public static String CREATE_TABLE = "CREATE TABLE " + DATABASE_TABLE_NAME + " ( " +
            COL_USER_ID + " INTEGER PRIMARY KEY, " +
            COL_NAME + " TEXT NOT NULL," +
            COL_EMAIL + " TEXT NOT NULL," +
            COL_PHONE + " TEXT NOT NULL);";*/

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
