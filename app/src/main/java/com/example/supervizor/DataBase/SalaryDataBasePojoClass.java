package com.example.supervizor.DataBase;

public class SalaryDataBasePojoClass {
    String name;
    String email;
    String phone;
    String companyiesWorkingDays;
    String attendanceCount;
    String basicSalary;
    String totalSalary;
    String bonusSalary;
    String salaryFine;
    String payAbleSalary;

    public SalaryDataBasePojoClass(String name, String email, String phone, String companyisWorkingDays, String attendanceCount, String basicSalary, String totalSalary, String bonusSalary, String salaryFine, String payAbleSalary) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.companyiesWorkingDays = companyisWorkingDays;
        this.attendanceCount = attendanceCount;
        this.basicSalary = basicSalary;
        this.totalSalary = totalSalary;
        this.bonusSalary = bonusSalary;
        this.salaryFine = salaryFine;
        this.payAbleSalary = payAbleSalary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyiesWorkingDays() {
        return companyiesWorkingDays;
    }

    public void setCompanyiesWorkingDays(String companyiesWorkingDays) {
        this.companyiesWorkingDays = companyiesWorkingDays;
    }

    public String getAttendanceCount() {
        return attendanceCount;
    }

    public void setAttendanceCount(String attendanceCount) {
        this.attendanceCount = attendanceCount;
    }

    public String getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(String basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(String totalSalary) {
        this.totalSalary = totalSalary;
    }

    public String getBonusSalary() {
        return bonusSalary;
    }

    public void setBonusSalary(String bonusSalary) {
        this.bonusSalary = bonusSalary;
    }

    public String getSalaryFine() {
        return salaryFine;
    }

    public void setSalaryFine(String salaryFine) {
        this.salaryFine = salaryFine;
    }

    public String getPayAbleSalary() {
        return payAbleSalary;
    }

    public void setPayAbleSalary(String payAbleSalary) {
        this.payAbleSalary = payAbleSalary;
    }
}
