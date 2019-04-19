package com.solution.engineering.docket_ereceipt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by mavra on 11-Mar-18.
 */

public class viewFurniture extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mRef;
    private RecyclerView mReceipt;
    String user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.furniture_layout, container, false);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if(mUser != null){
            user = mUser.getUid();
        }else startActivity(new Intent(getContext(), DashboardActivity.class));
        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user).child("Bills").child("Furniture");
        mReceipt = (RecyclerView) rootView.findViewById(R.id.furnitureRecycler);
        mReceipt.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerAdapter<DataModel, ReceiptViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataModel, ReceiptViewHolder>
                (DataModel.class, R.layout.reciept_row, ReceiptViewHolder.class, mRef){
            @Override
            protected void populateViewHolder(ReceiptViewHolder viewHolder, DataModel model, int position) {
                viewHolder.setBillNo("Bill No : " + model.getBillNo());
                viewHolder.setStoreName(model.getCompany());
                viewHolder.setDate("Date : " + model.getDate());
                viewHolder.setTime("Time : " + model.getTime());
                viewHolder.setPhone("Phone : " + model.getPhone());
                viewHolder.setBillTotal("Total : " + model.getTotal());
                viewHolder.setPaymentMode("Payment Mode : " + model.getPaymentMode());
            }
        };

        mReceipt.setAdapter(firebaseRecyclerAdapter);

        return rootView;
    }
}
