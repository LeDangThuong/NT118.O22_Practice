package com.example.lab_5.Bai4;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import com.example.lab_5.R;

public class MusicTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private ProgressDialog progressDialog;
    private MediaPlayer mediaPlayer;

    public MusicTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.please_wait));
        progressDialog.show();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        mediaPlayer = MediaPlayer.create(context, R.raw.music_file); // Add your music file in res/raw folder
        mediaPlayer.start();
        mediaPlayer.setVolume(1.0f, 1.0f);
        try {
            Thread.sleep(mediaPlayer.getDuration());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        mediaPlayer.release();
    }
}
