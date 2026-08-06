package com.example.mobiletestapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.navigation.ui.AppBarConfiguration;

import java.util.ArrayList;
import java.util.Objects;

import adpters.TaskAdapter;
import model.Task;

public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREFERENCES_NAME = "taskflow_preferences";
    private static final String TASK_NUMBER = "task_number";
    private static final String LAST_TASK_NAME = "last_task_name";
    private static final String LAST_TASK_DESCRIPTION = "last_task_description";
    private static final String LAST_TASK_PRIORITY = "last_task_priority";

    private AppBarConfiguration appBarConfiguration;

    // vista e contatore in cima alla schermata
    private TextView counterView;
    private Integer numberOfActivityToBeDone = 0;

    private Button buttonAdd;
    private Button buttonSettings;

    // vista per l'ultima attivita
    private TextView lastActivityView;
    private EditText editTextNomeAttivita;
    private EditText editTextDescrizioneAttivita;
    private EditText editTextPrioritaAttivita;

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private ArrayList<Task> taskArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MobileTestApplication", "onCreate");

        // qua si sta dicendo a quali files del layout bisogna collegare l'activity
        setContentView(R.layout.activity_main);
        setContentView(R.layout.content_main);

        initializeViews();

        initializeListeners();

        loadPreferencies(savedInstanceState);

        restoreState(savedInstanceState);

    }

    private void loadPreferencies(Bundle saveInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences(
                SHARED_PREFERENCES_NAME,
                MODE_PRIVATE);

        // check if already exist a task inside bundle if exist it won't upload the data
        if (saveInstanceState == null || (!saveInstanceState.containsKey("counter") ||
                (saveInstanceState.containsKey("counter") && saveInstanceState.getInt("counter") == 0))) {

            // check if already exist a task inside sharedPreferences
            String taskInsidePreferences = sharedPreferences.getString(LAST_TASK_NAME, "");

            if (taskInsidePreferences != null && !taskInsidePreferences.isEmpty()) {

                Task task = new Task(
                        sharedPreferences.getString(LAST_TASK_NAME, ""),
                        sharedPreferences.getString(LAST_TASK_DESCRIPTION, ""),
                        sharedPreferences.getInt(LAST_TASK_PRIORITY, 0));

                taskArrayList.add(task);
                taskAdapter = new TaskAdapter(taskArrayList);
                recyclerView.setLayoutManager(
                        new LinearLayoutManager(this));

                numberOfActivityToBeDone = sharedPreferences.getInt(TASK_NUMBER, 0);
                String tmp = counterView.getText() + " " + numberOfActivityToBeDone;
                counterView.setText(tmp);

                String tmpLastActivity = lastActivityView.getText() + " " + task.getTitle();
                lastActivityView.setText(tmpLastActivity);
            } else Log.i("loadPreferences", "there are no preferences to load!");
        }
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("counter")) {
            String activityNumberAsString = "Numero delle attività: " + savedInstanceState.getInt("counter");
            counterView.setText(activityNumberAsString);
            numberOfActivityToBeDone = savedInstanceState.getInt("counter");
        }
        if (savedInstanceState != null && savedInstanceState.containsKey("lastActivity")) {
            String lastActivityAsString = "Ultima attività inserita: " + savedInstanceState.getString("lastActivity");
            lastActivityView.setText(lastActivityAsString);
        }
        if (savedInstanceState != null && savedInstanceState.containsKey("tasksList"))
            taskArrayList.addAll(Objects.requireNonNull(savedInstanceState.getParcelableArrayList("tasksList")));

    }

    private void initializeListeners() {

        buttonAdd.setOnClickListener(v -> addActivityToList());

        // add botton to open settings
        buttonSettings.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SettingsActivity.class);
            v.getContext().startActivity(intent);
        });

    }

    private void initializeViews() {

        // inizializzazione delle view (textView, buttons)
        counterView = findViewById(R.id.numberOfActivityToBeDoneView);
        buttonAdd  = findViewById(R.id.buttonAdd);
        lastActivityView = findViewById(R.id.lastActivityToBeDoneView);
        editTextNomeAttivita = findViewById(R.id.editTextNomeAttivita);
        editTextDescrizioneAttivita = findViewById(R.id.editTextDescrizioneAttivita);
        editTextPrioritaAttivita = findViewById(R.id.editTextPrioritaAttivita);
        recyclerView = findViewById(R.id.recyclerTasks);
        buttonSettings = findViewById(R.id.buttonSettings);

        taskAdapter = new TaskAdapter(taskArrayList);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);
    }


    private void addActivityToList() {

        // check che il form sia pieno
        String nameActivityToBeDone = editTextNomeAttivita.getText().toString();
        if (TextUtils.isEmpty(nameActivityToBeDone)) {
            editTextNomeAttivita.setError("Inserire un'attivita prima di premere il bottone Add!");
            return;
        }

        String descriptionActivityToBeDone = editTextDescrizioneAttivita.getText().toString();
        if (TextUtils.isEmpty(descriptionActivityToBeDone)) {
            editTextDescrizioneAttivita.setError("Inserire una descrizione prima di premere il bottone Add!");
            return;
        }

        String priorityActivityToBeDone = String.valueOf(editTextPrioritaAttivita.getText());
        if (priorityActivityToBeDone.isEmpty() || priorityActivityToBeDone.toCharArray().length != 1 ||
                !Character.isDigit(priorityActivityToBeDone.charAt(0))) {
            editTextPrioritaAttivita.setError("Inserire una priorita da 0 a 9 prima di premere il bottone Add!");
            return;
        }


        Task task = new Task(nameActivityToBeDone, descriptionActivityToBeDone, Integer.parseInt(priorityActivityToBeDone));
        taskArrayList.add(task);
        taskAdapter.notifyItemInserted(taskArrayList.size() - 1);

        // aggiunta del testo nella text view dell'ultima attività aggiunta
        String lastActivityAsString = "Ultima attività inserita: " + taskArrayList.get(numberOfActivityToBeDone).getTitle();
        lastActivityView.setText(lastActivityAsString);

        // aggiunta del testo nella text view del numero attività
        numberOfActivityToBeDone++;
        String activityNumberAsString = "Numero delle attività: " + numberOfActivityToBeDone;
        counterView.setText(activityNumberAsString);

        // rimozione testo dal form e chiusura della tastiera
        editTextNomeAttivita.setText("");
        editTextDescrizioneAttivita.setText("");
        editTextPrioritaAttivita.setText("");

        editTextNomeAttivita.clearFocus();
        editTextDescrizioneAttivita.clearFocus();
        editTextPrioritaAttivita.clearFocus();

        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.hideSoftInputFromWindow(editTextPrioritaAttivita.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(editTextDescrizioneAttivita.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(editTextNomeAttivita.getWindowToken(), 0);
        }

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
        
        savePreferencies();
    }

    private void savePreferencies() {

        SharedPreferences preferences = getSharedPreferences(
                SHARED_PREFERENCES_NAME,
                MODE_PRIVATE
        );

        SharedPreferences.Editor editor = preferences.edit();

        if (numberOfActivityToBeDone == 1) {
            editor.putString(LAST_TASK_NAME, taskArrayList.get(0).getTitle());
            editor.putString(LAST_TASK_DESCRIPTION, taskArrayList.get(0).getDescription());
            editor.putInt(LAST_TASK_PRIORITY, taskArrayList.get(0).getPriority());
            editor.putInt(TASK_NUMBER, 1);
        } else {
            editor.putString(LAST_TASK_NAME, taskArrayList.get(numberOfActivityToBeDone - 1).getTitle());
            editor.putString(LAST_TASK_DESCRIPTION, taskArrayList.get(numberOfActivityToBeDone - 1).getDescription());
            editor.putInt(LAST_TASK_PRIORITY, taskArrayList.get(numberOfActivityToBeDone - 1).getPriority());
        }

        editor.apply();
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
            } else outState.putString("lastActivity", taskArrayList.get(0).getTitle());
        }
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}