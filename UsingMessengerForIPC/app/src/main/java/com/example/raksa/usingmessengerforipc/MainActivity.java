package com.example.raksa.usingmessengerforipc;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText editTextValue1;
    EditText editTextValue2;
    TextView textViewResult;

    boolean isBind = false;

    private class ActivityHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 43:

                    Bundle bundle = msg.getData();
                    int result = bundle.getInt("result",0);
                    textViewResult.setText(String.valueOf(result));
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }

    ActivityHandler activityHandler = new ActivityHandler();


    Messenger mActivityMessenger = new Messenger(activityHandler);


    Messenger mServiceMessenger = null;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            isBind = true;
            mServiceMessenger = new Messenger(binder);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
        editTextValue1 = (EditText) findViewById(R.id.editTextValue1);
        editTextValue2 = (EditText) findViewById(R.id.editTextValue2);


    }

    public void onAddButton(View view) {

        if (isBind){

            Message messageToService = Message.obtain(null,43);

            Bundle bundle = new Bundle();
            bundle.putInt("value1",Integer.valueOf(editTextValue1.getText().toString()));
            bundle.putInt("value2",Integer.valueOf(editTextValue2.getText().toString()));
            bundle.putParcelable("activityMessenger",mActivityMessenger);
            messageToService.setData(bundle);

            try {
                mServiceMessenger.send(messageToService);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Please Bind The Service First!",Toast.LENGTH_SHORT).show();
        }

    }

    public void onBindButton(View view) {

        Intent bindIntent = new Intent(MainActivity.this,MyService.class);
        bindService(bindIntent,serviceConnection,BIND_AUTO_CREATE);

    }

    public void onUnbindButton(View view) {
        if (isBind){
            unbindService(serviceConnection);
            Toast.makeText(getApplicationContext(),"Service was unbinded !!!",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"There is no connection to the service !!",Toast.LENGTH_SHORT).show();
        }
    }
}
