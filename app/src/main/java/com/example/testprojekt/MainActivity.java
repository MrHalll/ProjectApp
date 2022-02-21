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

/**
 * The main class which handles the connection to the database and can communicate with the two
 * fragments ProjectListFragment and ChecklistFragment.x
 */
public class MainActivity extends AppCompatActivity implements ChecklistFragment.onProjectDeletedListener, ProjectListFragment.onProjectAddedListener {
    ArrayList<Project> projectList;
    FirebaseDatabase database;
    DatabaseReference dbProjectRef;
    int projectID = 0;

    /**
     * This is the first method that is called when the activity is launched. Creates a reference
     * to the database and reads the data if it exists.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        projectList = new ArrayList<>();

        //Database
        database = FirebaseDatabase.getInstance();
        dbProjectRef = database.getReference("Projects");
        dbProjectRef.addValueEventListener(new ValueEventListener() {
            /**
             * This method is called every time a change is made in the database.
             * @param snapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<Project>> type = new GenericTypeIndicator<ArrayList<Project>>() {};
                List<Project> project = new ArrayList<Project>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    project.add((Project) ds.getValue(Project.class));
                }
                projectList.clear();
                if (project != null) {
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

    /**
     * Method that replaces the fragmentContainerView with projectListFrag and passes the list of
     * projects to that fragment using a bundle.
     *
     */
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

    /**
     * Method implemented by the interface onProjectDeletedListener which can be called from the
     * fragments to delete a specific project from database
     * @param projectId The id of the project that should be removed
     */
    @Override
    public void onProjectDelete(int projectId) {
        dbProjectRef.child(String.valueOf(projectId)).removeValue();
        projectID++;
    }

    /**
     * Method implemented by the interface onProjectAddedListener which can be called from the
     * fragments to add a project to the database
     * @param name The name of the project
     */
    @Override
    public void onProjectAdd(String name) {
        projectID++;
        Project project = new Project(name, projectID);
        dbProjectRef.child(String.valueOf(projectID)).setValue(project);
    }
}