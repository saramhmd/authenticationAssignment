package com.example.authenticationassignment;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    Button logoutbtn, choosePhoto, addUser ,uploadImage,updateData;
    TextView detailes;
    EditText name, phone ;
    StorageReference storageRef;
    FirebaseStorage storage;
    ImageView personImage;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri uriImage;
    private DatabaseReference mDatabase;

    //sara
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutbtn = findViewById(R.id.logout);
        detailes = findViewById(R.id.user_detailes);
        name = findViewById(R.id.editTextTextPersonName);
        phone = findViewById(R.id.editTextPhone);
        choosePhoto = findViewById(R.id.chooseImageButton);
        personImage = findViewById(R.id.imageView);
        addUser = findViewById(R.id.addData);
        updateData = findViewById(R.id.updateDataMain);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        uploadImage = findViewById(R.id.uploadImage);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        CollectionReference usersRef = db.collection("AuthenticatedUser");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storageRef = storage.getReference().child("DislayImages");




        if (firebaseUser == null) {
            Intent intent = new Intent(getApplicationContext(), logIn.class);
            startActivity(intent);
            finish();
        } else {
            detailes.setText(firebaseUser.getEmail());
            name.setText(firebaseUser.getDisplayName());
//            phone.setText(user.getPhoneNumber());
        }

        //Add User
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String addName = name.getText().toString();
               String addPhone = phone.getText().toString();
               User user = new User(addName, addPhone);
               usersRef.add(user);
            }
        });

        // Choose Image
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uriImage != null){
                    StorageReference fileRef = storageRef.child(mAuth.getCurrentUser().getUid() + "."+ getFiLExtention(uriImage));
                    fileRef.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUri = uri;
                                    firebaseUser = mAuth.getCurrentUser();
                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                            .setPhotoUri(downloadUri).build();
                                    firebaseUser.updateProfile(profileChangeRequest);
                                }
                            });
                        }
                    });
                }
            }
        });

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), UpdateUserData.class);
                intent.putExtra("id",firebaseUser.getUid());
                startActivity(intent);

            }
        });

        // Log Out
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), logIn.class);
                startActivity(intent);
                finish();
            }
        });
    }
//
//    public void writeNewUser(String userId, String name, String phone) {
//        User user = new User(userId,name, phone);
//
//        mDatabase.child("users").child(userId).setValue(user);
//    }
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }
    private String getFiLExtention(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri) );
    }
    //Upload Image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImage = data.getData();
            personImage.setImageURI(uriImage);

        }
    }

}
