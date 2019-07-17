package com.example.supervizor.JavaPojoClass;

public class Holiday_information {

    String date;
    String day;
    String month;
    String year;
    String holiday_information;

    public Holiday_information() {
    }

    public Holiday_information(String date, String day, String month, String year, String holiday_information) {
        this.date = date;
        this.day = day;
        this.month = month;
        this.year = year;
        this.holiday_information = holiday_information;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHoliday_information() {
        return holiday_information;
    }

    public void setHoliday_information(String holiday_information) {
        this.holiday_information = holiday_information;
    }
}
