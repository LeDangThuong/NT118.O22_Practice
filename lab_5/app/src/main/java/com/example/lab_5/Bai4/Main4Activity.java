package com.example.lab_5.Bai4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab_5.R;

public class Main4Activity extends AppCompatActivity {

    private Button btnPlayMusic;
    private MusicTask musicTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        btnPlayMusic = findViewById(R.id.btn_play_music);

        btnPlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicTask = new MusicTask(Main4Activity.this);
                musicTask.execute();
            }
        });
    }
}