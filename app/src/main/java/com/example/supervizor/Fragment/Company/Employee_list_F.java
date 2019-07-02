package com.example.supervizor.Fragment.Company;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.supervizor.Fragment.Company.Add_Employee;
import com.example.supervizor.JavaPojoClass.SignUp_Pojo;
import com.example.supervizor.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import es.dmoral.toasty.Toasty;

public class Employee_list_F extends Fragment {

    public static ImageButton plus_icon_button;
    public static EditText search_ET;
    public static Spinner spinner_ET;
    int count = 0;
    Button add_employee_btn;
    Button add_receptionist_btn;
    LinearLayout add_emplo_layout, all_search_layout;

    SignUp_Pojo signUp_pojo;


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
    }


    private void initialization(View view) {
        plus_icon_button = view.findViewById(R.id.plus_icon_button);
        search_ET = view.findViewById(R.id.search_ET_ID);
        spinner_ET = view.findViewById(R.id.spinner_ID);
        add_employee_btn = view.findViewById(R.id.add_employee_btn);
        add_receptionist_btn = view.findViewById(R.id.add_receptionist_btn);
        add_emplo_layout = view.findViewById(R.id.add_emplo_layout);
        all_search_layout = view.findViewById(R.id.all_search_plus_layout);
    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((CompanyMainActivity) getActivity())
                .setActionBarTitle("Dashboard");
    }

}
