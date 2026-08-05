package com.example.mobiletestapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import model.Task;

public class TaskActivity extends AppCompatActivity {

    private TextView nome;
    private TextView priorita;
    private TextView completata;
    private TextView descrizione;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TaskActivity", "TaskActivity onCreate() method");

        setContentView(R.layout.activity_taskdetail);

        Intent intent = getIntent();
        Task task = intent.getParcelableExtra("task");

        intializeView(task);
    }

    private void intializeView(Task task) {

        nome = findViewById(R.id.task_name);
        descrizione = findViewById(R.id.task_description);
        completata = findViewById(R.id.task_completed);
        priorita = findViewById(R.id.task_priority);

        nome.setText(getString(R.string.task_name, task.getTitle()));
        descrizione.setText(getString(R.string.task_description, task.getDescription()));
        priorita.setText(getString(R.string.task_priority, task.getPriority()));
    }

}
