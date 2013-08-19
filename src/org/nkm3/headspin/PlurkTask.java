package org.nkm3.headspin;

public class PlurkTask {
    Plurk api = null;
    AsyncTaskCallback callback = null;

    public PlurkTask(Plurk api, AsyncTaskCallback callback){
        this.api = api;
        this.callback = callback;
    }
}
