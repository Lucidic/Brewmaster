package com.gt.brewmasters.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * Represents an asynchronous http task
 * 
 * This is setup to be a skeleton for general http requests, but currently only POST is implemented
 */
public class HttpTask extends AsyncTask<Void, Void, String> {
	
    // Debugging
    private static final String TAG = "Brewmaster";
    private static final boolean D  = true;
	
	private String url;
	private List<NameValuePair> nvp;
	private JSONObject json;
	private Handler handler;
	private int type;
	
	private String[] postResult;
	private String response;
	private String serverResponsePhrase;
	private int serverStatusCode = -1;
	
	private MultipartEntity multEntity;
	private StringEntity strEntity;
	
	public static final int GET    = 1;
	public static final int POST   = 2;
	public static final int DELETE = 3;
	
	public static final int BAD_RESPONSE = -1;
	public static final int RESPONSE     =  1;
	
	//For use with nvp
	public HttpTask (String url, List<NameValuePair> nvp, Handler handler, int type) {
		this.url=url;
		this.nvp=nvp;
		this.handler=handler;
		this.type=type;

		multEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

        for(int index=0; index < nvp.size(); index++) {
            // Normal string data
            try {
				multEntity.addPart(nvp.get(index).getName(), new StringBody(nvp.get(index).getValue()));
			} 
            catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
        }
		
	}
	
	//For use with jsons
	public HttpTask (String url, JSONObject json, Handler handler, int type) {
		this.url=url;
		this.json=json;
		this.handler=handler;
		this.type=type;

		strEntity = null;
		
		try {
			strEntity = new StringEntity(json.toString(), HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		strEntity.setContentType("application/json");
		
	}
	
	@Override
	protected String doInBackground(Void... params) {

		if (multEntity!=null) {
			if(this.type==HttpTask.POST) {
				HttpPoster poster = new HttpPoster(url, multEntity);
				if(D) Log.e(TAG, "*******POSTING MultipartEntity*******");
				postResult = poster.post();	
				response = postResult[0];
				serverResponsePhrase = postResult[1];
				serverStatusCode = Integer.valueOf(postResult[2]);
			}
		}
		
		if (strEntity!=null) {
			if(this.type==HttpTask.POST) {
				HttpPoster poster = new HttpPoster(url, strEntity);
				if(D) Log.e(TAG, poster.toString());
				if(D) Log.e(TAG, "*******POSTING JSON*******");
				postResult = poster.post();
				response = postResult[0];
				serverResponsePhrase = postResult[1];
				serverStatusCode = Integer.valueOf(postResult[2]);
			}
		}
		
		if(D) Log.d(TAG, "returning server response: " + response);
		return response;
	}

	@Override 
	protected void onPostExecute(String result) {
		Message msg = new Message();
		
		if(result == null || result.startsWith("-1") || result.startsWith("null"))
		{
			if(D) Log.d(TAG, "result: " + result);
	        msg.what = this.BAD_RESPONSE;
	        handler.sendMessage(msg);
		}
		
		else
		{
			if(D) Log.d(TAG, "result: " + result);
	        msg.what = this.RESPONSE;
	        Bundle data = new Bundle();
	        data.putString("server_response", this.serverResponsePhrase);
	        data.putInt("server_status_code", this.serverStatusCode);
	        data.putString("token", result);
	        msg.setData(data);
	        handler.sendMessage(msg);
		}				
	}
}