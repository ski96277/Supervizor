package com.example.supervizor.JavaPojoClass;

public class AddEmployee_PojoClass {

    private String employee_name;
    private String employee_designation;
    private String employee_email;
    private String employee_joinDate;
    private String employee_salary;
    private String employee_password;
    private String employee_status;
    private String employee_Contact_period_number;
    private String employee_Contact_period_year_OR_month;
    private String company_User_id;
    private String employee_User_id;
    private String employee_profile_image_link;
    private String user_type;
    private String user_phone_number;

    public AddEmployee_PojoClass() {
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

    public void setUser_phone_number(String user_phone_number) {
        this.user_phone_number = user_phone_number;
    }

    public AddEmployee_PojoClass(String employee_name, String employee_designation, String employee_email, String employee_joinDate, String employee_salary, String employee_password, String employee_status, String employee_Contact_period_number, String employee_Contact_period_year_OR_month, String company_User_id, String employee_User_id, String employee_profile_image_link, String user_type, String user_phone_number) {
        this.employee_name = employee_name;
        this.employee_designation = employee_designation;
        this.employee_email = employee_email;
        this.employee_joinDate = employee_joinDate;
        this.employee_salary = employee_salary;
        this.employee_password = employee_password;
        this.employee_status = employee_status;
        this.employee_Contact_period_number = employee_Contact_period_number;
        this.employee_Contact_period_year_OR_month = employee_Contact_period_year_OR_month;
        this.company_User_id = company_User_id;
        this.employee_User_id = employee_User_id;
        this.employee_profile_image_link = employee_profile_image_link;
        this.user_type = user_type;
        this.user_phone_number = user_phone_number;
    }

    public AddEmployee_PojoClass(String employee_name, String employee_designation, String employee_email, String employee_joinDate, String employee_salary, String employee_password, String employee_status, String employee_Contact_period_number, String employee_Contact_period_year_OR_month, String company_User_id, String employee_User_id, String employee_profile_image_link, String user_type) {
        this.employee_name = employee_name;
        this.employee_designation = employee_designation;
        this.employee_email = employee_email;
        this.employee_joinDate = employee_joinDate;
        this.employee_salary = employee_salary;
        this.employee_password = employee_password;
        this.employee_status = employee_status;
        this.employee_Contact_period_number = employee_Contact_period_number;
        this.employee_Contact_period_year_OR_month = employee_Contact_period_year_OR_month;
        this.company_User_id = company_User_id;
        this.employee_User_id = employee_User_id;
        this.employee_profile_image_link = employee_profile_image_link;
        this.user_type = user_type;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_designation() {
        return employee_designation;
    }

    public void setEmployee_designation(String employee_designation) {
        this.employee_designation = employee_designation;
    }

    public String getEmployee_email() {
        return employee_email;
    }

    public void setEmployee_email(String employee_email) {
        this.employee_email = employee_email;
    }

    public String getEmployee_joinDate() {
        return employee_joinDate;
    }

    public void setEmployee_joinDate(String employee_joinDate) {
        this.employee_joinDate = employee_joinDate;
    }

    public String getEmployee_salary() {
        return employee_salary;
    }

    public void setEmployee_salary(String employee_salary) {
        this.employee_salary = employee_salary;
    }

    public String getEmployee_password() {
        return employee_password;
    }

    public void setEmployee_password(String employee_password) {
        this.employee_password = employee_password;
    }

    public String getEmployee_status() {
        return employee_status;
    }

    public void setEmployee_status(String employee_status) {
        this.employee_status = employee_status;
    }

    public String getEmployee_Contact_period_number() {
        return employee_Contact_period_number;
    }

    public void setEmployee_Contact_period_number(String employee_Contact_period_number) {
        this.employee_Contact_period_number = employee_Contact_period_number;
    }

    public String getEmployee_Contact_period_year_OR_month() {
        return employee_Contact_period_year_OR_month;
    }

    public void setEmployee_Contact_period_year_OR_month(String employee_Contact_period_year_OR_month) {
        this.employee_Contact_period_year_OR_month = employee_Contact_period_year_OR_month;
    }

    public String getCompany_User_id() {
        return company_User_id;
    }

    public void setCompany_User_id(String company_User_id) {
        this.company_User_id = company_User_id;
    }

    public String getEmployee_User_id() {
        return employee_User_id;
    }

    public void setEmployee_User_id(String employee_User_id) {
        this.employee_User_id = employee_User_id;
    }

    public String getEmployee_profile_image_link() {
        return employee_profile_image_link;
    }

    public void setEmployee_profile_image_link(String employee_profile_image_link) {
        this.employee_profile_image_link = employee_profile_image_link;
    }
}
