package org.nkm3.headspin;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;

import android.util.Log;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String consumer_key = getResources().getString(R.string.consumer_key);
        String consumer_secret = getResources().getString(R.string.consumer_secret);

        String access_token = AppPreferences.getString(this, "access_token");
        String access_secret = AppPreferences.getString(this, "access_secret");

        setLogoutListner(this);

        if(access_token == null || access_secret == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
            Plurk api = new Plurk(consumer_key, consumer_secret, access_token, access_secret);
            setHeadspinListner(this, new Plurk(consumer_key, consumer_secret, access_token, access_secret));

            api.set_url("http://www.plurk.com/APP/Users/getKarmaStats");
            api.sign_request();

            AsyncPlurkTask asynk_plurk_task = new AsyncPlurkTask(this);
            AsyncTaskCallback callback = new AsyncTaskCallback(this){
                @Override
                void execute(Object result){
                    PlurkResult plurk_result  = (PlurkResult)result;
                    TextView textView = (TextView)this.activity.findViewById(R.id.parsed_json_textview);
                    textView.setText(plurk_result.get_karma());
                }
            };
            PlurkTask plurk_task = new PlurkTask(api, callback);
            asynk_plurk_task.execute(plurk_task);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    private void setLogoutListner(Activity _activity){
        Button logout_button = (Button)findViewById(R.id.logout_button);
        final Activity activity = _activity;

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppPreferences.remove(activity, "access_secret");
                AppPreferences.remove(activity, "access_secret");
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
            }
        }
        );
    }

    private void setHeadspinListner(Activity _activity, Plurk _api){
        Button headspin_button = (Button)findViewById(R.id.headspin_button);
        final Activity activity = _activity;
        final Plurk api = _api;

        headspin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText edit_text = (EditText)findViewById(R.id.editText);

                api.set_method(api.POST);
                api.set_url("http://www.plurk.com/APP/Timeline/plurkAdd");
                api.add_post_value("content", "(headspin)" + edit_text.getText());
                api.add_post_value("qualifier", ":");
                api.add_post_value("lang", "ja");
                api.sign_request();

                AsyncPlurkTask asynk_plurk_task = new AsyncPlurkTask(activity);
                AsyncTaskCallback callback = new AsyncTaskCallback(activity){
                    @Override
                    void execute(Object result){
                        String toast_string;
                        PlurkResult plurk_result  = (PlurkResult)result;
                        if (plurk_result.status == 200){
                            toast_string = "plurked:" + plurk_result.get_plurk_string();
                            Log.d("toast_string", toast_string);
                            Toast.makeText(this.activity, toast_string, Toast.LENGTH_LONG).show();
                        }else{
                            toast_string = "error:" + plurk_result.status;
                            Log.d("toast_string", toast_string);
                            Toast.makeText(this.activity, toast_string, Toast.LENGTH_LONG).show();
                        }

                    }
                };
                PlurkTask plurk_task = new PlurkTask(api, callback);
                asynk_plurk_task.execute(plurk_task);
            }
        }
        );
    }

}





