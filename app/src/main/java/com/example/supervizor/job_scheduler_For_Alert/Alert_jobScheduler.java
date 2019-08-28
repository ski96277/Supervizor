package com.example.supervizor.job_scheduler_For_Alert;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.supervizor.Activity.ReceptionistMainActivity;
import com.example.supervizor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import es.dmoral.toasty.Toasty;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Alert_jobScheduler extends JobService {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public boolean onStartJob(JobParameters params) {

        //get company user id from local database
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String company_userID = preferences.getString("company_userID", "");
        String userID_employee = preferences.getString("userID_employee", "");

        Log.e("TAG - ", "onStartJob:company_userID " + company_userID);
        Log.e("TAG - ", "onStartJob:userID_employee " + userID_employee);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String company_status=dataSnapshot.child("alert_status_company")
                        .child(company_userID)
                        .child("alert_status").getValue(String.class);
              /*  if (!company_status.equals("1")){
                    return;
                }*/

                if ("1".equals(company_status)){

                   String reception_status =  dataSnapshot.child("alert_status_receptionist")
                            .child(company_userID)
                            .child(userID_employee)
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
