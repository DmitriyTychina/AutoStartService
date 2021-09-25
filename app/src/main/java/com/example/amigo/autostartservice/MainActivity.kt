package com.example.amigo.autostartservice

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(
            "autostart",
            MODE_PRIVATE
        )
    }

    //    private var sharedPreferences: SharedPreferences = getSharedPreferences("autostart", MODE_PRIVATE)
    private var sAutoStartService: AutoStartService = AutoStartService()
//    private var broadcastReceiver: BroadcastReceiver? = null

    //    private val editor = sharedPreferences.edit()
    val tag = "@@Main"


    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate")
        setContentView(R.layout.activity_main)
        val editor = sharedPreferences.edit()

//        Toast.makeText(applicationContext, "onCreate MainActivity", Toast.LENGTH_LONG).show()

        val vas: Switch = findViewById(R.id.AutostartS)

//        val broadcastReceiver = MainBroadcastReceiver()
//        val filter = IntentFilter()
//        filter.addAction(Intent.ACTION_SCREEN_ON)
//        filter.addAction(Intent.ACTION_SCREEN_OFF)
//        registerReceiver(broadcastReceiver, filter)


        vas.setChecked(sharedPreferences.getBoolean("AutoStartService", false))

        Log.d(tag, "sAutoStartService=" + sAutoStartService.toString())
        startService(Intent(this@MainActivity, AutoStartService::class.java).setAction("init"))

        //打开
        findViewById<View>(R.id.button1).setOnClickListener {
//            editor.putBoolean("AutoStartService", true)
//            editor.apply()
//            Toast.makeText(applicationContext, "service autostarted", Toast.LENGTH_LONG).show()
//            vas.setChecked(true)

            startService(Intent(this@MainActivity, AutoStartService::class.java).setAction("start"))
//            startForegroundService(sAutoStartService)
        }

        //关闭
        findViewById<View>(R.id.button2).setOnClickListener {
//            val editor = sharedPreferences.edit()
//            editor.putBoolean("AutoStartService", false)
//            editor.apply()
//            Toast.makeText(applicationContext, "service no autostarted", Toast.LENGTH_LONG).show()
//            vas.setChecked(false)

            startService(Intent(this@MainActivity, AutoStartService::class.java).setAction("stop"))
//            stopForegroundService(sAutoStartService)
        }

        vas.setOnClickListener {
            editor.putBoolean("AutoStartService", vas.isChecked).apply()
//            Toast.makeText(applicationContext, "service autostarted", Toast.LENGTH_LONG).show()

            startService(
                Intent(
                    this@MainActivity,
                    AutoStartService::class.java
                ).setAction(if (vas.isChecked) "start" else "stop")
            )
//            startForegroundService(sAutoStartService)
        }
    }
}