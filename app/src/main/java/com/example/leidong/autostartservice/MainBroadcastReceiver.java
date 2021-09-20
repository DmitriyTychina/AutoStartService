package com.example.leidong.autostartservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by leidong on 2017/4/28.
 */

public class MainBroadcastReceiver extends BroadcastReceiver {
    private static final String mACTION = "android.intent.action.BOOT_COMPLETED";
    private SharedPreferences sharedPreferences = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences("autostart", ContextWrapper.MODE_PRIVATE);
        boolean flag = sharedPreferences.getBoolean("AutoStartService", false);
        if (intent.getAction().equals(mACTION) && flag) {
            Intent service = new Intent(context, AutoStartService.class);
            context.startService(service);

            Intent newIntent = context.getPackageManager()
                    .getLaunchIntentForPackage("com.example.leidong.autostartservice");
            context.startActivity(newIntent);
        }
    }
}
