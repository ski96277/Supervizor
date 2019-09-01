package com.example.supervizor.Activity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.example.supervizor.Fragment.Employee.Employee_Attendance_F;
import com.example.supervizor.Fragment.Employee.Employee_Calender_Home_Page_F;
import com.example.supervizor.Fragment.Employee.Leave_Application_Employee_F;
import com.example.supervizor.Fragment.Employee.MyLeaveApplication_Employee_F;
import com.example.supervizor.Fragment.Employee.My_Team_F;
import com.example.supervizor.Fragment.Employee.ProfileView_Employee_F;
import com.example.supervizor.Fragment.Employee.Scan_Employee_F;
import com.example.supervizor.Fragment.Employee.Team_List_As_A_Member;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
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
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EmployeeMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    Fragment fragment;
    Toolbar toolbar;

    Button calender_Btn;
    Button scan_Btn;
    LinearLayout scan_calender_layout;
    LinearLayout calender_layout;
    LinearLayout scan_layout;

    CircleImageView circleImageView_nav;
    TextView name_TV_nav;
    TextView email_TV_nav;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Check_User_information check_user_information;
    String user_ID;
    String company_user_ID;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    //general event Notification

    public static final int JOB_ID = 102;
    private static final long REFRESH_INTERVAL = 3 * 1000; // 1 seconds
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setActionBarTitle("Dashboard");
//initialize the view
        initialize();
        //start general event notification service
//        startGeneralEventnotificationService();

        check_user_information = new Check_User_information();
        user_ID = check_user_information.getUserID();

        calender_Btn.setOnClickListener(this);
        scan_Btn.setOnClickListener(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_employee);
        navigationView.setNavigationItemSelectedListener(this);
        Menu nav_Menu = navigationView.getMenu();
//check if user is team leader then show the nav menu
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //check the user is team leader
                boolean yes = dataSnapshot.child("team_leader_ID_List")
                        .hasChild(user_ID);
                if (yes) {
                    nav_Menu.findItem(R.id.nav_My_Team_application_employee).setVisible(true);
                }

             /*   //if you are a team member
                for (DataSnapshot snapshot : dataSnapshot.child("my_team_request").getChildren()) {
                    String userID_key = snapshot.getKey();

                    for (DataSnapshot snapshot1 : dataSnapshot.child("my_team_request").child(userID_key).getChildren()) {
                        String team_name_key = snapshot1.getKey();

                        for (DataSnapshot snapshot2 : dataSnapshot.child("my_team_request").child(userID_key).child(team_name_key).getChildren()) {
                            Check_User_information user_ID = new Check_User_information();

                            boolean hasChild = dataSnapshot.child("my_team_request").child(userID_key).child(team_name_key)
                                    .child(snapshot2.getKey()).hasChild(user_ID.getUserID());

                            Log.e("TAG", "onDataChange: team leader = "+userID_key );
                            Log.e("TAG", "onDataChange: team_name_key = "+team_name_key );
                            Log.e("TAG", "onDataChange: user ID = "+snapshot2.getKey() );

                            Log.e("TAG", "onDataChange: user id Key " +snapshot2.getKey() );

                            if (hasChild) {
                                Toast.makeText(EmployeeMainActivity.this, "OK ", Toast.LENGTH_SHORT).show();
                                nav_Menu.findItem(R.id.nav_My_Team_as_member_application_employee).setVisible(true);
                            } else {
                                nav_Menu.findItem(R.id.nav_My_Team_as_member_application_employee).setVisible(false);
                                Toast.makeText(EmployeeMainActivity.this, "Not ", Toast.LENGTH_SHORT).show();


                            }
                        }
                    }
                }*/


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        View nav_view = navigationView.getHeaderView(0);

        circleImageView_nav = nav_view.findViewById(R.id.profile_image_employee_nav);
        name_TV_nav = nav_view.findViewById(R.id.profile_employee_name_TV_ID_nav);
        email_TV_nav = nav_view.findViewById(R.id.profile_employee_email_TV_ID_nav);
//get user data from firebase and set it on the nav bar view
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                AddEmployee_PojoClass addEmployee_pojoClass = dataSnapshot.child("employee_list").child(user_ID)
                        .getValue(AddEmployee_PojoClass.class);

                //subscribe topic for Firebase Notification

                //for general  event notification
                FirebaseMessaging.getInstance().subscribeToTopic(addEmployee_pojoClass.getCompany_User_id());
                company_user_ID = addEmployee_pojoClass.getCompany_User_id();
                //for leave application  notification
                FirebaseMessaging.getInstance().subscribeToTopic(addEmployee_pojoClass.getEmployee_User_id() + "leave_approved");
                //for personal event notification
                FirebaseMessaging.getInstance().subscribeToTopic(addEmployee_pojoClass.getEmployee_User_id())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg = "Success topic";
                                if (!task.isSuccessful()) {
                                    msg = "Failed Topic";
                                }
                                Log.d("TAG", "Topic suscribe by employee" + msg);
