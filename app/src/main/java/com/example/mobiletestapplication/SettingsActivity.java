package com.example.mobiletestapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    private String viewButtonForInternetSearch;
    private Button internetLinkButton;

    @Override
    public void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);

        Log.d("SettingsActivity", "chiamata verso la pagina delle impostazioni");

        setContentView(R.layout.activity_settings);

        initializeView();
        initializeListeners();

    }

    private void initializeListeners() {

        internetLinkButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.google.com"));
            if (intent.resolveActivity(getPackageManager()) != null)
                startActivity(intent);
        });

    }

    private void initializeView() {
        internetLinkButton = findViewById(R.id.buttonSettings2);

    }


}
