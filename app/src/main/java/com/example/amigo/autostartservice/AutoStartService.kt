package com.example.amigo.autostartservice

import android.app.Service
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class AutoStartService : Service() {
    override fun onBind(intent: Intent): IBinder? = null

    private var tag = "@@Service"
    private var state = false
    private val helper by lazy { NotificationHelper(this) }
//    private val sharedPreferences by lazy {
//        applicationContext.getSharedPreferences("autostart", ContextWrapper.MODE_PRIVATE)
//    }
//    private val flagautostart by lazy { sharedPreferences.getBoolean("AutoStartService", false) }

    //    private BroadcastReceiver broadcastReceiver;
    private var broadcastReceiver: MainBroadcastReceiver? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(tag, "onCreate")
        if (broadcastReceiver == null) {
            broadcastReceiver = MainBroadcastReceiver()
            val filter = IntentFilter()
            filter.addAction(Intent.ACTION_SCREEN_ON)
            filter.addAction(Intent.ACTION_SCREEN_OFF)
//            filter.addAction(Intent.ACTION_BOOT_COMPLETED)
//            filter.addAction(Intent.ACTION_REBOOT)
//            filter.addAction(Intent.ACTION_LOCKED_BOOT_COMPLETED)
            registerReceiver(broadcastReceiver, filter)
        }
//        Toast.makeText(applicationContext, "service create!!!", Toast.LENGTH_LONG).show()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val sharedPreferences =
            applicationContext.getSharedPreferences("autostart", ContextWrapper.MODE_PRIVATE)
        val flagautostart = sharedPreferences.getBoolean("AutoStartService", false)
        Log.d(tag, "onStartCommand")
//        Toast.makeText(applicationContext, "service onStartCommand!!!", Toast.LENGTH_LONG).show()
        Log.d(
            tag,
            "intent= " + intent?.action + "   Extra.action= " + intent?.getStringExtra("action") + "   Extra.state= " + intent?.getStringExtra(
                "state"
            )
        )
//        Log.d(tag, "flags= $flags   startId= $startId")

        when (intent?.action) {
            "init" -> {
                if (flagautostart)
                    fStartService()
                else
                    fStopService()
            }
            "start" -> {
                fStartService()
            }
            "broadcast" -> when (intent?.getStringExtra("action")) {
                "boot" -> {
                    if (flagautostart)
                        fStartService()
                    else
                        fStopService()
//                        var tmp: MainBroadcastReceiver = intent.component
                }
            }
            "stop" -> {
                fStopService()
            }
        }
        return if (state)
            START_STICKY_COMPATIBILITY
        else
            return START_NOT_STICKY
        //super.onStartCommand(intent, flags, startId)
    }

    private fun fStartService() {
        if (!state) {
            Toast.makeText(applicationContext, "service start!!!", Toast.LENGTH_LONG).show()
            Log.d(tag, "fStartService")
            state = true
            startForeground(NotificationHelper.NOTIFICATION_ID, helper.getNotification())
//        helper.updateNotification("update")
        }
    }

    private fun fStopService() {
        if (state) {
            Log.d(tag, "fStopService")
            state = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                stopForeground(true)
            }
            stopSelf()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy")
        Toast.makeText(applicationContext, "service stop!!!", Toast.LENGTH_LONG).show()
        unregisterReceiver(broadcastReceiver)
    }
}

