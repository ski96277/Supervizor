package com.example.supervizor.Fragment.Company;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.supervizor.Activity.CompanyActivity.Add_Employee_Activity_by_Admin;
import com.example.supervizor.Activity.CompanyActivity.Add_Receptionist_Activity_by_Admin;
import com.example.supervizor.Activity.CompanyActivity.CompanyMainActivity;
import com.example.supervizor.AdapterClass.All_Employee_List_Adapter;
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
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Util;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;


public class Employee_list_F extends Fragment {

//    private Button plus_icon_button;
    private SearchView search_ET;
    //    private Spinner spinner_ET;
   /* private int count = 0;
    private Button add_employee_btn;
    private Button add_receptionist_btn;
    private LinearLayout add_emplo_layout, all_search_layout;*/
    private TextView no_employee_found_TV_ID;


    private ArrayList<AddEmployee_PojoClass> addEmployee_pojoClasses;

    private All_Employee_List_Adapter all_employee_list_adapter;

    private BoomMenuButton bmb;


    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference myDatabaseRef;

    RecyclerView recyclerview_all_employee_ID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CompanyMainActivity.employee_and_calender_layout.setVisibility(View.VISIBLE);
//        CompanyMainActivity.pending_and_approved_layout.setVisibility(View.GONE);
//set Button background color
        CompanyMainActivity.employee_button_layout.setBackgroundColor(Color.parseColor("#00CCCC"));
        CompanyMainActivity.calender_button_layout.setBackgroundColor(Color.parseColor("#000000"));
//checked nav menu
        CompanyMainActivity.navigationView.setCheckedItem(R.id.nav_home);

        return inflater.inflate(R.layout.employee_list_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization(view);
        //set focus able
        search_ET.requestFocus();


        assert bmb != null;

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder();
            builder.normalImageRes(R.drawable.team_member);
            builder.buttonHeight(150);
//            builder.normalColor(Color.parseColor("#000000"));
            builder.imageRect(new Rect(0, 0, Util.dp2px(60), Util.dp2px(60)));
            builder.textSize(21);
            builder.subHighlightedTextColor(Color.parseColor("#FFFFFF"));
            builder.subNormalTextColor(Color.parseColor("#FFFFFF"));
            builder.imagePadding(new Rect(10, 10, 10, 10));

            if (i==0){
                builder.normalText("Add Employee")
                        .subNormalText("Add employee to your company");

            }
            if (i==1){
                builder.normalText("Add Receptionist");
                builder.subNormalText("Add Receptionist to your company");

            }
            builder.listener(index -> {

                if (index==0){

                    if (!CheckInternet.isInternet(getContext())) {
                        Toasty.info(getContext(), "Check internet Connection").show();
                        return;
                    }
                    startActivity(new Intent(getContext(), Add_Employee_Activity_by_Admin.class));


                }
                if (index==1){

                    if (!CheckInternet.isInternet(getContext())) {
                        Toasty.info(getContext(), "Check internet Connection").show();
                        return;
                    }
                    startActivity(new Intent(getContext(), Add_Receptionist_Activity_by_Admin.class));

                }
            });
            bmb.addBuilder(builder);
        }
        //end boom menu



        Check_User_information check_user_information = new Check_User_information();
        String userID_company = check_user_information.getUserID();

        if (!CheckInternet.isInternet(getContext())) {
            Toasty.info(getContext(), "Check internet Connection").show();
            return;
        }

        KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        kAlertDialog.setTitleText("Loading....");
        kAlertDialog.show();

        myDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                addEmployee_pojoClasses.clear();

                for (DataSnapshot snapshot : dataSnapshot.child("employee_list_by_company").child(userID_company)
                        .getChildren()) {

                    AddEmployee_PojoClass addEmployeePojoClass = snapshot.getValue(AddEmployee_PojoClass.class);

                    addEmployee_pojoClasses.add(addEmployeePojoClass);

                }

                if (addEmployee_pojoClasses.isEmpty()) {
                    no_employee_found_TV_ID.setVisibility(View.VISIBLE);
                    recyclerview_all_employee_ID.setVisibility(View.GONE);
//                    Toasty.info(getActivity(),"No Data Found").show();
                }

                all_employee_list_adapter = new All_Employee_List_Adapter(addEmployee_pojoClasses);

                all_employee_list_adapter.notifyDataSetChanged();

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                recyclerview_all_employee_ID.setLayoutManager(linearLayoutManager);

                recyclerview_all_employee_ID.setAdapter(all_employee_list_adapter);
                //dismiss alert dialog
                kAlertDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //search action

        search_ET.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                all_employee_list_adapter.getFilter().filter(query);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                all_employee_list_adapter.getFilter().filter(newText);

                return true;
            }
        });
    }


    private void initialization(View view) {
        no_employee_found_TV_ID = view.findViewById(R.id.no_employee_found_TV_ID);
//        plus_icon_button = view.findViewById(R.id.plus_icon_button);
        search_ET = view.findViewById(R.id.search_ET_ID);
        bmb = view.findViewById(R.id.bmb);
//        add_employee_btn = view.findViewById(R.id.add_employee_btn);
//        add_receptionist_btn = view.findViewById(R.id.add_receptionist_btn);
//        add_emplo_layout = view.findViewById(R.id.add_emplo_layout);
//        all_search_layout = view.findViewById(R.id.all_search_plus_layout);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myDatabaseRef = database.getReference();

        addEmployee_pojoClasses = new ArrayList<>();

        recyclerview_all_employee_ID = view.findViewById(R.id.recyclerview_all_employee_ID);

    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((CompanyMainActivity) getActivity())
                .setActionBarTitle("Dashboard");
    }

}
