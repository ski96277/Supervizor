package com.example.supervizor.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataInsert {

   static DataBaseHelper dataBaseHelper;
   static SQLiteDatabase db;


    public DataInsert(Context context) {

        db.execSQL("DROP TABLE IF EXISTS '" + DataBaseHelper.DATABASE_TABLE_NAME + "'");

        dataBaseHelper = new DataBaseHelper(context);
    }

    public static SQLiteDatabase getReadableDatabase() {
        db=dataBaseHelper.getReadableDatabase();

        return db;
    }

    public void openDB() {
        db = dataBaseHelper.getWritableDatabase();


    }

    public void closeBD() {

        db.close();
    }

    public boolean insertData(SalaryDataBasePojoClass salaryDataBase) {
        this.openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.COL_NAME, salaryDataBase.name);
        contentValues.put(DataBaseHelper.COL_EMAIL, salaryDataBase.email);
        contentValues.put(DataBaseHelper.COL_PHONE, salaryDataBase.phone);
        contentValues.put(DataBaseHelper.COL_COMPANIESWORKINGDAYSS, salaryDataBase.companyiesWorkingDays);
        contentValues.put(DataBaseHelper.COL_ATTENDANCECOUNT, salaryDataBase.attendanceCount);
        contentValues.put(DataBaseHelper.COL_BASICSALARY, salaryDataBase.basicSalary);
        contentValues.put(DataBaseHelper.COL_TOTALSALARY, salaryDataBase.totalSalary);
        contentValues.put(DataBaseHelper.COL_BONUSSALARY, salaryDataBase.bonusSalary);
        contentValues.put(DataBaseHelper.COL_SALARYFINE, salaryDataBase.salaryFine);
        contentValues.put(DataBaseHelper.COL_PAYABLESALARY, salaryDataBase.payAbleSalary);
        long number = db.insert(DataBaseHelper.DATABASE_TABLE_NAME, null, contentValues);
        this.closeBD();
        if (number > 0) {
            return true;
        } else {
            return false;
        }
    }
}
