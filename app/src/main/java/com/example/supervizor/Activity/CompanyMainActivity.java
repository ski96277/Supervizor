package com.example.supervizor.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.supervizor.Fragment.Company.Calender_F;
import com.example.supervizor.Fragment.Company.Company_Profile_View;
import com.example.supervizor.Fragment.Company.Employee_list_F;
import com.example.supervizor.Fragment.Company.LeaveApplication_Approved_F;
import com.example.supervizor.Fragment.Company.LeaveApplication_Pending_F;
import com.example.supervizor.Fragment.Company.TeamLeader_F;
import com.example.supervizor.Fragment.Company.Team_Request;
import com.example.supervizor.Fragment.Company.User_Attendance_F;
import com.example.supervizor.JavaPojoClass.AddReceptionist_PojoClass;
import com.example.supervizor.JavaPojoClass.LeaveApplication_PojoClass;
import com.example.supervizor.JavaPojoClass.SignUp_Pojo;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;

import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
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
import com.kinda.alert.KAlertDialog;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import es.dmoral.toasty.Toasty;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CompanyMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment = null;

    private Button button_employee, button_calender;
    private Button button_pending, button_approved;
    private ImageView profile_image_nav;
    private TextView profile_company_name_nav;
    private TextView profile_company_email_nav;
    public static TextView leave_notification_nav;
    private List<LeaveApplication_PojoClass> leaveApplication_pojoClasses_list = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference myDatabaseRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private SignUp_Pojo signUp_pojo;
    private Check_User_information check_user_information;

    public static NavigationView navigationView;
    public static LinearLayout employee_and_calender_layout;
    public static LinearLayout pending_and_approved_layout;
    public static LinearLayout employee_button_layout;
    public static LinearLayout calender_button_layout;
    public static LinearLayout pending_button_layout;
    public static LinearLayout approved_button_layout;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setActionBarTitle("Dashboard");
//hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initialize();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View nav_header = navigationView.getHeaderView(0);
        //set nav image action
        profile_image_nav = nav_header.findViewById(R.id.profile_image_nav);
        profile_company_name_nav = nav_header.findViewById(R.id.profile_company_name_TV_ID_nav);
        profile_company_email_nav = nav_header.findViewById(R.id.profile_company_email_TV_ID_nav);
//set profile information in navigation
        LoadCompanyinformation_On_The_Nav();

//set action into nav profile image and intent_text
        profile_image_nav.setOnClickListener(v -> {
            load_profile_From_navBar();
        });
        profile_company_name_nav.setOnClickListener(v -> {
            load_profile_From_navBar();
        });
        profile_company_email_nav.setOnClickListener(v -> {
            load_profile_From_navBar();
        });

//set leave notification count
        leave_notification_nav = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_leave_application));
        //Gravity property aligns the intent_text
        leave_notification_nav.setGravity(Gravity.CENTER_VERTICAL);
        leave_notification_nav.setTypeface(null, Typeface.BOLD);
        leave_notification_nav.setTextColor(getResources().getColor(R.color.red_Color));
//End Leave Application Count END

//load default fragment
        load_Employee_list(signUp_pojo);


//employee list show button_login
        button_employee.setOnClickListener(v -> load_Employee_list(signUp_pojo));
//Calender View show button_login
        button_calender.setOnClickListener(v -> calender_Fragment());
//load leave application Pending
        button_pending.setOnClickListener(v -> leave_application_pending_fragment());
//load leave application Approve
        button_approved.setOnClickListener(v -> leave_application_approved_fragment());

    }

    private void load_profile_From_navBar() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("singup_information", signUp_pojo);
        fragment = new Company_Profile_View();
        if (fragment != null) {
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.company_main_screen, fragment);
            fragmentTransaction.commit();

            DrawerLayout drawer1 = findViewById(R.id.drawer_layout);
            if (drawer1.isDrawerOpen(GravityCompat.START)) {
                drawer1.closeDrawer(GravityCompat.START);
            }
        }
    }
