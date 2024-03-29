package com.example.supervizor.Fragment.Employee;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.supervizor.Activity.EmployeeActivity.EmployeeMainActivity;
import com.example.supervizor.AdapterClass.All_Event_List_Adapter_For_Employee;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.JavaPojoClass.Event_details_PojoClass;
import com.example.supervizor.JavaPojoClass.Holiday_information;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.vo.DateData;

public class Employee_Calender_Home_Page_F extends Fragment implements View.OnClickListener {
    private Button allEventBtnID;
    private Button personalEventBtnID;
    private MCalendarView mCalendarView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<Event_details_PojoClass> event_date_list = new ArrayList<>();
    List<Event_details_PojoClass> event_personal_date_list = new ArrayList<>();

    Check_User_information check_user_information;
    String user_ID;
    RecyclerView recyclerView_all_event;
    RecyclerView recyclerView_personal_Event;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.employee_calender_page_f, container, false);
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

        if (!CheckInternet.isInternet(getContext())) {
//            kAlertDialog.dismissWithAnimation();
            Toasty.error(getContext(), "Check Internet Connection").show();
            return;
        }
//get the event list from Firebase
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AddEmployee_PojoClass addEmployee_pojoClass = dataSnapshot.child("employee_list")
                        .child(check_user_information.getUserID()).getValue(AddEmployee_PojoClass.class);
                String user_ID_company = addEmployee_pojoClass.getCompany_User_id();
                //get all event list
                databaseReference.child("Event_list")
                        .child(user_ID_company)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                event_date_list.clear();

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    String date = snapshot.getKey();

                                    for (DataSnapshot snapshot1 : dataSnapshot.child(date).getChildren()) {
                                        Event_details_PojoClass event_details_pojoClass = snapshot1.getValue(Event_details_PojoClass.class);

                                        event_date_list.add(event_details_pojoClass);

                                    }


                                    for (Event_details_PojoClass event_details_pojoClass : event_date_list) {
//highlight the calender
                                        if (event_details_pojoClass.getDate() != null) {
                                            mCalendarView.markDate(
                                                    new DateData(Integer.parseInt(event_details_pojoClass.getYear()),
                                                            Integer.parseInt(event_details_pojoClass.getMonth()),
                                                            Integer.parseInt(event_details_pojoClass.getDay()))
                                                            .setMarkStyle(new MarkStyle(MarkStyle.DEFAULT, Color.GREEN)));

                                        }

                                    }

                                }


                                if (event_date_list != null) {
                                    All_Event_List_Adapter_For_Employee event_list_adapter = new All_Event_List_Adapter_For_Employee(getContext(), event_date_list);

                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                                    recyclerView_all_event.setLayoutManager(linearLayoutManager);
                                    recyclerView_all_event.setAdapter(event_list_adapter);
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toasty.info(getContext(), "have some problem try it later").show();

                            }
                        });
                //get personal event
                databaseReference.child("personal_event")
                        .child(user_ID_company)
                        .child(check_user_information.getUserID())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                event_personal_date_list.clear();

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    Event_details_PojoClass event_details_pojoClass = snapshot.getValue(Event_details_PojoClass.class);
                                    event_personal_date_list.add(event_details_pojoClass);
                                }


                                if (event_personal_date_list != null) {
                                    Log.e("TAG - ", "onDataChange: size + " + event_personal_date_list.size());
                                    All_Event_List_Adapter_For_Employee event_list_adapter = new All_Event_List_Adapter_For_Employee(getContext(), event_personal_date_list);

                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                                    recyclerView_personal_Event.setLayoutManager(linearLayoutManager);
                                    recyclerView_personal_Event.setAdapter(event_list_adapter);
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toasty.info(getContext(), "have some problem try it later").show();
                            }
                        });

                //holiday list fo highlight
                databaseReference.child("holiday_list")
                        .child(user_ID_company)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Holiday_information holiday_information = snapshot.getValue(Holiday_information.class);

                                    mCalendarView.markDate(
                                            new DateData(Integer.parseInt(holiday_information.getYear()),
                                                    Integer.parseInt(holiday_information.getMonth()),
                                                    Integer.parseInt(holiday_information.getDay()))
                                                    .setMarkStyle(new MarkStyle(MarkStyle.DEFAULT, Color.RED)));

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.info(getContext(), "have some problem try it later").show();
            }
        });

    }

    private void initialize(View view) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        recyclerView_all_event = view.findViewById(R.id.recycler_view_ID_Employee_Event);
        recyclerView_personal_Event = view.findViewById(R.id.recycler_view_ID_Personal_Event);

        allEventBtnID = (Button) view.findViewById(R.id.all_event_btn_ID);
        personalEventBtnID = (Button) view.findViewById(R.id.personal_event_btn_ID);
        allEventBtnID.setOnClickListener(this);
        personalEventBtnID.setOnClickListener(this);

        mCalendarView = view.findViewById(R.id.calenderView_ID);
    }


    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((EmployeeMainActivity) getActivity())
                .setActionBarTitle("Dashboard");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_event_btn_ID:

                recyclerView_all_event.setVisibility(View.VISIBLE);
                recyclerView_personal_Event.setVisibility(View.GONE);
                allEventBtnID.setBackground(getResources().getDrawable(R.drawable.button_background_event_type_dark));
                personalEventBtnID.setBackground(getResources().getDrawable(R.drawable.button_background_event));

                break;
            case R.id.personal_event_btn_ID:

                recyclerView_all_event.setVisibility(View.GONE);
                recyclerView_personal_Event.setVisibility(View.VISIBLE);
                allEventBtnID.setBackground(getResources().getDrawable(R.drawable.button_background_event));
                personalEventBtnID.setBackground(getResources().getDrawable(R.drawable.button_background_event_type_dark));

                break;
        }
    }
}
