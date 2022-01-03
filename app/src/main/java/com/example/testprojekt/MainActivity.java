package com.example.testprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    FloatingActionButton newProjectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        List<Project> projectList = new ArrayList<Project>();
        ArrayAdapter<Project> adapter = new ArrayAdapter<Project>(this,  R.layout.list_view, R.id.item_text_view, projectList);

        //Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setLogLevel(Logger.Level.DEBUG);
        DatabaseReference myRef = database.getReference("message");

        //Button för att lägga till ett nytt projekt
        newProjectButton = findViewById(R.id.newProjectButton);
        newProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.setValue("Oskar");
                Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
                projectList.add(new Project("Nytt projekt"));
                listView.setAdapter(adapter);
            }
        });
    }
}