package com.solution.engineering.docket_ereceipt;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class docket_ereceipt extends Application{
    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        //this class is created just to implement offline capabilities of fire base database
    }
}
