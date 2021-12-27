package com.example.testprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list_view);
        List<Project> projectList = new ArrayList<Project>();
        for (int j = 0; j < projectList.size() ; j ++) {
            projectList.add(new Project("Example"));
        }

        List<String> projectNames = new ArrayList<String>();
        for (int i = 0; i < projectList.size(); i++) {
            projectNames.add(projectList.get(i).toString());
        }

        ArrayAdapter<Project> adapter = new ArrayAdapter<Project>(this, android.R.layout.simple_list_item_1, projectNames);
        listView.setAdapter(adapter);
    }
}