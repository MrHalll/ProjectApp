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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProjectListFragment extends Fragment {
    ListView listView;
    ArrayAdapter<Project> adapter;
    FloatingActionButton newProjectButton;
    EditText inputText;
    private onProjectAddedListener listener;

    public interface onProjectAddedListener {
        public void onProjectAdd(EditText inputText);
    }

    public ProjectListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onProjectAddedListener) {
            listener = (onProjectAddedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ProjectListFragment.onProjectAddedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);

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
                        listener.onProjectAdd(inputText);
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
        ArrayList<Project> projectList = getArguments().getParcelableArrayList("projectList");
        adapter = new ArrayAdapter<Project>(getActivity(),  R.layout.list_view, R.id.item_text_view, projectList);

        listView = view.findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //byt till andra fragment
            }
        });
        return view;
    }
}