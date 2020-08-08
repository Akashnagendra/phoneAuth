package com.example.phoneauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddDetails extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    EditText firstname,lastName,email;
    Button saveBtn;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);
        firstname=findViewById(R.id.firstName);
        lastName=findViewById(R.id.lastName);
        email=findViewById(R.id.emailAddress);
        saveBtn=findViewById(R.id.saveBtn);

        firebaseAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userId=firebaseAuth.getCurrentUser().getUid();
        final DocumentReference docRef=fStore.collection("users").document(userId);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!firstname.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !email.getText().toString().isEmpty() ){
                    String first=firstname.getText().toString();
                    String last=lastName.getTransitionName().toString();
                    String useremail=email.getText().toString();

                    Map<String,Object> user= new HashMap<>();
                    user.put("firstName",first);
                    user.put("lastName",last);
                    user.put("emailAddress",useremail);

                    docRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                          if (task.isSuccessful()){
                              startActivity(new Intent(getApplicationContext(),MainActivity.class));
                              finish();
                          }else{
                              Toast.makeText(AddDetails.this,"Data is not Inserted",Toast.LENGTH_SHORT).show();
                          }
                        }
                    });
                }else{
                    Toast.makeText(AddDetails.this,"All Fields are Required",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}
