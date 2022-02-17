package com.example.testprojekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("projectList", projectList);
        Fragment projectListFrag = new ProjectListFragment();
        projectListFrag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentContainer, ProjectListFragment.class, null)
                .commit();

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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onProjectDelete(String link) {
        //Kod för att ta bort projekt från databas
    }

    @Override
    public void onProjectAdd(EditText inputText) {
        Project project = new Project(inputText.getText().toString(), projectID);
        dbRef.child(String.valueOf(projectID++)).setValue(project);
    }
}