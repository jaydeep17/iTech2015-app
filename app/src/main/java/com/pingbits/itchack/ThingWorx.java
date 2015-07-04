package com.pingbits.itchack;


import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ThingWorx {

    public static final String SET_URL = "https://i3liot4.cloudapp.net:8443/Thingworx/Things/Jar/Services/setStart";
    private static final String tag = "ThingWorx";

//    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        client.get(url, params, responseHandler);
//    }
//
//    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        client.post(url, params, responseHandler);
//    }

    public static void setStartValue(Context context, Boolean value) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.addHeader("appKey", "18fe31a8-d6d6-4cba-8eea-03b713fd34c4");
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("value", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post(context, SET_URL, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(tag, "SUCCESS!");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(tag, "Failed!", error);
            }
        });
    }
}
