package com.example.supervizor.JavaPojoClass;

public class SalaryPolicyPojoClass {

    String policyTitleSt, amountSt, calculateTypeSpinner_plus, calculateTypeSpinner, salaryStatus;

    public SalaryPolicyPojoClass(String policyTitleSt, String amountSt, String calculateTypeSpinner_plus, String calculateTypeSpinner, String salaryStatus) {
        this.policyTitleSt = policyTitleSt;
        this.amountSt = amountSt;
        this.calculateTypeSpinner_plus = calculateTypeSpinner_plus;
        this.calculateTypeSpinner = calculateTypeSpinner;
        this.salaryStatus = salaryStatus;
    }

    public SalaryPolicyPojoClass() {
    }

    public String getPolicyTitleSt() {
        return policyTitleSt;
    }

    public void setPolicyTitleSt(String policyTitleSt) {
        this.policyTitleSt = policyTitleSt;
    }

    public String getAmountSt() {
        return amountSt;
    }

    public void setAmountSt(String amountSt) {
        this.amountSt = amountSt;
    }

    public String getCalculateTypeSpinner_plus() {
        return calculateTypeSpinner_plus;
    }

    public void setCalculateTypeSpinner_plus(String calculateTypeSpinner_plus) {
        this.calculateTypeSpinner_plus = calculateTypeSpinner_plus;
    }

    public String getCalculateTypeSpinner() {
        return calculateTypeSpinner;
    }

    public void setCalculateTypeSpinner(String calculateTypeSpinner) {
        this.calculateTypeSpinner = calculateTypeSpinner;
    }

    public String getSalaryStatus() {
        return salaryStatus;
    }

    public void setSalaryStatus(String salaryStatus) {
        this.salaryStatus = salaryStatus;
    }
}


