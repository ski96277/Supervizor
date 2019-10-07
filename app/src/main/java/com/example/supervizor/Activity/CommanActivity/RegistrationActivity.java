package com.example.supervizor.Activity.CommanActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.supervizor.Fragment.Company.Registartion_page_1;
import com.example.supervizor.R;

public class RegistrationActivity extends AppCompatActivity {
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Sign Up");

        //hide notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fragment = new Registartion_page_1();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.registration_main_screen, fragment);
            fragmentTransaction.commit();
        }

    }

}
