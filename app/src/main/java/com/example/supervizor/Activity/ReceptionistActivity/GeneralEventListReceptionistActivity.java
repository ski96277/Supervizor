package com.example.supervizor.Activity.ReceptionistActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.supervizor.AdapterClass.General_Event_List_view_receptionist_Adapter;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
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

import java.util.ArrayList;
import java.util.List;

public class GeneralEventListReceptionistActivity extends AppCompatActivity {


    protected RecyclerView recyclerViewGeneralEventListViewReceptionist;
    private DatabaseReference databaseReference;
    private Check_User_information check_user_information;
    private General_Event_List_view_receptionist_Adapter general_event_list_view_receptionist_adapter;
    private List<Event_details_PojoClass> event_details_pojoClassList = new ArrayList<>();

    KAlertDialog dataLoading_alert;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_event_list_view_receptionist);
//hide notification
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("General Event");


        initView();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewGeneralEventListViewReceptionist.setLayoutManager(linearLayoutManager);

        if (!CheckInternet.isInternet(this)) {
            Toasty.error(this, "Check Internet Connection").show();
            return;
        }
        dataLoading_alert = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        dataLoading_alert.setTitleText("Loading...");
        dataLoading_alert.show();
        //get general event list
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AddEmployee_PojoClass addEmployee_pojoClass = dataSnapshot.child("employee_list")
                        .child(check_user_information.getUserID()).getValue(AddEmployee_PojoClass.class);

                for (DataSnapshot snapshot : dataSnapshot.child("Event_list")
                        .child(addEmployee_pojoClass.getCompany_User_id()).getChildren()) {

                    String date_key=snapshot.getKey();
                    for (DataSnapshot snapshot1 : dataSnapshot
                            .child("Event_list")
                            .child(addEmployee_pojoClass.getCompany_User_id()).child(date_key).getChildren()) {

                        Event_details_PojoClass event_details_pojoClass = snapshot1.getValue(Event_details_PojoClass.class);
                        event_details_pojoClassList.add(event_details_pojoClass);
                    }


                }
                dataLoading_alert.dismissWithAnimation();

                if (event_details_pojoClassList.isEmpty()) {
                    KAlertDialog noEventDialog = new KAlertDialog(GeneralEventListReceptionistActivity.this, KAlertDialog.WARNING_TYPE);
                    noEventDialog.setTitleText("NO Event Found");
                    noEventDialog.show();
                    noEventDialog.setConfirmClickListener(kAlertDialog -> {
                        noEventDialog.dismissWithAnimation();
                    });

                    return;
                }
                general_event_list_view_receptionist_adapter = new General_Event_List_view_receptionist_Adapter(GeneralEventListReceptionistActivity.this, event_details_pojoClassList);
                recyclerViewGeneralEventListViewReceptionist.setAdapter(general_event_list_view_receptionist_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.info(GeneralEventListReceptionistActivity.this, "Failed to Load Data").show();
                dataLoading_alert.dismissWithAnimation();
            }
        });

    }

    private void initView( ) {
        recyclerViewGeneralEventListViewReceptionist = (RecyclerView)findViewById(R.id.recyclerView_general_event_list_view_receptionist);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        check_user_information = new Check_User_information();
    }
}
