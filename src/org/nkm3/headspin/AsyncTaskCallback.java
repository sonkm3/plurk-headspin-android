package org.nkm3.headspin;

import android.app.Activity;

public class AsyncTaskCallback {
    public Activity activity;

    public AsyncTaskCallback(Activity activity){
        this.activity = activity;
    }

    void execute(Object result){
    }
}
