package com.example.supervizor.Fragment.Company;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.supervizor.Activity.CompanyMainActivity;
import com.example.supervizor.AdapterClass.All_Employee_List_Adapter;
import com.example.supervizor.Fragment.Company.Add_Employee;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.JavaPojoClass.SignUp_Pojo;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

public class Employee_list_F extends Fragment {

    public static ImageButton plus_icon_button;
    public static EditText search_ET;
    public static Spinner spinner_ET;
    int count = 0;
    Button add_employee_btn;
    Button add_receptionist_btn;
    LinearLayout add_emplo_layout, all_search_layout;

    //    SignUp_Pojo signUp_pojo;
    AddEmployee_PojoClass addEmployee_pojoClass;

    ArrayList<AddEmployee_PojoClass> addEmployee_pojoClasses;


    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference myDatabaseRef;

    RecyclerView recyclerview_all_employee_ID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CompanyMainActivity.employee_and_calender_layout.setVisibility(View.VISIBLE);
        CompanyMainActivity.pending_and_approved_layout.setVisibility(View.GONE);
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
        plus_icon_button.requestFocus();
        search_ET.requestFocus();
        spinner_ET.requestFocus();


        plus_icon_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count % 2 != 0) {
/*//font style
                    Typeface DoppioOne = Typeface.createFromAsset(getResources(),
                            "font/doppio_one_regular.ttf");
                    add_employee_btn.setTypeface(DoppioOne);*/

                    add_emplo_layout.setVisibility(View.VISIBLE);
                    all_search_layout.setBackgroundColor(Color.parseColor("#000000"));
                    spinner_ET.setVisibility(View.INVISIBLE);
                    spinner_ET.setBackgroundColor(Color.parseColor("#010D1B"));
                    search_ET.setVisibility(View.INVISIBLE);
                    search_ET.setBackgroundColor(Color.parseColor("#010D1B"));
                    plus_icon_button.setImageResource(R.drawable.cross_icon);


//hide key pad start
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
//Hide key pad End

                } else {

                    add_emplo_layout.setVisibility(View.GONE);
                    all_search_layout.setBackgroundColor(Color.parseColor("#F5F5F5"));
                    spinner_ET.setVisibility(View.VISIBLE);
                    search_ET.setVisibility(View.VISIBLE);

                    plus_icon_button.requestFocus();
                    search_ET.requestFocus();
                    spinner_ET.requestFocus();

                    spinner_ET.setBackgroundColor(Color.parseColor("#EEEDEF"));
                    search_ET.setBackgroundColor(Color.parseColor("#EEEDEF"));

                    plus_icon_button.setImageResource(R.drawable.plus);
                }
            }
        });
        //add employee btn
        add_employee_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new Add_Employee();


                if (fragment != null) {
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.company_main_screen, fragment);
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                }
            }
        });
        //add employee btn END
        add_receptionist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new Add_Receptionist();
                if (fragment != null) {
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.company_main_screen, fragment);
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                }
            }
        });

        Check_User_information check_user_information = new Check_User_information();
        String userID_company = check_user_information.getUserID();
        String email_company = check_user_information.getEmail();

//        Toasty.info(getContext(), userID_company).show();

        if (!CheckInternet.isInternet(getContext())){
            Toasty.info(getContext(),"internet Error").show();
            return;
        }

        KAlertDialog kAlertDialog=new KAlertDialog(getContext(),KAlertDialog.PROGRESS_TYPE);
        kAlertDialog.setTitleText("Data Loading");
        kAlertDialog.show();

        myDatabaseRef.child("employee_list_by_company").child(userID_company)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        addEmployee_pojoClasses.clear();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            AddEmployee_PojoClass addEmployeePojoClass = snapshot.getValue(AddEmployee_PojoClass.class);

                            Log.e("TAG", "onDataChange: " + addEmployeePojoClass.getEmployee_User_id());
//                            Toasty.info(getContext(), addEmployeePojoClass.getCompany_User_id()).show();
                            addEmployee_pojoClasses.add(addEmployeePojoClass);

                        }

                        All_Employee_List_Adapter all_employee_list_adapter=new All_Employee_List_Adapter(addEmployee_pojoClasses);

                        all_employee_list_adapter.notifyDataSetChanged();
                        
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                        recyclerview_all_employee_ID.setLayoutManager(linearLayoutManager);

                        recyclerview_all_employee_ID.setAdapter(all_employee_list_adapter);
                        //dismiss alert dialog
                        kAlertDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }


    private void initialization(View view) {
        plus_icon_button = view.findViewById(R.id.plus_icon_button);
        search_ET = view.findViewById(R.id.search_ET_ID);
        spinner_ET = view.findViewById(R.id.spinner_ID);
        add_employee_btn = view.findViewById(R.id.add_employee_btn);
        add_receptionist_btn = view.findViewById(R.id.add_receptionist_btn);
        add_emplo_layout = view.findViewById(R.id.add_emplo_layout);
        all_search_layout = view.findViewById(R.id.all_search_plus_layout);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myDatabaseRef = database.getReference();

        addEmployee_pojoClasses = new ArrayList<>();

        recyclerview_all_employee_ID=view.findViewById(R.id.recyclerview_all_employee_ID);

    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((CompanyMainActivity) getActivity())
                .setActionBarTitle("Dashboard");
    }

}
