package com.example.supervizor.Fragment.Company;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.supervizor.Activity.CompanyMainActivity;
import com.example.supervizor.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Calender_F extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
        //hide key pad start
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
//Hide key pad End
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //set Button background color
        CompanyMainActivity.employee_button_layout.setBackgroundColor(Color.parseColor("#000000"));
        CompanyMainActivity.calender_button_layout.setBackgroundColor(Color.parseColor("#00CCCC"));

        return inflater.inflate(R.layout.calender_f,container,false);
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
                .setActionBarTitle("Calender");
    }

}
