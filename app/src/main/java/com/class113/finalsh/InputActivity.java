package com.class113.finalsh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputActivity extends AppCompatActivity {
    private EditText workNameET;
    private EditText descriptionET;
    private Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);


        finish = findViewById(R.id.finishInp);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workNameET = findViewById(R.id.workNameInp);
                descriptionET = findViewById(R.id.descriptionInp);
                Intent data  = new Intent();
                data.putExtra("workName",workNameET.getText().toString());
                data.putExtra("description",descriptionET.getText().toString());
                setResult(RESULT_OK,data);
                finish();
            }
        });



    }
}