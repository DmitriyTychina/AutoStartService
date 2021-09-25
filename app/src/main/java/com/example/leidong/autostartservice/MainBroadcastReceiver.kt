package com.example.leidong.autostartservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast

class MainBroadcastReceiver : BroadcastReceiver() {
    //    private val sharedPreferences: SharedPreferences by lazy {
//        context.getApplicationContext().getSharedPreferences(
//            "autostart",
//            ContextWrapper.MODE_PRIVATE)
//    }
    var tag = "@@Receiver"
//    var service: Intent? = null

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        val service = Intent(
            context.applicationContext,
            AutoStartService::class.java
        ) // context.getApplicationContext()

        Toast.makeText(context.applicationContext, "action=$action", Toast.LENGTH_LONG).show()
        if (action == null) return
        Log.d(tag, "onReceive.action=$action")
        service.action = "broadcast"
        if (action == "android.intent.action.SCREEN_ON" || action == "android.intent.action.SCREEN_OFF") {
//            service.putExtra("action", "screen");
//            service.putExtra("state", action.equals("android.intent.action.SCREEN_ON") ? "true" : "false");
//            context.startService(service);
            context.applicationContext.startService(
                service.putExtra("action", "screen").putExtra(
                    "state",
                    if (action == "android.intent.action.SCREEN_ON") "true" else "false"
                )
            )
        } else if (action == "android.intent.action.BOOT_COMPLETED" || action == "android.intent.action.QUICKBOOT_POWERON" || action == "android.intent.action.REBOOT" || action == "com.htc.intent.action.QUICKBOOT_POWERON") {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.getApplicationContext().startForegroundService(
                        service.putExtra(
                            "action",
                            "boot"
                        )
                    )
                } else {
                    context.getApplicationContext().startService(
                        service.putExtra(
                            "action",
                            "boot"
                        )
                    )
                }
       }
    }
}