package com.example.supervizor.JavaPojoClass;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class SignUp_Pojo implements Serializable, Parcelable {

    String company_name;
    String company_location;
    String company_contact_number;
    String company_email;
    String company_password;
    String company_entry_time;
    String company_exit_time;
    String company_penalty_time;
    String company_working_day;
    String company_daley_count;
    String user_type;
    String company_user_id;
    String logo_download_url;


    public SignUp_Pojo(String company_name, String company_location, String company_contact_number, String company_email, String company_password) {
        this.company_name = company_name;
        this.company_location = company_location;
        this.company_contact_number = company_contact_number;
        this.company_email = company_email;
        this.company_password = company_password;
    }

    public SignUp_Pojo(String company_name, String company_location, String company_contact_number, String company_email, String company_password, String company_entry_time, String company_exit_time, String company_penalty_time) {
        this.company_name = company_name;
        this.company_location = company_location;
        this.company_contact_number = company_contact_number;
        this.company_email = company_email;
        this.company_password = company_password;
        this.company_entry_time = company_entry_time;
        this.company_exit_time = company_exit_time;
        this.company_penalty_time = company_penalty_time;
    }

    public SignUp_Pojo() {

    }

    public SignUp_Pojo(String company_name, String company_location, String company_contact_number, String company_email, String company_password, String company_entry_time, String company_exit_time, String company_penalty_time, String company_working_day, String company_daley_count, String user_type, String company_user_id, String logo_download_url) {
        this.company_name = company_name;
        this.company_location = company_location;
        this.company_contact_number = company_contact_number;
        this.company_email = company_email;
        this.company_password = company_password;
        this.company_entry_time = company_entry_time;
        this.company_exit_time = company_exit_time;
        this.company_penalty_time = company_penalty_time;
        this.company_working_day = company_working_day;
        this.company_daley_count = company_daley_count;
        this.user_type = user_type;
        this.company_user_id = company_user_id;
        this.logo_download_url = logo_download_url;
    }

    protected SignUp_Pojo(Parcel in) {
        company_name = in.readString();
        company_location = in.readString();
        company_contact_number = in.readString();
        company_email = in.readString();
        company_password = in.readString();
        company_entry_time = in.readString();
        company_exit_time = in.readString();
        company_penalty_time = in.readString();
        company_working_day = in.readString();
        company_daley_count = in.readString();
        user_type = in.readString();
        company_user_id = in.readString();
        logo_download_url = in.readString();
    }

    public static final Creator<SignUp_Pojo> CREATOR = new Creator<SignUp_Pojo>() {
        @Override
        public SignUp_Pojo createFromParcel(Parcel in) {
            return new SignUp_Pojo(in);
        }

        @Override
        public SignUp_Pojo[] newArray(int size) {
            return new SignUp_Pojo[size];
        }
    };


    public String getLogo_download_url() {
        return logo_download_url;
    }

    public void setLogo_download_url(String logo_download_url) {
        this.logo_download_url = logo_download_url;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_location() {
        return company_location;
    }

    public void setCompany_location(String company_location) {
        this.company_location = company_location;
    }

    public String getCompany_contact_number() {
        return company_contact_number;
    }

    public void setCompany_contact_number(String company_contact_number) {
        this.company_contact_number = company_contact_number;
    }

    public String getCompany_email() {
        return company_email;
    }

    public void setCompany_email(String company_email) {
        this.company_email = company_email;
    }

    public String getCompany_password() {
        return company_password;
    }

    public void setCompany_password(String company_password) {
        this.company_password = company_password;
    }

    public String getCompany_entry_time() {
        return company_entry_time;
    }

    public void setCompany_entry_time(String company_entry_time) {
        this.company_entry_time = company_entry_time;
    }

    public String getCompany_exit_time() {
        return company_exit_time;
    }

    public void setCompany_exit_time(String company_exit_time) {
        this.company_exit_time = company_exit_time;
    }

    public String getCompany_penalty_time() {
        return company_penalty_time;
    }

    public void setCompany_penalty_time(String company_penalty_time) {
        this.company_penalty_time = company_penalty_time;
    }

    public String getCompany_working_day() {
        return company_working_day;
    }

    public void setCompany_working_day(String company_working_day) {
        this.company_working_day = company_working_day;
    }

    public String getCompany_daley_count() {
        return company_daley_count;
    }

    public void setCompany_daley_count(String company_daley_count) {
        this.company_daley_count = company_daley_count;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getCompany_user_id() {
        return company_user_id;
    }

    public void setCompany_user_id(String company_user_id) {
        this.company_user_id = company_user_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(company_name);
        dest.writeString(company_location);
        dest.writeString(company_contact_number);
        dest.writeString(company_email);
        dest.writeString(company_password);
        dest.writeString(company_entry_time);
        dest.writeString(company_exit_time);
        dest.writeString(company_penalty_time);
        dest.writeString(company_working_day);
        dest.writeString(company_daley_count);
        dest.writeString(user_type);
        dest.writeString(company_user_id);
        dest.writeString(logo_download_url);
    }
}
