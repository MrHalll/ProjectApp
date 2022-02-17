package com.example.testprojekt;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ChecklistFragment extends Fragment {

    private onProjectDeletedListener listener;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_list, container, false);
    }

    public void onSomeClick(View v) {
        listener.onProjectDelete("some link");
    }
}