package com.class113.finalsh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterSignUp extends AppCompatActivity {
    private Button signUpBtn;
    private EditText userNameET, passwordET, emailET,subPasswordET;
    private String userName, password, email, subPassword;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sign_up);

        userNameET = findViewById(R.id.signUpName);
        passwordET = findViewById(R.id.signUpPassword);
        emailET = findViewById(R.id.signUpEmail);
        subPasswordET = findViewById(R.id.signUpSubPassword);

        email = emailET.getText().toString();
        subPassword = subPasswordET.getText().toString();

        signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//first check the names and passwords with inner methods
                userName = userNameET.getText().toString();
                password = passwordET.getText().toString();
                email = emailET.getText().toString();

                User user = new User(userName,password,email);
                Log.d("AAAAAAA",user.toString());

                databaseReference = FirebaseDatabase.getInstance().getReference("Users");
              //  FirebaseDatabase database = FirebaseDatabase.getInstance();
                databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Log.d("CCCCC","com");

                        DataSnapshot dataSnapshot = task.getResult();
                        if( dataSnapshot.child(userName).exists()){
                            Toast.makeText(RegisterSignUp.this, "username exists", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(RegisterSignUp.this, userName, Toast.LENGTH_LONG).show();

                            databaseReference.child(user.getUserName()).setValue(user);
                            Intent i = new Intent();
                            i.putExtra("username" , user.getUserName());
                            i.putExtra("password" , user.getPassword());
                            setResult(RESULT_OK,i);
                            finish();//finishes and goes back to login
                        }
                    }
                });
                writeToDatabase(user);//method requires userObject to  upload it to firebase
            }
        });
    }

    private void writeToDatabase(User user) {

        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override

            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Log.d("CCCCC","com");

                DataSnapshot dataSnapshot = task.getResult();
                if( dataSnapshot.child(userName).exists()){
                    Toast.makeText(RegisterSignUp.this, "username exists", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(RegisterSignUp.this, userName, Toast.LENGTH_LONG).show();

                    databaseReference.child(user.getUserName()).setValue(user);
                    Intent i = new Intent();
                    i.putExtra("userName" , user.getUserName());
                    i.putExtra("password" , user.getPassword());
                    setResult(RESULT_OK,i);
                    finish();//finishes and goes back to login
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("CCCCC","Fail");
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Log.d("CCCCC","Fail..");

            }
        });

    }


}