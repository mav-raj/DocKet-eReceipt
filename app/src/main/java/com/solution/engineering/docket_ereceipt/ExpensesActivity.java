package com.solution.engineering.docket_ereceipt;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ExpensesActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("Bills");




    }




}
