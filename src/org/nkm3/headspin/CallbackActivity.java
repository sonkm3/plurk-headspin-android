package org.nkm3.headspin;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.util.Log;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;

import android.content.Intent;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;


public class CallbackActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);

        String callback_url = getResources().getString(R.string.callback_url);

        HeadspinApp headspin_app = ((HeadspinApp)getApplicationContext());
        CommonsHttpOAuthConsumer consumer = headspin_app.getConsumer();
        CommonsHttpOAuthProvider provider = headspin_app.getProvider();

        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null && uri.toString().startsWith(callback_url)) {
                String oauth_verifier = uri.getQueryParameter(oauth.signpost.OAuth.OAUTH_VERIFIER);
                try {
                    provider.retrieveAccessToken(consumer, oauth_verifier);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                AppPreferences.putString(this, "access_token", consumer.getToken());
                AppPreferences.putString(this, "access_secret", consumer.getTokenSecret());

                startActivity(new Intent(this, MainActivity.class));

//                # am start -a android.intent.action.VIEW headspin://headspin.nkm3.org/callback
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