//                                Toast.makeText(EmployeeMainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
//end the notification topic


                editor.putString("company_userID", addEmployee_pojoClass.getCompany_User_id());
                editor.putString("userID_employee", addEmployee_pojoClass.getEmployee_User_id());
                editor.putString("user_type", "employee");

                editor.apply();

                if (!addEmployee_pojoClass.getEmployee_profile_image_link().equals("null")) {

                    Picasso.get().load(addEmployee_pojoClass.getEmployee_profile_image_link())
                            .into(circleImageView_nav);
                }
                name_TV_nav.setText(addEmployee_pojoClass.getEmployee_name());
                email_TV_nav.setText(addEmployee_pojoClass.getEmployee_email());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        circleImageView_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProfileFragment();
            }
        });
        name_TV_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProfileFragment();

            }
        });
        email_TV_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProfileFragment();

            }
        });

        //load default home fragment
        loadDefault_Home_Fragment();
    }

    /*  //start general event notification service
      @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
      private void startGeneralEventnotificationService() {
          ComponentName componentName = new ComponentName(this, GeneralEventNotification.class);

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

              jobInfo = new JobInfo.Builder(JOB_ID, componentName)
                      .setPeriodic(REFRESH_INTERVAL)
                      .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                      .setPersisted(true)
                      .build();
          } else {
              jobInfo = new JobInfo.Builder(JOB_ID, componentName)
                      .setPeriodic(REFRESH_INTERVAL)
                      .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                      .setPersisted(true)
                      .build();
          }
          jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

          //start Job schedule  Start
          int resultCode = jobScheduler.schedule(jobInfo);
          if (resultCode == JobScheduler.RESULT_SUCCESS) {
              Log.e("TAG", "startGeneralEventnotificationService: job started.........");
          } else {
              Log.e("TAG", "startGeneralEventnotificationService: job Failed.........");
          }
      }

  */
    private void initialize() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        scan_calender_layout = findViewById(R.id.linearLayout_id);

        calender_layout = findViewById(R.id.calender_employee_button_layout);
        scan_layout = findViewById(R.id.scan_employee_button_layout);
        calender_layout.setBackgroundColor(Color.parseColor("#00CCCC"));

        calender_Btn = findViewById(R.id.calender_employee_button);
        scan_Btn = findViewById(R.id.scan_employee_button);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.employee_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home_employee) {
            // Handle the camera action
            loadDefault_Home_Fragment();

        } else if (id == R.id.nav_attandence_employee) {
            load_Attendance_employee_Fragment();

        } else if (id == R.id.nav_leave_application_employee) {
            leave_application_Fragment();

        } else if (id == R.id.nav_My_leave_application_employee) {
            my_leave_application_Fragment();

        } else if (id == R.id.nav_My_Team_as_member_application_employee) {
            load_as_a_team_member_Fragment();

        } else if (id == R.id.nav_My_Team_application_employee) {
            load_my_team_employee_Fragment();

        } else if (id == R.id.nav_logout) {

            //for company general notification
            FirebaseMessaging.getInstance().unsubscribeFromTopic(company_user_ID);
            //for leave application  notification
            FirebaseMessaging.getInstance().unsubscribeFromTopic(check_user_information.getUserID() + "leave_approved");
            //for personal event notification
            FirebaseMessaging.getInstance().unsubscribeFromTopic(check_user_information.getUserID());


            FirebaseAuth.getInstance().signOut();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user == null) {
                Toasty.info(getApplicationContext(), "Log out").show();
                startActivity(new Intent(this, Login_Activity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void load_as_a_team_member_Fragment() {
        scan_calender_layout.setVisibility(View.GONE);
        //load default Fragment
        fragment = new Team_List_As_A_Member();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment);
            fragmentTransaction.commit();
        }
    }

    private void load_my_team_employee_Fragment() {
        scan_calender_layout.setVisibility(View.GONE);
        //load default Fragment
        fragment = new My_Team_F();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment);
            fragmentTransaction.commit();
        }
    }

    private void my_leave_application_Fragment() {
        scan_calender_layout.setVisibility(View.GONE);
        //load default Fragment
        fragment = new MyLeaveApplication_Employee_F();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment);
            fragmentTransaction.commit();
        }
    }

    private void leave_application_Fragment() {
        scan_calender_layout.setVisibility(View.GONE);
        //load default Fragment
        fragment = new Leave_Application_Employee_F();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment);
            fragmentTransaction.commit();
        }
    }

    private void loadProfileFragment() {
        scan_calender_layout.setVisibility(View.GONE);

//close the nav drawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        fragment = new ProfileView_Employee_F();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment);
            fragmentTransaction.commit();

        }

    }

    private void load_Attendance_employee_Fragment() {

        scan_calender_layout.setVisibility(View.GONE);
        //load default Fragment
        fragment = new Employee_Attendance_F();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment);
            fragmentTransaction.commit();
        }
    }

    private void loadDefault_Home_Fragment() {

        scan_calender_layout.setVisibility(View.VISIBLE);
        //load default Fragment
        fragment = new Employee_Calender_Home_Page_F();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment);
            fragmentTransaction.commit();
        }
    }

    private void load_Scan_Fragment() {

        scan_calender_layout.setVisibility(View.VISIBLE);
        //load default Fragment
        fragment = new Scan_Employee_F();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calender_employee_button:
                calender_layout.setBackgroundColor(Color.parseColor("#00CCCC"));
                scan_layout.setBackgroundColor(Color.parseColor("#000000"));
                loadDefault_Home_Fragment();

                break;
            case R.id.scan_employee_button:
                scan_layout.setBackgroundColor(Color.parseColor("#00CCCC"));
                calender_layout.setBackgroundColor(Color.parseColor("#000000"));
                load_Scan_Fragment();
                break;
        }
    }
}
