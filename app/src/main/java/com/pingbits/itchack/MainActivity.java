package com.pingbits.itchack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pingbits.greendao.Ordder;
import com.pingbits.greendao.Order;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    boolean startValue = false;
    private FloatingActionButton fab;
    private Prefs prefs;
    private View jarFill;
    private ImageView jarView;
    private TextView fillInfo;

    private int fullHeight;
    private int orderValue = -1;


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            float message = intent.getFloatExtra("message", -1);
            Log.d("receiver", "Got message: " + message);
        }
    };
    private BroadcastReceiver tempReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ThingWorx.getJarValue(MainActivity.this);
            ThingWorx.getOrderValue(MainActivity.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        getSupportActionBar().setTitle("");
        prefs = new Prefs(this);
        startValue = prefs.getStartValue();
        updateFab(startValue);
        jarView = (ImageView) findViewById(R.id.jar);
        jarFill = findViewById(R.id.jarFill);
        fillInfo = (TextView) findViewById(R.id.fillInfo);
        fullHeight = getResources().getDimensionPixelSize(R.dimen.fullHeight);

        setJarFill(50);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent("dexter.percnt");
                LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
            }
        }, 750, 750);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.orders) {
            Intent intent = new Intent(this, OrderListActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("dexter.data"));
        LocalBroadcastManager.getInstance(this).registerReceiver(tempReceiver,
                new IntentFilter("dexter.percnt"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(tempReceiver);
    }

    public void setStart(Boolean value) {
        startValue = value;
        ThingWorx.setStartValue(this, value);
        updateFab(value);
    }

    private void updateFab(Boolean value) {
        if (value) {
            fab.setImageResource(R.drawable.ic_clear_white_24dp);
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue_grey)));
        } else {
            fab.setImageResource(R.drawable.ic_play_arrow_white_24dp);
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.accent)));
        }
    }

    @Override
    public void onClick(View view) {
        Log.d("abc", "click!");
        setStart(!startValue);
    }

    public void setJarFill(float percnt) {
        if (percnt < 0) {
            return;
        }
        if (percnt > 100) {
            percnt = 100;
        }
        float height = fullHeight * percnt / 100;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) jarFill.getLayoutParams();
        params.height = (int) height;
        jarFill.setLayoutParams(params);
        fillInfo.setText(String.format("%d%%", (int) percnt));
    }

    private void showOrderPlaced() {
        Snackbar.make(findViewById(R.id.rootView), "Your order has been placed", Snackbar.LENGTH_LONG)
                .setAction("CANCEL", this).show();
    }

    public void setOrder(int val) {
        if (orderValue == -1) {
            orderValue = val;
        } else if (val > orderValue) {
            showOrderPlaced();
            Ordder order = new Ordder(null, "Sugar (5Kg)", new Date());
            ((MyApplication) getApplicationContext()).getOrderDao().insert(order);
            ThingWorx.addOrder();
            orderValue = val;
        }
    }
}
