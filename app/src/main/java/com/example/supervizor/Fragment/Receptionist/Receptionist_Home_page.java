package com.example.supervizor.Fragment.Receptionist;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.supervizor.Activity.ReceptionistMainActivity;
import com.example.supervizor.JavaPojoClass.AddEmployee_PojoClass;
import com.example.supervizor.Java_Class.CheckInternet;
import com.example.supervizor.Java_Class.Check_User_information;
import com.example.supervizor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.kinda.alert.KAlertDialog;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;

public class Receptionist_Home_page extends Fragment {

    ImageView imageView_qr_ID;

    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    AddEmployee_PojoClass addEmployee_pojoClass;
    KAlertDialog kAlertDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.receptionist_home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView_qr_ID = view.findViewById(R.id.qrcode_set_IV);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        qr_COde_Generate();

    }

    private void qr_COde_Generate() {
        kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        kAlertDialog.setTitleText("Loading...");

        if (!CheckInternet.isInternet(getContext())) {
            Toasty.error(getContext(), "Check Internet Connection").show();
            kAlertDialog.dismissWithAnimation();
            return;
        }


        Random random = new Random();
        int number = random.nextInt(100000);
        String number_St = String.valueOf(number);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(number_St, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView_qr_ID.setImageBitmap(bitmap);

            //get user information
            Check_User_information check_user_information = new Check_User_information();
            databaseReference.child("employee_list").child(check_user_information.getUserID())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            addEmployee_pojoClass = dataSnapshot.getValue(AddEmployee_PojoClass.class);
//set the qr number
                            database.getReference("qr_code")
                                    .child(addEmployee_pojoClass.getCompany_User_id()).child("number").setValue(number_St);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


    //set title
    public void onResume() {
        super.onResume();
        // Set title bar
        ((ReceptionistMainActivity) getActivity())
                .setActionBarTitle("DashBoard");
    }
}
