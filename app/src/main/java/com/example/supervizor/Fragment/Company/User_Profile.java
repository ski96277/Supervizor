package com.example.supervizor.Fragment.Company;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.supervizor.Activity.CompanyMainActivity;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.JavaPojoClass.Event_details_PojoClass;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.NOtification_Firebase.MySingleton;
import com.example.supervizor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class User_Profile extends Fragment {

    private TextView name_Tv;
    private TextView phone_TV;
    private TextView email_TV;
    private TextView calender_TV;
    private TextView name_profile_TV;
    private TextView designation_profile_TV;
    CircleImageView circleImageView;

    String user_id_employee;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AddEmployee_PojoClass addEmployee_pojoClass;

    int CalendarHour, CalendarMinute;
    String format;
    Calendar calendar;
    TimePickerDialog timepickerdialog;

    int day_select;
    int month_select;
    int year_select;

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAXfQqZOg:APA91bEktl8FWv0s4gALfJ5-Y5vTj4no54F5NQ5CAgAqIoyvE1uJMDSXHfOgDmtlHyCX_jZIRduGFSFLi2PmQRUEoBkv6pZvR-2gHcymDXeQNyXSCkCb_3bPQ8EA_2Lbq_Myx34-Wj0i";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    static String TOPIC_NAME;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//for option menu
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        CompanyMainActivity.employee_and_calender_layout.setVisibility(View.GONE);
        CompanyMainActivity.pending_and_approved_layout.setVisibility(View.GONE);

        Bundle bundle = getArguments();
        user_id_employee = bundle.getString("user_id");

        return inflater.inflate(R.layout.user_profile_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);

        //get user information

        databaseReference.child("employee_list").child(user_id_employee)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        addEmployee_pojoClass = dataSnapshot.getValue(AddEmployee_PojoClass.class);


                        if (!addEmployee_pojoClass.getEmployee_profile_image_link().equals("null")) {

                            Picasso.get().load(Uri.parse(addEmployee_pojoClass.getEmployee_profile_image_link())).into(circleImageView);
                        } else {
                            circleImageView.setImageResource(R.drawable.profile);
                        }

                        name_profile_TV.setText(addEmployee_pojoClass.getEmployee_name());
                        designation_profile_TV.setText(addEmployee_pojoClass.getEmployee_designation());

                        name_Tv.setText(addEmployee_pojoClass.getEmployee_name());
                        email_TV.setText(addEmployee_pojoClass.getEmployee_email());
                        phone_TV.setText(addEmployee_pojoClass.getEmployee_Contact_period_number());
                        calender_TV.setText(addEmployee_pojoClass.getEmployee_joinDate());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }

    private void initialize(View view) {
        name_Tv = view.findViewById(R.id.name_tv_profile_view_page_1);
        phone_TV = view.findViewById(R.id.phone_number_tv_profile_view_page_1);
        email_TV = view.findViewById(R.id.email_tv_profile_view_page_1);
        calender_TV = view.findViewById(R.id.date_tv_profile_view_page_1);
        name_profile_TV = view.findViewById(R.id.name_tv_profile_view);
        designation_profile_TV = view.findViewById(R.id.designation_profile_view);
        circleImageView = view.findViewById(R.id.profile_image_user_profile);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((CompanyMainActivity) getActivity())
                .setActionBarTitle("Profile");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

//        inflater.inflate(R.menu.company_main,menu);
        menu.findItem(R.id.set_event_to_this_employee).setVisible(true);
        menu.findItem(R.id.make_Team_Leader_this_employee).setVisible(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean yes = dataSnapshot.child("team_leader_ID_List")
                        .hasChild(user_id_employee);
                if (yes) {
                    menu.findItem(R.id.make_Team_Leader_this_employee).setVisible(false);
                    menu.findItem(R.id.remove_team_Leader_this_employee).setVisible(true);

                } else {
                    menu.findItem(R.id.make_Team_Leader_this_employee).setVisible(true);

                    menu.findItem(R.id.remove_team_Leader_this_employee).setVisible(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set_event_to_this_employee:

                showCustomAlertDialog_setEvent_for_Employee();

                break;
            case R.id.make_Team_Leader_this_employee:

                KAlertDialog team_Lead_Confirm_Alert = new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE);
                team_Lead_Confirm_Alert.show();
                team_Lead_Confirm_Alert.setTitleText("Make Team Leader");

                team_Lead_Confirm_Alert.setConfirmClickListener(kAlertDialog -> {
                    if (!CheckInternet.isInternet(getContext())) {
                        Toasty.error(getContext(), "Internet Connection Error");
                        team_Lead_Confirm_Alert.dismissWithAnimation();
                        return;
                    }

                    making_Team_leader(team_Lead_Confirm_Alert);

                });

                break;
            case R.id.remove_team_Leader_this_employee:

                KAlertDialog remove_Team_Leader_Alert = new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE);
                remove_Team_Leader_Alert.show();
                remove_Team_Leader_Alert.setTitleText("Remove this Team Leader?");
                remove_Team_Leader_Alert.setConfirmClickListener(kAlertDialog -> {

                    if (!CheckInternet.isInternet(getContext())) {
                        Toasty.error(getContext(), "Internet Connection Error");
                        remove_Team_Leader_Alert.dismissWithAnimation();
                        return;
                    }
                    remove_Data_as_Team_Leader(remove_Team_Leader_Alert);
                });

                break;
        }
        return false;
    }

    private void remove_Data_as_Team_Leader(KAlertDialog remove_Team_Leader_Alert) {
        databaseReference.child("my_team_request")
                .child(user_id_employee).removeValue();
        databaseReference.child("team_leader_ID_List")
                .child(user_id_employee).removeValue();
        remove_Team_Leader_Alert.dismissWithAnimation();
    }

    //make team leader
    private void making_Team_leader(KAlertDialog team_Lead_Confirm_Alert) {

        databaseReference.child("team_leader_ID_List")
                .child(user_id_employee)
                .child("name")
                .setValue(name_profile_TV.getText().toString());
        databaseReference.child("team_leader_ID_List")
                .child(user_id_employee)
                .child("email")
                .setValue(email_TV.getText().toString());

  //send the notification data
        TOPIC_NAME=user_id_employee;

        sendDataToFireabase(TOPIC_NAME,"Team Leader","Now you are Team leader");

        team_Lead_Confirm_Alert.dismissWithAnimation();

    }

    //show alert for adding event
    private void showCustomAlertDialog_setEvent_for_Employee() {


        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_alert_dialog_for_add_employee_event);
        dialog.setCancelable(false);

        TextView name_TV = dialog.findViewById(R.id.event_employee_name_TV_ID);
        name_TV.setText(name_Tv.getText().toString());
        EditText title_ET = dialog.findViewById(R.id.event_employee_title_name_TV_ID);
        EditText details_ET = dialog.findViewById(R.id.event_employee_Details_TV_ID);
        EditText time_ET = dialog.findViewById(R.id.event_employee_time_TV_ID);
        EditText date_ET = dialog.findViewById(R.id.event_employee_Date_TV_ID);
        Button button_cancle = dialog.findViewById(R.id.event_employee_cancel_btn_ID);
        Button button_submit = dialog.findViewById(R.id.event_employee_submit_event_btn_ID);

        //show time picker dialog
        time_ET.setOnClickListener(v -> SelectTime(v, time_ET));
        date_ET.setOnClickListener(v -> {

            selectDate(v, date_ET);
        });

        //cancel alert dialog
        button_cancle.setOnClickListener(v -> dialog.dismiss());
        //save event alert dialog
        button_submit.setOnClickListener(v -> {

            Check_User_information check_user_information = new Check_User_information();

            String user_ID_company = check_user_information.getUserID();

            String title = title_ET.getText().toString();
            String details = details_ET.getText().toString();
            String time = time_ET.getText().toString();
            String date = date_ET.getText().toString();

            String day = String.valueOf(day_select);
            String month = String.valueOf(month_select);
            String year = String.valueOf(year_select);
            //check data is empty or not ?
            if (user_ID_company.isEmpty() || user_id_employee.isEmpty() || title.isEmpty()
                    || details.isEmpty() || time.isEmpty() || date.isEmpty()) {
                Toasty.info(getContext(), "Fill up the information").show();
                return;
            }
            //check internet connection
            if (!CheckInternet.isInternet(getContext())) {
                Toasty.error(getContext(), "Internet Connection Error").show();
                return;
            }
            KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
            kAlertDialog.show();
            kAlertDialog.setTitleText("Uploading Data....");


            Event_details_PojoClass event_details_pojoClass = new Event_details_PojoClass(date, day,
                    month, year, title, details, time);
            databaseReference.child("personal_event")
                    .child(user_ID_company).child(user_id_employee)
                    .child(date).setValue(event_details_pojoClass)
                    .addOnSuccessListener(aVoid -> {

                        //send the notification data
                        TOPIC_NAME=user_id_employee;

                        sendDataToFireabase(TOPIC_NAME,title,details);


                        kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                        kAlertDialog.setTitleText("Done");
                        kAlertDialog.setConfirmClickListener(kAlertDialog1 -> {

                            kAlertDialog1.dismissWithAnimation();
                            dialog.dismiss();
                        });

                    });
            //set notification status
            databaseReference.child("personal_Event_notification")
                    .child(user_id_employee)
                    .child("status")
                    .setValue("1");
            databaseReference.child("personal_Event_notification_Text")
                    .child(user_id_employee)
                    .setValue(event_details_pojoClass);
            //set notification status END


        });//save event alert dialog END

