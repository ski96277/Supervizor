package com.example.supervizor.job_scheduler_For_Alert;

import android.app.NotificationChannel;
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
import com.example.supervizor.Java_Class.Check_User_information;
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

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MjobScheduler extends JobService {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public boolean onStartJob(JobParameters params) {

        //get company user id from local database
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String company_userID = preferences.getString("company_userID", "");
        String userID_receptionist = preferences.getString("userID_receptionist", "");

        Log.e("TAG - ", "onStartJob:company_userID "+company_userID );
        Log.e("TAG - ", "onStartJob:userID_receptionist "+userID_receptionist);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String company_status=dataSnapshot.child("alert_status_company")
                        .child(company_userID)
                        .child("alert_status").getValue(String.class);

                if (company_status.equals("1")){
                  //Notification
                  callNotification();

                   String reception_status =  dataSnapshot.child("alert_status_receptionist")
                            .child(company_userID)
                            .child(userID_receptionist)
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

    private void callNotification() {
         int noticationID = 100;
         int NumberMessage = 0;

         NotificationManager notificationManager;


        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(this);

        builder.setContentTitle("New Message");
        builder.setSmallIcon(R.drawable.email_icon);
        builder.setContentText("hello this is notification");
        builder.setTicker("message alert");
        builder.setSound(Uri.parse("android.resource://"
                + getApplicationContext().getPackageName() + "/" + R.raw.notification_sound));

        /* Increase notification number every time a new notification arrives */
        builder.setNumber(++NumberMessage);

        /* Creates an explicit intent for an Activity in your app */
        Intent resultInten = new Intent(this, ReceptionistMainActivity.class);

        /* Adds the Intent that starts the Activity to the top of the stack */
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(ReceptionistMainActivity.class);
        taskStackBuilder.addNextIntent(resultInten);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(noticationID, builder.build());

    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
