package com.example.testprojekt;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public class ChecklistFragment extends Fragment {

    private onProjectDeletedListener listener;
    ImageButton deleteProjectBtn;
    TextView title;

    public interface onProjectDeletedListener {
        public void onProjectDelete(String link);
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
        String projectName = getArguments().getString("projectName");
        title.setText(projectName);
        deleteProjectBtn = view.findViewById(R.id.newProjectButton);
        deleteProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete project
            }
        });
        return view;
    }

    public void onSomeClick(View v) {
        listener.onProjectDelete("some link");
    }
}