package com.example.raksa.usingmessengerforipc;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean isBind = false;

    MyService myService;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            MyService.MyLocalBinder myLocalBinder = (MyService.MyLocalBinder) binder;
            myService = myLocalBinder.getService();
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
        }
    };

    TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult = (TextView) findViewById(R.id.textViewResult);


    }

    public void onAddButton(View view) {

    }

    public void onBindButton(View view) {

        Intent bindIntent = new Intent(MainActivity.this,MyService.class);
        bindService(bindIntent,serviceConnection,BIND_AUTO_CREATE);

    }

    public void onUnbindButton(View view) {
        unbindService(serviceConnection);
    }
}
