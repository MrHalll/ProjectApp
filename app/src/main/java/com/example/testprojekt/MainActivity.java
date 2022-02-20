package com.example.testprojekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ChecklistFragment.onProjectDeletedListener, ProjectListFragment.onProjectAddedListener {
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
                addFragment();
                GenericTypeIndicator<ArrayList<Project>> type = new GenericTypeIndicator<ArrayList<Project>>() {};
                ArrayList<Project> project = snapshot.getValue(type);
                if (project != null) {
                    projectList.clear();
                    projectID = project.size();
                    for (int i = 0; i < project.size() ; i++) {
                        Project tempProject = project.get(i);
                        projectList.add(tempProject);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("projectList", projectList);
        Fragment projectListFrag = new ProjectListFragment();
        projectListFrag.setArguments(bundle);

        getSupportFragmentManager().
                beginTransaction()
                .add(R.id.fragmentContainer, projectListFrag)
                .commit();
    }

    @Override
    public void onProjectDelete(int project) {
        dbRef.child("Projects").child(String.valueOf(project)).removeValue();
    }

    @Override
    public void onProjectAdd(EditText inputText, ArrayList checklist) {
        Project project = new Project(inputText.getText().toString(), projectID);
        dbRef.child(String.valueOf(projectID)).setValue(project);
        dbRef.child(String.valueOf(projectID)).child(project.getName()).setValue(checklist);
    }
}