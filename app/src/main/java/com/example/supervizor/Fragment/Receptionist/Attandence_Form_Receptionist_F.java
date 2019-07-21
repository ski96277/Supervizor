package com.example.supervizor.Fragment.Receptionist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.supervizor.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;

public class Attandence_Form_Receptionist_F extends Fragment {

    EditText email_ET;
    EditText password_ET;
    Button button_login;

//    RadioButton entry_RadioButton;
//    RadioButton exit_RadioButton;
    RadioGroup radioGroup;
    private RadioButton radioButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.attandence_form_receptionist_f, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize(view);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFrom_Receptionist(v);
            }
        });
    }

    private void initialize(View view) {

//        entry_RadioButton = view.findViewById(R.id.entry_time_RD);
//        exit_RadioButton = view.findViewById(R.id.exit_time_RD);
        radioGroup=view.findViewById(R.id.radio_group_ID);
        email_ET = view.findViewById(R.id.email_reception_form_login_ET_ID);
        password_ET = view.findViewById(R.id.password_reception_form_login_ET_ID);
        button_login=view.findViewById(R.id.login_btn_receptionist_form_ID);


    }

    public void loginFrom_Receptionist(View v) {

//     int radio_id=   radioGroup.getCheckedRadioButtonId();


//        Boolean entry_True = entry_RadioButton.isSelected();
//        Boolean exit_True = exit_RadioButton.isSelected();
        String email = email_ET.getText().toString();
        String password = password_ET.getText().toString();
//
//        radioButton=(RadioButton)v.findViewById(radio_id);
//        String radioText = radioButton.getText().toString();


        if (email.isEmpty()) {
            email_ET.requestFocus();
            email_ET.setError("Email ?");
            return;
        }
        if (password.isEmpty()) {
            password_ET.requestFocus();
            password_ET.setError("password ?");
            return;
        }

//        if (radioText.equals("Entry Time")){
//            Toasty.info(getContext(),"Entry  time").show();
//        }
//        if (radioText.equals("Exit Time")){
//            Toasty.info(getContext(),"exit  time").show();
//        }

    }
}
