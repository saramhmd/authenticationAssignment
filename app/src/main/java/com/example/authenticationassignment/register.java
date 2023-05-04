package com.example.authenticationassignment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.time.temporal.TemporalUnit;

public class register extends AppCompatActivity {
    EditText editTextEmail;
    EditText editTextPass;
    Button regiterBtn;
    Button logIn;
    ProgressBar progressBar;
    EditText name, phone ;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
FirebaseUser firebaseUser;
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent= new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextEmail = findViewById(R.id.emailr);
        editTextPass = findViewById(R.id.editTextTextPasswordr);
        regiterBtn = findViewById(R.id.register_btnr);
        logIn = findViewById(R.id.logIn_btnr);
        progressBar = findViewById(R.id.progressBarr);
        name = findViewById(R.id.editTextTextPersonNameR);
        phone = findViewById(R.id.editTextPhoneR);
        firebaseUser = mAuth.getCurrentUser();
        String name1 =name.getText().toString();
        String phone1 =phone.getText().toString();

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), com.example.authenticationassignment.logIn.class);
                startActivity(intent);
//                finish();
            }
        });
        regiterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email,password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPass.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(register.this,"Enter Email",Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(register.this,"Enter Password",Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    registerUser(name1,phone1,email,password);
                }





    }
    public void registerUser (String name , String phone ,String email ,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Log.d("TAG", "createUserWithEmail:success");

                            FirebaseMessaging.getInstance().subscribeToTopic("topic profile")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Log.e("sara","done");
                                            }else {
                                                Log.e("sara","failed");
                                            }
                                        }
                                    });


                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest userProfileChangeRequest= new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                            user.updateProfile(userProfileChangeRequest);
                            User user1 = new User(name1,phone1);
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("userAuthentication");
                            databaseReference.child(firebaseUser.getUid()).setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(register.this, "User Registered", Toast.LENGTH_LONG).show();
                                        Intent intent= new Intent(getApplicationContext(), com.example.authenticationassignment.MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
        });
    }
}