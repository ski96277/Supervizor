package com.example.supervizor.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.supervizor.Fragment.Employee.Employee_Attendance_F;
import com.example.supervizor.Fragment.Employee.Employee_Calender_Home_Page_F;
import com.example.supervizor.Fragment.Employee.Leave_Application_Employee_F;
import com.example.supervizor.Fragment.Employee.ProfileView_Employee_F;
import com.example.supervizor.Fragment.Receptionist.Profile_view_receptionist_F;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
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
        check_user_information =new Check_User_information();
        user_ID=check_user_information.getUserID();

        calender_Btn.setOnClickListener(this);
        scan_Btn.setOnClickListener(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View nav_view=navigationView.getHeaderView(0);

        circleImageView_nav=nav_view.findViewById(R.id.profile_image_employee_nav);
        name_TV_nav=nav_view.findViewById(R.id.profile_employee_name_TV_ID_nav);
        email_TV_nav=nav_view.findViewById(R.id.profile_employee_email_TV_ID_nav);
//get user data from firebase and set it on the nav bar view
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AddEmployee_PojoClass addEmployee_pojoClass = dataSnapshot.child("employee_list").child(user_ID)
                        .getValue(AddEmployee_PojoClass.class);

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

    private void loadProfileFragment() {
        scan_calender_layout.setVisibility(View.GONE);

//close the nav drawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        fragment=new ProfileView_Employee_F();
        if (fragment!=null){
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.employee_main_layout_ID,fragment);
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

    private void initialize() {

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        scan_calender_layout=findViewById(R.id.linearLayout_id);

        calender_layout = findViewById(R.id.calender_employee_button_layout);
        scan_layout = findViewById(R.id.scan_employee_button_layout);
        calender_layout.setBackgroundColor(Color.parseColor("#00CCCC"));

        calender_Btn = findViewById(R.id.calender_employee_button);
        scan_Btn = findViewById(R.id.scan_employee_button);

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

        if (id == R.id.nav_home_employee) {
            // Handle the camera action
            loadDefault_Home_Fragment();

        } else if (id == R.id.nav_attandence_employee) {
            load_Attendance_employee_Fragment();

        } else if (id == R.id.nav_leave_application_employee) {
            leave_application_Fragment();

        } else if (id == R.id.nav_logout) {

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calender_employee_button:
                calender_layout.setBackgroundColor(Color.parseColor("#00CCCC"));
                scan_layout.setBackgroundColor(Color.parseColor("#000000"));

                break;
            case R.id.scan_employee_button:
                scan_layout.setBackgroundColor(Color.parseColor("#00CCCC"));
                calender_layout.setBackgroundColor(Color.parseColor("#000000"));

                break;
        }
    }
}
