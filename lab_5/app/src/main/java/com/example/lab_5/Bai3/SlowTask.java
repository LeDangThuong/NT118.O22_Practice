package com.example.lab_5.Bai3;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.lab_5.R;

public class SlowTask extends AsyncTask<Void, Integer, Void> {
    private Context context;
    private TextView tvStatus;
    private Long startTime;
    private ProgressDialog pdWaiting;

    public SlowTask(Context context, TextView tvStatus) {
        this.context = context;
        this.tvStatus = tvStatus;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pdWaiting = new ProgressDialog(context);
        startTime= System.currentTimeMillis();
        tvStatus.setText("Start time: "+startTime);
        pdWaiting.setMessage(context.getString(R.string.please_wait));
        pdWaiting.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (int i = 0; i <=5; i++) {
            try {
                Thread.sleep(2000);
                publishProgress(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Integer...values) {
        super.onProgressUpdate(values);
        tvStatus.setText(String.format("\nWorking ...", values[0]));
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (pdWaiting.isShowing()) {
            pdWaiting.dismiss();
        }
        tvStatus.append("\nEnd time: " + System.currentTimeMillis());
        tvStatus.append("\nDone!");

    }
}

