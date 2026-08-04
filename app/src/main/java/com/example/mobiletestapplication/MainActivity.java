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
import java.util.Objects;

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

    private Integer numberOfActivityToBeDone = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MobileTestApplication", "onCreate");

        setContentView(R.layout.activity_main);
        setContentView(R.layout.content_main);

        initializeViews();

        initializeListeners();

        restoreState(savedInstanceState);


//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("counter")) {
            String activityNumberAsString = "Numero delle attività: " + savedInstanceState.getInt("counter");
            counterView.setText(activityNumberAsString);
            numberOfActivityToBeDone = savedInstanceState.getInt("counter");
        }
        if (savedInstanceState != null && savedInstanceState.containsKey("lastActivity")) {
            String lastActivityAsString = "Ultima attività inserita: " + savedInstanceState.getString("lastActivity");
            lastActivity.setText(lastActivityAsString);
            Task lastActivity = new Task(savedInstanceState.getString("lastActivity"));
            taskArrayList.add(lastActivity);
        }
        if (savedInstanceState != null && savedInstanceState.containsKey("tasksList"))
            taskArrayList.addAll(Objects.requireNonNull(savedInstanceState.getParcelableArrayList("tasksList")));

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
        Task task = new Task(activityToBeDone);
        taskArrayList.add(task);
        taskAdapter.notifyItemInserted(taskArrayList.size() - 1);
        String lastActivityAsString = "Ultima attività inserita: " + taskArrayList.get(numberOfActivityToBeDone).getTitle();
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("onSaveInstanceState", "chiamata alla funzione onSaveInstanceState");
        if (numberOfActivityToBeDone != 0) {
            outState.putInt("counter", numberOfActivityToBeDone);
            outState.putSerializable("tasksList", taskArrayList);
            if (numberOfActivityToBeDone > 1) {
                outState.putString("lastActivity", taskArrayList.get(numberOfActivityToBeDone - 1).getTitle());
            } else outState.putString("lastActivity", taskArrayList.get(numberOfActivityToBeDone).getTitle());
        }
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}