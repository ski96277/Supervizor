package com.example.supervizor.Fragment.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.supervizor.Activity.ScanResult_Activiy;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.R;

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
            entryTimeMethod();

        } else if (view.getId() == R.id.exit_time_btn_ID) {
            exitTimeMethod();
        }
    }

    private void entryTimeMethod() {

        if (CheckInternet.isInternet(getContext())){

            startActivity(new Intent(getContext(), ScanResult_Activiy.class));

        }else {
            Toasty.info(getContext(),"Check Internet Connection").show();
        }

    }

    private void exitTimeMethod() {
        Toast.makeText(getContext(), "exit time", Toast.LENGTH_SHORT).show();

    }

    private void initView(View rootView) {
        entryTimeBtnID = (Button) rootView.findViewById(R.id.entry_Time_btn_ID);
        entryTimeBtnID.setOnClickListener(Scan_Employee_F.this);
        exitTimeBtnID = (Button) rootView.findViewById(R.id.exit_time_btn_ID);
        exitTimeBtnID.setOnClickListener(Scan_Employee_F.this);
    }
}
