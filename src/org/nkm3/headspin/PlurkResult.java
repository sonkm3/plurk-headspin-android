package org.nkm3.headspin;

import org.json.JSONObject;
import org.json.JSONException;

import java.lang.RuntimeException;
import android.util.Log;

public class PlurkResult {

	String json_string = null;
	int status = 0;

	public PlurkResult(int status, String json_string){
		this.json_string = json_string;
		this.status = status;
        Log.d("json", json_string);
	}

	public String get_karma(){
		if (this.json_string != null){
			String current_karma = null;
			JSONObject json_object = null;

	        try{
		        json_object = new JSONObject(json_string);
		        current_karma = json_object.getString("current_karma");
	        }catch (JSONException e){
	        	e.printStackTrace();
	        	current_karma = "";
	    	}
	        return current_karma;
		}else{
			throw new RuntimeException();
		}
	}

    public int get_plurk_id(){
        if (this.json_string != null){
            int plurk_id = 0;
            JSONObject json_object = null;

            try{
                json_object = new JSONObject(json_string);
                plurk_id = json_object.getInt("plurk_id");
            }catch (JSONException e){
                e.printStackTrace();
                plurk_id = 0;
            }
            return plurk_id;
        }else{
            throw new RuntimeException();
        }
    }

    public String get_plurk_string(){
        if (this.json_string != null){
            String plurk_string = null;
            JSONObject json_object = null;

            try{
                json_object = new JSONObject(json_string);
                plurk_string = json_object.getString("content_raw");
            }catch (JSONException e){
                e.printStackTrace();
                plurk_string = "";
            }
            return plurk_string;
        }else{
            throw new RuntimeException();
        }
    }


	public String get_parsed_string(){
		JSONObject json_object = null;
		String parsed_string = null;

		if (this.json_string != null){
	        try{
		        json_object = new JSONObject(json_string);
		        parsed_string = json_object.toString(4);
	        }catch (JSONException e){
	        	e.printStackTrace();
	        	parsed_string = "";
	    	}
	        return parsed_string;
		}else{
			throw new RuntimeException();
		}
	}



}
