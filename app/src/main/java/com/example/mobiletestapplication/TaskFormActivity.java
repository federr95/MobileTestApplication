package com.example.mobiletestapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import model.Task;

public class TaskFormActivity extends AppCompatActivity {

    private static final String TASK_VALUE = "task_value";

    private EditText editTextNomeAttivita;
    private EditText editTextDescrizioneAttivita;
    private EditText editTextPrioritaAttivita;
    private Button savingButton;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_taskdetailform);
        
        initializeView();

        initializeListener();
        
    }

    private void initializeListener() {

        savingButton.setOnClickListener(v -> {

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

            Intent resultIntent = new Intent();
            resultIntent.putExtra(
                    TASK_VALUE,
                    task
            );
            setResult(
                    RESULT_OK,
                    resultIntent
            );

            finish();

        });

    }

    private void initializeView() {

        editTextNomeAttivita = findViewById(R.id.editTextNomeAttivita);
        editTextDescrizioneAttivita = findViewById(R.id.editTextDescrizioneAttivita);
        editTextPrioritaAttivita = findViewById(R.id.editTextPrioritaAttivita);
        savingButton = findViewById(R.id.buttonSave);

    }

}
