package org.nkm3.headspin;

import android.app.Activity;
import android.os.AsyncTask;


public class AsyncPlurkTask extends AsyncTask<PlurkTask, Void, PlurkResult>{
    private Activity activity;
    private PlurkTask plurktask;

    public AsyncPlurkTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected PlurkResult doInBackground(PlurkTask... params) {
        plurktask = params[0];
        Plurk api = plurktask.api;
        PlurkResult result = api.execute_request();

        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(PlurkResult result) {
        plurktask.callback.execute(result);
        return;
    }

}
