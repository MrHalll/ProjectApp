package com.example.testprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ProjectInfoActivity extends AppCompatActivity {
    ImageButton deleteButton;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_info);

        title = findViewById(R.id.projectTitle);
        setTitle();
        deleteButton = findViewById(R.id.deleteProjectImage);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setTitle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            title.setText(extras.getString("projectName"));
        }
    }
}