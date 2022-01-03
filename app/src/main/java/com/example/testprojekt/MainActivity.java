package com.example.testprojekt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    FloatingActionButton newProjectButton;
    EditText inputText;
    List<Project> projectList;
    ArrayAdapter<Project> adapter;
    FirebaseDatabase database;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        projectList = new ArrayList<Project>();
        adapter = new ArrayAdapter<Project>(this,  R.layout.list_view, R.id.item_text_view, projectList);

        //Database
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("Projects");

        //Button för att lägga till ett nytt projekt
        newProjectButton = findViewById(R.id.newProjectButton);
        newProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Alert dialog that shows up when newProjectButton is pressed
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Project Name");
                inputText = new EditText(MainActivity.this);
                inputText.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(inputText);

                // Listeners to the OK and Cancel buttons in the AlertDialog
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Project project = new Project(inputText.getText().toString());
                        projectList.add(project);
                        listView.setAdapter(adapter);
                        dbRef.setValue(inputText.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //För att se så lyssnaren fungerar
                Toast.makeText(MainActivity.this, "Du klickade på ett objekt i listan", Toast.LENGTH_SHORT).show();
                //Här ska innehållet bytas ut med Fragment kanske?
            }
        });
    }
}