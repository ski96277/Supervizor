package com.example.supervizor.Fragment.Employee;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.supervizor.Activity.EmployeeMainActivity;
import com.example.supervizor.AdapterClass.Team_Member_List_Adapter;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
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

public class Team_Member_List_F extends Fragment {

    private RecyclerView recyclerViewTeamMemberList;
    String team_name;
    DatabaseReference databaseReference;
    Check_User_information check_user_information;

    ArrayList<AddEmployee_PojoClass> addEmployee_pojoClasses = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//for option menu
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.team_member_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            team_name = bundle.getString("team_name");
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("team_name", team_name);
            editor.apply();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewTeamMemberList.setLayoutManager(linearLayoutManager);

        //Load member list from data base
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String company_user_ID = dataSnapshot.child("employee_list").child(check_user_information.getUserID())
                        .child("company_User_id").getValue(String.class);
                //check request status
                String status = dataSnapshot.child("my_team_request_pending")
                        .child(company_user_ID)
                        .child(check_user_information.getUserID())
                        .child(team_name)
                        .child("status")
                        .getValue(String.class);
                if (status != null) {
                    if (status.equals("0")) {
                        KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE);
                        kAlertDialog.show();
                        kAlertDialog.setTitleText("You have no Permission");
                        kAlertDialog.setConfirmClickListener(kAlertDialog1 -> {
                            load_My_Team_F(kAlertDialog);
                        });
                        return;
                    }
                }

//if the team has no member go to the employee list
                if (!dataSnapshot.child("my_team_request")
                        .child(check_user_information.getUserID()).hasChild(team_name)) {

                    KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE);
                    kAlertDialog.show();
                    kAlertDialog.setTitleText("No user Found");
                    kAlertDialog.setContentText(" Add New Team Mate");
                    kAlertDialog.setConfirmClickListener(kAlertDialog12 -> {

                        loadNewteam_From_List();
                        kAlertDialog12.dismissWithAnimation();
                    });

                }

                for (DataSnapshot snapshot : dataSnapshot.child("my_team_request")
                        .child(check_user_information.getUserID()).child(team_name).getChildren()) {
                    String user_id = snapshot.getKey();


                    //get user information
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            AddEmployee_PojoClass addEmployee_pojoClass = dataSnapshot.child("employee_list").child(user_id).getValue(AddEmployee_PojoClass.class);
                            addEmployee_pojoClasses.add(addEmployee_pojoClass);

                            Team_Member_List_Adapter team_member_list_adapter = new Team_Member_List_Adapter(addEmployee_pojoClasses);

                            recyclerViewTeamMemberList.setAdapter(team_member_list_adapter);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        menu.findItem(R.id.add_team_mate).setVisible(true);
        menu.findItem(R.id.add_Event_to_this_Group).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_team_mate:
                loadNewteam_From_List();
                break;
            case R.id.add_Event_to_this_Group:
                loadEvent_Fragment_By_Team_Leader();
                break;
        }

        return false;
    }

    private void loadEvent_Fragment_By_Team_Leader() {
        Fragment fragment = new Event_list_and_Add_by_Team_Leader_F();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment);
            fragmentTransaction.commit();
        }
    }

    private void load_My_Team_F(KAlertDialog kAlertDialog) {
        Fragment fragment = new My_Team_F();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment);
            fragmentTransaction.commit();
            kAlertDialog.dismissWithAnimation();
        }
    }

    //Load Fragment
    private void loadNewteam_From_List() {
        Fragment fragment = new Add_New_Team_Mate_From_List_F();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment);
            fragmentTransaction.commit();
        }
    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((EmployeeMainActivity) getActivity())
                .setActionBarTitle("Team member (" + team_name + ")");
    }


    private void initView(View rootView) {
        recyclerViewTeamMemberList = (RecyclerView) rootView.findViewById(R.id.recyclerView_Team_member_List);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        check_user_information = new Check_User_information();
    }
}
