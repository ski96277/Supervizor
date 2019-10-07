/*
package com.example.supervizor.Fragment.Employee;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.example.supervizor.Activity.EmployeeActivity.EmployeeMainActivity;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.JavaPojoClass.LeaveApplication_PojoClass;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import es.dmoral.toasty.Toasty;

public class Leave_Application_Employee_F extends Fragment implements View.OnClickListener {

    private EditText leaveTitleETID;
    private EditText leaveDescriptionETID;
    private TextView leaveStartTimeTVID;
    private TextView leaveENDTimeTVID;
    private Button leaveSubmitBtnID;
    private AddEmployee_PojoClass addEmployee_pojoClass;
    private int year;
    private int month;
    private int day;
    private Calendar calendar;

    private DatePickerDialog datePickerDialog;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAXfQqZOg:APA91bEktl8FWv0s4gALfJ5-Y5vTj4no54F5NQ5CAgAqIoyvE1uJMDSXHfOgDmtlHyCX_jZIRduGFSFLi2PmQRUEoBkv6pZvR-2gHcymDXeQNyXSCkCb_3bPQ8EA_2Lbq_Myx34-Wj0i";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    static String TOPIC_NAME;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.leave_application_employee_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.leave_start_Time_TV_ID) {
            callDate_Start_Method();

        } else if (view.getId() == R.id.leave_END_Time_TV_ID) {

            callDate_END_Method();


        } else if (view.getId() == R.id.submit_leave_btn_ID) {
//save value to Database
            saveValueToDatabase();
        }
    }

    private void saveValueToDatabase() {

        if (!CheckInternet.isInternet(getContext())) {
            Toasty.info(getContext(), "check Internet Connection").show();
            return;
        }


        String leave_Title = leaveTitleETID.getText().toString();
        String description = leaveDescriptionETID.getText().toString();
        String startDate = leaveStartTimeTVID.getText().toString();
        String endDate = leaveENDTimeTVID.getText().toString();

        if (leave_Title.isEmpty()) {
            leaveTitleETID.requestFocus();
            leaveTitleETID.setError("Title ?");
            return;
        }
        if (description.isEmpty()) {
            leaveDescriptionETID.requestFocus();
            leaveDescriptionETID.setError("Description ?");
            return;
        }
        if (startDate.isEmpty()) {
            leaveStartTimeTVID.requestFocus();
            return;
        }
        if (endDate.isEmpty()) {
            leaveENDTimeTVID.requestFocus();
            return;
        }


//show loading
        KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        kAlertDialog.setTitleText("Sending Leave Application");
        kAlertDialog.show();

        Check_User_information check_user_information = new Check_User_information();
        String user_ID_employee = check_user_information.getUserID();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //get value from Database
                addEmployee_pojoClass = dataSnapshot.child("employee_list").child(user_ID_employee).getValue(AddEmployee_PojoClass.class);

                LocalDate current_Date = LocalDate.now();

                calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DATE);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

//save value to Database
                LeaveApplication_PojoClass leaveApplication_pojoClass =
                        new LeaveApplication_PojoClass(addEmployee_pojoClass.getEmployee_User_id(),
                                addEmployee_pojoClass.getCompany_User_id(),
                                leave_Title,
                                description,
                                startDate,
                                endDate,
                                String.valueOf(day),
                                String.valueOf(month+1),
                                String.valueOf(year),
                                current_Date.toString(),
                                addEmployee_pojoClass.getEmployee_profile_image_link(),
                                addEmployee_pojoClass.getEmployee_name(),
                                addEmployee_pojoClass.getEmployee_designation(),
                                false);
                databaseReference.child("leave_application")
                        .child(addEmployee_pojoClass.getCompany_User_id())
                        .child(check_user_information.getUserID())
                        .child(leave_Title)
                        .setValue(leaveApplication_pojoClass)
                        .addOnCompleteListener(task -> {

                            //send the notification data Start
                            TOPIC_NAME = addEmployee_pojoClass.getCompany_User_id() + "leave";

                            sendDataToFireabase(TOPIC_NAME, leave_Title, description);
                            //send the notification data END

                            kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                            kAlertDialog.setTitleText("Uploaded...");
                            kAlertDialog.setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(KAlertDialog kAlertDialog) {
                                    kAlertDialog.dismissWithAnimation();
                                    loadMyLeaveApplication();
                                }
                            });
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.info(getContext(), "try later, somethings is wrong").show();
            }
        });

    }

    private void loadMyLeaveApplication() {
        Fragment fragment = new MyLeaveApplication_Employee_F();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment);
            fragmentTransaction.commit();
        }
    }

    private void callDate_Start_Method() {
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                leaveStartTimeTVID.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void callDate_END_Method() {
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) ->
                leaveENDTimeTVID.setText(dayOfMonth + "/" + month + "/" + year), year, month, day);
        datePickerDialog.show();

    }

    private void initView(View rootView) {
        leaveTitleETID = (EditText) rootView.findViewById(R.id.leave_title_ET_ID);
        leaveDescriptionETID = (EditText) rootView.findViewById(R.id.leave_description_ET_ID);
        leaveStartTimeTVID = (TextView) rootView.findViewById(R.id.leave_start_Time_TV_ID);
        leaveStartTimeTVID.setOnClickListener(Leave_Application_Employee_F.this);
        leaveENDTimeTVID = (TextView) rootView.findViewById(R.id.leave_END_Time_TV_ID);
        leaveENDTimeTVID.setOnClickListener(Leave_Application_Employee_F.this);
        leaveSubmitBtnID = (Button) rootView.findViewById(R.id.submit_leave_btn_ID);
        leaveSubmitBtnID.setOnClickListener(Leave_Application_Employee_F.this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }


    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((EmployeeMainActivity) getActivity())
                .setActionBarTitle("Leave application");
    }

    //Notification Data send
    private void sendDataToFireabase(String topicName, String event_title, String event_details) {

        TOPIC = "/topics/" + topicName; //topic must match with what the receiver subscribed to
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
            Log.e(TAG, "onCreate: " + e.getMessage());
        }
        sendNotification(notification);

    }

    private void sendNotification(JSONObject notification) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
//                        Toasty.info(getContext(),"send notification").show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }) {
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
*/
