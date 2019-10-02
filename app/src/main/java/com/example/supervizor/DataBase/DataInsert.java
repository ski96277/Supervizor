package com.example.supervizor.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataInsert {

   static DataBaseHelper dataBaseHelper;
   static SQLiteDatabase db;


    public DataInsert(Context context) {
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

    public boolean insertData(SalaryDataBasePojoClass salaryDataBasePojoClass) {
        this.openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.COL_NAME, salaryDataBasePojoClass.name);
        contentValues.put(DataBaseHelper.COL_EMAIL, salaryDataBasePojoClass.email);
        contentValues.put(DataBaseHelper.COL_PHONE, salaryDataBasePojoClass.phone);
        contentValues.put(DataBaseHelper.COL_COMPANIESWORKINGDAYSS, salaryDataBasePojoClass.companyiesWorkingDays);
        contentValues.put(DataBaseHelper.COL_ATTENDANCECOUNT, salaryDataBasePojoClass.attendanceCount);
        contentValues.put(DataBaseHelper.COL_BASICSALARY, salaryDataBasePojoClass.basicSalary);
        contentValues.put(DataBaseHelper.COL_TOTALSALARY, salaryDataBasePojoClass.totalSalary);
        contentValues.put(DataBaseHelper.COL_BONUSSALARY, salaryDataBasePojoClass.bonusSalary);
        contentValues.put(DataBaseHelper.COL_SALARYFINE, salaryDataBasePojoClass.salaryFine);
        contentValues.put(DataBaseHelper.COL_PAYABLESALARY, salaryDataBasePojoClass.payAbleSalary);
        long number = db.insert(DataBaseHelper.DATABASE_TABLE_NAME, null, contentValues);
        this.closeBD();
        if (number > 0) {
            return true;
        } else {
            return false;
        }
    }


}
