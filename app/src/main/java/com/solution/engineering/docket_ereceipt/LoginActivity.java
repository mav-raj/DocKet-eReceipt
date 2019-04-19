package com.solution.engineering.docket_ereceipt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends Activity {
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private String email, password;
    private EditText etEmail, etPassword;
    private Button bLogin;
    private TextView tv_sign_up, tv_forgot_pass;
    public static final String MyPREF = "MyPreference";
    public static final String sh_email = "email_key";
    public static final String sh_password = "password_key";
    public static final String login_stat = "login_status";
    boolean doubleBackToExitPressedOnce = false;

    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/circular_std_book.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_login);
        etEmail = (EditText)findViewById (R.id.text_input_login);
        etPassword = (EditText)findViewById(R.id.text_input_password);
        bLogin = (Button)findViewById(R.id.login_button);
        tv_sign_up = (TextView)findViewById(R.id.signup_textview);
        tv_forgot_pass = (TextView)findViewById(R.id.forgot_pass_textview);

        mAuth = FirebaseAuth.getInstance();
        mProgress =new ProgressDialog(this);
        mProgress.setMessage("Logging In..");
        mPrefs = getSharedPreferences(MyPREF, Context.MODE_PRIVATE);
        if(mPrefs.getBoolean(login_stat,false)){
            startActivity(new Intent(LoginActivity.this,qrScanActivity.class));
            finish();
        }else{
            bLogin.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(isValidate()){
                        Login();
                    }
                }
            });

        }
        tv_forgot_pass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
                finish();
            }
        });

        tv_sign_up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onBackPressed(){
        if(doubleBackToExitPressedOnce){
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "press again to exit",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        },2000);

    }
    private void Login() {
        mProgress.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    mProgress.dismiss();
                    Log.v("EmailPassword","signInWithEmail:failed",task.getException());
                    Toast.makeText(LoginActivity.this,"User Authentication Failed: " + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }else{
                    mProgress.dismiss();
                    checkIfEmailVerified();
                }
            }
        });
    }
    private void checkIfEmailVerified(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.isEmailVerified()) {
            Toast.makeText(LoginActivity.this,"Successfully Logged In", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(sh_email,email);
            editor.putString(sh_password,password);
            editor.putBoolean(login_stat,true);
            editor.commit();
            startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
            finish();
        }
        else{
            Toast.makeText(LoginActivity.this,"Email not verified yet, Check registered mail",Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();
            //to restart activity
            finish();
            startActivity(getIntent());
        }
    }


    private boolean isValidate() {
        boolean isValidate = true;
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Required");
            isValidate = false;
        }
        if(TextUtils.isEmpty(password)) {
            etPassword.setError("Required");
            isValidate = false;
        }
        return isValidate;
    }
}
