/*
package com.example.supervizor.Fragment.Employee;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.supervizor.Activity.EmployeeActivity.EmployeeMainActivity;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class Employee_profile_View_By_Leader_F extends Fragment implements View.OnClickListener {
    private CircleImageView profileImageUserProfileByLeader;
    private TextView nameTvProfileViewbyLeader;
    private TextView designationProfileViewByLeader;
    private TextView nameTvProfileViewByLeader;
    private TextView phoneNumberTvProfileViewByLeader;
    private TextView emailTvProfileViewByLeader;
    private Button cancelButtonByLeader;
    private Button addToButtonByLeader;
    String user_id_employee;
    String getUser_id_team_Leader;
    String TAG = "TAG";

    DatabaseReference databaseReference;
    private AddEmployee_PojoClass addEmployee_pojoClass;
    private Fragment fragment;
    private Check_User_information check_user_information;
    private KAlertDialog kAlertDialog;

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAXfQqZOg:APA91bEktl8FWv0s4gALfJ5-Y5vTj4no54F5NQ5CAgAqIoyvE1uJMDSXHfOgDmtlHyCX_jZIRduGFSFLi2PmQRUEoBkv6pZvR-2gHcymDXeQNyXSCkCb_3bPQ8EA_2Lbq_Myx34-Wj0i";
    final private String contentType = "application/json";
    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    static String TOPIC_NAME;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.employee_profile_view_by_leader_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();

        user_id_employee = bundle.getString("user_id");
        initView(view);
        //check internet Connection
        if (!CheckInternet.isInternet(getContext())) {
            Toasty.info(getContext(), "Check internet Connection").show();
            return;
        }



        databaseReference.child("employee_list")
                .child(user_id_employee)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
                        kAlertDialog.setTitleText("Loading Data");
                        kAlertDialog.show();

                        addEmployee_pojoClass = dataSnapshot.getValue(AddEmployee_PojoClass.class);

                        if (!addEmployee_pojoClass.getEmployee_profile_image_link().equals("null")) {

                            Picasso.get().load(Uri.parse(addEmployee_pojoClass.getEmployee_profile_image_link())).into(profileImageUserProfileByLeader);
                        } else {
                            profileImageUserProfileByLeader.setImageResource(R.drawable.profile);
                        }
                        nameTvProfileViewbyLeader.setText(addEmployee_pojoClass.getEmployee_name());
                        nameTvProfileViewByLeader.setText(addEmployee_pojoClass.getEmployee_name());
                        designationProfileViewByLeader.setText(addEmployee_pojoClass.getEmployee_designation());
                        emailTvProfileViewByLeader.setText(addEmployee_pojoClass.getEmployee_email());
                        phoneNumberTvProfileViewByLeader.setText("Add The phone number to Database");
                        kAlertDialog.dismissWithAnimation();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        kAlertDialog.dismissWithAnimation();

                    }
                });


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cancel_button_by_Leader) {

            loadAddNewTeamMate_List_FragmentList();

        } else if (view.getId() == R.id.add_to_button_by_Leader) {

            if (!CheckInternet.isInternet(getContext())){
                Toasty.info(getContext(),"Check Internet Connection").show();
                return;
            }

            kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
            kAlertDialog.setTitleText("saving Data");
            kAlertDialog.show();

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String team_name = preferences.getString("team_name", "");

            //
      */
/*      databaseReference.child("event_list_by_user")
                    .child(user_id_employee)
                    .child(check_user_information.getUserID())
                    .child(team_name)
                    .setValue(team_name);
*//*

      //Set request Pending status

//            get company ID
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String company_user_ID = dataSnapshot.child("employee_list").child(check_user_information.getUserID())
                            .child("company_User_id").getValue(String.class);

                    databaseReference.child("my_team_request_pending")
                            .child(company_user_ID)
                            .child(check_user_information.getUserID())
                            .child(team_name)
                            .child("status")
                            .setValue("0")
                    .addOnCompleteListener(task -> {
                //admin notify
                        //send the notification data Start
                        TOPIC_NAME=company_user_ID+"teamRequest";

                        sendDataToFireabase(TOPIC_NAME,"Team Request",team_name);
                        //send the notification data END
                   //personal notyfy
                        //send the notification data Start
                        TOPIC_NAME=user_id_employee;

                        sendDataToFireabase(TOPIC_NAME,"Team member",team_name);
                        //send the notification data END
                    });

                    databaseReference.child("my_team_request")
                            .child(check_user_information.getUserID())
                            .child(team_name)
                            .child(user_id_employee)
                            .child("name")
                            .setValue(nameTvProfileViewByLeader.getText().toString());

                    databaseReference.child("my_team_request")
                            .child(check_user_information.getUserID())
                            .child(team_name)
                            .child(user_id_employee)
                            .child("email")
                            .setValue(emailTvProfileViewByLeader.getText().toString())
                            .addOnCompleteListener(task -> {
                                kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                kAlertDialog.setTitleText("Done");
                                kAlertDialog.setConfirmClickListener(kAlertDialog -> {
                                    kAlertDialog.dismissWithAnimation();
                                    loadAddNewTeamMate_List_FragmentList();
                                });
                            });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    private void loadAddNewTeamMate_List_FragmentList() {
        fragment = new Add_New_Team_Mate_From_List_F();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.employee_main_layout_ID, fragment);
            fragmentTransaction.commit();
        }
    }

    private void initView(View rootView) {
        profileImageUserProfileByLeader = (CircleImageView) rootView.findViewById(R.id.profile_image_user_profile_by_Leader);
        nameTvProfileViewbyLeader = (TextView) rootView.findViewById(R.id.name_tv_profile_viewby_Leader);
        designationProfileViewByLeader = (TextView) rootView.findViewById(R.id.designation_profile_view_by_Leader);
        nameTvProfileViewByLeader = (TextView) rootView.findViewById(R.id.name_tv_profile_view_by_Leader);
        phoneNumberTvProfileViewByLeader = (TextView) rootView.findViewById(R.id.phone_number_tv_profile_view_by_Leader);
        emailTvProfileViewByLeader = (TextView) rootView.findViewById(R.id.email_tv_profile_view_by_Leader);
        cancelButtonByLeader = (Button) rootView.findViewById(R.id.cancel_button_by_Leader);
        cancelButtonByLeader.setOnClickListener(Employee_profile_View_By_Leader_F.this);
        addToButtonByLeader = (Button) rootView.findViewById(R.id.add_to_button_by_Leader);
        addToButtonByLeader.setOnClickListener(Employee_profile_View_By_Leader_F.this);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        check_user_information = new Check_User_information();


    }

    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((EmployeeMainActivity) getActivity())
                .setActionBarTitle("Add Team Mate");
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
//                        Toasty.info(getActivity(),"send notification").show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getContext(), "Request error", Toast.LENGTH_LONG).show();
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
*/
