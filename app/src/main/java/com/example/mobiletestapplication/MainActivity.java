package com.example.mobiletestapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.navigation.ui.AppBarConfiguration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import adpters.TaskAdapter;
import model.Task;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private TextView counterView;
    private Button buttonAdd;
    private TextView lastActivity;
    private EditText editText;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private ArrayList<Task> taskArrayList = new ArrayList<>();

    private List<String> toDoActivitiesList = new LinkedList<>();
    private Integer numberOfActivityToBeDone = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MobileTestApplication", "onCreate");

        setContentView(R.layout.activity_main);
        setContentView(R.layout.content_main);

        initializeViews();

        initializeListeners();




//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    private void initializeListeners() {

        buttonAdd.setOnClickListener(v -> addActivityToList());

    }

    private void initializeViews() {

        counterView = findViewById(R.id.numberOfActivityToBeDoneView);
        buttonAdd  = findViewById(R.id.buttonAdd);
        lastActivity = findViewById(R.id.lastActivityToBeDoneView);
        editText = findViewById(R.id.editText);
        recyclerView = findViewById(R.id.recyclerTasks);
        taskAdapter = new TaskAdapter(taskArrayList);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);
    }


    private void addActivityToList() {

        String activityToBeDone = editText.getText().toString();
        if (TextUtils.isEmpty(activityToBeDone)) {
            editText.setError("Inserire un'attivita prima di premere il bottone Add!");
            return;
        }
        Task task = new Task();
        task.setTitle(activityToBeDone);
        taskArrayList.add(task);
        taskAdapter.notifyItemInserted(taskArrayList.size() - 1);
        toDoActivitiesList.add(activityToBeDone);
        String lastActivityAsString = "Ultima attività inserita: " + toDoActivitiesList.get(numberOfActivityToBeDone);
        lastActivity.setText(lastActivityAsString);
        numberOfActivityToBeDone++;
        String activityNumberAsString = "Numero delle attività: " + numberOfActivityToBeDone;
        counterView.setText(activityNumberAsString);
        editText.setText("");
    }

    @Override
    protected void onStart(){
        super.onStart();

        Log.d("MobileTestApplication", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("MobileTestApplication", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("MobileTestApplication", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("MobileTestApplication", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("MobileTestApplication", "onDestroy");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}