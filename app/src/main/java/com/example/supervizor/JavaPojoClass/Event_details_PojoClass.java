package com.example.supervizor.JavaPojoClass;

public class Event_details_PojoClass {

    String date;
    String day;
    String month;
    String year;
    String event_title;
    String event_details;
    String event_time;

    public Event_details_PojoClass() {
    }

    public Event_details_PojoClass(String date, String day, String month, String year, String event_title, String event_details,String event_time) {
        this.date = date;
        this.day = day;
        this.month = month;
        this.year = year;
        this.event_title = event_title;
        this.event_details = event_details;
        this.event_time=event_time;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
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

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_details() {
        return event_details;
    }

    public void setEvent_details(String event_details) {
        this.event_details = event_details;
    }
}
