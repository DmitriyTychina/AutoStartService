package com.example.leidong.autostartservice;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by leidong on 2017/4/28.
 */

public class AutoStartService extends Service {

    public IBinder onBind(Intent intent) {
        return null;
    }

    String tag = "@@Service";
    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(tag, "onCreate");
        Toast.makeText(getApplicationContext(), "service create!!!", Toast.LENGTH_LONG).show();
        broadcastReceiver = new MainBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(tag, "onStartCommand");
        Toast.makeText(getApplicationContext(), "service onStartCommand!!!", Toast.LENGTH_LONG).show();
        Log.d(tag, "intent= " + intent.getAction() + "   Extra.action= " + intent.getStringExtra("action") + "   Extra.state= " + intent.getStringExtra("state"));
        Log.d(tag, "flags= " + flags + "   startId= " + startId);

//        Toast.makeText(getApplicationContext(), "service start!!!", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(tag, "onDestroy");
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "service stop!!!", Toast.LENGTH_LONG).show();
    }
}
