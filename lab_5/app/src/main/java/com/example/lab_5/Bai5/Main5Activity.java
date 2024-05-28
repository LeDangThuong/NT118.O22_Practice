package com.example.lab_5.Bai5;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import com.example.lab_5.R;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Main5Activity extends AppCompatActivity {
    private Button btnPlayMusic;
    private Disposable disposable;
    private ProgressDialog progressDialog;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        btnPlayMusic = findViewById(R.id.btn_play_music);

        btnPlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusicWithRxJava();
            }
        });
    }

    private void playMusicWithRxJava() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        disposable = Observable.fromCallable(this::playMusic)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        },
                        throwable -> {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(Main5Activity.this, "Error playing music", Toast.LENGTH_SHORT).show();
                        }
                );
    }

    private boolean playMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.music_file); // Add your music file in res/raw folder
        mediaPlayer.start();
        try {
            Thread.sleep(mediaPlayer.getDuration());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mediaPlayer.release();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
