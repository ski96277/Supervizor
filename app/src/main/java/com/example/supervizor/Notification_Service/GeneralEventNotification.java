package com.example.supervizor.Notification_Service;

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

import com.example.supervizor.Activity.EmployeeMainActivity;
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

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class GeneralEventNotification extends JobService {


    @Override
    public boolean onStartJob(JobParameters params) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        //get company user id from local database
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String company_userID = preferences.getString("company_userID", "");
        String userID_employee = preferences.getString("userID_employee", "");

        Log.e("TAG - ", "onStartJob:company_userID " + company_userID);
        Log.e("TAG - ", "onStartJob:userID_employee " + userID_employee);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String value_event  = dataSnapshot.child("event_notification_status")
                        .child(company_userID)
                        .child(userID_employee)
                        .child("status")
                        .getValue(String.class);

                String value_holiday_event = dataSnapshot.child("holiday_event_notification_status")
                        .child(company_userID)
                        .child(userID_employee)
                        .child("status")
                        .getValue(String.class);


                Log.e("TAG", "onDataChange: value_holiday_event --" + value_holiday_event);


                String  value_personal_event = dataSnapshot.child("personal_Event_notification")
                        .child(userID_employee)
                        .child("status")
                        .getValue(String.class);

                if (value_event != null) {

                    if (value_event.equals("1")) {

                        databaseReference.child("event_notification_status")
                                .child(company_userID)
                                .child(userID_employee)
                                .child("status")
                                .setValue("0");

                        String title = dataSnapshot.child("event_notification_text")
                                .child(company_userID)
                                .child("event_title")
                                .getValue(String.class);
                        String details = dataSnapshot.child("event_notification_text")
                                .child(company_userID)
                                .child("event_details")
                                .getValue(String.class);

                        callNotification_General_Event(title, details);
                    }
                }

                if (value_holiday_event!=null){

                    if (value_holiday_event.equals("1")) {


                        databaseReference.child("holiday_event_notification_status")
                                .child(company_userID)
                                .child(userID_employee)
                                .child("status")
                                .setValue("0");

                        String date = dataSnapshot.child("holiday_event_notification_text")
                                .child(company_userID)
                                .child("date")
                                .getValue(String.class);
                        String details = dataSnapshot.child("holiday_event_notification_text")
                                .child(company_userID)
                                .child("holiday_information")
                                .getValue(String.class);
                        callNotification_holiday_Event(date, details);
                    }
                }
                if (value_personal_event!=null){

                    if (value_personal_event.equals("1")) {

                        databaseReference.child("personal_Event_notification")
                                .child(userID_employee)
                                .child("status")
                                .setValue("0");

                        String event_title = dataSnapshot.child("personal_Event_notification_Text")
                                .child(userID_employee)
                                .child("event_title")
                                .getValue(String.class);
                        String details = dataSnapshot.child("personal_Event_notification_Text")
                                .child(userID_employee)
                                .child("event_details")
                                .getValue(String.class);

                        callNotification_personal_Event(event_title, details);

                    }
                }


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


    private void callNotification_General_Event(String title, String details) {
        int noticationID = 101;
        int NumberMessage = 0;

        NotificationManager notificationManager;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        //set oreo setting
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel("my_channel_01",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }


        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(this);

        builder.setContentTitle(title);
        builder.setSmallIcon(R.drawable.email_icon);
        builder.setContentText(details);
        builder.setTicker("Event Notification");
        builder.setSound(Uri.parse("android.resource://"
                + getApplicationContext().getPackageName() + "/" + R.raw.notification_sound));

        /* Increase notification number every time a new notification arrives */
        builder.setNumber(++NumberMessage);
        builder.setAutoCancel(true);

        /* Creates an explicit intent for an Activity in your app */
        Intent resultInten = new Intent(this, EmployeeMainActivity.class);

        /* Adds the Intent that starts the Activity to the top of the stack */
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(ReceptionistMainActivity.class);
        taskStackBuilder.addNextIntent(resultInten);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        notificationManager.notify(noticationID, builder.build());

    }

    private void callNotification_holiday_Event(String date, String details) {
        int noticationID = 102;
        int NumberMessage = 0;

        NotificationManager notificationManager;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        //set oreo setting
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel("my_channel_01",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(this);

        builder.setContentTitle("Holiday = " + date);
        builder.setSmallIcon(R.drawable.email_icon);
        builder.setContentText(details);
        builder.setTicker("Holiday Notification");
        builder.setSound(Uri.parse("android.resource://"
                + getApplicationContext().getPackageName() + "/" + R.raw.notification_sound));

        /* Increase notification number every time a new notification arrives */
        builder.setNumber(++NumberMessage);
        builder.setAutoCancel(true);

        /* Creates an explicit intent for an Activity in your app */
        Intent resultInten = new Intent(this, EmployeeMainActivity.class);

        /* Adds the Intent that starts the Activity to the top of the stack */
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(ReceptionistMainActivity.class);
        taskStackBuilder.addNextIntent(resultInten);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        notificationManager.notify(noticationID, builder.build());


    }

    private void callNotification_personal_Event(String title, String details) {
        int noticationID = 103;
        int NumberMessage = 0;

        NotificationManager notificationManager;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        //set oreo setting
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel("my_channel_01",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(this);

        builder.setContentTitle(title);
        builder.setSmallIcon(R.drawable.email_icon);
        builder.setContentText(details);
        builder.setTicker("Personal Notification");
        builder.setSound(Uri.parse("android.resource://"
                + getApplicationContext().getPackageName() + "/" + R.raw.notification_sound));

        /* Increase notification number every time a new notification arrives */
        builder.setNumber(++NumberMessage);
        builder.setAutoCancel(true);

        /* Creates an explicit intent for an Activity in your app */
        Intent resultInten = new Intent(this, EmployeeMainActivity.class);

        /* Adds the Intent that starts the Activity to the top of the stack */
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(ReceptionistMainActivity.class);
        taskStackBuilder.addNextIntent(resultInten);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        notificationManager.notify(noticationID, builder.build());


    }
}
