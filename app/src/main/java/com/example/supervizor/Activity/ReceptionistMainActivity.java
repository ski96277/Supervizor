package com.example.supervizor.Activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import com.example.supervizor.Fragment.Receptionist.Attandence_Form_Receptionist_F;
import com.example.supervizor.Fragment.Receptionist.Profile_view_receptionist_F;
import com.example.supervizor.Fragment.Receptionist.Receptionist_Attendance_F;
import com.example.supervizor.Fragment.Receptionist.Receptionist_Home_page;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.Notification_Service.GeneralEventNotification;
import com.example.supervizor.R;

import android.preference.PreferenceManager;
import android.view.View;

import com.example.supervizor.job_scheduler_For_Alert.Alert_jobScheduler;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ReceptionistMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    LinearLayout qr_code_layout;
    LinearLayout linearLayout_qr_code_form_btn;
    LinearLayout attendance_form_layout;
    Button qr_code_btn;
    Button attendance_form_btn;

    Fragment fragment;

    ImageView nav_Image_profile_receptionist;
    TextView nav_name_profile_receptionist;
    TextView nav_email_profile_receptionist;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public static final int JOB_ID = 101;
    public static final int JOB_ID_Notification = 102;
    private static final long REFRESH_INTERVAL  = 3 * 1000; // 1 seconds
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;
    Check_User_information check_user_information;

    SharedPreferences preferences;
    SharedPreferences.Editor editor ;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptionist_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setActionBarTitle("DashBoard");

//initilaze the widget
        initialize();
        //start background service

        startJobService();
        startGeneralEventnotificationService();

        CheckAlert_Is_Calling();

//set default color in button background
        qr_code_layout.setBackgroundColor(Color.parseColor("#01C1FF"));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View nav_header = navigationView.getHeaderView(0);

        nav_Image_profile_receptionist = nav_header.findViewById(R.id.profile_image_receptionist_nav);
        nav_name_profile_receptionist = nav_header.findViewById(R.id.profile_receptionist_name_TV_ID_nav);
        nav_email_profile_receptionist = nav_header.findViewById(R.id.profile_receptionist_email_TV_ID_nav);

        qr_code_btn.setOnClickListener(this);
        attendance_form_btn.setOnClickListener(this);

        nav_Image_profile_receptionist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ReceptionistMainActivity.this, "name TV", Toast.LENGTH_SHORT).show();
                loadProfileFragment();
            }
        });

        nav_name_profile_receptionist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProfileFragment();
            }
        });
        nav_email_profile_receptionist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProfileFragment();
            }
        });
        //get user information and set to nav bar view
        Check_User_information check_user_information = new Check_User_information();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AddEmployee_PojoClass addEmployee_pojoClass = dataSnapshot.child("employee_list").child(check_user_information.getUserID())
                        .getValue(AddEmployee_PojoClass.class);

