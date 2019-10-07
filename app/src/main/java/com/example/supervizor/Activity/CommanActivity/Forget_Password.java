package com.example.supervizor.Activity.CommanActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.supervizor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.kinda.alert.KAlertDialog;

public class Forget_Password extends AppCompatActivity implements View.OnClickListener {

    EditText email_ET;
    Button send_main_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);
        initView();
        //hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setActionBarTitle("Forget Password");
    }

    private void initView() {
        email_ET=findViewById(R.id.email_ET_Reset__ID);
        send_main_button=findViewById(R.id.send_mail_btn_ID);
        send_main_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_mail_btn_ID:
                send_Email();
                break;
        }
    }

    private void send_Email() {
        KAlertDialog kAlertDialog=new KAlertDialog(this,KAlertDialog.PROGRESS_TYPE);
        kAlertDialog.setTitleText("Check Email Address");
        kAlertDialog.show();

        String email=email_ET.getText().toString();
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                            kAlertDialog.setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(KAlertDialog kAlertDialog) {
                                    kAlertDialog.dismissWithAnimation();
                               startActivity(new Intent(getApplicationContext(),Login_Activity.class)
                               .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                                }
                            });
                        }else {
                            kAlertDialog.changeAlertType(KAlertDialog.ERROR_TYPE);
                            kAlertDialog.setTitleText("Try again later");
                            kAlertDialog.setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(KAlertDialog kAlertDialog) {
                               kAlertDialog.dismissWithAnimation();
                                }
                            });
                        }
                    }
                });
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}
