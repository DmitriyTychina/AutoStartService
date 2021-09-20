package com.example.leidong.autostartservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

/**
 * Created by leidong on 2017/4/28.
 */

public class AutoStartService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Toast.makeText(getApplicationContext(), "onCreate！！！", Toast.LENGTH_LONG).show();
    }
}
