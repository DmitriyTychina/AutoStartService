package com.example.leidong.autostartservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by leidong on 2017/4/28.
 */

public class MainBroadcastReceiver extends BroadcastReceiver {
    private SharedPreferences sharedPreferences = null;
    String tag = "@@Receiver";
    Intent service;


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        sharedPreferences = context.getSharedPreferences("autostart", ContextWrapper.MODE_PRIVATE);
        boolean flag = sharedPreferences.getBoolean("AutoStartService", false);
        Toast.makeText(context.getApplicationContext(), "action=" + action, Toast.LENGTH_LONG).show();
        if (action == null) return;
        Log.d(tag, "onReceive");
        Log.d(tag, "onReceive.action=" + action + "   autostart=" + flag);
        service = new Intent(context, AutoStartService.class);
        service.setAction("broadcast");

        if (action.equals("android.intent.action.SCREEN_ON")
            || action.equals("android.intent.action.SCREEN_OFF")) {
//            service.putExtra("action", "screen");
//            service.putExtra("state", action.equals("android.intent.action.SCREEN_ON") ? "true" : "false");
//            context.startService(service);
            context.startService(service.putExtra("action", "screen").putExtra("state", action.equals("android.intent.action.SCREEN_ON") ? "true" : "false"));
        } else if ((action.equals("android.intent.action.BOOT_COMPLETED") ||
                action.equals("android.intent.action.QUICKBOOT_POWERON") ||
                action.equals("android.intent.action.REBOOT") ||
                action.equals("com.htc.intent.action.QUICKBOOT_POWERON"))) {
            if (flag) {

//                context.startService(new Intent(context, AutoStartService.class));
//                context.startService(service);
                context.startService(service.setAction("boot"));

                Intent newIntent = context.getPackageManager()
                        .getLaunchIntentForPackage("com.example.leidong.autostartservice");
                context.startActivity(newIntent);
            }
        }


    }

}
