package com.class113.finalsh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterSignUp extends AppCompatActivity {
    protected static final int requestCodeSignUp= 55;
    private Button signUpBtn;
    private EditText fullNameET, passwordET, emailET,subPasswordET;
    private String fullName, password, email, subPassword;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sign_up);

        fullNameET = findViewById(R.id.signUpName);
        passwordET = findViewById(R.id.signUpPassword);
        emailET = findViewById(R.id.signUpEmail);
        subPasswordET = findViewById(R.id.signUpSubPassword);

        email = emailET.getText().toString();
        subPassword = subPasswordET.getText().toString();

        signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//first check the names and passwords with inner methods
                Log.d("AAAAAA1","sdfd");

                fullName = fullNameET.getText().toString();
                password = passwordET.getText().toString();
                Log.d("AAAAAA1",fullName+" "+password);

                //  if( check validation )
                User user = new User(fullName,password,email);
                databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                writeToDatabase(user);//method requires userObject to  upload it to firebase
               // Intent intent = new Intent();
            }
        });
    }

    private void writeToDatabase(User user) {
        Log.d("AAAA",user.toString());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("AAAAAA2",user.toString());

                if(snapshot.child(user.getFullName()).exists()) {//check if it exists
                    Log.d("AAAAAA4", user.toString());

                    Toast.makeText(RegisterSignUp.this, "username exists", Toast.LENGTH_LONG).show();
                }else{
                    Log.d("AAAAAA3",user.toString());
                    databaseReference.child(user.getFullName()).setValue(user);
                    Intent i = new Intent();
                    i.putExtra("userName" , user.getFullName());
                    i.putExtra("password" , user.getPassword());
                    setResult(RESULT_OK,i);
                    finish();//finishes and goes back to login
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}