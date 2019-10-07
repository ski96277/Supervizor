/*
package com.example.supervizor.Fragment.Employee;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.supervizor.Activity.EmployeeActivity.EmployeeMainActivity;
import com.example.supervizor.AdapterClass.Team_Event_List_Adapter_View_by_Team_member;
import com.example.supervizor.JavaPojoClass.Event_Details_Team_PojoClass;
import com.example.supervizor.R;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

public class Team_Event_View_As_A_Member extends Fragment {
    private RecyclerView recyclerView_Team_event_view_by_member;
    String team_leader_id;
    String team_name;
    private DatabaseReference databaseReference;
    private Event_Details_Team_PojoClass event_details_team_pojoClass;
    private List<Event_Details_Team_PojoClass> event_details_team_pojoClasses = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.team_event_show_as_a_member, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        Bundle bundle = getArguments();
        team_leader_id = bundle.getString("team_leader_id");
        team_name = bundle.getString("team_name");

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView_Team_event_view_by_member.setLayoutManager(linearLayoutManager);

        databaseReference.child("event_list_by_Team")
                .child(team_leader_id)
                .child(team_name)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                            event_details_team_pojoClass = snapshot.getValue(Event_Details_Team_PojoClass.class);
                            Log.e("TAG", "onDataChange: Date = " + event_details_team_pojoClass.getDate());

                            event_details_team_pojoClasses.add(event_details_team_pojoClass);
                        }
                        Log.e("TAG", "onDataChange:list size =  " + event_details_team_pojoClasses.size());
                        if (event_details_team_pojoClasses.isEmpty()) {
                            KAlertDialog emptyEventAlert = new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE);
                            emptyEventAlert.setTitleText("No Event Found");
                            emptyEventAlert.show();
                        }
                        Team_Event_List_Adapter_View_by_Team_member team_event_list_adapter_view_by_team_member = new Team_Event_List_Adapter_View_by_Team_member(event_details_team_pojoClasses);
                        recyclerView_Team_event_view_by_member.setAdapter(team_event_list_adapter_view_by_team_member);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toasty.error(getContext(), "Failed to load data ").show();
                    }
                });
    }

    private void initView(View rootView) {
        recyclerView_Team_event_view_by_member = rootView.findViewById(R.id.team_event_show_by_member);
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((EmployeeMainActivity) getActivity())
                .setActionBarTitle("Team Event");
    }
}
*/
