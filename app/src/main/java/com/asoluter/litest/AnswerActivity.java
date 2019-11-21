package com.asoluter.litest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.asoluter.litest.Objects.Strings;

public class AnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ansver);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView ansText = findViewById(R.id.ansText);
        ansText.setText(getIntent().getStringExtra(Strings.TEST_RESULT));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //       .setAction("Action", null).show();
        });
    }

}
