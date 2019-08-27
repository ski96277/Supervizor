package com.example.supervizor.Fragment.Employee;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.supervizor.Activity.EmployeeMainActivity;
import com.example.supervizor.AdapterClass.Team_Name_List_Adapter;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

public class My_Team_F extends Fragment implements View.OnClickListener {

    protected View rootView;
    protected RecyclerView recyclerViewIDTeamList;
    private FloatingActionButton addNewTeamMate;
    Fragment fragment;
    Bundle bundle;

    DatabaseReference databaseReference;
    Check_User_information check_user_information;
    Team_Name_List_Adapter team_name_list_adapter;

    List<String> team_name_list = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_team_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewIDTeamList.setLayoutManager(gridLayoutManager);

//get tam list from database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //get team name
                for (DataSnapshot snapshot : dataSnapshot
                        .child("my_team_request")
                        .child(check_user_information.getUserID())
                        .getChildren()) {
                    String team_name = snapshot.getKey();
                    team_name_list.add(team_name);
                }
                //if have no team
                if (team_name_list.isEmpty()) {
                    KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE);
                    kAlertDialog.setContentText("You have no Team Yet");
                    kAlertDialog.show();
                    kAlertDialog.setConfirmClickListener(kAlertDialog1 -> {
                        kAlertDialog.dismissWithAnimation();
                    });
                }

                team_name_list_adapter = new Team_Name_List_Adapter(team_name_list);
                recyclerViewIDTeamList.setAdapter(team_name_list_adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addNew_team_mate) {

//            load_Add_new_team_mate_F();
            addTeamNameToDatabase();

        }
    }

    private void addTeamNameToDatabase() {
        Dialog dialog = new Dialog(getContext());
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_input_team_title_view);
        dialog.show();

        EditText editText = dialog.findViewById(R.id.teamTitleET_ID);
        Button button_cancel = dialog.findViewById(R.id.button_cancel);
        Button button_add = dialog.findViewById(R.id.button_add);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new My_Team_F();
                if (fragment != null) {
                    dialog.dismiss();
                }
            }
        });
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String team_name = editText.getText().toString();
//check the the team has exit
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean yes = dataSnapshot.child("my_team_request").child(check_user_information.getUserID())
                                .hasChild(team_name);
                        if (yes) {
                            Toasty.info(getContext(), "You can't set Same Team name \n Change the team name").show();
                        } else {
                            team_name_list.add(team_name);
                            if (team_name_list_adapter != null) {
                                team_name_list_adapter.notifyDataSetChanged();

                            }
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }

    private void initView(View rootView) {
        addNewTeamMate = (FloatingActionButton) rootView.findViewById(R.id.addNew_team_mate);
        addNewTeamMate.setOnClickListener(My_Team_F.this);
        bundle = new Bundle();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        check_user_information = new Check_User_information();
        recyclerViewIDTeamList = (RecyclerView) rootView.findViewById(R.id.recyclerView_ID_team_list);


    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((EmployeeMainActivity) getActivity())
                .setActionBarTitle("My Team");
    }
}
