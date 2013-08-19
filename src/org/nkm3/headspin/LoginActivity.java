package org.nkm3.headspin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.os.AsyncTask;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

import java.lang.RuntimeException;
import java.lang.Exception;

import android.content.Intent;
import android.net.Uri;


public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Activity login_activity = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);

        AsyncTask<Void, Void, String> get_auth_url_task = new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... voids) {
                String consumer_key = getResources().getString(R.string.consumer_key);
                String consumer_secret = getResources().getString(R.string.consumer_secret);
                String request_token_endpoint_url = getResources().getString(R.string.request_token_endpoint_url);
                String access_token_endpoint_url = getResources().getString(R.string.access_token_endpoint_url);
                String authorization_website_url = getResources().getString(R.string.authorization_website_url);
                String callback_url = getResources().getString(R.string.callback_url);
                String auth_url = null;

                CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumer_key, consumer_secret);
                CommonsHttpOAuthProvider provider = new CommonsHttpOAuthProvider(request_token_endpoint_url, access_token_endpoint_url, authorization_website_url);
                try{
                    auth_url = provider.retrieveRequestToken(consumer, callback_url);
                }catch (Exception e){
                    Log.d("zzz", "fail to retrive request token");
                    Log.d("xxx", e.toString());
                    throw new RuntimeException();
                }
                HeadspinApp headspin_app = ((HeadspinApp)getApplicationContext());
                headspin_app.setProvider(provider);
                headspin_app.setConsumer(consumer);

                return auth_url;
            }

            @Override
            protected void onPostExecute(String url) {
                Uri auth_uri = Uri.parse(url);
                startActivity(new Intent(Intent.ACTION_VIEW, auth_uri));
            }
        };
        get_auth_url_task.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
