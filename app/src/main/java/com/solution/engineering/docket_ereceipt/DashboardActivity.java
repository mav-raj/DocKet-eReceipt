package com.solution.engineering.docket_ereceipt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    boolean doubleBackToExitPressedOnce = false;
    FloatingActionButton fab;
    private TextView nav_header_name, nav_header_email;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CardView viewAll, clothing, grocery, medical, electronics, furniture, hardware, others, profilePic;
    private android.support.v7.widget.Toolbar mToolbar;
    String currPass;

    SharedPreferences mPrefs;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/circular_std_book.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_dashboard);
        fab = (FloatingActionButton)findViewById(R.id.button_fab);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_drawer);
        setSupportActionBar(mToolbar);
        mDrawerLayout =(DrawerLayout)findViewById(R.id.dashboard_activity_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView mNavigationView = (NavigationView)findViewById(R.id.nav_view);
        mNavigationView.setItemIconTintList(null);
        nav_header_name = (TextView)mNavigationView.getHeaderView(0).findViewById(R.id.tv_nav_header_name);
        nav_header_email = (TextView)mNavigationView.getHeaderView(0).findViewById(R.id.tv_nav_header_email);
        if(mNavigationView != null)mNavigationView.setNavigationItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        viewAll = (CardView)findViewById(R.id.dashboardViewAll);
        clothing = (CardView)findViewById(R.id.dashboardClothing);
        grocery = (CardView)findViewById(R.id.dashboardGrocery);
        medical = (CardView)findViewById(R.id.dashboardMedical);
        electronics = (CardView)findViewById(R.id.dashboardElectronics);
        hardware = (CardView)findViewById(R.id.dashboardHardware);
        furniture = (CardView)findViewById(R.id.dashboardFurniture);
        others = (CardView)findViewById(R.id.dashboardOthers);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("Info");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nav_header_name.setText(dataSnapshot.child("name").getValue().toString());
                nav_header_email.setText(dataSnapshot.child("email").getValue().toString());
                currPass = dataSnapshot.child("password").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, CategoryActivity.class));
            }
        });
        clothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CategoryActivity.class);
                intent.putExtra("position", 0);
                startActivity(intent);

            }
        });
        electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CategoryActivity.class);
                intent.putExtra("position", 1);
                startActivity(intent);
            }
        });

        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CategoryActivity.class);
                intent.putExtra("position", 2);
                startActivity(intent);
            }
        });

        grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CategoryActivity.class);
                intent.putExtra("position", 3);
                startActivity(intent);

            }
        });
        hardware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CategoryActivity.class);
                intent.putExtra("position", 4);
                startActivity(intent);
            }
        });
        medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CategoryActivity.class);
                intent.putExtra("position", 5);
                startActivity(intent);
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CategoryActivity.class);
                intent.putExtra("position", 6);
                startActivity(intent);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, qrScanActivity.class));
            }
        });

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected((item)))return true;
        return super.onOptionsItemSelected(item);
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_logout)sigOut();
        //if (id == R.id.nav_settings)startActivity(new Intent(DashboardActivity.this, ExpensesActivity.class));
        if(id == R.id.nav_about) startActivity(new Intent(DashboardActivity.this, AboutActivity.class));
        if(id == R.id.nav_edit_profile){


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Current Password");
            final EditText currentPass = new EditText(this);
            currentPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(currentPass);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String pass = currentPass.getText().toString();
                    if (pass.equals(currPass))startActivity(new Intent(DashboardActivity.this, EditProfileActivity.class));
                    else{
                        dialogInterface.cancel();
                        Toast.makeText(DashboardActivity.this, "Password doesn't match",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
        return false;
    }
    private void sigOut(){
        mAuth.signOut();
        mPrefs = getSharedPreferences(LoginActivity.MyPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
        finish();
    }
}
