package com.example.supervizor.Activity.CommanActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.supervizor.R;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kinda.alert.KAlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    protected EditText currentPassETID;
    protected EditText newPasswordETID;
    protected EditText reTypePasswordETID;
    protected Button submitBtnID;
    private FirebaseUser firebase_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
        //hide Notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setActionBarTitle("Change Password");
    }

    private void initView() {
        currentPassETID = (EditText) findViewById(R.id.current_pass_ET_ID);
        newPasswordETID = (EditText) findViewById(R.id.new_password_ET_ID);
        reTypePasswordETID = (EditText) findViewById(R.id.re_type_password_ET_ID);
        submitBtnID = (Button) findViewById(R.id.submit_btn_ID);
        firebase_user = FirebaseAuth.getInstance().getCurrentUser();

        submitBtnID.setOnClickListener(ChangePasswordActivity.this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submit_btn_ID) {
            String current_passWord = currentPassETID.getText().toString();
            String new_password = newPasswordETID.getText().toString();
            String re_type_password = reTypePasswordETID.getText().toString();

            if (current_passWord.isEmpty()) {
                currentPassETID.requestFocus();
                currentPassETID.setError("current password ?");
                return;
            }
            if (new_password.isEmpty()) {
                newPasswordETID.requestFocus();
                newPasswordETID.setError("new password ?");
                return;
            }
            if (re_type_password.isEmpty()) {
                reTypePasswordETID.requestFocus();
                reTypePasswordETID.setError("re-type password ?");
                return;
            }
            if (!new_password.equals(re_type_password)) {
                Toasty.error(getApplicationContext(), "new password not matching").show();
                return;
            }
            updatePassword(current_passWord, new_password, re_type_password, firebase_user);

        }
    }

    private void updatePassword(String current_passWord, String new_password, String re_type_password, FirebaseUser firebase_user) {
        KAlertDialog kAlertDialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        kAlertDialog.setTitleText("Updating Password");
        kAlertDialog.show();

        final String email = firebase_user.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email, current_passWord);
        firebase_user.reauthenticate(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebase_user.updatePassword(new_password).addOnCompleteListener(task1 -> {
                    if (!task1.isSuccessful()) {

                        kAlertDialog.changeAlertType(KAlertDialog.ERROR_TYPE);
                        kAlertDialog.setTitleText("Something went wrong. Please try again later");

                    } else {
                        kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                        kAlertDialog.setTitleText("password updated");
                        kAlertDialog.setConfirmClickListener(kAlertDialog1 ->
                                startActivity(new Intent(getApplicationContext(), Login_Activity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)));

                    }
                });
            } else {
                Toasty.info(getApplicationContext(), "Authentication Failed").show();
                /*Snackbar snackbar_su = Snackbar
                        .make(coordinatorLayout, "Authentication Failed", Snackbar.LENGTH_LONG);
                snackbar_su.show();*/
            }
        });

    }


    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}
