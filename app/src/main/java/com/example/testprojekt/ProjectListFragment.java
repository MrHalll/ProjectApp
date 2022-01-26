package com.example.testprojekt;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.ListFragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProjectListFragment extends ListFragment {
    ListView listView;
    FloatingActionButton newProjectButton;
    EditText inputText;
    List<Project> projectList;
    ArrayAdapter<Project> adapter;
    FirebaseDatabase database;
    DatabaseReference dbRef;
    int projectID = 0;

    public ProjectListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);

        projectList = new ArrayList<>();
        adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, projectList);
        setListAdapter(adapter);

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
                    setListAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        //Button för att lägga till ett nytt projekt
        newProjectButton = view.findViewById(R.id.newProjectButton);
        newProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Alert dialog that shows up when newProjectButton is pressed
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Project Name");
                inputText = new EditText(getActivity());
                inputText.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(inputText);

                // Listeners to the OK and Cancel buttons in the AlertDialog
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Project project = new Project(inputText.getText().toString(), projectID);
                        dbRef.child(String.valueOf(projectID++)).setValue(project);
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

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    interface Listener {
        void itemClicked(long id);
    };
}