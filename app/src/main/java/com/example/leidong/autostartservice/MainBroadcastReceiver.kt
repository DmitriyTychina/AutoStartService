package com.example.leidong.autostartservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

class MainBroadcastReceiver : BroadcastReceiver() {
    private var tag = "@@Receiver"

    init {
        Log.d(tag, "init $this")

    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        val service = Intent(
            context.applicationContext,
            AutoStartService::class.java
        ) // context.getApplicationContext()

//        Toast.makeText(context.applicationContext, "action=$action", Toast.LENGTH_LONG).show()
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
                context.applicationContext.startForegroundService(
                    service.putExtra("action", "boot")
                )
            } else {
                context.applicationContext.startService(
                    service.putExtra("action", "boot")
                )
            }
        }
    }
}