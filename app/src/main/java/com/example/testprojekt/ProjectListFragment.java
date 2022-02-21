package com.example.testprojekt;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment som innehåller lista med alla projekt i databasen, även en knapp för att lägga till projekt
 *
 * @author Oskar Persson & Melvin Hall
 */

public class ProjectListFragment extends Fragment {
    ListView listView;
    ArrayAdapter<Project> adapter;
    FloatingActionButton newProjectButton;
    EditText inputText;
    private onProjectAddedListener listener;
    ArrayList<Project> projectList;
    ArrayList<String> checklist;

    /**
     * Interface som används med listener
     */
    public interface onProjectAddedListener {
        public void onProjectAdd(EditText inputText, ArrayList checklist);
    }

    public ProjectListFragment() {
        // Required empty public constructor
    }

    /**
     * Metod som körs när fragmentet anknyts till activity
     * Skapar Listener
     * @param context
     * @return void
     */
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

    /**
     * Körs när fragmentet skapas, kallar endast super
     * @param savedInstanceState
     * @return void
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Metod som skapar view genom inflater
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);
        listView = view.findViewById(R.id.list_view);
        newProjectButton = view.findViewById(R.id.newProjectButton);

        projectList = getArguments().getParcelableArrayList("projectList");
        adapter = new ArrayAdapter<>(getActivity(),  R.layout.list_view, R.id.item_text_view, projectList);
        listView.setAdapter(adapter);

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
                        checklist = new ArrayList<>();
                        listener.onProjectAdd(inputText, checklist);
                        listView.setAdapter(adapter);
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
            //När användare klickar på ett projekt byts detta fragment ut mot ett ChecklistFragment
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("projectList", projectList);
                bundle.putStringArrayList("checklist", checklist);
                bundle.putParcelable("project", projectList.get(position));
                ChecklistFragment checklistFrag = new ChecklistFragment();
                checklistFrag.setArguments(bundle);
                getActivity().getSupportFragmentManager().
                        beginTransaction()
                        .replace(R.id.fragmentContainer, checklistFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }
}