package com.solution.engineering.docket_ereceipt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText et_reset_pass_email;
    private Button btn_reset;
    private String email;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/circular_std_book.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_reset_password);
        et_reset_pass_email = (EditText)findViewById(R.id.rpa_et_email);
        btn_reset = (Button)findViewById(R.id.rpa_btn_reset);
        mProgress =new ProgressDialog(this);
        mProgress.setMessage("Verifying Email..");
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidate()){
                    mProgress.show();
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mProgress.dismiss();
                                Toast.makeText(ResetPasswordActivity.this,"Password reset link has been sent, Check Email",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ResetPasswordActivity.this,LoginActivity.class));
                                finish();
                            }else{
                                mProgress.dismiss();
                                Toast.makeText(ResetPasswordActivity.this,"Registered Email not found",Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void onBackPressed(){
        startActivity(new Intent(ResetPasswordActivity.this,LoginActivity.class));
        finish();
    }
    private boolean isValidate() {
        boolean isValidate = true;
        email = et_reset_pass_email.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            et_reset_pass_email.setError("Required");
            isValidate = false;
        }else if (!ResetPasswordActivity.Utility.isValidEmail(email)) {
            et_reset_pass_email.setError("Invalid");
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
