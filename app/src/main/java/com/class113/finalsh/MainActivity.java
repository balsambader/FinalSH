package com.class113.finalsh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private static final int REQUEST_CODE = 6473;
    private List<WorkInfo> listItems;
    ListView listView;
    ListViewAdapter listViewAdapter;
    DatabaseReference databaseReference;
    private Button addBtn;
    private SharedPreferences sharedPreferences;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sharedPreferences = getSharedPreferences("data.txt",MODE_PRIVATE);
       addBtn = findViewById(R.id.addBtn);

        databaseReference = FirebaseDatabase.getInstance().getReference("Works");
        listItems = new ArrayList<WorkInfo>();
        listView = findViewById(R.id.listWorkItems);
        listViewAdapter = new ListViewAdapter(this,R.layout.list_work_item,listItems);
        listView.setAdapter(listViewAdapter);

        userName = sharedPreferences.getString("username","");
        Log.d("AAAA",userName);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listItems.clear();
                for( DataSnapshot dataSnapshot : snapshot.getChildren()){
                    for(DataSnapshot userWorks : dataSnapshot.getChildren()) {
                        WorkInfo workInfo = userWorks.getValue(WorkInfo.class);
                        listItems.add(workInfo);
                    }
                }
                listViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,InputActivity.class);
                startActivityForResult(i,REQUEST_CODE);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,userName+" "+requestCode+" "+resultCode+" "+RESULT_OK);
        if( requestCode == REQUEST_CODE && resultCode == RESULT_OK ){
            WorkInfo workInfo = new WorkInfo(data.getStringExtra("workName"),data.getStringExtra("description"));
            Log.d(TAG,workInfo.toString());
            databaseReference.child(userName).push().setValue(workInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d(TAG,workInfo+" saved to database");
                }
            });

        }

    }
}