//save company userID to local database
                String company_userID = addEmployee_pojoClass.getCompany_User_id();
                String userID_receptionist = addEmployee_pojoClass.getEmployee_User_id();
                editor.putString("company_userID",addEmployee_pojoClass.getCompany_User_id());
                editor.putString("userID_employee",addEmployee_pojoClass.getEmployee_User_id());
                editor.putString("user_type","receptionist");
                editor.apply();


                if (!addEmployee_pojoClass.getEmployee_profile_image_link().equals("null")) {

                    Picasso.get().load(addEmployee_pojoClass.getEmployee_profile_image_link())
                            .into(nav_Image_profile_receptionist);
                }

                nav_name_profile_receptionist.setText(addEmployee_pojoClass.getEmployee_name());
                nav_email_profile_receptionist.setText(addEmployee_pojoClass.getEmployee_email());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        loadDefaultFragment
        receptionistHomeFragment();

    }

    private void CheckAlert_Is_Calling() {
        check_user_information = new Check_User_information();
        //check if the activity start from job service
        String string_value = getIntent().getStringExtra("value");
        if (string_value != null && !string_value.isEmpty()) {
            if (string_value.equals("from_jobservice")) {

                //get company user id from local database
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                String company_userID = preferences.getString("company_userID", "");

                databaseReference.child("alert_status_receptionist")
                        .child(company_userID)
                        .child(check_user_information.getUserID())
                        .child("alert_status")
                        .setValue("0");

                final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.alertmusic);
                mp.start();

                KAlertDialog kAlertDialog = new KAlertDialog(this, KAlertDialog.WARNING_TYPE);
                kAlertDialog.setTitleText("Alert !");
                kAlertDialog.setContentText("Call From HR Head");
                kAlertDialog.show();
                kAlertDialog.showCancelButton(true);
                kAlertDialog.setCancelText("Cancel");
                kAlertDialog.setCancelClickListener(kAlertDialog12 -> {
                    Toasty.info(getApplicationContext(), "cancel").show();
                    kAlertDialog.dismissWithAnimation();
                    mp.stop();
                    mp.release();
                });
                kAlertDialog.setConfirmText("OK")
                        .setConfirmClickListener(kAlertDialog1 ->

                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String company_alert_status=dataSnapshot
                                .child("alert_status_company")
                                        .child(company_userID)
                                        .child("alert_status")
                                        .getValue(String.class);

                                if (company_alert_status.equals("0")){
                                    /*kAlertDialog.dismissWithAnimation();
                                    kAlertDialog.show();*/
                                    kAlertDialog.setTitleText("Call already Accepted, wait for next call");
                                    kAlertDialog.setConfirmClickListener(kAlertDialog2 -> kAlertDialog.dismissWithAnimation());

                                }else if (company_alert_status.equals("1")){

                                    databaseReference.child("alert_status_company")
                                            .child(company_userID)
                                            .child("alert_status")
                                            .setValue("0");
                                    kAlertDialog.dismiss();
                                    mp.stop();
                                    mp.release();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        }));
            }
        }
        //check if the activity start from job service  END
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startJobService() {

        ComponentName componentName = new ComponentName(this, Alert_jobScheduler.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                jobInfo = new JobInfo.Builder(JOB_ID, componentName)
//                        .setMinimumLatency(REFRESH_INTERVAL)
                        .setPeriodic(REFRESH_INTERVAL)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setPersisted(true)
                        .build();
        } else {
                jobInfo = new JobInfo.Builder(JOB_ID, componentName)
//                        .setMinimumLatency(REFRESH_INTERVAL)
                        .setPeriodic(REFRESH_INTERVAL)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setPersisted(true)
                        .build();
        }

  /*      JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName);
        builder.setPeriodic(1000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);

        jobInfo = builder.build();*/

        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        //start Job schedule  Start
        jobScheduler.schedule(jobInfo);
        //start Job schedule END
    }


    //start general event notification service
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startGeneralEventnotificationService() {
        ComponentName componentName=new ComponentName(this, GeneralEventNotification.class);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){

            jobInfo = new JobInfo.Builder(JOB_ID_Notification, componentName)
                    .setPeriodic(REFRESH_INTERVAL)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPersisted(true)
                    .build();
        }else {
            jobInfo = new JobInfo.Builder(JOB_ID_Notification, componentName)
                    .setPeriodic(REFRESH_INTERVAL)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPersisted(true)
                    .build();
        }
        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        //start Job schedule  Start
        jobScheduler.schedule(jobInfo);
    }


    //Profile Fragment Call
    private void loadProfileFragment() {
        linearLayout_qr_code_form_btn.setVisibility(View.GONE);

        fragment = new Profile_view_receptionist_F();
        if (fragment != null) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.receptionist_main_layout_ID, fragment);
            fragmentTransaction.commit();
        }
//close the nav drawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    private void receptionistHomeFragment() {
        fragment = new Receptionist_Home_page();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.receptionist_main_layout_ID, fragment);
        fragmentTransaction.commit();
    }

    private void receptionist_Attandence_Fragment() {
        fragment = new Receptionist_Attendance_F();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.receptionist_main_layout_ID, fragment);
        fragmentTransaction.commit();
    }

    private void receptionist_Form_Fragment() {
        fragment = new Attandence_Form_Receptionist_F();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.receptionist_main_layout_ID, fragment);
        fragmentTransaction.commit();
    }

    private void initialize() {
        linearLayout_qr_code_form_btn = findViewById(R.id.linearLayout);

        qr_code_layout = findViewById(R.id.qr_code_button_layout);
        attendance_form_layout = findViewById(R.id.attendance_button_form_layout);
        qr_code_btn = findViewById(R.id.qr_code_generate_button);
        attendance_form_btn = findViewById(R.id.attendance_submission_form_button);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
    }


    private void show_Alert_to_Exit_The_App() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to Exit this App?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            int count = getSupportFragmentManager().getBackStackEntryCount();

            if (count == 0) {
//                super.onBackPressed();
                //additional code
                show_Alert_to_Exit_The_App();
            } else {
                getSupportFragmentManager().popBackStack();
            }
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

        if (id == R.id.nav_home_reception) {
            linearLayout_qr_code_form_btn.setVisibility(View.VISIBLE);

            qr_code_layout.setBackgroundColor(Color.parseColor("#01C1FF"));
            attendance_form_layout.setBackgroundColor(Color.parseColor("#010D1B"));

            receptionistHomeFragment();
            // Handle the camera action
        } else if (id == R.id.nav_attandence_receptionist) {
            linearLayout_qr_code_form_btn.setVisibility(View.GONE);
            receptionist_Attandence_Fragment();


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_log_out) {


            //END Job schedule  Start
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                jobScheduler.cancel(JOB_ID);
            }

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
        switch (v.getId()) {
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

    //set title method
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}
