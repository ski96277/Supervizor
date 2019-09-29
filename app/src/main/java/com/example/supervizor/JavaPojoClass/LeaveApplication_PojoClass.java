package com.example.supervizor.JavaPojoClass;

public class LeaveApplication_PojoClass {

    String user_ID_Employee;
    String user_ID_company;
    String leave_Title;
    String leave_description;
    String leave_start_date;
    String leave_End_Date;
    String leave_applying_Date;
    String profile_image_link;
    String leave_employee_name;
    String leave_employee_designation;
    String day;
    String month;
    String year;


    boolean leave_seen;

    public LeaveApplication_PojoClass() {
    }

    public String getLeave_employee_name() {
        return leave_employee_name;
    }

    public void setLeave_employee_name(String leave_employee_name) {
        this.leave_employee_name = leave_employee_name;
    }

    public String getLeave_employee_designation() {
        return leave_employee_designation;
    }

    public void setLeave_employee_designation(String leave_employee_designation) {
        this.leave_employee_designation = leave_employee_designation;
    }

    public LeaveApplication_PojoClass(String user_ID_Employee,
                                      String user_ID_company,
                                      String leave_Title,
                                      String leave_description,
                                      String leave_start_date,
                                      String leave_End_Date,
                                      String leave_day,
                                      String leave_month,
                                      String leave_year,
                                      String leave_applying_Date,
                                      String profile_image_link,
                                      String leave_employee_name,
                                      String leave_employee_designation,
                                      boolean leave_seen) {
        this.user_ID_Employee = user_ID_Employee;
        this.user_ID_company = user_ID_company;
        this.leave_Title = leave_Title;
        this.leave_description = leave_description;
        this.leave_start_date = leave_start_date;
        this.leave_End_Date = leave_End_Date;
        this.day=leave_day;
        this.month=leave_month;
        this.year=leave_year;
        this.leave_applying_Date = leave_applying_Date;
        this.profile_image_link = profile_image_link;
        this.leave_employee_name = leave_employee_name;
        this.leave_employee_designation = leave_employee_designation;
        this.leave_seen = leave_seen;
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

    public String getProfile_image_link() {
        return profile_image_link;
    }

    public void setProfile_image_link(String profile_image_link) {
        this.profile_image_link = profile_image_link;
    }

    public String getLeave_applying_Date() {
        return leave_applying_Date;
    }

    public void setLeave_applying_Date(String leave_applying_Date) {
        this.leave_applying_Date = leave_applying_Date;
    }

    public String getUser_ID_Employee() {
        return user_ID_Employee;
    }

    public void setUser_ID_Employee(String user_ID_Employee) {
        this.user_ID_Employee = user_ID_Employee;
    }

    public String getUser_ID_company() {
        return user_ID_company;
    }

    public void setUser_ID_company(String user_ID_company) {
        this.user_ID_company = user_ID_company;
    }

    public String getLeave_Title() {
        return leave_Title;
    }

    public void setLeave_Title(String leave_Title) {
        this.leave_Title = leave_Title;
    }

    public String getLeave_description() {
        return leave_description;
    }

    public void setLeave_description(String leave_description) {
        this.leave_description = leave_description;
    }

    public String getLeave_start_date() {
        return leave_start_date;
    }

    public void setLeave_start_date(String leave_start_date) {
        this.leave_start_date = leave_start_date;
    }

    public String getLeave_End_Date() {
        return leave_End_Date;
    }

    public void setLeave_End_Date(String leave_End_Date) {
        this.leave_End_Date = leave_End_Date;
    }

    public boolean isLeave_seen() {
        return leave_seen;
    }

    public void setLeave_seen(boolean leave_seen) {
        this.leave_seen = leave_seen;
    }
}
