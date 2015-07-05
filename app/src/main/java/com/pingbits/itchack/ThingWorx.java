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

    public static final String setStartUrl = "https://i3liot7.cloudapp.net:8443/Thingworx/Things/Jar/Services/setStart";
    public static final String getJarValueUrl = "https://i3liot7.cloudapp.net:8443/Thingworx/Things/Jar/Services/getPercnt";
    public static final String getOrderValueUrl = "https://i3liot7.cloudapp.net:8443/Thingworx/Things/Jar/Services/getOrder";
    private static final String tag = "ThingWorx";

//    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        client.get(url, params, responseHandler);
//    }
//
//    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        client.post(url, params, responseHandler);
//    }

    private static final String appKey = "92a1c97a-0b35-45d3-ba30-d0266a1ba9fc";

    public static void setStartValue(Context context, Boolean value) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.addHeader("appKey", appKey);
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
        client.post(context, setStartUrl, entity, "application/json", new AsyncHttpResponseHandler() {
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

    public static void getJarValue(final Context context) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.addHeader("appKey", appKey);
        client.post(context, getJarValueUrl, null, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String html = new String(responseBody);
                String value = HtmlParser.parse(html);
                float val = Float.valueOf(value);
                ((MainActivity)context).setJarFill(val);
                Log.i(tag, "SUCCESS! " + value);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(tag, "Failed!", error);
            }
        });
    }


    public static void getOrderValue(final Context context) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.addHeader("appKey", appKey);
        client.post(context, getOrderValueUrl, null, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String html = new String(responseBody);
                String value = HtmlParser.parse(html);
                int val = Integer.valueOf(value);
                ((MainActivity)context).setOrder(val);
                Log.i(tag, "SUCCESS! " + value);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(tag, "Failed!", error);
            }
        });
    }

    public static void addOrder() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://172.16.1.32:3000/order", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(tag, "Order added!");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("tag", "error", error);
            }
        });
    }
}
