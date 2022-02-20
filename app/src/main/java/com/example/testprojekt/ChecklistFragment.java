package com.example.testprojekt;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ChecklistFragment extends Fragment {

    private onProjectDeletedListener listener;
    ImageButton deleteProjectBtn;
    TextView title;
    ListView listView;
    FloatingActionButton newTaskButton;
    ArrayList<String> checklist;
    ArrayList<Project> projectList;
    ArrayAdapter<String> adapter;
    EditText inputText;
    Project project;


    public interface onProjectDeletedListener {
        public void onProjectDelete(int project);
    }

    public ChecklistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onProjectDeletedListener) {
            listener = (onProjectDeletedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnProjectedDeletedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_list, container, false);

        title = view.findViewById(R.id.projectTitle);
        listView = view.findViewById(R.id.list_view);
        deleteProjectBtn = view.findViewById(R.id.deleteButton);
        newTaskButton = view.findViewById(R.id.newProjectButton);

        //Hämtar det projekt man klickade på
        projectList = getArguments().getParcelableArrayList("projectList");
        project = getArguments().getParcelable("project");
        checklist = getArguments().getStringArrayList("checklist");
        title.setText(project.getName());
        if (checklist != null){
            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_multiple_choice, checklist);
            listView.setAdapter(adapter);
        }

        deleteProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete the project in the database and switch fragment
                listener.onProjectDelete(project.getID());
            }
        });

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("New task");
                inputText = new EditText(getActivity());
                inputText.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(inputText);
                // Listeners to the OK and Cancel buttons in the AlertDialog
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //lägg till en ny task i checklistan
                        String task = inputText.getText().toString();
                        updateChecklist(task);
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
        return view;
    }

    private void updateChecklist(String task) {
        project.addToChecklist(task);
        checklist = project.getChecklist();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_multiple_choice, checklist);
        listView.setAdapter(adapter);
    }

    public void onSomeClick(View v) {
        listener.onProjectDelete(0);
    }
}