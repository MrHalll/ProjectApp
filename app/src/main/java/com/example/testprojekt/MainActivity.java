package com.example.testprojekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ChecklistFragment.OnProjectDeletedListener{
    ArrayList<Project> projectList;
    FirebaseDatabase database;
    DatabaseReference dbRef;
    int projectID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        projectList = new ArrayList<>();

        //Database
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("Projects");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<Project>> type = new GenericTypeIndicator<ArrayList<Project>>() {};
                ArrayList<Project> project = snapshot.getValue(type);
                if (project != null) {
                    projectList.clear();
                    projectID = project.size();
                    for (int i = 0; i < project.size() ; i++) {
                        Project tempProject = project.get(i);
                        projectList.add(tempProject);
                    }
                    //listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Fragment projectListFrag = new ProjectListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("projectList", projectList);
        projectListFrag.setArguments(bundle);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentContainer, ProjectListFragment.class, null)
                    .commit();
        }

    }

    @Override
    public void onProjectDelete(String link) {
        //Kod för att ta bort projekt från databas
    }
}