package com.example.supervizor.Fragment.Employee;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.supervizor.Activity.EmployeeMainActivity;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

public class Add_New_Team_Mate_From_List_F extends Fragment {
    private RecyclerView recyclerviewIDAddNewTeamMateShow;
    private DatabaseReference databaseReference;
    private Add_Team_Mate_All_Employee_List_Adapter add_team_mate_all_employee_list_adapter;
    Check_User_information check_user_information;
    String company_user_ID;
    String team_name;
    String TAG = "TAG";

    ArrayList<AddEmployee_PojoClass> addEmployee_pojoClasses = new ArrayList<AddEmployee_PojoClass>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_team_mate_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        company_user_ID = sharedPreferences.getString("company_userID", "");
        team_name = sharedPreferences.getString("team_name", "");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerviewIDAddNewTeamMateShow.setLayoutManager(linearLayoutManager);
//check Internet Connection
        if (!CheckInternet.isInternet(getContext())) {
            Toasty.info(getContext(), "check internet Connection").show();
            return;
        }
        //get user list
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                for (DataSnapshot snapshot : dataSnapshot.child("employee_list_by_company")
                        .child(company_user_ID)
                        .getChildren()) {
                    AddEmployee_PojoClass addEmployee_pojoClass = snapshot.getValue(AddEmployee_PojoClass.class);

                    if (check_user_information.getUserID().equals(addEmployee_pojoClass.getEmployee_User_id())) {

                    } else if (dataSnapshot.child("my_team_request")
                            .child(check_user_information.getUserID())
                            .child(team_name).hasChild(addEmployee_pojoClass.getEmployee_User_id())) {

                    } else {
                        addEmployee_pojoClasses.add(addEmployee_pojoClass);
                    }

                }
                if (addEmployee_pojoClasses.isEmpty()) {
                    KAlertDialog kAlertDialog1 = new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE);
                    kAlertDialog1.show();
                    kAlertDialog1.setTitleText("No user Found");
                    kAlertDialog1.setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(KAlertDialog kAlertDialog) {
                            loadTeam_List_Page();
                            kAlertDialog1.dismissWithAnimation();
                        }
                    });

                }
                {

                    add_team_mate_all_employee_list_adapter =
                            new Add_Team_Mate_All_Employee_List_Adapter(addEmployee_pojoClasses);
                    recyclerviewIDAddNewTeamMateShow.setAdapter(add_team_mate_all_employee_list_adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "failed to load data from firebase", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadTeam_List_Page() {
        Fragment fragment = new My_Team_F();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment);
            fragmentTransaction.commit();
        }
    }

    private void initView(View rootView) {
        recyclerviewIDAddNewTeamMateShow = rootView.findViewById(R.id.recyclerview_ID_add_new_team_mate_show);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        check_user_information = new Check_User_information();
    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((EmployeeMainActivity) getActivity())
                .setActionBarTitle("Add Team Mate");
    }
}
