package com.solution.engineering.docket_ereceipt;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mavra on 20-Mar-18.
 */

public class ReceiptViewHolder extends RecyclerView.ViewHolder{
    View mView;
    public ReceiptViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }
    public void setBillNo(String billNo){
        TextView BillNo = (TextView)mView.findViewById(R.id.receiptBillNo);
        BillNo.setText(billNo);
    }
    public void setStoreName(String company){
        TextView StoreName = (TextView)mView.findViewById(R.id.receiptStoreName);
        StoreName.setText(company);
    }
    public void setDate(String date){
        TextView Date = (TextView)mView.findViewById(R.id.receiptDate);
        Date.setText(date);
    }
    public void setTime(String time){
        TextView Time = (TextView)mView.findViewById(R.id.receiptTime);
        Time.setText(time);
    }
    public void setPhone(String phone){
        TextView Phone = (TextView)mView.findViewById(R.id.receiptPhone);
        Phone.setText(phone);
    }
    public void setBillTotal(String total){
        TextView BillTotal = (TextView)mView.findViewById(R.id.receiptTotal);
        BillTotal.setText(total);
    }

    public void setPaymentMode(String paymentMode){
        TextView PaymentMode = (TextView)mView.findViewById(R.id.receiptPaymentMode);
        PaymentMode.setText(paymentMode);
    }

}
