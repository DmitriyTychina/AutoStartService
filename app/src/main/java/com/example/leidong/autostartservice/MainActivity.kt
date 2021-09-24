package com.example.leidong.autostartservice

import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(
            "autostart",
            MODE_PRIVATE
        )
    }

    //    private var sharedPreferences: SharedPreferences = getSharedPreferences("autostart", MODE_PRIVATE)
    private var sAutoStartService: AutoStartService = AutoStartService()

    //    private val editor = sharedPreferences.edit()
    val tag = "@@Main"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate")
        setContentView(R.layout.activity_main)
        val editor = sharedPreferences.edit()

//        Toast.makeText(applicationContext, "onCreate MainActivity", Toast.LENGTH_LONG).show()

        val vas: Switch = findViewById(R.id.AutostartS)
        val vsw: Switch = findViewById(R.id.SWork)
        vas.setChecked(sharedPreferences.getBoolean("AutoStartService", false))

        Log.d(tag, "sAutoStartService="+sAutoStartService.toString())
        startService(Intent(this@MainActivity, AutoStartService::class.java).setAction("init"))
        //打开
        findViewById<View>(R.id.button1).setOnClickListener {
            editor.putBoolean("AutoStartService", true)
            editor.apply()
            Toast.makeText(applicationContext, "service autostarted", Toast.LENGTH_LONG).show()
            vas.setChecked(true)

            startService(Intent(this@MainActivity, AutoStartService::class.java).setAction("start"))
//            startForegroundService(sAutoStartService)
        }

        //关闭
        findViewById<View>(R.id.button2).setOnClickListener {
//            val editor = sharedPreferences.edit()
            editor.putBoolean("AutoStartService", false)
            editor.apply()
            Toast.makeText(applicationContext, "service no autostarted", Toast.LENGTH_LONG).show()
            vas.setChecked(false)

            stopService(Intent(this@MainActivity, AutoStartService::class.java).setAction("stop"))
//            stopForegroundService(sAutoStartService)
        }

        vas.setOnClickListener { v: View? ->
//            Toast.makeText(applicationContext, "Switch " + vas.isChecked + "v="+v.toString(), Toast.LENGTH_LONG).show()
            editor.putBoolean("AutoStartService", vas.isChecked)
            vsw.setChecked(vas.isChecked)
        }
    }

    //This is from Util class so as not to cloud your service
    fun startForegroundService(StartService: AutoStartService) {
        val notificationTitle = "Service running"
        val notificationContent = "<My app> is using <service name> "
        val actionButtonText = "Stop"
//        //Check android version and create channel for Android O and above
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            //You can do this on your own
//            //createNotificationChannel(CHANNEL_ID_SERVICE)
//        }
        //Build notification
        val notificationBuilder =
            NotificationCompat.Builder(applicationContext, "CHANNEL_ID_SERVICE")
        notificationBuilder.setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_baseline_vpn_lock_24)
            .setContentTitle(notificationTitle)
            .setContentText(notificationContent)
            .setVibrate(null)
//        //Add stop button on notification
//        val pStopSelf = createStopButtonIntent(AutoStartService)
//        notificationBuilder.addAction(R.drawable.ic_location, actionButtonText, pStopSelf)
        //Build notification
        val notificationManagerCompact = NotificationManagerCompat.from(applicationContext)
        notificationManagerCompact.notify(0, notificationBuilder.build())
        val notification = notificationBuilder.build()
        //Start notification in foreground to let user know which service is running.
        StartService.startForeground(0, notification)
        //Send notification
        notificationManagerCompact.notify(0, notification)
    }

    // Function to create stop button intent to stop the service.
    private fun stopForegroundService(StopService: AutoStartService): PendingIntent? {
        val stopSelf = Intent(applicationContext, StopService::class.java)
        stopSelf.action = "ACTION_STOP_SERVICE"
        return PendingIntent.getService(
            StopService, 0,
            stopSelf, PendingIntent.FLAG_CANCEL_CURRENT
        )
    }
}