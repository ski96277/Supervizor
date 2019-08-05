package com.example.supervizor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kinda.alert.KAlertDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class ScanResult_Activiy extends AppCompatActivity {

    Intent intent;
    String intent_text;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Check_User_information check_user_information;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result__activiy);

        intent = getIntent();
        intent_text = intent.getStringExtra("value");
        Log.e("Tag","intent intent_text= "+ intent_text);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        check_user_information=new Check_User_information();

        //for scanner
        new IntentIntegrator(this).initiateScan(); // `this` is the current Activity


    }


    // Get the results:
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
//write your code here
                if (!CheckInternet.isInternet(getApplicationContext())){
                    startActivity(new Intent(getApplicationContext(),EmployeeMainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    Toasty.info(getApplicationContext(),"Check Internet Connection").show();
                    return;
                }
                //get the scanner value

                String scan_Text= result.getContents();
                Log.e("Tag","scan data = "+scan_Text);
                Log.e("Tag","intent intent_text= "+ intent_text);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       AddEmployee_PojoClass addEmployee_pojoClass =  dataSnapshot.child("employee_list")
                               .child(check_user_information.getUserID()).getValue(AddEmployee_PojoClass.class);

                       String data = dataSnapshot.child("qr_code").child(addEmployee_pojoClass.getCompany_User_id()).child("number").getValue(String.class);

                       //if scan data and database is same
                       if (scan_Text.equals(data)){

                           if (intent_text.equals("entry")){
//save Entry Time

                               KAlertDialog kAlertDialog=new KAlertDialog(ScanResult_Activiy.this,KAlertDialog.SUCCESS_TYPE);
                               kAlertDialog.setTitleText("Entry Success...");
                               kAlertDialog.show();

                               Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                               int year = calendar.get(Calendar.YEAR);
                               int month = calendar.get(Calendar.MONTH);
                               int day = calendar.get(Calendar.DAY_OF_MONTH);

                                DateFormat df = new SimpleDateFormat("h:mm a");
                                String time = df.format(Calendar.getInstance().getTime());
                                databaseReference.child("Attendance").child(addEmployee_pojoClass.getCompany_User_id())
                                        .child(check_user_information.getUserID())
                                        .child(String.valueOf(year)).child(String.valueOf(month + 1))
                                        .child(String.valueOf(day)).child("Entry").child("entryTime").setValue(time)
                                        .addOnCompleteListener(task -> kAlertDialog.setConfirmClickListener(kAlertDialog1 -> {
                                            kAlertDialog1.dismissWithAnimation();

                                            startActivity(new Intent(getApplicationContext(),EmployeeMainActivity.class)
                                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                        }));




                               }else if (intent_text.equals("exit")){


                               KAlertDialog kAlertDialog=new KAlertDialog(ScanResult_Activiy.this,KAlertDialog.SUCCESS_TYPE);
                               kAlertDialog.setTitleText("Exit Success...");
                               kAlertDialog.show();

                               Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                               int year = calendar.get(Calendar.YEAR);
                               int month = calendar.get(Calendar.MONTH);
                               int day = calendar.get(Calendar.DAY_OF_MONTH);

                               DateFormat df = new SimpleDateFormat("h:mm a");
                               String time = df.format(Calendar.getInstance().getTime());

                               databaseReference.child("Attendance").child(addEmployee_pojoClass.getCompany_User_id())
                                       .child(check_user_information.getUserID()).child(String.valueOf(year)).child(String.valueOf(month + 1))
                                       .child(String.valueOf(day)).child("Exit").child("exitTime").setValue(time)

                                       .addOnCompleteListener(task -> kAlertDialog.setConfirmClickListener(kAlertDialog1 -> {
                                   kAlertDialog1.dismissWithAnimation();

                                   startActivity(new Intent(getApplicationContext(),EmployeeMainActivity.class)
                                           .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                               }));


                           }

                       }else {
                           startActivity(new Intent(getApplicationContext(),EmployeeMainActivity.class)
                           .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                       }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

