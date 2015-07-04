package com.pingbits.itchack;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;

public class MyBroadCast extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getExtras().getString("com.parse.Data");
        ParseData pd = new Gson().fromJson(data, ParseData.class);
        Log.e("TAG", "GOT Data! " + pd.data);
        sendMessage(context, pd.data);
    }

    private void sendMessage(Context context, int value) {
        Intent intent = new Intent("dexter.data");
        // You can also include some extra data.
        intent.putExtra("message", value);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
