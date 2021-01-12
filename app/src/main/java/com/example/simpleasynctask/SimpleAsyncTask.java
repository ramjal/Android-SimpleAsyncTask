package com.example.simpleasynctask;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {

    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> mProgressBar;

    SimpleAsyncTask(TextView tv, ProgressBar pb) {

        mTextView = new WeakReference<>(tv);
        mProgressBar = new WeakReference<>(pb);
    }

    @Override
    protected String doInBackground(Void... voids) {
        Random r = new Random();
        int n = r.nextInt(11) + 1;
        int sleepTime = n * 1000;  //Make it big enough
        int sleepTimeChunk = sleepTime / 10;

        try {
            //Thread.sleep(sleepTime);
            for (int i = 0; i < 10; i++) {
                Thread.sleep(sleepTimeChunk);
                publishProgress(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Awake at last after sleeping for " + sleepTime/1000 + " seconds!";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgressBar.get().incrementProgressBy(1);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mTextView.get().setText(result);
    }
}
