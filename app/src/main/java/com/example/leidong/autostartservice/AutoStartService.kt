package com.example.leidong.autostartservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat


class AutoStartService : Service() {
    override fun onBind(intent: Intent): IBinder? = null

    var tag = "@@Service"
    var state = false
    private var builder: NotificationCompat.Builder? = null
    internal val channelID = "running"
    private val helper by lazy { NotificationHelper(this) }

    //    private BroadcastReceiver broadcastReceiver;
//    override fun onCreate() {
//        super.onCreate()
//        Log.d(tag, "onCreate")
//        Toast.makeText(applicationContext, "service create!!!", Toast.LENGTH_LONG).show()
//        //        broadcastReceiver = new MainBroadcastReceiver();
////        IntentFilter filter = new IntentFilter();
////        filter.addAction(Intent.ACTION_SCREEN_ON);
////        filter.addAction(Intent.ACTION_SCREEN_OFF);
////        registerReceiver(broadcastReceiver, filter);
//    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val sharedPreferences =
            applicationContext.getSharedPreferences("autostart", ContextWrapper.MODE_PRIVATE)
        val flagautostart = sharedPreferences.getBoolean("AutoStartService", false)
        Log.d(tag, "onStartCommand")
        Toast.makeText(applicationContext, "service onStartCommand!!!", Toast.LENGTH_LONG).show()
        Log.d(
            tag,
            "intent= " + intent?.action + "   Extra.action= " + intent?.getStringExtra("action") + "   Extra.state= " + intent?.getStringExtra(
                "state"
            )
        )
        Log.d(tag, "flags= $flags   startId= $startId")

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
            "broadcast" -> {
                when (intent.getStringExtra("action")) {
                    "boot" -> {
                        if (flagautostart)
                            fStartService()
                        else
                            fStopService()
                    }
                }
            }
            "stop" -> {
                fStopService()
            }
        }
//        Toast.makeText(getApplicationContext(), "service start!!!", Toast.LENGTH_LONG).show();
        return START_STICKY_COMPATIBILITY
        //super.onStartCommand(intent, flags, startId)
    }

    private fun fStartService() {
        Log.d(tag, "fStartService")
        startForeground(NotificationHelper.NOTIFICATION_ID, helper.getNotification())
//        helper.updateNotification("update")


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                channelID,
//                channelID,
//                NotificationManager.IMPORTANCE_MIN
//            ) //IMPORTANCE_MIN - без звука
//            val notificationManager: NotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//
////        val intent = Intent(
////            applicationContext,
////            SstpVpnService::class.java
////        )//.setAction(VpnAction.ACTION_DISCONNECT.value)
////        val pendingIntent = PendingIntent.getService(applicationContext, 0, intent, 0)
//        builder = NotificationCompat.Builder(applicationContext, channelID).also {
//            it.setSmallIcon(R.drawable.ic_baseline_vpn_lock_24)
//            it.setContentText("Disconnect SSTP connection")
////            it.priority = NotificationCompat.PRIORITY_LOW
////            it.setContentIntent(pendingIntent)
////            it.setAutoCancel(true)
////            it.setSound(null)
//        }
//
//        startForeground(0, builder!!.build())

//        Log.d(tag, "fStartService")
//        state = true
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                channelID,
//                channelID,
//                NotificationManager.IMPORTANCE_MIN
//            ) //IMPORTANCE_MIN - без звука
//            val notificationManager: NotificationManager =
//                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//
////        val intent = Intent(
////            applicationContext,
////            SstpVpnService::class.java
////        )//.setAction(VpnAction.ACTION_DISCONNECT.value)
////        val pendingIntent = PendingIntent.getService(applicationContext, 0, intent, 0)
//            val context = applicationContext
//            val notification =
//                Notification.Builder(context, channelID)
////                    .setContentTitle(getNotificationTitle(context))
////                    .setContentText(getNotificationContent(context))
//                    .setSmallIcon(R.drawable.ic_baseline_vpn_lock_24)
////                    .setContentIntent(piLaunchMainActivity)
////                    .setActions(stopAction)
//                    .setStyle(Notification.BigTextStyle())
//                    .build()
//
////                Notification.Builder(applicationContext, CHANNEL_ID).also {
////                it.setSmallIcon(R.drawable.ic_baseline_vpn_lock_24)
////                it.setContentText("Disconnect SSTP connection")
////            it.priority = NotificationCompat.PRIORITY_LOW
////            it.setContentIntent(pendingIntent)
////            it.setAutoCancel(true)
////            it.setSound(null)
////            }
//            startForeground(0, notification)
//            Log.d(tag, "startForeground")
//        } else {
//
//        }
    }

    private fun fStopService() {
        Log.d(tag, "fStopService")
        state = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(0)
        }
        START_NOT_STICKY
//        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy")
        Toast.makeText(applicationContext, "service stop!!!", Toast.LENGTH_LONG).show()
    }
}

