package com.example.authenticationassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.TransactionOptions;

public class UpdateUserData extends AppCompatActivity {
    EditText editTextName, editTextPhone;
    Button updateEmail ,updateProfileButton;
    ImageView uploadImage;
    FirebaseAuth authProfile;
    String textName,textPhone;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference mDatabase;
    CollectionReference usersRef = db.collection("AuthenticatedUser");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_data);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        uploadImage = findViewById(R.id.updateImage);
//        updateEmail = findViewById(R.id.updateEmail);
        updateProfileButton = findViewById(R.id.updateProfileButton);
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("AuthenticatedUser");

        String userIdOfRegisterd = firebaseUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AuthenticatedUser");
        databaseReference.child("GrSqoJY3bihpfGPKliSdPxx6cA62").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Log.d("sara", firebaseUser.getDisplayName());

                if (user != null){
                    textName = firebaseUser.getDisplayName();
                    textPhone = user.getPhoneNumber();
                    editTextPhone.setText(textPhone);
                    editTextName.setText(textName);
                }else {
                    Toast.makeText(UpdateUserData.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateUserData.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        });
//
        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });



    }




}