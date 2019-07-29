package com.example.supervizor.Fragment.Employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.supervizor.Activity.EmployeeMainActivity;
import com.example.supervizor.AdapterClass.All_Event_List_Adapter;
import com.example.supervizor.AdapterClass.All_Event_List_Adapter_For_Employee;
import com.example.supervizor.JavaPojoClass.Event_details_PojoClass;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

public class Employee_Calender_Home_Page_F extends Fragment{
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<Event_details_PojoClass> event_date_list = new ArrayList<>();

    Check_User_information check_user_information;
    String user_ID;
    KAlertDialog kAlertDialog;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.employee_calender_page_f,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
initialize(view);

        //get user ID
        check_user_information = new Check_User_information();
        user_ID = check_user_information.getUserID();

        if (!CheckInternet.isInternet(getActivity())) {
            Toasty.info(getActivity(), "Check Internet Connection").show();
            return;
        }
        kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        kAlertDialog.setTitleText("Loading.....");


        kAlertDialog.show();
//get the event list from Firebase

        databaseReference.child("Event_list")
                .child("2xc8CYa24aVGhHEMAwcbyx0hweF3")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        event_date_list.clear();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Event_details_PojoClass event_details_pojoClass = snapshot.getValue(Event_details_PojoClass.class);
                            event_date_list.add(event_details_pojoClass);
                        }


                        if (event_date_list!=null){
                            All_Event_List_Adapter_For_Employee event_list_adapter = new All_Event_List_Adapter_For_Employee(getContext(), event_date_list);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(event_list_adapter);
                            kAlertDialog.dismissWithAnimation();
                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        kAlertDialog.dismissWithAnimation();
                    }
                });

    }

    private void initialize(View view) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        recyclerView=view.findViewById(R.id.recycler_view_ID_Employee_Event);
    }


    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((EmployeeMainActivity) getActivity())
                .setActionBarTitle("Dashboard");
    }

}
