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
        List<String> projectList = new ArrayList<String>();
        projectList.add(new Project("Example").toString());
        projectList.add(new Project("Android projekt").toString());
        projectList.add(new Project("Djikstra projekt").toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  R.layout.list_view, R.id.item_text_view,
                projectList);
        listView.setAdapter(adapter);
    }
}