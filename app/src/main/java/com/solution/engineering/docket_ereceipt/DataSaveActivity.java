package com.solution.engineering.docket_ereceipt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class DataSaveActivity extends AppCompatActivity {

    String keyValue;
    private RecyclerView mReceipt;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_save);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/circular_std_book.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        mReceipt = new RecyclerView(this);
        Intent intent = getIntent();
        if(intent == null) {
            keyValue = null;
        } else {
            //keyValue = intent.getExtras().getString("key");
            keyValue = intent.getStringExtra("key");
            Toast.makeText(this,keyValue,Toast.LENGTH_SHORT).show();
        }
        mRef = FirebaseDatabase.getInstance().getReference().child("Bills");
        mReceipt = (RecyclerView)findViewById(R.id.othersRecycler);
        mReceipt.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerAdapter<DataModel, ReceiptViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataModel, ReceiptViewHolder>
                (DataModel.class, R.layout.reciept_row, ReceiptViewHolder.class, mRef){
            @Override
            protected void populateViewHolder(ReceiptViewHolder viewHolder,DataModel model, int position) {
                viewHolder.setBillNo("Bill No : " + model.getBillNo());
                viewHolder.setStoreName("Store Name : " + model.getCompany());
                viewHolder.setDate("Date : " + model.getDate());
                viewHolder.setTime("Time : " + model.getTime());
                viewHolder.setBillTotal("Total : " + model.getTotal());

            }
        };

        mReceipt.setAdapter(firebaseRecyclerAdapter);
    }
//    public static class ReceiptViewHolder extends RecyclerView.ViewHolder {
//        View mView;
//        public ReceiptViewHolder(View itemView){
//            super(itemView);
//            mView = itemView;
//        }
//        public void setBillNo(String billNo){
//            TextView BillNo = (TextView)mView.findViewById(R.id.receiptBillNo);
//            BillNo.setText(billNo);
//        }
//        public void setStoreName(String company){
//            TextView StoreName = (TextView)mView.findViewById(R.id.receiptStoreName);
//            StoreName.setText(company);
//        }
//        public void setDate(String date){
//            TextView Date = (TextView)mView.findViewById(R.id.receiptDate);
//            Date.setText(date);
//        }
//        public void setTime(String time){
//            TextView Time = (TextView)mView.findViewById(R.id.receiptTime);
//            Time.setText(time);
//        }
//        public void setPhone(String phone){
//            TextView Phone = (TextView)mView.findViewById(R.id.receiptPhone);
//            Phone.setText(phone);
//        }
//        public void setBillTotal(String total){
//            TextView BillTotal = (TextView)mView.findViewById(R.id.receiptTotal);
//            BillTotal.setText(total);
//        }
//
//    }
}
