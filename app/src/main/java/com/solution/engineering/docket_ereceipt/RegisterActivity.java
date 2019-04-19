package com.solution.engineering.docket_ereceipt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, email, password, confPassword;
    private Button createAccount;
    private String Name, Email, Password, ConfPassword;
    private TextView signin;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/circular_std_book.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_register);

        name = (EditText)findViewById(R.id.register_et_name);
        email = (EditText)findViewById(R.id.register_et_email);
        password = (EditText)findViewById(R.id.register_et_password);
        confPassword = (EditText)findViewById(R.id.register_et_confirm_password);
        createAccount = (Button)findViewById(R.id.register_btn_create_account);
        signin = (TextView)findViewById(R.id.register_tv_signin);

        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Creating account..");

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()){
                    mProgress.show();
                    SignUp();
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });



    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press Back Again to Exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
    private void SignUp(){
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //to prevent from duplicate email accounts
                if (!task.isSuccessful()) {
                    mProgress.dismiss();
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(RegisterActivity.this, "User with this email already exist", Toast.LENGTH_LONG).show();
                    }
                }else{
                    final FirebaseUser user = task.getResult().getUser();
                    if (user != null){
                        mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                                    databaseReference = databaseReference.child(user.getUid()).child("Info");
                                    //to store data
                                    //new class is created UserModel in java class folder
                                    UserModel userModel = new UserModel();
                                    userModel.setName(Name);
                                    userModel.setEmail(Email);
                                    userModel.setPassword(Password);
                                    databaseReference.setValue(userModel);
                                    mProgress.dismiss();
                                    //data is stored and email verification link sent
                                    sendEmailVerification();
                                }
                            }
                        });
                    }
                }
            }
        });
    }
    private void sendEmailVerification(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this,"Verification link sent to your email",Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        overridePendingTransition(0,0);
                        startActivity(getIntent());
                    }
                }
            });
        }
    }
    private boolean isValidate() {
        boolean isValidate = true;
        Name = name.getText().toString().trim();
        Email = email.getText().toString().trim();
        Password = password.getText().toString().trim();
        ConfPassword = confPassword.getText().toString().trim();

        if (TextUtils.isEmpty(Name)) {
            name.setError("Required");
            isValidate = false;
        }
        if (TextUtils.isEmpty(Email)) {
            email.setError("Required");
            isValidate = false;
        } else if (!Utility.isValidEmail(Email)) {
            email.setError("Invalid");
            isValidate = false;
        }
        if(TextUtils.isEmpty(Password)) {
            password.setError("Required");
            isValidate = false;
        }
        else if(Password.length() < 8){
            password.setError("Minimum 8 required");
            isValidate = false;
        }
        if (TextUtils.isEmpty(ConfPassword)) {
            confPassword.setError("Required");
            isValidate = false;
        }else if (!ConfPassword.equals(Password)){
            confPassword.setError("Doesn't match");
            isValidate = false;
        }
        return isValidate;
    }
    private static class Utility{
        public static boolean isValidEmail(String email){
            String exp = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;
            Pattern pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE);
            return pattern.matcher(inputStr).matches();
        }
    }
}
