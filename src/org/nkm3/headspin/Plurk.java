package org.nkm3.headspin;

import java.io.IOException;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.BasicResponseHandler;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.protocol.HTTP;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.lang.RuntimeException;

import android.util.Log;


public class Plurk {
    static String GET = "GET";
    static String POST = "POST";

    private OAuthConsumer consumer;
	private DefaultHttpClient httpclient;
	private String method = GET;

    private ArrayList<NameValuePair> postdata;

	private HttpGet get_request;
    private HttpPost post_request;
	private HttpResponse response = null;


	public Plurk(String consumer_key, String consumer_secret, String access_token, String access_secret) {
        this.consumer = new CommonsHttpOAuthConsumer(consumer_key, consumer_secret);
        this.consumer.setTokenWithSecret(access_token, access_secret);

        this.httpclient = new DefaultHttpClient();

        this.postdata = new ArrayList<NameValuePair>();
	}

	public void set_method(String method){
		this.method = method;
	}

    public void add_post_value(String key, String val){
        this.postdata.add(new BasicNameValuePair(key, val));
    }

	public void set_url(String url){
        if(this.method == GET){
            this.get_request = new HttpGet(url);
        }
        if(this.method == POST){
            this.post_request = new HttpPost(url);
        }
        Log.d("url set", url);
	}

	public void sign_request(){
        try {
            if(this.method == GET){
                this.consumer.sign(this.get_request);
            } else if(this.method == POST){
                try{
                    this.post_request.setEntity(new UrlEncodedFormEntity(this.postdata, HTTP.UTF_8));
                    this.post_request.addHeader("Content-Type", "application/x-www-form-urlencoded");
                }catch (Exception e){
                    Log.d("zzz", "error on setEntity");
                    e.printStackTrace();
                }
                this.consumer.sign(this.post_request);
            }

        } catch (OAuthMessageSignerException e) {
            e.printStackTrace();
        } catch (OAuthExpectationFailedException e) {
            e.printStackTrace();
        } catch (OAuthCommunicationException e) {
            e.printStackTrace();
        }
	}

	public HttpResponse execute_request_(){
        try {
            if(this.method == GET){
                this.response = httpclient.execute(this.get_request);
            }if(this.method == POST){
                this.response = httpclient.execute(this.post_request);
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally{
        	httpclient.getConnectionManager().shutdown();
        }
        int status = this.response.getStatusLine().getStatusCode();
        if(200 != status){
        	throw new RuntimeException();
        }

        return this.response;
	}

	public PlurkResult execute_request(){
		PlurkResult plurk_result = null;
        String result = null;
		try {
            if(this.method == GET){
			    result = httpclient.execute(this.get_request, new BasicResponseHandler());
            } else if(this.method == POST){
                result = httpclient.execute(this.post_request, new BasicResponseHandler());
            }

		    plurk_result = new PlurkResult(200, result);
		} catch (HttpResponseException e){
	        Log.d("http error", String.valueOf(e.getStatusCode()));
	        plurk_result = new PlurkResult(e.getStatusCode(), "");
		} catch (IOException e) {
		    throw new RuntimeException(e);
		} finally {
		    httpclient.getConnectionManager().shutdown();
            Log.d("httpclient", "shutdown");
		}
	    return plurk_result;
	}


//	try {
//	    String result = httpClient.execute(request, new ResponseHandler<String>() {
//	        @Override
//	        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
//	            switch (response.getStatusLine().getStatusCode()) {
//	            case HttpStatus.SC_OK:
//	                return EntityUtils.toString(response.getEntity(), "UTF-8");
//	            case HttpStatus.SC_NOT_FOUND:
//	                throw new RuntimeException();
//	            default:
//	                throw new RuntimeException();
//	            }
//	        }
//	    });
//	    Log.d("test", result);
//	} catch (ClientProtocolException e) {
//	    throw new RuntimeException(e);
//	} catch (IOException e) {
//	    throw new RuntimeException(e);
//	} finally {
//	    httpClient.getConnectionManager().shutdown();
//	}



}
