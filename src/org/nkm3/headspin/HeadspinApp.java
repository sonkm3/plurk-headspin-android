package org.nkm3.headspin;

import android.app.Application;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;


public class HeadspinApp extends Application {
    private CommonsHttpOAuthProvider provider;
    private CommonsHttpOAuthConsumer consumer;

    public CommonsHttpOAuthProvider getProvider(){
        return provider;
    }

    public void setProvider(CommonsHttpOAuthProvider p){
        provider = p;
    }
    public CommonsHttpOAuthConsumer getConsumer(){
        return consumer;
    }

    public void setConsumer(CommonsHttpOAuthConsumer c){
        consumer = c;
    }

}
