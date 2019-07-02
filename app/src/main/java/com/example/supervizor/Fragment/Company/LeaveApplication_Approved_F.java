package com.example.supervizor.Fragment.Company;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.supervizor.Activity.CompanyMainActivity;
import com.example.supervizor.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LeaveApplication_Approved_F extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        CompanyMainActivity.employee_and_calender_layout.setVisibility(View.GONE);
        CompanyMainActivity.pending_and_approved_layout.setVisibility(View.VISIBLE);

        CompanyMainActivity.pending_button_layout
                .setBackgroundColor(Color.parseColor("#000000"));
        CompanyMainActivity.approved_button_layout
                .setBackgroundColor(Color.parseColor("#00CCCC"));
//check nav leave application buttton
        CompanyMainActivity.navigationView.setCheckedItem(R.id.nav_leave_application);

        return inflater.inflate(R.layout.leave_application_approved_f, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //set title
    public void onResume(){
        super.onResume();
        // Set title bar
        ((CompanyMainActivity) getActivity())
                .setActionBarTitle("Leave Application");
    }

}
