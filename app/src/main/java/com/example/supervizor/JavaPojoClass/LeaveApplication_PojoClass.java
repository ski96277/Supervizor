package com.example.supervizor.JavaPojoClass;

public class LeaveApplication_PojoClass {

    String user_ID_Employee;
    String user_ID_company;
    String leave_Title;
    String leave_description;
    String leave_start_date;
    String leave_End_Date;
    String leave_applying_Date;
    boolean leave_seen;

    public LeaveApplication_PojoClass() {
    }

    public LeaveApplication_PojoClass(String user_ID_Employee, String user_ID_company, String leave_Title, String leave_description, String leave_start_date, String leave_End_Date, String leave_applying_Date, boolean leave_seen) {
        this.user_ID_Employee = user_ID_Employee;
        this.user_ID_company = user_ID_company;
        this.leave_Title = leave_Title;
        this.leave_description = leave_description;
        this.leave_start_date = leave_start_date;
        this.leave_End_Date = leave_End_Date;
        this.leave_applying_Date = leave_applying_Date;
        this.leave_seen = leave_seen;
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
