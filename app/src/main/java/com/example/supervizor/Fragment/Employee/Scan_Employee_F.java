package com.example.supervizor.Fragment.Employee;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.supervizor.Activity.EmployeeMainActivity;
import com.example.supervizor.Activity.ScanResult_Activiy;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.R;
import com.kinda.alert.KAlertDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;

public class Scan_Employee_F extends Fragment implements View.OnClickListener {
    protected View rootView;
    protected Button entryTimeBtnID;
    protected Button exitTimeBtnID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scan_employee_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        initView(view);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.entry_Time_btn_ID) {
//check time set default
            if (isTimeAutomatic(getContext())){
                entryTimeMethod();
            }else {
                Toasty.info(getContext(),"Set Automatic date & time",Toasty.LENGTH_SHORT).show();
                startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
            }

        } else if (view.getId() == R.id.exit_time_btn_ID) {
 //check time set default
            if (isTimeAutomatic(getContext())){
                exitTimeMethod();
            }else {
                Toasty.info(getContext(),"Set Automatic date & time",Toasty.LENGTH_SHORT).show();
                startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
            }
        }
    }

    private void entryTimeMethod() {

        if (CheckInternet.isInternet(getContext())) {


            KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE);
            kAlertDialog.setTitleText("Entry Time");

            kAlertDialog.setContentText("Do You Want to save your Entry Time ?");
            kAlertDialog.show();
            kAlertDialog.showCancelButton(true);
            //set alert dialog action
            kAlertDialog.setConfirmClickListener(kAlertDialog1 -> startActivity(new Intent(getContext(), ScanResult_Activiy.class)
                    .putExtra("value", "entry")));
            kAlertDialog.setCancelClickListener(kAlertDialog12 -> kAlertDialog12.dismissWithAnimation());


        } else {
            Toasty.info(getContext(), "Check Internet Connection").show();
        }

    }

    private void exitTimeMethod() {

        if (CheckInternet.isInternet(getContext())) {

            KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE);
            kAlertDialog.setTitleText("Exit Time");
            kAlertDialog.setContentText("Do You Want to save your Exit Time ?");
            kAlertDialog.show();

            kAlertDialog.showCancelButton(true);
            //set alert dialog action
            kAlertDialog.setConfirmClickListener(kAlertDialog1 -> startActivity(new Intent(getContext(), ScanResult_Activiy.class)
                    .putExtra("value", "exit")));
            kAlertDialog.setCancelClickListener(kAlertDialog12 -> kAlertDialog12.dismissWithAnimation());


        } else {
            Toasty.info(getContext(), "Check Internet Connection").show();
        }
    }

    private void initView(View rootView) {
        entryTimeBtnID = (Button) rootView.findViewById(R.id.entry_Time_btn_ID);
        entryTimeBtnID.setOnClickListener(Scan_Employee_F.this);
        exitTimeBtnID = (Button) rootView.findViewById(R.id.exit_time_btn_ID);
        exitTimeBtnID.setOnClickListener(Scan_Employee_F.this);
    }

    //check the time & Date is set auto
    public static boolean isTimeAutomatic(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(c.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0) == 1;
        }
    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((EmployeeMainActivity) getActivity())
                .setActionBarTitle("Scan");
    }
}