//set profile information in navigation

    private void LoadCompanyinformation_On_The_Nav() {
        myDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = null;
                currentUser = mAuth.getCurrentUser();
                if (currentUser == null) {
                    return;
                }

                if (currentUser != null) {
                    signUp_pojo = dataSnapshot.child("company_list").child(currentUser.getUid()).getValue(SignUp_Pojo.class);

                    if (!"null".equalsIgnoreCase(signUp_pojo.getLogo_download_url())) {
                        String logo_download_link = signUp_pojo.getLogo_download_url();
                        Picasso.get().load(logo_download_link).into(profile_image_nav);

                    }
                    String name = signUp_pojo.getCompany_name();
                    String email = signUp_pojo.getCompany_email();
                    profile_company_name_nav.setText(name);
                    profile_company_email_nav.setText(email);

                    leaveApplication_pojoClasses_list.clear();
                    for (DataSnapshot snapshot :
                            dataSnapshot.child("leave_application")
                                    .child(check_user_information.getUserID())
                                    .child("pending").getChildren()) {

                        LeaveApplication_PojoClass leaveApplication_pojoClass = snapshot.getValue(LeaveApplication_PojoClass.class);
                        if (leaveApplication_pojoClass.isLeave_seen()) {

                        } else {
                            leaveApplication_pojoClasses_list.add(leaveApplication_pojoClass);
                        }
                    }
                    leave_notification_nav.setText(String.valueOf(leaveApplication_pojoClasses_list.size()));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.info(getApplicationContext(), "Data Loading Failed, Try It later").show();
            }
        });


    }

    private void initialize() {
        check_user_information = new Check_User_information();

        button_employee = findViewById(R.id.employee_button);
        button_calender = findViewById(R.id.calender_button);

        button_pending = findViewById(R.id.pending_button);
        button_approved = findViewById(R.id.approved_button);


        employee_and_calender_layout = findViewById(R.id.employee_and_calender_layout_ID);
        pending_and_approved_layout = findViewById(R.id.pending_and_Approval_layout_ID);

        employee_button_layout = findViewById(R.id.employee_button_layout);
        calender_button_layout = findViewById(R.id.calender_button_layout);

        pending_button_layout = findViewById(R.id.pending_button_layout);
        approved_button_layout = findViewById(R.id.approved_button_layout);

        database = FirebaseDatabase.getInstance();
        myDatabaseRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

    }

    private void load_Employee_list(SignUp_Pojo signUp_pojo) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("singup_information", signUp_pojo);
        //Load default Fragment
        fragment = new Employee_list_F();
        if (fragment != null) {
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.company_main_screen, fragment);
            fragmentTransaction.commit();
        }
    }


    private void calender_Fragment() {

        Fragment fragment = new Calender_F();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.company_main_screen, fragment);
            fragmentTransaction.commit();
        }
    }

    //replace leave pending fragment in main screen of Company
    private void leave_application_pending_fragment() {
        fragment = new LeaveApplication_Pending_F();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.company_main_screen, fragment);
            //clear the back stack of fragment
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction.commit();
        }
    }

//replace leave approved fragment in main screen of Company

    private void leave_application_approved_fragment() {
        fragment = new LeaveApplication_Approved_F();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.addToBackStack("");
            fragmentTransaction.replace(R.id.company_main_screen, fragment);
            fragmentTransaction.commit();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.company_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_call_notification) {

            if (!CheckInternet.isInternet(this)) {
                Toasty.info(this, "Check Internet Connection").show();
            } else {
                callAlertReceptit();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void callAlertReceptit() {

        KAlertDialog kAlertDialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        kAlertDialog.show();
        kAlertDialog.setTitleText("Sending Request");


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Check_User_information check_user_information = new Check_User_information();

//check has the receptionist
               if (!dataSnapshot.child("receptionist_list_by_company").hasChild(check_user_information.getUserID()) ){
                    kAlertDialog.dismissWithAnimation();
                    Toasty.info(CompanyMainActivity.this, "No Receptionist Found").show();
                    return;
                }

                for (DataSnapshot snapshot : dataSnapshot.child("receptionist_list_by_company")
                        .child(check_user_information.getUserID()).getChildren()) {

                    Log.e("getChildrenCount - - ", "onDataChange: " + snapshot.getChildrenCount());
                    Log.e("getChildrenCount - - ", "onDataChange: " + snapshot.getKey());

                    databaseReference.child("alert_status_company").child(check_user_information.getUserID())
                            .child("alert_status").setValue("1");
                    databaseReference.child("alert_status_receptionist")
                            .child(check_user_information.getUserID())
                            .child(snapshot.getKey())
                            .child("alert_status").setValue("1")
                            .addOnCompleteListener(task -> {
                                kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                kAlertDialog.setTitleText("Request Send");
                                kAlertDialog.setConfirmClickListener(kAlertDialog1 -> {
                                    kAlertDialog.dismissWithAnimation();
                                });
                            });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                kAlertDialog.dismissWithAnimation();
            }
        });


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {

            load_Employee_list(signUp_pojo);

        } else if (id == R.id.nav_leave_application) {

            leave_application_pending_fragment();

        } else if (id == R.id.nav_Team_Leader) {

            load_Team_leader_F();


        }/* else if (id == R.id.nav_attendance) {
            load_User_Attendance();

        }*/ else if (id == R.id.nav_team_request_pending) {
            Bundle bundle = new Bundle();
            bundle.putString("error_handel_when_event_create", "1");
            load_Team_Request(bundle);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logOut) {
            FirebaseAuth.getInstance().signOut();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) {
                Toasty.info(getApplicationContext(), "Log out").show();
                startActivity(new Intent(this, Login_Activity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void load_Team_Request(Bundle bundle) {

        fragment = new Team_Request();
        if (fragment != null) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.company_main_screen, fragment);
            fragmentTransaction.commit();
        }
    }

    private void load_Team_leader_F() {
        fragment = new TeamLeader_F();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.company_main_screen, fragment);
            fragmentTransaction.commit();
        }

    }

    /*private void load_User_Attendance() {
        fragment = new User_Attendance_F();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.company_main_screen, fragment);
            fragmentTransaction.commit();
        }
    }*/


}
