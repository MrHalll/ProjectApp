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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ChecklistFragment.onProjectDeletedListener, ProjectListFragment.onProjectAddedListener {
    ArrayList<Project> projectList;
    FirebaseDatabase database;
    DatabaseReference dbProjectRef;
    DatabaseReference dbTaskRef;
    int projectID = 0;
    int taskID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        projectList = new ArrayList<>();

        //Database
        database = FirebaseDatabase.getInstance();
        dbTaskRef = database.getReference("Tasks");
        dbProjectRef = database.getReference("Projects");
        dbProjectRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<Project>> type = new GenericTypeIndicator<ArrayList<Project>>() {};
                List<Project> project = new ArrayList<Project>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    project.add((Project) ds.getValue(Project.class));
                }
                projectList.clear();
                if (project != null) {
                    //projectID = project.size();
                    for (int i = 0; i < project.size() ; i++) {
                        if (project.get(i) != null) {
                            Project tempProject = project.get(i);
                            projectList.add(tempProject);
                        }
                    }
                }
                addFragment();
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
                .replace(R.id.fragmentContainer, projectListFrag)
                .commit();
    }

    @Override
    public void onProjectDelete(int projectId) {
        dbProjectRef.child(String.valueOf(projectId)).removeValue();
        addFragment();
        projectID++;
    }

    @Override
    public void onProjectAdd(EditText inputText, ArrayList checklist) {
        projectID++;
        Project project = new Project(inputText.getText().toString(), projectID);
        dbProjectRef.child(String.valueOf(projectID)).setValue(project);
        for (Object task : checklist) {
            dbProjectRef.child(String.valueOf(projectID)).child(String.valueOf(taskID)).setValue(task);
        }


        taskID++;
        //dbRef.child(String.valueOf(projectID)).updateChildren()
    }
}