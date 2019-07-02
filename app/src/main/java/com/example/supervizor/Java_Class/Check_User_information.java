package com.example.supervizor.Java_Class;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Check_User_information {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    String userID;
    String email;

    public Check_User_information() {
        firebaseAuth = FirebaseAuth.getInstance();
         firebaseUser =firebaseAuth.getCurrentUser();

        this.userID = firebaseUser.getUid();
        this.email=firebaseUser.getEmail();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
