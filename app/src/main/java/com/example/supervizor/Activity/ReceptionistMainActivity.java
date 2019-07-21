package com.example.supervizor.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.supervizor.Fragment.Receptionist.Attandence_Form_Receptionist_F;
import com.example.supervizor.Fragment.Receptionist.Receptionist_Home_page;
import com.example.supervizor.R;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import es.dmoral.toasty.Toasty;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

public class ReceptionistMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    LinearLayout qr_code_layout;
    LinearLayout attendance_form_layout;
    Button qr_code_btn;
    Button attendance_form_btn;

    Fragment fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptionist_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();
//set default color in button background
        qr_code_layout.setBackgroundColor(Color.parseColor("#01C1FF"));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        qr_code_btn.setOnClickListener(this);
        attendance_form_btn.setOnClickListener(this);

//        loadDefaultFragment
receptionistHomeFragment();

    }

    private void receptionistHomeFragment() {
        fragment=new Receptionist_Home_page();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.receptionist_main_layout_ID,fragment);
        fragmentTransaction.commit();
    }
    private void receptionist_Form_Fragment() {
        fragment=new Attandence_Form_Receptionist_F();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.receptionist_main_layout_ID,fragment);
        fragmentTransaction.commit();
    }

    private void initialize() {
        qr_code_layout=findViewById(R.id.qr_code_button_layout);
        attendance_form_layout=findViewById(R.id.attendance_button_form_layout);

        qr_code_btn=findViewById(R.id.qr_code_generate_button);
        attendance_form_btn=findViewById(R.id.attendance_submission_form_button);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.receptionist_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_log_out) {
            
            FirebaseAuth.getInstance().signOut();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) {
                Toasty.info(getApplicationContext(), "Log out").show();
                startActivity(new Intent(this, Login_Activity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.qr_code_generate_button:
                qr_code_layout.setBackgroundColor(Color.parseColor("#01C1FF"));
                attendance_form_layout.setBackgroundColor(Color.parseColor("#010D1B"));
                receptionistHomeFragment();

                break;
            case R.id.attendance_submission_form_button:
                receptionist_Form_Fragment();
                qr_code_layout.setBackgroundColor(Color.parseColor("#010D1B"));
                attendance_form_layout.setBackgroundColor(Color.parseColor("#01C1FF"));
                break;
        }
    }
}
