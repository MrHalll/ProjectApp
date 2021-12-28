package com.example.testprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        List<String> projectList = new ArrayList<String>();
        projectList.add(new Project("Example").toString());
        projectList.add(new Project("Android projekt").toString());
        projectList.add(new Project("Djikstra projekt").toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  R.layout.list_view, R.id.item_text_view, projectList);
        listView.setAdapter(adapter);

        //Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        //Button för att lägga till ett nytt projekt
        newProjectButton = findViewById(R.id.newProjectButton);
        newProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectList.add(new Project("Nytt projekt").toString());
                myRef.setValue("Nytt projekt");
            }
        });
    }
}