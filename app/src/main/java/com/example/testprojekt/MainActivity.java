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
        Project project = new Project("Android projekt");
        Project project2 = new Project("Algo projekt");
        Project project3 = new Project("Example projekt");

        List<Project> projectList = new ArrayList<Project>(5);
        for (Project project: projectList) {
            projectList.add(new Project("Example Projekt"));
        }
        ArrayAdapter<Project> adapter = new ArrayAdapter<Project>(this, android.R.layout.simple_list_item_1, projectList);
        listView.setAdapter(adapter);
    }
}