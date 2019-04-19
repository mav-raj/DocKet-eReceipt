package com.solution.engineering.docket_ereceipt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String email;

    SharedPreferences mPrefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        final EditText nameEdit = (EditText)findViewById(R.id.edit_name);
        TextView  passwordEdit = (TextView)findViewById(R.id.edit_password);
        final Button confirmEdit = (Button)findViewById(R.id.confirm_edit_button);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("Info");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameEdit.setText(dataSnapshot.child("name").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        passwordEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference dRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("Info");
                dRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        email = dataSnapshot.child("email").getValue().toString();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                builder.setTitle("Send Password Reset Email");
                builder.setMessage("If you press YES, then we will send a reset password email linked  with this account and you will currently be LOGGED OUT. Do you want to continue?");

//                To create two edit text under a same alert dialog
//                Context context = EditProfileActivity.this;
//                LinearLayout layout = new LinearLayout(context);
//                layout.setOrientation(LinearLayout.VERTICAL);
//                final EditText newPass = new EditText(context);
//                newPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                newPass.setHint("New Password");
//                layout.addView(newPass);
//                final EditText confirmNewPass = new EditText(context);
//                confirmNewPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                confirmNewPass.setHint("Confirm New Password");
//                layout.addView(confirmNewPass);
//                builder.setView(layout);

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(EditProfileActivity.this,"Password reset link has been sent, Check Email",Toast.LENGTH_LONG).show();
                                    sigOut();
                                }
                                else Toast.makeText(EditProfileActivity.this,"Registered Email not found",Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });


                builder.show();

            }
        });

        confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                builder.setTitle("Do you want to save the changes made?");
                 builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         String name = nameEdit.getText().toString();
                         DatabaseReference dRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("Info");
                         dRef.child("name").setValue(name);
                         Toast.makeText(EditProfileActivity.this, "Changes Saved", Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(EditProfileActivity.this, DashboardActivity.class));

                     }
                 });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();

            }
        });


    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(EditProfileActivity.this,DashboardActivity.class));
        finish();
    }

    private void sigOut(){
        mAuth.signOut();
        mPrefs = getSharedPreferences(LoginActivity.MyPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(EditProfileActivity.this,LoginActivity.class));
        finish();
    }
}
