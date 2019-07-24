package com.example.supervizor.Activity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.supervizor.R;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    protected EditText currentPassETID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_change_password);
        initView();
    }

    private void initView() {
        currentPassETID = (EditText) findViewById(R.id.current_pass_ET_ID);
    }
}
