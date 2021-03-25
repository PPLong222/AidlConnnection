package com.example.mylastaidl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this,MyService.class));

            ActivityManager mActivityManager =
                    (ActivityManager)getSystemService(ACTIVITY_SERVICE);

            List<ActivityManager.RunningServiceInfo> mServiceList = mActivityManager.getRunningServices(30);
            for (ActivityManager.RunningServiceInfo runningServiceInfo : mServiceList) {
                Log.d("client123","123"+runningServiceInfo.service.getClassName());
            }


    }
}