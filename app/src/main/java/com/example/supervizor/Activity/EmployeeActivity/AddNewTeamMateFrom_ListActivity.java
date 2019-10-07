package com.example.supervizor.Activity.EmployeeActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.supervizor.AdapterClass.Add_Team_Mate_All_Employee_List_Adapter;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;

import java.util.ArrayList;

public class AddNewTeamMateFrom_ListActivity extends AppCompatActivity {

    private RecyclerView recyclerviewIDAddNewTeamMateShow;
    private DatabaseReference databaseReference;
    private Add_Team_Mate_All_Employee_List_Adapter add_team_mate_all_employee_list_adapter;
    Check_User_information check_user_information;
    String team_name;
    String TAG = "TAG";

    ArrayList<AddEmployee_PojoClass> addEmployee_pojoClasses = new ArrayList<AddEmployee_PojoClass>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_team_mate_f);
        //hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Add New Team Member");


        initView();

        Intent intent=getIntent();
         team_name = intent.getStringExtra("team_name");
        Toast.makeText(this, ""+team_name, Toast.LENGTH_SHORT).show();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerviewIDAddNewTeamMateShow.setLayoutManager(linearLayoutManager);
//check Internet Connection
        if (!CheckInternet.isInternet(this)) {
            Toasty.info(this, "check internet Connection").show();
            return;
        }
        //get user list
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                addEmployee_pojoClasses.clear();

                String company_user_ID = dataSnapshot.child("employee_list")
                        .child(check_user_information.getUserID())
                        .child("company_User_id").getValue(String.class);

                for (DataSnapshot snapshot : dataSnapshot.child("employee_list_by_company")
                        .child(company_user_ID)
                        .getChildren()) {

                    AddEmployee_PojoClass addEmployee_pojoClass = snapshot.getValue(AddEmployee_PojoClass.class);

                    Log.e("TAG - - : ", "onDataChange: getUserID "+check_user_information.getUserID() );
                    Log.e("TAG - - : ", "onDataChange: getUserID "+addEmployee_pojoClass.getEmployee_User_id() );
                    if (!check_user_information.getUserID().equals(addEmployee_pojoClass.getEmployee_User_id())) {

                        if (!dataSnapshot.child("my_team_request")
                                .child(check_user_information.getUserID())
                                .child(team_name).hasChild(addEmployee_pojoClass.getEmployee_User_id())) {
                            addEmployee_pojoClasses.add(addEmployee_pojoClass);

                        }

                    }

                }
                if (addEmployee_pojoClasses.isEmpty()) {
                    KAlertDialog kAlertDialog1 = new KAlertDialog(AddNewTeamMateFrom_ListActivity.this, KAlertDialog.WARNING_TYPE);

                    kAlertDialog1.setTitleText("No user Found");
                    if(!((Activity) getApplicationContext()).isFinishing())
                    {
                        //show dialog
                        kAlertDialog1.show();
                    }

                    kAlertDialog1.setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(KAlertDialog kAlertDialog) {
//                            loadTeam_List_Page();
                            kAlertDialog1.dismissWithAnimation();
                            startActivity(new Intent(getApplicationContext(), My_Team_List_Activity.class));
                        }
                    });

                } else {

                    add_team_mate_all_employee_list_adapter =
                            new Add_Team_Mate_All_Employee_List_Adapter(addEmployee_pojoClasses,team_name);
                    recyclerviewIDAddNewTeamMateShow.setAdapter(add_team_mate_all_employee_list_adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "failed to load data from firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        recyclerviewIDAddNewTeamMateShow = findViewById(R.id.recyclerview_ID_add_new_team_mate_show);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        check_user_information = new Check_User_information();
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,My_Team_List_Activity.class)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));

        super.onBackPressed();
    }
}
