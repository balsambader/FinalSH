package com.class113.finalsh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends Activity {
    protected static final int REQUEST_CODE_SIGN_UP = 55;

    private Button loginBtn ;
    private EditText usernameET, passwordET;
    private TextView registerNowClick;
    private DatabaseReference databaseReference;// upload to firebase
    private SharedPreferences sharedPreferences;//saves data in users phone
    private SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");//build a category called "users" in firebase realtime(realtime database)

        usernameET = findViewById(R.id.LoginUsername);
        passwordET = findViewById(R.id.LoginPassword);
        registerNowClick = findViewById(R.id.RegisterNowClick);
        loginBtn = findViewById(R.id.loginBtn);


        //this saves data so we dont register again
        sharedPreferences = getSharedPreferences("data.txt" , MODE_PRIVATE);//uploads info in data.txt file
        editor = sharedPreferences.edit();
        String name = sharedPreferences.getString("username",null);
        if(name != null){// there is a user who has logged in
            Intent intent = new Intent(Login.this,MainActivity.class);
            intent.putExtra("username",name);
            startActivity(intent);
        }


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username   = usernameET.getText().toString();//convert edittext username,password to texts(String)
                String password  = passwordET.getText().toString();
                databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot snapshot = task.getResult();
                        if(snapshot.child(username).exists()){
                            User user  = snapshot.child(username).getValue(User.class);//checks if login exists
                            if( user.getPassword().equals(password)){
                                Intent intent = new Intent(Login.this,MainActivity.class);
                                editor.putString("username",user.getUserName());//saves as key : value
                                editor.putString("password",user.getPassword());
                                editor.commit();//finish editing
                                startActivity(intent);
                            }else{
                                Toast.makeText(Login.this,"password Error",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(Login.this, "User Name isn't Exist", Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });


        registerNowClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent: saves data (and transform it between two activities)
                Intent intent  = new Intent(Login.this,RegisterSignUp.class);
                startActivityForResult(intent, REQUEST_CODE_SIGN_UP);//goes from this activity to another and then continues methods below here from the method: onActivityREsult
            }
        });



    }
    //public methods

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_SIGN_UP && resultCode == RESULT_OK) {
            usernameET.setText( data.getStringExtra("username"));
            passwordET.setText(data.getStringExtra("password"));
        }
    }
}