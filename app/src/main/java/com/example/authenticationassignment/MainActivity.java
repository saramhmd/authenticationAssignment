package com.example.authenticationassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Button logoutbtn;
    TextView detailes;
    FirebaseUser user = mAuth.getCurrentUser();

//sara
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutbtn = findViewById(R.id.logout);
        detailes = findViewById(R.id.user_detailes);
        if (user == null){
            Intent intent= new Intent(getApplicationContext(), logIn.class);
            startActivity(intent);
            finish();
        }else {
            detailes.setText(user.getEmail());
        }
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(getApplicationContext(), logIn.class);
                startActivity(intent);
                finish();
             }
        });
    }
}