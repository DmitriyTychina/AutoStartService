package com.example.leidong.autostartservice;

import android.annotation.SuppressLint;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toast.makeText(MainActivity.this, "onCreate MainActivity", Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "onCreate MainActivity", Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("autostart", ContextWrapper.MODE_PRIVATE);

        //打开
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("AutoStartService", true);
                editor.commit();

                Intent intent = new Intent(MainActivity.this, AutoStartService.class);
                startService(intent);
            }
        });

        //关闭
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("AutoStartService", false);
                editor.commit();
                Toast.makeText(getApplicationContext(), "onClick！！！", Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.switch1).setOnClickListener(v -> {
                        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switch1 = findViewById(R.id.switch1);
                        Toast.makeText(getApplicationContext(), "Switch " + switch1.isChecked(), Toast.LENGTH_LONG).show();
        });
    }
}