//show alert dialog
        dialog.show();

    }

    private void selectDate(View v, EditText date_ET) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                day_select = day;
                month_select = month + 1;
                year_select = year;

                if (month < 10) {
                    date_ET.setText(year + "-0" + (month + 1) + "-" + dayOfMonth);
                } else if (dayOfMonth < 10) {
                    date_ET.setText(year + "-" + (month + 1) + "-0" + dayOfMonth);
                } else {
                    date_ET.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                }
            }
        }, year, month, day);
        datePickerDialog.show();

    }

    private void SelectTime(View v, EditText time_ET) {


        calendar = Calendar.getInstance();
        CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
        CalendarMinute = calendar.get(Calendar.MINUTE);

        timepickerdialog = new TimePickerDialog(getContext(),
                (view, hourOfDay, minute) -> {

                    if (hourOfDay == 0) {

                        hourOfDay += 12;

                        format = "AM";
                    } else if (hourOfDay == 12) {

                        format = "PM";

                    } else if (hourOfDay > 12) {

                        hourOfDay -= 12;

                        format = "PM";

                    } else {

                        format = "AM";
                    }

                    if (minute < 10) {
                        time_ET.setText(hourOfDay + " : 0" + minute + " " + format);

                    } else {
                        time_ET.setText(hourOfDay + " : " + minute + " " + format);

                    }


                }, CalendarHour, CalendarMinute, false);
        timepickerdialog.show();

    }

    //Notification Data send
    private void sendDataToFireabase(String topicName, String event_title, String event_details) {

        TOPIC = "/topics/"+topicName; //topic must match with what the receiver subscribed to
        NOTIFICATION_TITLE = event_title;
        NOTIFICATION_MESSAGE = event_details;

        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", NOTIFICATION_TITLE);
            notifcationBody.put("message", NOTIFICATION_MESSAGE);

            notification.put("to", TOPIC);
            notification.put("data", notifcationBody);

        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }
        sendNotification(notification);

    }

    private void sendNotification(JSONObject notification) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                        Toasty.info(getContext(),"send notification").show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

}
