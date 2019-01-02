package com.example.tronc.gamesdaily.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.tronc.gamesdaily.R;

public class LoadingAsyncTask extends AsyncTask<String, String, String> {

    private Context c;
    private ProgressDialog progDailog;
    private int count_progress;
    private String current_progress;

    public LoadingAsyncTask(Context c, String titulo){
        this.c = c;
        count_progress = 0;
        current_progress = "";
        progDailog = new ProgressDialog(c, R.style.asyncTaskLoadingDialog);
        progDailog.setTitle(titulo);

        progDailog.setIndeterminate(true);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(false);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();;
        progDailog.show();
    }

    @Override
    protected void onCancelled() {
        if(progDailog != null && progDailog.isShowing())
        {
            progDailog.dismiss();
        }
    }
    @Override
    protected String doInBackground(String... aurl) {
        while(!isCancelled()){
            try {
                Thread.sleep(500);

                if(count_progress > c.getResources().getInteger(R.integer.max_number_char_loading_dialog)){
                    count_progress = 0;
                    current_progress = "";
                }
                current_progress = current_progress + ".";
                publishProgress(current_progress);

                count_progress++;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(progDailog != null && progDailog.isShowing())
        {
            progDailog.dismiss();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        progDailog.setMessage(progress[0]);
        progDailog.show();
    }

    @Override
    protected void onPostExecute(String unused) {
        super.onPostExecute(unused);

    }
}
