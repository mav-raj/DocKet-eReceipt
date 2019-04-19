package com.solution.engineering.docket_ereceipt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HelperActivity extends AppCompatActivity {

    SharedPreferences mPrefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mPrefs = getSharedPreferences("com.solution.engineering.docket_ereceipt", MODE_PRIVATE);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (mPrefs.getBoolean("firstRun", true)) {
            // Do first run stuff here then set 'firstRun' as false
            // start  DataActivity because its your app first run
            // using the following line to edit/commit prefs
            mPrefs.edit().putBoolean("firstRun", false).commit();
            startActivity(new Intent(HelperActivity.this , SplashActivity.class));
            finish();
        }
        else {
            startActivity(new Intent(HelperActivity.this , LoginActivity.class));
            finish();
        }
    }
}
