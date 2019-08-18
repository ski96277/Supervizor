package com.example.supervizor.job_scheduler_For_Alert;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.example.supervizor.Activity.ReceptionistMainActivity;
import com.example.supervizor.Java_Class.Check_User_information;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MjobScheduler extends JobService {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Check_User_information check_user_information;

    @Override
    public boolean onStartJob(JobParameters params) {

        //get company user id from local database
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String company_userID = preferences.getString("company_userID", "");


        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        check_user_information=new Check_User_information();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String company_status=dataSnapshot.child("alert_status_company")
                        .child(company_userID)
                        .child("alert_status").getValue(String.class);

                if (company_status.equals("1")){
                   String reception_status =  dataSnapshot.child("alert_status_receptionist")
                            .child(company_userID)
                            .child(check_user_information.getUserID())
                            .child("alert_status")
                            .getValue(String.class);

                   if (reception_status.equals("1")){

                       startActivity(new Intent(getApplicationContext(), ReceptionistMainActivity.class)
                               .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK)
                               .putExtra("value","from_jobservice"));
                   }

                }
                jobFinished(params,false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
