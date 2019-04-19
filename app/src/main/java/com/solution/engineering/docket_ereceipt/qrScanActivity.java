package com.solution.engineering.docket_ereceipt;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class qrScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference wRef,mRef;
    String user, number, type, store, date, time, phone, total, payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        scannerView = new ZXingScannerView(this);

        user = mUser.getUid();

        setContentView(scannerView);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkPermission()){
                Toast.makeText(qrScanActivity.this, "Permission granted", Toast.LENGTH_LONG).show();
            }
            else{
                requestPermission();
            }
        }
    }

    private  boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(qrScanActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }
    @Override
    public  void onRequestPermissionsResult(int requestCode, String permission[], int grantResults[]){
        switch (requestCode){
            case REQUEST_CAMERA:
                if(grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        //Toast.makeText(qrScanActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(qrScanActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                            if (shouldShowRequestPermissionRationale(CAMERA)){
                                displayAlertMessage("You need allow access for both permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                                }

                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }

    }
    @Override
    public void onResume(){
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkPermission()){
                if(scannerView == null){
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
            else{
                requestPermission();
            }
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(qrScanActivity.this,DashboardActivity.class));
        finish();
    }
    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(qrScanActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    @Override
    public void handleResult(Result result) {
        final String scanResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog.Builder reBuilder = new AlertDialog.Builder(this);
        reBuilder.setTitle("Scan Once Again");
        reBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity( new Intent(qrScanActivity.this,qrScanActivity.class));
            }
        });
        builder.setTitle("Scan Result");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                wRef = FirebaseDatabase.getInstance().getReference().child("Bills").child(scanResult);
                wRef.child("userId").setValue(user);

                wRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        number = dataSnapshot.child("billNo").getValue(String.class);
                        type = dataSnapshot.child("billType").getValue(String.class);
                        store = dataSnapshot.child("company").getValue(String.class);
                        date = dataSnapshot.child("date").getValue(String.class);
                        time = dataSnapshot.child("time").getValue(String.class);
                        phone = dataSnapshot.child("phone").getValue(String.class);
                        total = dataSnapshot.child("total").getValue(String.class);
                        payment =dataSnapshot.child("paymentMode").getValue(String.class);

                        if(type == null){
                            AlertDialog alertDialog = reBuilder.create();
                            alertDialog.show();
                        }else{
                            mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user);
                            mRef = mRef.child("Bills").child(type);

                            DataModel dataModel = new DataModel();
                            dataModel.setBillNo(number);
                            dataModel.setBillType(type);
                            dataModel.setCompany(store);
                            dataModel.setDate(date);
                            dataModel.setTime(time);
                            dataModel.setTotal(total);
                            dataModel.setPhone(phone);
                            dataModel.setPaymentMode(payment);
                            DatabaseReference databaseReference = mRef.push();
                            databaseReference.setValue(dataModel);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Toast.makeText(qrScanActivity.this, "Receipt Saved Successfully",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(qrScanActivity.this, DashboardActivity.class);
                startActivity(intent);

            }
        });

        builder.setNeutralButton("Nope", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                scannerView.resumeCameraPreview(qrScanActivity.this);
            }
        });
        builder.setMessage("Do want to save this receipt to your account");
        AlertDialog alert = builder.create();
        alert.show();

    }

}
